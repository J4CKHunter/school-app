package com.test.school.service;

import com.test.school.model.Role;
import com.test.school.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getByRoleType(Role.RoleType roleType){
        return roleRepository.getByRoleType(roleType);
    }
}
