package com.zosh.job.mapper;

import com.zosh.job.model.Company;
import com.zosh.job.model.SocialLink;
import dto.response.CompanyResponse;
import dto.response.SocialLinkResponse;

import java.util.Collections;
import java.util.List;

public class CompanyMapper {
    public static CompanyResponse toResponse(Company company){
        List<SocialLinkResponse> socialLinks = company.getSocialLinks() == null ? Collections.emptyList()
                :company.getSocialLinks()
                .stream()
                .map(CompanyMapper::toSocialLinkResponse)
                .toList();

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsiteUrl())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .status(company.getStatus())
                .ownerId(company.getOwnerId())
                .active(company.getIsActive())
                .socialLinks(socialLinks)

                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    public static SocialLinkResponse toSocialLinkResponse(SocialLink socialLinks){
        return SocialLinkResponse.builder()
                .platform(socialLinks.getPlatform())
                .url(socialLinks.getUrl())
                .build();
    }
}
