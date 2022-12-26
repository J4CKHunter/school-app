package com.test.school.controller;

import com.test.school.dto.ProfileDto;
import com.test.school.model.Person;
import com.test.school.service.AddressService;
import com.test.school.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller("ProfileControllerBean")
public class ProfileController {

    private final PersonService personService;
    private final AddressService addressService;

    public ProfileController(PersonService personService,
                             AddressService addressService) {
        this.personService = personService;
        this.addressService = addressService;
    }

    @RequestMapping("/displayProfile")
    public ModelAndView displayProfile(HttpSession httpSession){

        Person person = (Person)httpSession.getAttribute("loggedInPerson");

        ProfileDto profile = new ProfileDto();
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());

        if(person.getAddress() != null && person.getAddress().getId() > 0){
            profile.setFirstAddress(person.getAddress().getFirstAddress());
            profile.setSecondAddress(person.getAddress().getSecondAddress());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile", profile);

        return modelAndView;
    }

    @PostMapping("/updateProfile")
    public String updateProfile (@Valid @ModelAttribute("profile") ProfileDto profile,
                                 Errors errors,
                                 HttpSession httpSession){

        if(errors.hasErrors())
            return "profile.html";

        Person person = (Person)httpSession.getAttribute("loggedInPerson");

        person = personService.updatePersonUsingProfile(person, profile);

        Person savedPerson = null;

        try{
            savedPerson = personService.savePerson(person);
        }catch (Exception e){
            e.printStackTrace();
        }

        httpSession.setAttribute("loggedInPerson", savedPerson);
        return "redirect:/displayProfile";
    }
}
