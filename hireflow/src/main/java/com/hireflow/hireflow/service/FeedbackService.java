package com.hireflow.hireflow.service;

import com.hireflow.hireflow.entity.Feedback;
import com.hireflow.hireflow.entity.Interview;
import com.hireflow.hireflow.entity.InterviewPanel;
import com.hireflow.hireflow.repository.FeedbackRepository;
import com.hireflow.hireflow.repository.InterviewPanelRepository;
import com.hireflow.hireflow.repository.InterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewPanelRepository interviewPanelRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            InterviewRepository interviewRepository,
            InterviewPanelRepository interviewPanelRepository) {

        this.feedbackRepository = feedbackRepository;
        this.interviewRepository = interviewRepository;
        this.interviewPanelRepository = interviewPanelRepository;
    }

    // Submit feedback for an interview
    public Feedback createFeedback(
            Feedback feedback,
            Long interviewId,
            Long interviewerId) {

        // Find interview
        Interview interview = interviewRepository
                .findById(interviewId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Interview not found with ID: "
                                        + interviewId));

        // Find interviewer
        InterviewPanel interviewer = interviewPanelRepository
                .findById(interviewerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Interviewer not found with ID: "
                                        + interviewerId));

        // Check whether interviewer is assigned to this interview
        boolean assigned = interview.getPanelMembers()
                .stream()
                .anyMatch(panel ->
                        panel.getId().equals(interviewer.getId()));

        if (!assigned) {
            throw new RuntimeException(
                    "This interviewer is not assigned to the interview"
            );
        }

        // Validate rating
        if (feedback.getRating() != null) {

            if (feedback.getRating() < 1 ||
                    feedback.getRating() > 5) {

                throw new RuntimeException(
                        "Rating must be between 1 and 5"
                );
            }
        }

        // Validate recommendation
        if (feedback.getRecommendation() != null) {

            String recommendation =
                    feedback.getRecommendation().toUpperCase();

            if (!recommendation.equals("POSITIVE")
                    && !recommendation.equals("NEGATIVE")
                    && !recommendation.equals("PENDING")) {

                throw new RuntimeException(
                        "Recommendation must be POSITIVE, NEGATIVE, or PENDING"
                );
            }

            feedback.setRecommendation(recommendation);
        }

        // Set relationships
        feedback.setInterview(interview);
        feedback.setInterviewer(interviewer);

        // Save feedback
        return feedbackRepository.save(feedback);
    }

    // Get all feedback
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    // Get feedback by ID
    public Feedback getFeedbackById(Long id) {

        return feedbackRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Feedback not found with ID: " + id
                        ));
    }

    // Update feedback
    public Feedback updateFeedback(
            Long id,
            Feedback updatedFeedback) {

        Feedback existingFeedback =
                getFeedbackById(id);

        existingFeedback.setComments(
                updatedFeedback.getComments());

        existingFeedback.setRating(
                updatedFeedback.getRating());

        if (updatedFeedback.getRecommendation() != null) {

            String recommendation =
                    updatedFeedback
                            .getRecommendation()
                            .toUpperCase();

            if (!recommendation.equals("POSITIVE")
                    && !recommendation.equals("NEGATIVE")
                    && !recommendation.equals("PENDING")) {

                throw new RuntimeException(
                        "Recommendation must be POSITIVE, NEGATIVE, or PENDING"
                );
            }

            existingFeedback.setRecommendation(
                    recommendation);
        }

        return feedbackRepository.save(existingFeedback);
    }

    // Delete feedback
    public void deleteFeedback(Long id) {

        if (!feedbackRepository.existsById(id)) {

            throw new RuntimeException(
                    "Feedback not found with ID: " + id
            );
        }

        feedbackRepository.deleteById(id);
    }
}