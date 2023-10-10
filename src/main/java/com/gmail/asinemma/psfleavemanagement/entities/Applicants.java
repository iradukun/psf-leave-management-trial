package com.gmail.asinemma.psfleavemanagement.entities;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "applicants")
public class Applicants extends Base implements Serializable {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "telephone", nullable = false, unique = true)
    private String telephone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "title", nullable = false)
    private String title;

    @JoinColumn(name = "department_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private UserDepartments department;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp not null default CURRENT_TIMESTAMP")
    public Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "timestamp not null default CURRENT_TIMESTAMP")
    public Date updatedAt;

    public UserDepartments getDepartment() {
        return department;
    }

    public void setDepartment(UserDepartments department) {
        this.department = department;
    }

    @Column(name = "authorized_days", nullable = false)
    private int authorizedDays;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorizedDays() {
        return authorizedDays;
    }

    public void setAuthorizedDays(int authorizedDays) {
        this.authorizedDays = authorizedDays;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
