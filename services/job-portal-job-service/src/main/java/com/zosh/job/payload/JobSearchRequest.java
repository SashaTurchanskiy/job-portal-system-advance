package com.zosh.job.payload;

import domain.ExperienceLevel;
import domain.JobStatus;
import domain.JobType;
import domain.WorkMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSearchRequest {

    private String keyword;
    private Long categoryId;
    private List<Long> skillIds;
    private List<Long> tagIds;
    private Long companyId;
    private String location;
    private String minSalary;
    private String maxSalary;
    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;
    private JobStatus status;
    private Integer minOpenings;
    private Integer maxOpenings;
}
