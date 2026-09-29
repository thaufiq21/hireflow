package com.hireflow.hireflow.controller;

import com.hireflow.hireflow.dto.InterviewRequest;
import com.hireflow.hireflow.entity.Interview;
import com.hireflow.hireflow.service.InterviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "*")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // Create and schedule an interview
    @PostMapping
    public ResponseEntity<Interview> createInterview(
            @RequestBody InterviewRequest request) {

        Interview interview = new Interview();

        interview.setInterviewDate(request.getInterviewDate());
        interview.setStartTime(request.getStartTime());
        interview.setEndTime(request.getEndTime());
        interview.setStatus(request.getStatus());

        Interview createdInterview =
                interviewService.createInterview(
                        interview,
                        request.getApplicantId(),
                        request.getJobOpeningId(),
                        request.getPanelMemberIds()
                );

        return new ResponseEntity<>(
                createdInterview,
                HttpStatus.CREATED
        );
    }

    // Get all interviews
    @GetMapping
    public ResponseEntity<List<Interview>> getAllInterviews() {

        return ResponseEntity.ok(
                interviewService.getAllInterviews()
        );
    }

    // Get interview by ID
    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterviewById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                interviewService.getInterviewById(id)
        );
    }

    // Update interview
    @PutMapping("/{id}")
    public ResponseEntity<Interview> updateInterview(
            @PathVariable Long id,
            @RequestBody Interview interview) {

        return ResponseEntity.ok(
                interviewService.updateInterview(
                        id,
                        interview
                )
        );
    }

    // Cancel interview
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Interview> cancelInterview(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                interviewService.cancelInterview(id)
        );
    }

    // Delete interview
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(
            @PathVariable Long id) {

        interviewService.deleteInterview(id);

        return ResponseEntity.noContent().build();
    }
}