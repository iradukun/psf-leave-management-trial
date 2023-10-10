package com.gmail.asinemma.psfleavemanagement.dtos;

public record CreateApplicantDto(String firstName, String lastName, String telephone, String email, String title,
                DepartmentDto department, int authorizedDays) {

}
