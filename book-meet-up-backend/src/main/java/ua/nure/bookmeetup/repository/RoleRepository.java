package ua.nure.bookmeetup.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ua.nure.bookmeetup.entity.role.Role;
import ua.nure.bookmeetup.entity.role.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(UserRole name);

    @NonNull  List<Role> findAll();

}
