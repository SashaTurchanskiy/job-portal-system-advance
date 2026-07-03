package com.zosh.job.service;

import dto.request.CompanyRequest;
import dto.response.CompanyResponse;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest);
}
