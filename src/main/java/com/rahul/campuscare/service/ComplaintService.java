package com.rahul.campuscare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rahul.campuscare.dto.AdminStatsResponse;
import com.rahul.campuscare.dto.CreateComplaintRequest;
import com.rahul.campuscare.entity.Complaint;
import com.rahul.campuscare.entity.ComplaintStatus;
import com.rahul.campuscare.entity.User;
import com.rahul.campuscare.repository.ComplaintRepository;
import com.rahul.campuscare.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public Complaint createComplaint(CreateComplaintRequest request) {

        User user = getLoggedInUser();

        Complaint complaint = Complaint.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .status(ComplaintStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint Not Found"));
    }

    public List<Complaint> getMyComplaints() {

        User user = getLoggedInUser();

        return complaintRepository.findByUser(user);
    }

    public Complaint updateStatus(Long id, ComplaintStatus status, String adminNote) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint Not Found"));

        complaint.setStatus(status);

        if (adminNote != null) {
            complaint.setAdminNote(adminNote);
        }

        return complaintRepository.save(complaint);
    }

    public AdminStatsResponse getAdminStats() {

        long total = complaintRepository.count();

        long pending = complaintRepository.countByStatus(ComplaintStatus.PENDING);

        long inProgress = complaintRepository.countByStatus(ComplaintStatus.IN_PROGRESS);

        long resolved = complaintRepository.countByStatus(ComplaintStatus.RESOLVED);

        long rejected = complaintRepository.countByStatus(ComplaintStatus.REJECTED);

        return new AdminStatsResponse(
                total,
                pending,
                inProgress,
                resolved,
                rejected
        );
    }

    private User getLoggedInUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}