package com.zosh.job.service.impl;

import com.zosh.job.model.JobCategory;
import com.zosh.job.payload.JobCategoryRequest;
import com.zosh.job.repository.JobCategoryRepository;
import com.zosh.job.service.JobCategoryService;
import dto.response.JobCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository jobRepo;

    @Override
    public JobCategoryResponse createCategory(JobCategoryRequest request) {
        return null;
    }

    @Override
    public JobCategoryResponse getCategoryById(Long id) {
        return null;
    }

    @Override
    public List<JobCategoryResponse> getAllCategories() {
        return List.of();
    }

    @Override
    public JobCategoryResponse updateCategory(Long id, JobCategoryRequest request) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @Override
    public JobCategory getCategoryEntityById(Long id) {
        return null;
    }
}
