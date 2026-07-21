package com.zosh.job.service.impl;

import com.zosh.job.mapper.JobCategoryMapper;
import com.zosh.job.mapper.JobMapper;
import com.zosh.job.model.JobCategory;
import com.zosh.job.payload.JobCategoryRequest;
import com.zosh.job.repository.JobCategoryRepository;
import com.zosh.job.service.JobCategoryService;
import dto.response.JobCategoryResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository jobRepo;
    private final JobMapper jobMapper;
    private final JobCategoryMapper jobCategoryMapper;

    @Override
    public JobCategoryResponse createCategory(JobCategoryRequest request) throws Exception {
        if (jobRepo.existsByName(request.getName())){
            throw new Exception("Job Category with name " + request.getName() + " already exists ");
        }
        JobCategory parent = null;
        if (request.getParentId() != null){
            parent = getCategoryEntityById(request.getParentId());
        }

        String slug = generateUniqueSlug(request.getName());

        JobCategory jobCategory = jobCategoryMapper.toEntity(request);

        return jobCategoryMapper.toResponse(jobRepo.save(jobCategory));
    }

    @Override
    public JobCategoryResponse getCategoryById(Long id) throws Exception {
        JobCategory jobCategory = getCategoryEntityById(id);
        return jobCategoryMapper.toResponse(jobCategory);
    }

    @Override
    public List<JobCategoryResponse> getAllCategories() {
        return jobRepo.findAll()
                .stream()
                .map(jobCategoryMapper::toResponse)
                .toList();
    }

    @Override
    public JobCategoryResponse updateCategory(Long id, JobCategoryRequest request) throws Exception {
        JobCategory jobCategory = getCategoryEntityById(id);

        if (!jobCategory.getName().equals(request.getName()) &&
            jobRepo.existsByName(request.getName())){
                throw new Exception("Job Category with name " + request.getName() + " already exists ");
            }
        JobCategory parent = null;
        if (request.getName() != null){
            if (request.getParentId().equals(id)){
                throw new Exception("A category cannot be its own parent.");
            }
            parent = getCategoryEntityById(request.getParentId());
        }
        jobCategory.setName(request.getName());
        jobCategory.setDescription(request.getDescription());
        jobCategory.setIconUrl(request.getIconUrl());
        jobCategory.setParent(parent);

        return jobCategoryMapper.toResponse(jobRepo.save(jobCategory));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        JobCategory jobCategory = getCategoryEntityById(id);
        jobCategory.setActive(false);
        jobRepo.delete(jobCategory);

    }

    @Override
    public JobCategory getCategoryEntityById(Long id) throws Exception {
        return jobRepo.findById(id)
                .orElseThrow(()-> new Exception("Job Category not found with +" + id));
    }

    private String generateUniqueSlug( String name) {
        String base = name.toLowerCase()
                .replace("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]+", "-");

        if (!jobRepo.existsBySlug(base)){
            return base;
        }
        int counter = 1;
        while (jobRepo.existsBySlug(base + "-" + counter)){
            counter++;
        }
        return base + "-" + counter;
    }
}
