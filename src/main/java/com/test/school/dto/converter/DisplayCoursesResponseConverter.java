package com.test.school.dto.converter;


import com.test.school.dto.DisplayCoursesResponse;
import com.test.school.model.Course;
import org.springframework.stereotype.Component;

@Component
public class DisplayCoursesResponseConverter {
    public DisplayCoursesResponse convert (Course from){

        DisplayCoursesResponse displayCoursesResponse = new DisplayCoursesResponse();
        displayCoursesResponse.setId(from.getId());
        displayCoursesResponse.setName(from.getName());
        displayCoursesResponse.setFees(from.getFees());

        return displayCoursesResponse;
    }
}
