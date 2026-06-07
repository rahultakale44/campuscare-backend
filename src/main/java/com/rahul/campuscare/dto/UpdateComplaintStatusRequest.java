package com.rahul.campuscare.dto;

import com.rahul.campuscare.entity.ComplaintStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateComplaintStatusRequest {

    private ComplaintStatus status;
    private String adminNote;
}