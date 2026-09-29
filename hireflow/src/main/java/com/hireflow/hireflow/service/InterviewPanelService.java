package com.hireflow.hireflow.service;

import com.hireflow.hireflow.entity.InterviewPanel;
import com.hireflow.hireflow.repository.InterviewPanelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewPanelService {

    private final InterviewPanelRepository interviewPanelRepository;

    public InterviewPanelService(
            InterviewPanelRepository interviewPanelRepository) {
        this.interviewPanelRepository = interviewPanelRepository;
    }

    // Create a new panel member
    public InterviewPanel createPanelMember(InterviewPanel panelMember) {
        return interviewPanelRepository.save(panelMember);
    }

    // Get all panel members
    public List<InterviewPanel> getAllPanelMembers() {
        return interviewPanelRepository.findAll();
    }

    // Get panel member by ID
    public InterviewPanel getPanelMemberById(Long id) {
        return interviewPanelRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Interview panel member not found with ID: " + id
                        ));
    }

    // Update panel member
    public InterviewPanel updatePanelMember(
            Long id,
            InterviewPanel updatedPanelMember) {

        InterviewPanel existingPanelMember =
                getPanelMemberById(id);

        existingPanelMember.setName(
                updatedPanelMember.getName());

        existingPanelMember.setEmail(
                updatedPanelMember.getEmail());

        existingPanelMember.setDepartment(
                updatedPanelMember.getDepartment());

        existingPanelMember.setRole(
                updatedPanelMember.getRole());

        return interviewPanelRepository.save(
                existingPanelMember);
    }

    // Delete panel member
    public void deletePanelMember(Long id) {

        if (!interviewPanelRepository.existsById(id)) {
            throw new RuntimeException(
                    "Interview panel member not found with ID: " + id
            );
        }

        interviewPanelRepository.deleteById(id);
    }
}