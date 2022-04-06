package ua.nure.bookmeetup.dto.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.nure.bookmeetup.dto.EmployeeDto;
import ua.nure.bookmeetup.entity.user.Employee;
import ua.nure.bookmeetup.entity.role.Role;
import ua.nure.bookmeetup.entity.role.UserRole;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDto toEmployeeDto(Employee employee) {
        return new EmployeeDto()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setEmail(employee.getEmail())
                .setPhoneNumber(employee.getPhoneNumber())
                .setPassword(employee.getPassword())
                .setRole(UserRole.EMPLOYEE)
                .setLockStatus(employee.isLocked());
    }

    public static List<EmployeeDto> toEmployeeDto(List<Employee> employee) {
        return employee.stream().map(EmployeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

    public static Employee toEmployee(EmployeeDto employeeDto,
                                      Employee employee, BCryptPasswordEncoder encoder) {
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setRole(Role.from(UserRole.EMPLOYEE));

        String password = employeeDto.getPassword();
        if (password.length() == 60) {
            employee.setPassword(password);
        } else {
            employee.setPassword(encoder.encode(password));
        }

        employee.isLocked(employeeDto.getLockStatus() != null && employeeDto.getLockStatus());
        return employee;
    }

}
