package ua.nure.bookmeetup.service;

import ua.nure.bookmeetup.entity.role.Role;
import ua.nure.bookmeetup.entity.role.UserRole;

import java.util.List;

public interface RoleService {

    Role findById(Long id);

    Role findByName(UserRole name);

    List<Role> findAll();

}
