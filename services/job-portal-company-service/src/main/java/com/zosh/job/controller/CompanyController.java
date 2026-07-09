package com.zosh.job.controller;

import com.zosh.job.service.CompanyService;
import domain.CompanyStatus;
import domain.CompanyType;
import domain.IndustryType;
import dto.request.CompanyRequest;
import dto.response.ApiResponse;
import dto.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<CompanyResponse> addCompany(
            @RequestHeader ("X-User-Id") Long ownerId,
            @RequestBody CompanyRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createCompany(ownerId,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false) CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus companyStatus
            ){
        return ResponseEntity.ok(companyService.getAllCompanies(companyType, industryType, companyStatus));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @RequestHeader ("X-User-Id") Long ownerId,
            @RequestBody CompanyRequest request) throws Exception {
        return ResponseEntity.ok(companyService.updateCompany(id, ownerId, request));
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(companyService.verifyCompany(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(companyService.deactivateCompany(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long id,
            @RequestHeader ("X-User-Id") Long ownerId) throws Exception {

                companyService.deleteCompany(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("company deleted successfully", true));
    }

}
