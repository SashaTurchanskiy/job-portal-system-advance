package com.zosh.job.service;

import com.zosh.job.model.Company;
import domain.CompanyStatus;
import domain.CompanyType;
import domain.IndustryType;
import dto.request.CompanyRequest;
import dto.response.CompanyResponse;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest) throws Exception;

    CompanyResponse getCompanyById(Long id) throws Exception;

    CompanyResponse getMyCompany(Long ownerId) throws Exception;

    List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus);

    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest request) throws Exception;

    CompanyResponse verifyCompany(Long companyId) throws Exception;

    CompanyResponse deactivateCompany(Long companyId) throws Exception;

    void deleteCompany(Long id, Long companyId) throws Exception;

    Company getCompanyEntityById(Long id) throws Exception;


}
