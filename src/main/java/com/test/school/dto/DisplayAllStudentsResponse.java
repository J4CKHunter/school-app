package com.test.school.dto;

import lombok.Data;

/*
 *  admin -> students 'daki student'lar için
 */
@Data
public class DisplayAllStudentsResponse {
    private Integer id;
    private String name;
    private String email;
    private String mobileNumber;
    private String className;
    private String address;
}
