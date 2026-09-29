package com.hireflow.hireflow.controller;

import com.hireflow.hireflow.entity.InterviewPanel;
import com.hireflow.hireflow.service.InterviewPanelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/panel-members")
@CrossOrigin(origins = "*")
public class InterviewPanelController {

    private final InterviewPanelService interviewPanelService;

    public InterviewPanelController(
            InterviewPanelService interviewPanelService) {
        this.interviewPanelService = interviewPanelService;
    }

    // Create a new panel member
    @PostMapping
    public ResponseEntity<InterviewPanel> createPanelMember(
            @RequestBody InterviewPanel panelMember) {

        InterviewPanel createdPanelMember =
                interviewPanelService.createPanelMember(panelMember);

        return new ResponseEntity<>(
                createdPanelMember,
                HttpStatus.CREATED
        );
    }

    // Get all panel members
    @GetMapping
    public ResponseEntity<List<InterviewPanel>> getAllPanelMembers() {

        return ResponseEntity.ok(
                interviewPanelService.getAllPanelMembers()
        );
    }

    // Get panel member by ID
    @GetMapping("/{id}")
    public ResponseEntity<InterviewPanel> getPanelMemberById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                interviewPanelService.getPanelMemberById(id)
        );
    }

    // Update panel member
    @PutMapping("/{id}")
    public ResponseEntity<InterviewPanel> updatePanelMember(
            @PathVariable Long id,
            @RequestBody InterviewPanel panelMember) {

        return ResponseEntity.ok(
                interviewPanelService.updatePanelMember(
                        id,
                        panelMember
                )
        );
    }

    // Delete panel member
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanelMember(
            @PathVariable Long id) {

        interviewPanelService.deletePanelMember(id);

        return ResponseEntity.noContent().build();
    }
}