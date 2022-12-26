package com.test.school.controller;

import com.test.school.model.Person;
import com.test.school.service.PersonService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private final PersonService personService;

    public DashboardController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/dashboard")
    public ModelAndView displayDashboard(Authentication authentication,
                                         HttpSession httpSession){

        Person person = personService.findPersonByEmail(authentication.getName());

        ModelAndView modelAndView = new ModelAndView("dashboard.html");
        modelAndView.addObject("username", person.getName());
        modelAndView.addObject("role", authentication.getAuthorities().toString());

        if(person.getCourseClass() != null && person.getCourseClass().getName() != null){
            modelAndView.addObject("enrolledClass", person.getCourseClass().getName());
        }else {
            modelAndView.addObject("enrolledClass", "");
        }

        // student's enrolled courses'a girince sessionAttribute'den çekip
        // student name + studentCourses{ id, name, fees } alanlarını çekiyoruz
        httpSession.setAttribute("loggedInPerson", person);

        return modelAndView;

    }

}
