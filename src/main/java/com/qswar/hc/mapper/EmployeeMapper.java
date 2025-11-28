package com.qswar.hc.mapper;


import com.google.gson.Gson;
import com.qswar.hc.model.Employee;
import com.qswar.hc.pojos.requests.EmployeeRequest;
import com.qswar.hc.pojos.responses.EmployeeResponse;
import com.qswar.hc.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.util.Optional;

import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    private final EmployeeService employeeService;
    private final Gson gson = new Gson(); // Kept for the authorization logic

    public EmployeeMapper(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Gson getGson() {
        return gson;
    }

    /**
     * Converts Employee model to EmployeeResponse DTO, including nested Manager and Subordinates.
     * @param employee The Employee entity.
     * @return The EmployeeResponse DTO.
     */
    public EmployeeResponse convert(Employee employee) {
        if (employee == null) { return null; }

        EmployeeResponse employeeResponse = new EmployeeResponse();

        // Mapping basic fields
        employeeResponse.setGovEmpId(employee.getGovEmpId());
        employeeResponse.setName(employee.getName());
        employeeResponse.setPhone(employee.getPhone());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setPosition(employee.getPosition());
        employeeResponse.setAuthorised(employee.getAuthorised());
        employeeResponse.setDepartment(employee.getDepartment());
        employeeResponse.setUsername(employee.getUsername());
        employeeResponse.setQSwarId(employee.getQSwarId());

        // Handle Manager (avoiding deep recursion by creating a lighter manager DTO)
        if (employee.getManager() != null) {
            // OPTIMIZATION: Instead of full conversion, use a lighter DTO or only ID/Name if possible.
            // For now, retaining the original logic but calling the same mapper method.
            Employee manager = employeeService.getEmployeeByIdentity(employee.getEmail());
            employeeResponse.setManager(convertLight(manager));
        }

        // Handle Subordinates using streams
        employeeResponse.setSubordinates(
                employeeService.findSubordinatesById(employee.getId()).stream()
                        .map(this::convertLight) // Use light conversion for subordinates
                        .collect(Collectors.toList())
        );

        return employeeResponse;
    }

    // A lighter conversion method to prevent deep, possibly recursive calls
    public EmployeeResponse convertLight(Employee employee) {
        if (employee == null) { return null; }

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setGovEmpId(employee.getGovEmpId());
        employeeResponse.setName(employee.getName());
        employeeResponse.setPhone(employee.getPhone());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setPosition(employee.getPosition());
        employeeResponse.setAuthorised(employee.getAuthorised());
        employeeResponse.setDepartment(employee.getDepartment());
        employeeResponse.setUsername(employee.getUsername());
        employeeResponse.setQSwarId(employee.getQSwarId());
        // Do NOT set Manager or Subordinates here

        return employeeResponse;
    }

    /**
     * Converts EmployeeRequest DTO to Employee model.
     */
    public Employee convert(EmployeeRequest employeeRequest) {
        if (employeeRequest == null) { return null; }

        Employee employee = new Employee();
        employee.setGovEmpId(employeeRequest.getGovEmpId());
        employee.setName(employeeRequest.getName());
        employee.setPhone(employeeRequest.getPhone());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPosition(employeeRequest.getPosition());
        employee.setAuthorised(employeeRequest.getAuthorised());
        employee.setDepartment(employeeRequest.getDepartment());
        employee.setUsername(employeeRequest.getUsername());
        return employee;
    }

}