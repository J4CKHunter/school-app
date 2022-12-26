package com.test.school.dto.converter;

import com.test.school.dto.DisplayStudentsResponsePersonDto;
import com.test.school.model.Person;
import org.springframework.stereotype.Component;

@Component
public class DisplayStudentsResponsePersonDtoConverter {

    public DisplayStudentsResponsePersonDto convert (Person from){

        DisplayStudentsResponsePersonDto dto = new DisplayStudentsResponsePersonDto();

        dto.setId(from.getId());
        dto.setName(from.getName());
        dto.setEmail(from.getEmail());
        dto.setMobileNumber(from.getMobileNumber());


        return dto;
    }
}
