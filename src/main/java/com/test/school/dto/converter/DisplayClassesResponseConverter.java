package com.test.school.dto.converter;

import com.test.school.dto.DisplayClassesResponse;
import com.test.school.model.CourseClass;
import org.springframework.stereotype.Component;

@Component
public class DisplayClassesResponseConverter {

    public DisplayClassesResponse convert (CourseClass from){

        DisplayClassesResponse displayClassesResponse = new DisplayClassesResponse();
        displayClassesResponse.setId(from.getId());
        displayClassesResponse.setName(from.getName());

        return displayClassesResponse;
    }
}

/*
* Entity: DB classes
* Models:
*
*
*   FormModels:
*       Kullanıcıdan data aldın
*       FormModel -> Entity
*       Entity -> ResponseModel
*       return ResponseModel
*   ResponseModel
*
*
*
* */