package com.hireflow.hireflow.controller;

import com.hireflow.hireflow.dto.ApplicantRequest;
import com.hireflow.hireflow.entity.Applicant;
import com.hireflow.hireflow.service.ApplicantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applicants")
@CrossOrigin(origins = "*")
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<Applicant> createApplicant(
            @RequestBody ApplicantRequest request) {

        Applicant applicant = new Applicant();

        applicant.setName(request.getName());
        applicant.setEmail(request.getEmail());
        applicant.setPhone(request.getPhone());
        applicant.setResumeUrl(request.getResumeUrl());
        applicant.setStatus(request.getStatus());

        Applicant createdApplicant =
                applicantService.createApplicant(
                        applicant,
                        request.getJobOpeningId()
                );

        return new ResponseEntity<>(
                createdApplicant,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        return ResponseEntity.ok(
                applicantService.getAllApplicants()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getApplicantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                applicantService.getApplicantById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Applicant> updateApplicant(
            @PathVariable Long id,
            @RequestBody Applicant applicant) {

        return ResponseEntity.ok(
                applicantService.updateApplicant(
                        id,
                        applicant
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicant(
            @PathVariable Long id) {

        applicantService.deleteApplicant(id);

        return ResponseEntity.noContent().build();
    }
}