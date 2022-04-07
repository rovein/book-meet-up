package ua.nure.bookmeetup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.bookmeetup.entity.role.Role;
import ua.nure.bookmeetup.entity.role.UserRole;
import ua.nure.bookmeetup.exception.EntityNotFoundException;
import ua.nure.bookmeetup.repository.RoleRepository;
import ua.nure.bookmeetup.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find role by ID " + id));
    }

    @Override
    public Role findByName(UserRole name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find role by name " + name));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
