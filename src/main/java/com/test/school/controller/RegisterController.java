package com.test.school.controller;

import com.test.school.dto.CreateNewUserRequest;
import com.test.school.model.Person;
import com.test.school.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("public")
public class RegisterController {
    private final PersonService personService;

    public RegisterController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/register")
    public ModelAndView displayRegisterPage(@RequestParam(value = "error", required = false) String error){
        ModelAndView modelAndView = new ModelAndView("register.html");
        modelAndView.addObject("person", new CreateNewUserRequest());

        if(error != null){
            String errorMessage = "User already exists! Use different email to register!";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @PostMapping("/createUser")
    public ModelAndView createUser(@Valid @ModelAttribute("person") CreateNewUserRequest createPerson,
                             Errors errors){

        if(errors.hasErrors()){
            return new ModelAndView("register.html");
        }

        Person personFromDb = personService.findPersonByEmail(createPerson.getEmail());

        if(personFromDb != null){
            return new ModelAndView("redirect:/public/register?error=true");
        }

        boolean isSaved = personService.createNewUser(createPerson);

        if(isSaved){
            return new ModelAndView("redirect:/login?register=true");
        }else {
            return new ModelAndView("register.html");
        }
    }
}
