package ua.nure.bookmeetup.service;

import ua.nure.bookmeetup.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto create(EmployeeDto employeeDto);

    EmployeeDto update(EmployeeDto employeeDto);

    void delete(Long id);

    List<EmployeeDto> findAll();

    EmployeeDto findByPhoneNumber(String phoneNumber);

    EmployeeDto findByEmail(String email);

    EmployeeDto findById(Long id);

}
