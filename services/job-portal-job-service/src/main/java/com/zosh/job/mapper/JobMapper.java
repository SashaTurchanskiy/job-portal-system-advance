package com.zosh.job.mapper;

import com.zosh.job.model.Job;
import com.zosh.job.model.JobCategory;
import com.zosh.job.model.JobSkill;
import com.zosh.job.model.JobTag;
import dto.request.JobRequest;
import dto.response.JobResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface JobMapper {

    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", expression = "java(toCategory(jobRequest.getCategoryId()))")
    @Mapping(target = "skills", expression = "java(toSkills(jobRequest.getSkillIds()))")
    @Mapping(target = "tags", expression = "java(toTags(jobRequest.getTagIds()))")
    @Mapping(target = "location.city", source = "city")
    @Mapping(target = "location.address", source = "address")
    @Mapping(target = "location.country", source = "country")
    @Mapping(target = "location.state", source = "state")
    @Mapping(target = "location.zipCode", source = "zipCode")
    @Mapping(target = "salaryRange.minSalary", source = "minSalary")
    @Mapping(target = "salaryRange.maxSalary", source = "maxSalary")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "closedAt", ignore = true)
    Job toEntity(JobRequest jobRequest);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "city", source = "location.city")
    @Mapping(target = "address", source = "location.address")
    @Mapping(target = "country", source = "location.country")
    @Mapping(target = "state", source = "location.state")
    @Mapping(target = "zipCode", source = "location.zipCode")
    @Mapping(target = "minSalary", expression = "java(toSalaryString(job.getSalaryRange(), true))")
    @Mapping(target = "maxSalary", expression = "java(toSalaryString(job.getSalaryRange(), false))")
    JobResponse toResponse(Job job);

    default JobCategory toCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        JobCategory category = new JobCategory();
        category.setId(categoryId);
        return category;
    }

    default Set<JobTag> toTags(Set<Long> tagIds) {
        if (tagIds == null) {
            return null;
        }
        return tagIds.stream()
                .map(id -> {
                    JobTag tag = new JobTag();
                    tag.setId(id);
                    return tag;
                })
                .collect(Collectors.toSet());
    }

    @Named("toSalaryString")
    default String toSalaryString(com.zosh.job.model.embeddable.SalaryRange salaryRange, boolean isMin) {
        if (salaryRange == null) {
            return null;
        }
        BigDecimal value = isMin ? salaryRange.getMinSalary() : salaryRange.getMaxSalary();
        return value != null ? value.toString() : null;
    }
}
