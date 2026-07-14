package com.zosh.job.service.impl;

import com.zosh.job.mapper.JobMapper;
import com.zosh.job.model.Job;
import com.zosh.job.payload.JobSearchRequest;
import com.zosh.job.repository.JobRepository;
import com.zosh.job.repository.JobSpecification;
import com.zosh.job.service.JobService;
import domain.JobStatus;
import dto.request.JobRequest;
import dto.response.JobResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;


    @Override
    public JobResponse createJob(Long employerId, JobRequest jobRequest) throws Exception {
        Long companyId = 1L;

        Job job = jobMapper.toEntity(jobRequest);

        return jobMapper.toResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse getJobById(Long id) throws Exception {
        Job job = jobRepository.findById(id)
                .orElseThrow(()-> new Exception("could not find job with " + id));
        return jobMapper.toResponse(jobRepository.save(job));
    }

    @Override
    public List<JobResponse> getJobs(JobSearchRequest request) {
        return jobRepository.findAll(JobSpecification.build(request)).stream()
                .map(jobMapper::toResponse)
                .toList();
    }

    @Override
    public List<JobResponse> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId).stream()
                .map(jobMapper::toResponse)
                .toList();
    }

    @Override
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest jobRequest) throws Exception {
        Job existing = jobRepository.findById(jobId)
                .orElseThrow(()-> new Exception("could not find job with " + jobId));

        assertEmployer(existing, employerId);

        Job updated = jobMapper.toEntity(jobRequest);
        updated.setId(existing.getId());
        updated.setCompanyId(existing.getCompanyId());
        updated.setEmployerId(existing.getEmployerId());
        updated.setStatus(existing.getStatus());
        updated.setActive(existing.getActive());
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setPublishedAt(existing.getPublishedAt());
        updated.setClosedAt(existing.getClosedAt());

        return jobMapper.toResponse(jobRepository.save(updated));
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(()-> new Exception("Job not found"));

        assertEmployer(job, employerId);
        if (job.getStatus() == JobStatus.CLOSED || job.getStatus() == JobStatus.EXPIRED){
            throw new Exception("Job is expired");
        }
        job.setStatus(JobStatus.OPEN);
        job.setPublishedAt(LocalDateTime.now());
        job.setActive(true);
        return jobMapper.toResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse closeJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(()-> new Exception("Job not found"));

        assertEmployer(job, employerId);

        job.setStatus(JobStatus.CLOSED);
        job.setClosedAt(LocalDateTime.now());
        job.setActive(false);
        return jobMapper.toResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(()-> new Exception("Job not found"));

        assertEmployer(job, employerId);
        jobRepository.delete(job);
    }

    @Override
    public List<JobResponse> getAllJobsAdmin() {
        return jobRepository.findAll().stream()
                .map(jobMapper::toResponse)
                .toList();
    }

    private void assertEmployer(Job job, Long employerId) throws Exception {
        if (!job.getEmployerId().equals(employerId)){
            throw new Exception("you are not the employer who posted this job");
        }
    }
}
