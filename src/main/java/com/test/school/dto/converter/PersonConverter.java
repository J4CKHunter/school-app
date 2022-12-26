package com.test.school.dto.converter;

import com.test.school.dto.CreateNewUserRequest;
import com.test.school.dto.ProfileDto;
import com.test.school.model.Address;
import com.test.school.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;



@Component
public class PersonConverter {

    private final PasswordEncoder passwordEncoder;

    public PersonConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Person convert (CreateNewUserRequest from){

        Person person = new Person();

        person.setName(from.getName());
        person.setMobileNumber(from.getMobileNumber());
        person.setEmail(from.getEmail());
        person.setPassword(passwordEncoder.encode(from.getPassword()));;

        return person;
    }

    public Person update(Person person, ProfileDto from){

        person.setName(from.getName());
        person.setEmail(from.getEmail());
        person.setMobileNumber(from.getMobileNumber());

        if(person.getAddress() == null || !(person.getAddress().getId()>0)){
            person.setAddress(new Address());
        }

        person.getAddress().setFirstAddress(from.getFirstAddress());
        person.getAddress().setSecondAddress(from.getSecondAddress());
        person.getAddress().setCity(from.getCity());
        person.getAddress().setState(from.getState());
        person.getAddress().setZipCode(from.getZipCode());

        return person;

    }

}
