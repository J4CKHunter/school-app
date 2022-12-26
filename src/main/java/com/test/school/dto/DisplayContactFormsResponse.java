package com.test.school.dto;

import lombok.Data;

@Data
public class DisplayContactFormsResponse {

    private Integer id;

    private String name;

    private String mobileNumber;

    private String email;

    private String subject;

    private String message;
}
