package com.gmail.asinemma.psfleavemanagement.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmail.asinemma.psfleavemanagement.entities.Leaves;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves, UUID> {

    Leaves findByApplicationApplicantEmailAndCreatedAt(String email, Date createdAt);

    Leaves findByApplicationApplicantTelephoneAndCreatedAt(String telephone, Date createdAt);

    List<Leaves> findByApplicationApplicantEmail(String email);

    List<Leaves> findByApplicationApplicantTelephone(String telephone);

    // List<Leaves> findByApplicationApplicantDepartment(String department);

    // List<Leaves> findByApplicantStatus(String status);

    // List<Leaves> findByAll();

}
