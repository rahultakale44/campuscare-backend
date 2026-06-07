package com.rahul.campuscare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahul.campuscare.entity.Complaint;
import com.rahul.campuscare.entity.ComplaintStatus;
import com.rahul.campuscare.entity.User;

@Repository
public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {

    List<Complaint> findByUser(User user);

    long countByStatus(ComplaintStatus status);
}