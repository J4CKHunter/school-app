package com.test.school.dto;

import lombok.Data;

//classes.html içerisinde görünen class'ın id ve name'i
@Data
public class DisplayClassesResponse {
    private Integer id;
    private String name;
}
