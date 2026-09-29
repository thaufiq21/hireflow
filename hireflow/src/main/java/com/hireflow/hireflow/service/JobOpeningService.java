package com.hireflow.hireflow.service;

import com.hireflow.hireflow.entity.JobOpening;
import com.hireflow.hireflow.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOpeningService {

    private final JobOpeningRepository jobOpeningRepository;

    public JobOpeningService(JobOpeningRepository jobOpeningRepository) {
        this.jobOpeningRepository = jobOpeningRepository;
    }

    // Create a new job opening
    public JobOpening createJobOpening(JobOpening jobOpening) {
        return jobOpeningRepository.save(jobOpening);
    }

    // Get all job openings
    public List<JobOpening> getAllJobOpenings() {
        return jobOpeningRepository.findAll();
    }

    // Get a job opening by ID
    public JobOpening getJobOpeningById(Long id) {
        return jobOpeningRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job opening not found with ID: " + id));
    }

    // Update a job opening
    public JobOpening updateJobOpening(Long id, JobOpening updatedJob) {

        JobOpening existingJob = getJobOpeningById(id);

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setDepartment(updatedJob.getDepartment());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setStatus(updatedJob.getStatus());

        return jobOpeningRepository.save(existingJob);
    }

    // Delete a job opening
    public void deleteJobOpening(Long id) {

        if (!jobOpeningRepository.existsById(id)) {
            throw new RuntimeException(
                    "Job opening not found with ID: " + id
            );
        }

        jobOpeningRepository.deleteById(id);
    }
}