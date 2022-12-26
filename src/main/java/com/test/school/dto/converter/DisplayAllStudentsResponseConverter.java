package com.test.school.dto.converter;

import com.test.school.dto.DisplayAllStudentsResponse;
import com.test.school.dto.DisplayStudentsResponsePersonDto;
import com.test.school.model.Person;
import org.springframework.stereotype.Component;

@Component
public class DisplayAllStudentsResponseConverter {

    public DisplayAllStudentsResponse convert (Person from){

        DisplayAllStudentsResponse dto = new DisplayAllStudentsResponse();

        dto.setId(from.getId());
        dto.setName(from.getName());
        dto.setEmail(from.getEmail());
        dto.setMobileNumber(from.getMobileNumber());

        if(from.getAddress() == null || from.getAddress().getFirstAddress() == null){
            dto.setAddress("The user has not yet given address information.");
        }else{
            dto.setAddress(
                    from.getAddress().getFirstAddress() + " "
                    + from.getAddress().getState() + "/"
                    + from.getAddress().getCity() + " "
                    + from.getAddress().getZipCode());
        }

        if(from.getCourseClass() == null){
            dto.setClassName("");
        }else {
            dto.setClassName(from.getCourseClass().getName());
        }

        return dto;
    }
}
