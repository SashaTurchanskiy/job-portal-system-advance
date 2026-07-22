package com.zosh.job.controller;

import com.zosh.job.payload.JobCategoryRequest;
import com.zosh.job.service.JobCategoryService;
import dto.response.ApiResponse;
import dto.response.JobCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    @PostMapping("/add")
    public ResponseEntity<JobCategoryResponse> addJobCategory(
            @RequestBody @Valid JobCategoryRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobCategoryService.createCategory(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> getById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobCategoryService.getCategoryById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobCategoryResponse>> getAllCategories(){
        return ResponseEntity.ok(jobCategoryService.getAllCategories());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobCategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody JobCategoryRequest request) throws Exception {
        return ResponseEntity.ok(jobCategoryService.updateCategory(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id) throws Exception {
        jobCategoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", true));
    }


}
