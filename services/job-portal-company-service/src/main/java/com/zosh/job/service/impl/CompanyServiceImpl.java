package com.zosh.job.service.impl;

import com.zosh.job.mapper.CompanyMapper;
import com.zosh.job.model.Company;
import com.zosh.job.model.SocialLink;
import com.zosh.job.repository.CompanyRepository;
import com.zosh.job.service.CompanyService;
import domain.CompanyStatus;
import domain.CompanyType;
import domain.IndustryType;
import dto.request.CompanyRequest;
import dto.response.CompanyResponse;
import dto.response.SocialLinkResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest) throws Exception {
        if (companyRepository.existsByOwnerId(ownerId)){
            throw new Exception("You already have a company registered. " +
                    "Only one company per account is allowed.");
        }
        if (companyRepository.existsByName(companyRequest.getName())){
            throw new Exception("Company name already exists. Please choose a different name.");
        }
        if (companyRequest.getRegistrationNumber() != null && companyRepository.existsByRegistrationNumber(companyRequest.getRegistrationNumber())){
            throw new Exception("Company registration number already exists. Please provide a different registration number.");
        }

        String slug = generateUniqueSlug(companyRequest.getName());

        Company company = Company.builder()
                .name(companyRequest.getName())
                .slug(slug)
                .tagline(companyRequest.getTagline())
                .description(companyRequest.getDescription())
                .logoUrl(companyRequest.getLogoUrl())
                .coverImageUrl(companyRequest.getCoverImageUrl())
                .websiteUrl(companyRequest.getWebsite())
                .email(companyRequest.getEmail())
                .phone(companyRequest.getPhone())
                .foundedYear(companyRequest.getFoundedYear())
                .companySize(companyRequest.getCompanySize())
                .companyType(companyRequest.getCompanyType())
                .industryType(companyRequest.getIndustryType())
                .registrationNumber(companyRequest.getRegistrationNumber())
                .ownerId(ownerId)
                .socialLinks(mapSocialLinks(companyRequest.getSocialLinks()))
                .build();

        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
        if (socialLinks == null && socialLinks.isEmpty()){
            return new ArrayList<SocialLink>();
        }
        return socialLinks.stream().map(e -> SocialLink.builder()
                        .platform(e.getPlatform())
                        .url(e.getUrl())
                        .build())
                .collect(Collectors.toList()
                );

    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replace("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]", "-");

        if (!companyRepository.existsBySlug(base)){
            return base;
        }
        int counter = 1;
        while (companyRepository.existsBySlug(base + "-" + counter)){
            counter++;
        }
        return base + "-" + counter;

    }

    @Override
    public CompanyResponse getCompanyById(Long id) throws Exception {
        Company company = companyRepository.findById(id)
                .orElseThrow(()-> new Exception("Company not found with id: " + id));
        return CompanyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) throws Exception {
        Company company = companyRepository.findByOwnerId(ownerId)
                .orElseThrow(()-> new Exception("Company not found for owner with id: " + ownerId));
        return CompanyMapper.toResponse(company);
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {
        return companyRepository.findAll()
                .stream()
                .map(CompanyMapper::toResponse)
                .toList();
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest request) {
        return null;
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) {
        return null;
    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) {
        return null;
    }

    @Override
    public void deleteCompany(Long companyId) {

    }

    @Override
    public Company getCompanyEntityById(Long id) throws Exception {
        return companyRepository.findById(id)
                .orElseThrow(()-> new Exception("Company not found with id: " + id));
    }
}
