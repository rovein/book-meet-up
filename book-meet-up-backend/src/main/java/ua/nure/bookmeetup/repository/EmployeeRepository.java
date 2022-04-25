package ua.nure.bookmeetup.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ua.nure.bookmeetup.entity.user.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByPhoneNumber(String phoneNumber);

    Optional<Employee> findByEmail(String email);

    @NonNull List<Employee> findAll();

    @Query("select e from Employee e where e.email in :emails")
    List<Employee> findAllWithEmails(String[] emails);

}
