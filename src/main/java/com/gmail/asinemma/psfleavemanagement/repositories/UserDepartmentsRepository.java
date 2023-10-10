package com.gmail.asinemma.psfleavemanagement.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmail.asinemma.psfleavemanagement.entities.UserDepartments;

@Repository
public interface UserDepartmentsRepository extends JpaRepository<UserDepartments, UUID> {

    UserDepartments findByName(String name);
}
