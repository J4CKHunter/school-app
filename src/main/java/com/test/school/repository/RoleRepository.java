package com.test.school.repository;

import com.test.school.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getByRoleType(Role.RoleType roleType);
}
