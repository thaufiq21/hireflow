package com.hireflow.hireflow.service;

import com.hireflow.hireflow.entity.Applicant;
import com.hireflow.hireflow.entity.Interview;
import com.hireflow.hireflow.entity.InterviewPanel;
import com.hireflow.hireflow.entity.JobOpening;
import com.hireflow.hireflow.repository.ApplicantRepository;
import com.hireflow.hireflow.repository.InterviewPanelRepository;
import com.hireflow.hireflow.repository.InterviewRepository;
import com.hireflow.hireflow.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicantRepository applicantRepository;
    private final JobOpeningRepository jobOpeningRepository;
    private final InterviewPanelRepository interviewPanelRepository;

    public InterviewService(
            InterviewRepository interviewRepository,
            ApplicantRepository applicantRepository,
            JobOpeningRepository jobOpeningRepository,
            InterviewPanelRepository interviewPanelRepository) {

        this.interviewRepository = interviewRepository;
        this.applicantRepository = applicantRepository;
        this.jobOpeningRepository = jobOpeningRepository;
        this.interviewPanelRepository = interviewPanelRepository;
    }

    // Create a new interview
    public Interview createInterview(
            Interview interview,
            Long applicantId,
            Long jobOpeningId,
            List<Long> panelMemberIds) {

        // Check time
        if (interview.getStartTime() == null ||
                interview.getEndTime() == null) {

            throw new RuntimeException(
                    "Interview start time and end time are required"
            );
        }

        if (!interview.getStartTime()
                .isBefore(interview.getEndTime())) {

            throw new RuntimeException(
                    "Start time must be before end time"
            );
        }

        // Find applicant
        Applicant applicant = applicantRepository
                .findById(applicantId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Applicant not found with ID: "
                                        + applicantId));

        // Find job opening
        JobOpening jobOpening = jobOpeningRepository
                .findById(jobOpeningId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job opening not found with ID: "
                                        + jobOpeningId));

        // Find panel members
        List<InterviewPanel> panelMembers =
                interviewPanelRepository.findAllById(panelMemberIds);

        if (panelMembers.size() != panelMemberIds.size()) {
            throw new RuntimeException(
                    "One or more interview panel members were not found"
            );
        }

        // Check for interviewer conflicts
        List<Interview> existingInterviews =
                interviewRepository.findAll();

        for (Interview existingInterview : existingInterviews) {

            // Only check interviews on the same date
            if (!existingInterview.getInterviewDate()
                    .equals(interview.getInterviewDate())) {
                continue;
            }

            // Check time overlap
            if (!hasTimeOverlap(
                    interview.getStartTime(),
                    interview.getEndTime(),
                    existingInterview.getStartTime(),
                    existingInterview.getEndTime())) {

                continue;
            }

            // Check panel member overlap
            for (InterviewPanel newPanelMember : panelMembers) {

                for (InterviewPanel existingPanelMember :
                        existingInterview.getPanelMembers()) {

                    if (newPanelMember.getId()
                            .equals(existingPanelMember.getId())) {

                        throw new RuntimeException(
                                "Interview conflict: panel member "
                                        + newPanelMember.getName()
                                        + " is already booked during this time"
                        );
                    }
                }
            }
        }

        // Set relationships
        interview.setApplicant(applicant);
        interview.setJobOpening(jobOpening);
        interview.setPanelMembers(panelMembers);

        // Set default status
        if (interview.getStatus() == null ||
                interview.getStatus().isBlank()) {

            interview.setStatus("SCHEDULED");
        }

        // Save interview
        return interviewRepository.save(interview);
    }

    // Check whether two time ranges overlap
    private boolean hasTimeOverlap(
            LocalTime start1,
            LocalTime end1,
            LocalTime start2,
            LocalTime end2) {

        return start1.isBefore(end2)
                && start2.isBefore(end1);
    }

    // Get all interviews
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    // Get interview by ID
    public Interview getInterviewById(Long id) {

        return interviewRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Interview not found with ID: " + id
                        ));
    }

    // Update interview
    public Interview updateInterview(
            Long id,
            Interview updatedInterview) {

        Interview existingInterview =
                getInterviewById(id);

        existingInterview.setInterviewDate(
                updatedInterview.getInterviewDate());

        existingInterview.setStartTime(
                updatedInterview.getStartTime());

        existingInterview.setEndTime(
                updatedInterview.getEndTime());

        existingInterview.setStatus(
                updatedInterview.getStatus());

        return interviewRepository.save(existingInterview);
    }

    // Cancel an interview
    public Interview cancelInterview(Long id) {

        Interview interview = getInterviewById(id);

        interview.setStatus("CANCELLED");

        return interviewRepository.save(interview);
    }

    // Delete an interview
    public void deleteInterview(Long id) {

        if (!interviewRepository.existsById(id)) {

            throw new RuntimeException(
                    "Interview not found with ID: " + id
            );
        }

        interviewRepository.deleteById(id);
    }
}