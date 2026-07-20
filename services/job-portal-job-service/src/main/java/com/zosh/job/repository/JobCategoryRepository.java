package com.zosh.job.repository;

import com.zosh.job.model.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

    boolean existsBySlug(String slug);
    boolean existsByName(String name);
}
