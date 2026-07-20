package com.zosh.job.mapper;

import com.zosh.job.model.JobCategory;
import com.zosh.job.payload.JobCategoryRequest;
import dto.response.JobCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobCategoryMapper {

    JobCategoryMapper INSTANCE = Mappers.getMapper(JobCategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", source = "slug")
    @Mapping(target = "parent", expression = "java(toParentEntity(request.getParentId()))")
    @Mapping(target = "subCategories", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    JobCategory toEntity(JobCategoryRequest request);

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    @Mapping(target = "subCategories", expression = "java(toResponseList(category.getSubCategories()))")
    JobCategoryResponse toResponse(JobCategory category);

    List<JobCategoryResponse> toResponseList(List<JobCategory> categories);

    default JobCategory toParentEntity(Long parentId) {
        if (parentId == null) {
            return null;
        }
        JobCategory parent = new JobCategory();
        parent.setId(parentId);
        return parent;
    }
}
