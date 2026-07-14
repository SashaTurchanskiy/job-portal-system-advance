package com.zosh.job.repository;

import com.zosh.job.model.Job;
import com.zosh.job.payload.JobSearchRequest;
import domain.JobStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class JobSpecification {

    private JobSpecification(){}

    public static Specification<Job> build(JobSearchRequest request){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("active")));

            JobStatus status = request.getStatus() != null ? request.getStatus() : JobStatus.OPEN;
            predicates.add(cb.equal(root.get("status"), status));

            if (request.getJobType() != null){
                predicates.add(cb.equal(root.get("jobType"), request.getJobType()));
            }

            if (request.getWorkMode() != null){
                predicates.add(cb.equal(root.get("workMode"), request.getWorkMode()));
            }

            if (request.getExperienceLevel() != null){
                predicates.add(cb.equal(root.get("experienceLevel"), request.getExperienceLevel()));
            }

            if (request.getCompanyId() != null){
                predicates.add(cb.equal(root.get("companyId"), request.getCompanyId()));
            }

            if (request.getCategoryId() != null){
                predicates.add(cb.equal(root.get("category").get("id"), request.getCategoryId()));
            }

            if (request.getLocation() != null && !request.getLocation().isBlank()){
                String like = "%" + request.getLocation().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("location").get("city")), like),
                        cb.like(cb.lower(root.get("location").get("state")), like),
                        cb.like(cb.lower(root.get("location").get("country")), like)
                ));
            }

            if (request.getMinSalary() != null){
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("salaryRange").get("minSalary"),
                        new BigDecimal(request.getMinSalary())
                ));
            }

            if (request.getMaxSalary() != null){
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("salaryRange").get("maxSalary"),
                        new BigDecimal(request.getMaxSalary())
                ));
            }

            if (request.getMinOpenings() != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("openings"), request.getMinOpenings()));
            }

            if (request.getMaxOpenings() != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("openings"), request.getMaxOpenings()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
