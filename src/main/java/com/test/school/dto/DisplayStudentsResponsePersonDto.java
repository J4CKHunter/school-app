package com.test.school.dto;

import lombok.Data;

// "/displayClassStudents" and "/displayCourseStudents" -> One Person
// course_students.html içerisindeki id, name, email, mobileNumber olan dto
// class_students.hmtl içerisindeki id, name, email, mobileNumber olan dto
@Data
public class DisplayStudentsResponsePersonDto {
    private Integer id;
    private String name;
    private String email;
    private String mobileNumber;
}
