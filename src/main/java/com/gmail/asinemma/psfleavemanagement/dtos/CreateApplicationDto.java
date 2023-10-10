package com.gmail.asinemma.psfleavemanagement.dtos;

import com.gmail.asinemma.psfleavemanagement.entities.enums.LeaveTypes;

public record CreateApplicationDto(LeaveTypes type, String reason, String proposedStartDate, String proposedEndDate,
                Integer requestedDays) {

}
