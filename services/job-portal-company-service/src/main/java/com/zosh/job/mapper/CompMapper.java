package com.zosh.job.mapper;

import com.zosh.job.model.Company;
import dto.request.CompanyRequest;
import dto.response.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompMapper {


    @Mapping(target = "isActive", source = "active")
    @Mapping(target = "websiteUrl", source = "website")
    CompanyResponse toResponse(Company company);


    @Mapping(target = "website", source = "websiteUrl")
    Company toEntity(CompanyRequest companyRequest);

}
