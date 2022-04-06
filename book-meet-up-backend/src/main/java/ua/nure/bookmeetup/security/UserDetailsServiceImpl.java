package ua.nure.bookmeetup.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.bookmeetup.entity.user.Employee;
import ua.nure.bookmeetup.entity.user.Admin;
import ua.nure.bookmeetup.repository.AdminRepository;
import ua.nure.bookmeetup.repository.EmployeeRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    private final AdminRepository adminRepository;

    @Autowired
    public UserDetailsServiceImpl(EmployeeRepository employeeRepository, AdminRepository adminRepository) {
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        Optional<Admin> admin = adminRepository.findByEmail(email);

        return employee.map(UserDetailsImpl::new)
                .orElseGet(() -> admin.map(UserDetailsImpl::new)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User with email " + email + " not found")));
    }

}
