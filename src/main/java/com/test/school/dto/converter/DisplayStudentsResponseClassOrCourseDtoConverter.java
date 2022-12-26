package com.test.school.dto.converter;

import com.test.school.dto.DisplayClassesResponse;
import com.test.school.dto.DisplayStudentsResponseClassOrCourseDto;
import com.test.school.model.Course;
import com.test.school.model.CourseClass;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DisplayStudentsResponseClassOrCourseDtoConverter {

    private final DisplayStudentsResponsePersonDtoConverter displayStudentsResponsePersonDtoConverter;

    public DisplayStudentsResponseClassOrCourseDtoConverter(DisplayStudentsResponsePersonDtoConverter displayStudentsResponsePersonDtoConverter) {
        this.displayStudentsResponsePersonDtoConverter = displayStudentsResponsePersonDtoConverter;
    }

    public DisplayStudentsResponseClassOrCourseDto convert (CourseClass from){

        DisplayStudentsResponseClassOrCourseDto dto = new DisplayStudentsResponseClassOrCourseDto();

        dto.setId(from.getId());
        dto.setName(from.getName());
        dto.setPersons(from.getPersons()
                .stream()
                .map(displayStudentsResponsePersonDtoConverter::convert)
                .collect(Collectors.toSet()));

        return dto;
    }

    public DisplayStudentsResponseClassOrCourseDto convert (Course from){

        DisplayStudentsResponseClassOrCourseDto dto = new DisplayStudentsResponseClassOrCourseDto();

        dto.setId(from.getId());
        dto.setName(from.getName());
        dto.setPersons(from.getPersons()
                .stream()
                .map(displayStudentsResponsePersonDtoConverter::convert)
                .collect(Collectors.toSet()));

        return dto;
    }
}
