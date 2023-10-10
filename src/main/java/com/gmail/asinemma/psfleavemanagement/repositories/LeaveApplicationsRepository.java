package com.gmail.asinemma.psfleavemanagement.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmail.asinemma.psfleavemanagement.entities.LeaveApplications;

@Repository
public interface LeaveApplicationsRepository extends JpaRepository<LeaveApplications, UUID> {

    LeaveApplications findByApplicantEmailAndCreatedAt(String email, Date createdAt);

    LeaveApplications findByApplicantTelephoneAndCreatedAt(String telephone, Date createdAt);

    List<LeaveApplications> findByApplicantId(UUID id);

    List<LeaveApplications> findByApplicantDepartmentName(String name);

    List<LeaveApplications> findByStatusAndApplicantDepartmentName(String status, String name);

    List<LeaveApplications> findByStatus(String status);
}
