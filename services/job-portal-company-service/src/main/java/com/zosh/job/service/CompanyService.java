package com.zosh.job.service;

import com.zosh.job.model.Company;
import domain.CompanyStatus;
import domain.CompanyType;
import domain.IndustryType;
import dto.request.CompanyRequest;
import dto.response.CompanyResponse;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest);

    CompanyResponse getCompanyById(Long id);

    CompanyResponse getMyCompany(Long ownerId);

    List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus);

    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest request);

    CompanyResponse verifyCompany(Long companyId);

    CompanyResponse deactivateCompany(Long companyId);

    void deleteCompany(Long companyId);

    Company getCompanyEntityById(Long id);
}
