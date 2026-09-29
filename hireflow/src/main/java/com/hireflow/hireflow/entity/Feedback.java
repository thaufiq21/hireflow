package com.hireflow.hireflow.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Interview for which feedback is given
    @ManyToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    // Interviewer who gives the feedback
    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private InterviewPanel interviewer;

    // Feedback comments
    @Column(length = 2000)
    private String comments;

    // Rating given by interviewer
    private Integer rating;

    // Recommendation: POSITIVE, NEGATIVE, PENDING
    private String recommendation;

    // Date and time when feedback was submitted
    private LocalDateTime submittedAt;

    // Default constructor
    public Feedback() {
        this.submittedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public InterviewPanel getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(InterviewPanel interviewer) {
        this.interviewer = interviewer;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}