package com.zosh.job.service;

import com.zosh.job.model.JobCategory;
import com.zosh.job.payload.JobCategoryRequest;
import dto.response.JobCategoryResponse;

import java.util.List;

public interface JobCategoryService {

    JobCategoryResponse createCategory(JobCategoryRequest request) throws Exception;

    JobCategoryResponse getCategoryById(Long id) throws Exception;

    List<JobCategoryResponse> getAllCategories();

    JobCategoryResponse updateCategory(Long id, JobCategoryRequest request) throws Exception;

    void deleteCategory(Long id) throws Exception;

    JobCategory getCategoryEntityById(Long id) throws Exception;
}
