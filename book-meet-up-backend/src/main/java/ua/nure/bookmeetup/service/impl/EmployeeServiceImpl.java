package ua.nure.bookmeetup.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.bookmeetup.dto.EmployeeDto;
import ua.nure.bookmeetup.dto.mapper.EmployeeMapper;
import ua.nure.bookmeetup.entity.user.Employee;
import ua.nure.bookmeetup.exception.EntityNotFoundException;
import ua.nure.bookmeetup.repository.EmployeeRepository;
import ua.nure.bookmeetup.service.EmployeeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ua.nure.bookmeetup.dto.mapper.EmployeeMapper.toEmployee;
import static ua.nure.bookmeetup.dto.mapper.EmployeeMapper.toEmployeeDto;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_EMPLOYEE_BY_EMAIL;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_EMPLOYEE_BY_ID;
import static ua.nure.bookmeetup.util.ErrorMessagesUtil.ERROR_FIND_EMPLOYEE_BY_PHONE_NUMBER;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        Employee employee = toEmployee(employeeDto, new Employee(), bCryptPasswordEncoder);
        return toEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeDto update(EmployeeDto employeeDto) {
        return employeeRepository.findByEmail(employeeDto.getEmail())
                .map(initialEmployee -> {
                    String password = employeeDto.getPassword();
                    if (password == null || password.isEmpty()) {
                        employeeDto.setPassword(initialEmployee.getPassword());
                    }
                    Employee employee = toEmployee(employeeDto, initialEmployee, bCryptPasswordEncoder);
                    return toEmployeeDto(employee);
                }).orElseThrow(() -> new EntityNotFoundException(ERROR_FIND_EMPLOYEE_BY_EMAIL));
    }

    @Override
    public void delete(EmployeeDto employeeDto) {
        employeeRepository.deleteById(employeeDto.getId());
    }

    @Override
    public List<EmployeeDto> findAll() {
        return toEmployeeDto(employeeRepository.findAll());
    }

    @Override
    public EmployeeDto findByPhoneNumber(String phoneNumber) {
        return findEmployee(employeeRepository.findByPhoneNumber(phoneNumber), ERROR_FIND_EMPLOYEE_BY_PHONE_NUMBER);
    }

    @Override
    public EmployeeDto findByEmail(String email) {
        return findEmployee(employeeRepository.findByEmail(email), ERROR_FIND_EMPLOYEE_BY_EMAIL);
    }

    @Override
    public EmployeeDto findById(Long id) {
        return findEmployee(employeeRepository.findById(id),ERROR_FIND_EMPLOYEE_BY_ID);
    }

    private EmployeeDto findEmployee(Optional<Employee> foundEmployee, String errorMessage) {
        return foundEmployee.map(EmployeeMapper::toEmployeeDto)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage));
    }

}
