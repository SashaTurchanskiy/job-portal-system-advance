package com.zosh.job.model;

import domain.CompanySize;
import domain.CompanyStatus;
import domain.CompanyType;
import domain.IndustryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies")
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String slug;

    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;
    private String websiteUrl;

    private String email;
    private String phone;

    private Integer foundedYear;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Enumerated(EnumType.STRING)
    private IndustryType industryType;

    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

    @Column(unique = true)
    private String registrationNumber;

    @Column(unique = true, nullable = false)
    private Long ownerId;

    @ElementCollection
    private List<SocialLink> socialLinks = new ArrayList<>();

    private Boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;


}
