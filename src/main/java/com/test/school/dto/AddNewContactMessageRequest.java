package com.test.school.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AddNewContactMessageRequest {

    @NotBlank(message = "Name couldn't be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Mobile number couldn't be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "email couldn't be blank")
    @Email(message = "Provide a valid email adress")
    private String email;

    @NotBlank(message = "Subject couldn't be blank")
    @Size(min = 5, message = "Subject must be at least 5 characters long")
    private String subject;

    @NotBlank(message = "Message couldn't be blank")
    @Size(min = 10, message = "Message must be at least 10 characters long")
    private String message;
}
