package com.test.school.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddNewCourseRequest {

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Name must not be blank")
    @Min(value = 0, message = "Fees value must be greater than zero")
    private String fees;
}
