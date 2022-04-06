package ua.nure.bookmeetup.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nure.bookmeetup.entity.user.Admin;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

}
