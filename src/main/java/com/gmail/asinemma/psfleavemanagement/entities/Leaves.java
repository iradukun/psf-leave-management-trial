package com.gmail.asinemma.psfleavemanagement.entities;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmail.asinemma.psfleavemanagement.entities.enums.LeaveStatuses;
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
@Table(name = "leaves")
public class Leaves extends Base implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LeaveTypes type;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveStatuses status;

    @JsonIgnore
    @JoinColumn(name = "applicant_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private LeaveApplications application;

    public LeaveTypes getType() {
        return type;
    }

    public void setType(LeaveTypes type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public LeaveStatuses getStatus() {
        return status;
    }

    public void setStatus(LeaveStatuses status) {
        this.status = status;
    }

    public LeaveApplications getApplication() {
        return application;
    }

    public void setApplication(LeaveApplications application) {
        this.application = application;
    }
}
