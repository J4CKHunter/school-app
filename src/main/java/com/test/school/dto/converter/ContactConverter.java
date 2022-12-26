package com.test.school.dto.converter;

import com.test.school.dto.AddNewContactMessageRequest;
import com.test.school.dto.DisplayContactFormsResponse;
import com.test.school.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactConverter {

    public Contact convert (AddNewContactMessageRequest from){

        Contact contact = new Contact();

        contact.setName(from.getName());
        contact.setMobileNumber(from.getMobileNumber());
        contact.setEmail(from.getEmail());
        contact.setSubject(from.getSubject());
        contact.setMessage(from.getMessage());
        contact.setStatusType(Contact.StatusType.OPEN);

        return contact;
    }

    public DisplayContactFormsResponse convert (Contact from){

        DisplayContactFormsResponse response = new DisplayContactFormsResponse();

        response.setId(from.getId());
        response.setName(from.getName());
        response.setMobileNumber(from.getMobileNumber());
        response.setEmail(from.getEmail());
        response.setSubject(from.getSubject());
        response.setMessage(from.getMessage());

        return response;
    }
}
