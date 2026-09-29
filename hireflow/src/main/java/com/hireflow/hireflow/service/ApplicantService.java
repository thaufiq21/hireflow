package com.hireflow.hireflow.service;

import com.hireflow.hireflow.entity.Applicant;
import com.hireflow.hireflow.entity.JobOpening;
import com.hireflow.hireflow.repository.ApplicantRepository;
import com.hireflow.hireflow.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final JobOpeningRepository jobOpeningRepository;

    public ApplicantService(
            ApplicantRepository applicantRepository,
            JobOpeningRepository jobOpeningRepository) {

        this.applicantRepository = applicantRepository;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    public Applicant createApplicant(
            Applicant applicant,
            Long jobOpeningId) {

        JobOpening jobOpening = jobOpeningRepository.findById(jobOpeningId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job opening not found with ID: " + jobOpeningId
                        ));

        applicant.setJobOpening(jobOpening);

        return applicantRepository.save(applicant);
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    public Applicant getApplicantById(Long id) {
        return applicantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Applicant not found with ID: " + id
                        ));
    }

    public Applicant updateApplicant(
            Long id,
            Applicant updatedApplicant) {

        Applicant existingApplicant = getApplicantById(id);

        existingApplicant.setName(updatedApplicant.getName());
        existingApplicant.setEmail(updatedApplicant.getEmail());
        existingApplicant.setPhone(updatedApplicant.getPhone());
        existingApplicant.setResumeUrl(updatedApplicant.getResumeUrl());
        existingApplicant.setStatus(updatedApplicant.getStatus());

        if (updatedApplicant.getJobOpening() != null) {
            existingApplicant.setJobOpening(
                    updatedApplicant.getJobOpening()
            );
        }

        return applicantRepository.save(existingApplicant);
    }

    public void deleteApplicant(Long id) {

        if (!applicantRepository.existsById(id)) {
            throw new RuntimeException(
                    "Applicant not found with ID: " + id
            );
        }

        applicantRepository.deleteById(id);
    }
}