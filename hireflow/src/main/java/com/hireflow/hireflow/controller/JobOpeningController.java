package com.hireflow.hireflow.controller;

import com.hireflow.hireflow.entity.JobOpening;
import com.hireflow.hireflow.service.JobOpeningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobOpeningController {

    private final JobOpeningService jobOpeningService;

    public JobOpeningController(JobOpeningService jobOpeningService) {
        this.jobOpeningService = jobOpeningService;
    }

    // Create a new job opening
    @PostMapping
    public ResponseEntity<JobOpening> createJobOpening(
            @RequestBody JobOpening jobOpening) {

        JobOpening createdJob =
                jobOpeningService.createJobOpening(jobOpening);

        return new ResponseEntity<>(
                createdJob,
                HttpStatus.CREATED
        );
    }

    // Get all job openings
    @GetMapping
    public ResponseEntity<List<JobOpening>> getAllJobOpenings() {

        return ResponseEntity.ok(
                jobOpeningService.getAllJobOpenings()
        );
    }

    // Get job opening by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobOpening> getJobOpeningById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                jobOpeningService.getJobOpeningById(id)
        );
    }

    // Update job opening
    @PutMapping("/{id}")
    public ResponseEntity<JobOpening> updateJobOpening(
            @PathVariable Long id,
            @RequestBody JobOpening jobOpening) {

        return ResponseEntity.ok(
                jobOpeningService.updateJobOpening(
                        id,
                        jobOpening
                )
        );
    }

    // Delete job opening
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOpening(
            @PathVariable Long id) {

        jobOpeningService.deleteJobOpening(id);

        return ResponseEntity.noContent().build();
    }
}