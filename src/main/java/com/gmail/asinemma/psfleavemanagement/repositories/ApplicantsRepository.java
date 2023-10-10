package com.gmail.asinemma.psfleavemanagement.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmail.asinemma.psfleavemanagement.entities.Applicants;
import java.util.List;
// import com.gmail.asinemma.psfleavemanagement.entities.enums.Departments;

@Repository
public interface ApplicantsRepository extends JpaRepository<Applicants, UUID> {

    Applicants findByEmail(String email);

    Applicants findByTelephone(String telephone);

    List<Applicants> findByDepartmentName(String name);

    // List<Applicants> findAll();

}
