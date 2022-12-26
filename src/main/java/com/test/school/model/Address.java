package com.test.school.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Address extends BaseEntity{

    private String firstAddress;

    private String secondAddress;

    private String city;

    private String state;

    private String zipCode;
}
