package com.test.school.dto;

import lombok.Data;

import java.util.Set;

// class_students.html içerisindeki class Dto su
// admin controller -> "/displayClassStudents"
@Data
public class DisplayStudentsResponseClassOrCourseDto {
    private Integer id;
    private String name;
    private Set<DisplayStudentsResponsePersonDto> persons;
}
