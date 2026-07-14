package com.zosh.job.service;

import com.zosh.job.payload.JobSearchRequest;
import dto.request.JobRequest;
import dto.response.JobResponse;

import java.util.List;

public interface JobService {

    JobResponse createJob(Long employerId, JobRequest jobRequest) throws Exception;

    JobResponse getJobById(Long id) throws Exception;

    List<JobResponse> getJobs(JobSearchRequest request);

    List<JobResponse> getJobsByCompany(Long companyId);

    JobResponse updateJob(Long jobId, Long employerId, JobRequest jobRequest) throws Exception;

    JobResponse publishJob(Long jobId, Long employerId) throws Exception;

    JobResponse closeJob(Long jobId, Long employerId) throws Exception;

    void deleteJob(Long jobId, Long employerId) throws Exception;

    List<JobResponse> getAllJobsAdmin();

}
