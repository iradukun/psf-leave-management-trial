package com.gmail.asinemma.psfleavemanagement.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.asinemma.psfleavemanagement.dtos.CreateApplicantDto;
import com.gmail.asinemma.psfleavemanagement.dtos.CreateApplicationDto;
// import com.gmail.asinemma.psfleavemanagement.dtos.CreateUserDto;
import com.gmail.asinemma.psfleavemanagement.dtos.LoginDTO;
import com.gmail.asinemma.psfleavemanagement.dtos.UpdateApplicationDto;
import com.gmail.asinemma.psfleavemanagement.entities.Applicants;
import com.gmail.asinemma.psfleavemanagement.entities.LeaveApplications;
import com.gmail.asinemma.psfleavemanagement.entities.Leaves;
import com.gmail.asinemma.psfleavemanagement.entities.UserDepartments;
import com.gmail.asinemma.psfleavemanagement.entities.enums.LeaveStatuses;
import com.gmail.asinemma.psfleavemanagement.repositories.ApplicantsRepository;
import com.gmail.asinemma.psfleavemanagement.repositories.LeaveRepository;
import com.gmail.asinemma.psfleavemanagement.repositories.LeaveApplicationsRepository;
import com.gmail.asinemma.psfleavemanagement.repositories.UserDepartmentsRepository;
import com.gmail.asinemma.psfleavemanagement.utils.RemoteService;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin
@RestController
@RequestMapping("psf/leaves")
public class AppController extends RemoteService implements ApplicationRunner {

    @Autowired
    private ApplicantsRepository applicantsRepository;

    @Autowired
    private LeaveApplicationsRepository applicationsRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserDepartmentsRepository departmentsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.seedDepartments();
    }

    // JsonWebToken jwt;

    // private String baseUrl = "http://localhost:3001";

    @Operation(summary = "Create Applicant/user")
    @PostMapping("/add/applicant")
    public Applicants createApplicant(@RequestBody CreateApplicantDto dto) {

        Applicants applicant = new Applicants();
        if (dto != null) {

            // String userId = this.createUser(dto.userDto());

            // System.out.println(userId);

            applicant.setFirstName(dto.firstName());
            applicant.setLastName(dto.lastName());
            applicant.setTelephone(dto.telephone());
            applicant.setEmail(dto.email());
            if (dto.department() != null) {
                UserDepartments department = departmentsRepository.findByName(dto.department().name());
                if (department != null) {
                    applicant.setDepartment(department);
                }
            }
            applicant.setAuthorizedDays(dto.authorizedDays());
            applicant.setTitle(dto.title());

            applicantsRepository.saveAndFlush(applicant);

            return applicant;

        }
        return null;
    }

    @Operation(summary = "Get all departments")
    @GetMapping("/all/departments")
    public List<UserDepartments> getAllDepartments() {

        List<UserDepartments> departments = departmentsRepository.findAll();
        if (!departments.isEmpty()) {

            return departments;
        }

        return null;
    }

    @Operation(summary = "Get department by name")
    @GetMapping("/department/{name}")
    public UserDepartments getDepartmentByName(@PathVariable String name) {

        UserDepartments department = departmentsRepository.findByName(name);
        if (department != null) {
            return department;
        }
        return null;
    }

    @Operation(summary = "Request leave")
    @PostMapping("/apply/{applicantId}")
    public LeaveApplications applyForLeave(@RequestBody CreateApplicationDto dto, @PathVariable UUID applicantId) {

        LeaveApplications application = new LeaveApplications();
        Optional<Applicants> applicant = applicantsRepository.findById(applicantId);
        if (applicant.isPresent()) {

            Applicants applikant = applicant.get();
            if (dto.requestedDays() <= applikant.getAuthorizedDays()) {

                application.setApplicant(applikant);
                application.setStatus("PENDING");

                if (dto != null) {

                    application.setProposedStartDate(dto.proposedStartDate());
                    application.setProposedEndDate(dto.proposedEndDate());
                    application.setRequestedDays(dto.requestedDays());
                    application.setType(dto.type());

                    if (dto.reason() != null) {
                        application.setReason(dto.reason());
                    }

                    return applicationsRepository.saveAndFlush(application);
                }

            }

        }

        return null;

    }

    @Operation(summary = "Update or edit leave request/application")
    @PutMapping("/edit/application/{id}")
    public LeaveApplications editApplication(@RequestBody UpdateApplicationDto dto, @PathVariable UUID id) {

        Optional<LeaveApplications> applications = applicationsRepository.findById(id);
        if (applications.isPresent()) {
            LeaveApplications application = applications.get();

            if (dto.proposedEndDate() != null) {
                application.setProposedEndDate(dto.proposedEndDate());
            }

            if (dto.proposedStartDate() != null) {
                application.setProposedStartDate(dto.proposedStartDate());
            }
            if ((Integer) dto.requestedDays() != null) {

                application.setRequestedDays(dto.requestedDays());
            }

            applicationsRepository.saveAndFlush(application);

            return application;
        }
        return null;

    }

    @Operation(summary = "Get applicants's leave requests")
    @GetMapping("/applications/{applicantId}/applicant")
    public List<LeaveApplications> getApplicationsByApplicant(@PathVariable UUID applicantId) {

        List<LeaveApplications> applications = applicationsRepository.findByApplicantId(applicantId);
        if (!applications.isEmpty()) {

            return applications;
        }
        return null;

    }

    @Operation(summary = "Get leave requests in a department")
    @GetMapping("/applications/{departmentName}")
    public List<LeaveApplications> getApplicationsByDepartment(@PathVariable String departmentName) {

        List<LeaveApplications> applications = applicationsRepository.findByApplicantDepartmentName(departmentName);
        if (!applications.isEmpty()) {

            return applications;
        }
        return null;

    }

    @Operation(summary = "Get leave requests of a particular status in a department")
    @GetMapping("/applications/{status}/{departmentName}")
    public List<LeaveApplications> getApplicationsByDepartment(@PathVariable String status,
            @PathVariable String departmentName) {

        List<LeaveApplications> applications = applicationsRepository.findByStatusAndApplicantDepartmentName(status,
                departmentName);
        if (!applications.isEmpty()) {

            return applications;
        }
        return null;

    }

    @Operation(summary = "See the list of all  users")
    @GetMapping("/applicants/all")
    public List<Applicants> getAllApplicants() {

        List<Applicants> applicants = applicantsRepository.findAll();
        if (!applicants.isEmpty()) {

            return applicants;
        }

        return null;

    }

    @Operation(summary = "Leave request Approval")
    @GetMapping("/approve/{applicationId}")
    public LeaveApplications approveApplication(@PathVariable UUID applicationId) {

        LeaveApplications app = new LeaveApplications();

        String role = "CHIEF-EXECUTIVE-OFFICER";

        Optional<LeaveApplications> application = applicationsRepository.findById(applicationId);

        if (application.isPresent()) {

            app = application.get();

            if (role.equalsIgnoreCase("HR") && app.getStatus().equalsIgnoreCase("PENDING")) {

                app.setStatus("REVIEWED BY HR");
            }
            if (role.equalsIgnoreCase("DIRECTOR-OF-DEPARTMENT") && app.getStatus().equalsIgnoreCase("REVIEWED BY HR")) {

                app.setStatus("APPOVED BY DIRECTOR OF DEPARTMENT");

            }
            if (role.equalsIgnoreCase("CHIEF-OF-DEPARTMENT")
                    && app.getStatus().equalsIgnoreCase("APPOVED BY DIRECTOR OF DEPARTMENT")) {

                app.setStatus("APPOVED BY CHIEF OF DEPARTMENT");

            }
            if (role.equalsIgnoreCase("CHIEF-EXECUTIVE-OFFICER")
                    && app.getStatus().equalsIgnoreCase("APPOVED BY CHIEF OF DEPARTMENT")) {

                app.setStatus("APPOVED");

                Applicants applicant = app.getApplicant();
                applicant.setAuthorizedDays(applicant.getAuthorizedDays() - app.getRequestedDays());

                applicantsRepository.saveAndFlush(applicant);

                Leaves leave = new Leaves();
                leave.setType(app.getType());
                leave.setStartDate(new Date());
                leave.setEndDate(new Date());
                leave.setApplication(app);
                leave.setStatus(LeaveStatuses.ONGOING);

                leaveRepository.saveAndFlush(leave);

            }

            applicationsRepository.saveAndFlush(app);

            return app;

        }
        return null;
    }

    @Operation(summary = "Leave request rejection")
    @GetMapping("/reject/{applicationId}")
    public LeaveApplications rejectApplication(@PathVariable UUID applicationId) {

        Optional<LeaveApplications> application = applicationsRepository.findById(applicationId);

        if (application.isPresent()) {

            LeaveApplications app = application.get();
            app.setStatus("REJECTED");

            applicationsRepository.saveAndFlush(app);

            return app;

        }
        return null;
    }

    public void seedDepartments() {

        List<UserDepartments> departs = departmentsRepository.findAll();
        if (departs.isEmpty()) {

            String[] departments = { "OPERATIONS", "ADVOCACY", "OFFICE OF HR", "OFFICE OF CEO" };
            for (String depart : departments) {

                UserDepartments department = new UserDepartments();
                department.setName(depart);
                departmentsRepository.saveAndFlush(department);
            }
        }
    }

    // @PostMapping("/login")
    // public String signIn(@RequestBody LoginDTO dto) {

    // ObjectMapper objectMapper = new ObjectMapper();

    // Object response = send(baseUrl + "/users/authenticate", null, "POST", dto,
    // Object.class);

    // Map<String, Object> map = objectMapper.convertValue(response, Map.class);
    // Object user = map.get("user");

    // String token = (String) map.get("access_token");

    // Map<String, Object> userMap = objectMapper.convertValue(user, Map.class);
    // String userId = (String) userMap.get("id");

    // return token;
    // }

    // @GetMapping("/user")
    // public Object getUser() {

    // Map<String, String> headers = new HashMap<String, String>();
    // headers.put("Authorization", "");

    // return (Object) send(baseUrl + "/me", headers, "POST", null, Object.class);

    // }

    // public String createUser(CreateUserDto dto) {

    // ObjectMapper objectMapper = new ObjectMapper();
    // Object response = send(baseUrl + "/users", null, "POST", dto, Object.class);

    // Map<String, Object> map = objectMapper.convertValue(response, Map.class);
    // String id = (String) map.get("id");

    // return id;
    // }

}
