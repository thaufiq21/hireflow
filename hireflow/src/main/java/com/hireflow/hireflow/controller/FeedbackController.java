package com.hireflow.hireflow.controller;

import com.hireflow.hireflow.dto.FeedbackRequest;
import com.hireflow.hireflow.entity.Feedback;
import com.hireflow.hireflow.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Submit feedback
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(
            @RequestBody FeedbackRequest request) {

        Feedback feedback = new Feedback();

        feedback.setComments(request.getComments());
        feedback.setRating(request.getRating());
        feedback.setRecommendation(request.getRecommendation());

        Feedback createdFeedback =
                feedbackService.createFeedback(
                        feedback,
                        request.getInterviewId(),
                        request.getInterviewerId()
                );

        return new ResponseEntity<>(
                createdFeedback,
                HttpStatus.CREATED
        );
    }

    // Get all feedback
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {

        return ResponseEntity.ok(
                feedbackService.getAllFeedback()
        );
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                feedbackService.getFeedbackById(id)
        );
    }

    // Update feedback
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable Long id,
            @RequestBody Feedback feedback) {

        return ResponseEntity.ok(
                feedbackService.updateFeedback(
                        id,
                        feedback
                )
        );
    }

    // Delete feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long id) {

        feedbackService.deleteFeedback(id);

        return ResponseEntity.noContent().build();
    }
}