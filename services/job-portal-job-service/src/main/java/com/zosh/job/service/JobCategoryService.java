package com.zosh.job.service;

import com.zosh.job.model.JobCategory;
import com.zosh.job.payload.JobCategoryRequest;
import dto.response.JobCategoryResponse;

import java.util.List;

public interface JobCategoryService {

    JobCategoryResponse createCategory(JobCategoryRequest request);

    JobCategoryResponse getCategoryById(Long id);

    List<JobCategoryResponse> getAllCategories();

    JobCategoryResponse updateCategory(Long id, JobCategoryRequest request);

    void deleteCategory(Long id);

    JobCategory getCategoryEntityById(Long id);
}
