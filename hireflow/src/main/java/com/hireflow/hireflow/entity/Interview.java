package com.hireflow.hireflow.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interviews")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Applicant attending the interview
    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    // Job for which the interview is conducted
    @ManyToOne
    @JoinColumn(name = "job_opening_id", nullable = false)
    private JobOpening jobOpening;

    // Interview panel members
    @ManyToMany
    @JoinTable(
            name = "interview_panel_members",
            joinColumns = @JoinColumn(name = "interview_id"),
            inverseJoinColumns = @JoinColumn(name = "panel_member_id")
    )
    private List<InterviewPanel> panelMembers = new ArrayList<>();

    // Interview date
    @Column(nullable = false)
    private LocalDate interviewDate;

    // Interview start time
    @Column(nullable = false)
    private LocalTime startTime;

    // Interview end time
    @Column(nullable = false)
    private LocalTime endTime;

    // Interview status
    @Column(nullable = false)
    private String status;

    // Default constructor
    public Interview() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public JobOpening getJobOpening() {
        return jobOpening;
    }

    public void setJobOpening(JobOpening jobOpening) {
        this.jobOpening = jobOpening;
    }

    public List<InterviewPanel> getPanelMembers() {
        return panelMembers;
    }

    public void setPanelMembers(List<InterviewPanel> panelMembers) {
        this.panelMembers = panelMembers;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}