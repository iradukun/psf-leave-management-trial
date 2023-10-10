package com.gmail.asinemma.psfleavemanagement.entities;

import java.io.Serializable;

import com.gmail.asinemma.psfleavemanagement.entities.enums.LeaveTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_applications")
public class LeaveApplications extends Base implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LeaveTypes type;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "proposed_start_date", nullable = false)
    private String proposedStartDate;

    @Column(name = "proposed_end_date", nullable = false)
    private String proposedEndDate;

    @Column(name = "requested_days", nullable = false)
    private int requestedDays;

    public int getRequestedDays() {
        return requestedDays;
    }

    public void setRequestedDays(int requestedDays) {
        this.requestedDays = requestedDays;
    }

    @JoinColumn(name = "applicant_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Applicants applicant;

    public LeaveTypes getType() {
        return type;
    }

    public void setType(LeaveTypes type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProposedStartDate() {
        return proposedStartDate;
    }

    public void setProposedStartDate(String proposedStartDate) {
        this.proposedStartDate = proposedStartDate;
    }

    public String getProposedEndDate() {
        return proposedEndDate;
    }

    public void setProposedEndDate(String proposedEndDate) {
        this.proposedEndDate = proposedEndDate;
    }

    public Applicants getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicants applicant) {
        this.applicant = applicant;
    }

}
