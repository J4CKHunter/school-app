package com.test.school.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Entity
public class Role extends BaseEntity{

//    private String roleName;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public enum RoleType{
        STUDENT, ADMIN
    }
}
