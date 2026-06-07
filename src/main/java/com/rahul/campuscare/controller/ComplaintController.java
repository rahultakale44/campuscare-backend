package com.rahul.campuscare.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.campuscare.dto.CreateComplaintRequest;
import com.rahul.campuscare.dto.UpdateComplaintStatusRequest;
import com.rahul.campuscare.entity.Complaint;
import com.rahul.campuscare.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    public Complaint createComplaint(@RequestBody CreateComplaintRequest request) {
        return complaintService.createComplaint(request);
    }

    @GetMapping("/my")
    public List<Complaint> getMyComplaints() {
        return complaintService.getMyComplaints();
    }

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("/{id}")
    public Complaint getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id);
    }

    @PutMapping("/{id}/status")
    public Complaint updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateComplaintStatusRequest request) {

        return complaintService.updateStatus(
                id,
                request.getStatus(),
                request.getAdminNote()
        );
    }
}