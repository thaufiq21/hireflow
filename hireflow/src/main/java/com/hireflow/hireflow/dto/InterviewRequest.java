package com.hireflow.hireflow.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InterviewRequest {

    private Long applicantId;

    private Long jobOpeningId;

    private List<Long> panelMemberIds;

    private LocalDate interviewDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

    public InterviewRequest() {
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getJobOpeningId() {
        return jobOpeningId;
    }

    public void setJobOpeningId(Long jobOpeningId) {
        this.jobOpeningId = jobOpeningId;
    }

    public List<Long> getPanelMemberIds() {
        return panelMemberIds;
    }

    public void setPanelMemberIds(List<Long> panelMemberIds) {
        this.panelMemberIds = panelMemberIds;
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