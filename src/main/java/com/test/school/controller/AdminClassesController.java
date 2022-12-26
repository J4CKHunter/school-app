package com.test.school.controller;

import com.test.school.dto.AddNewCourseClassRequest;
import com.test.school.dto.DisplayClassesResponse;
import com.test.school.dto.DisplayStudentsResponseClassOrCourseDto;
import com.test.school.dto.EmailDto;
import com.test.school.model.CourseClass;
import com.test.school.model.Person;
import com.test.school.service.CourseClassService;
import com.test.school.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminClassesController {

    private final CourseClassService courseClassService;

    private final PersonService personService;

    public AdminClassesController(CourseClassService courseClassService, PersonService personService) {
        this.courseClassService = courseClassService;
        this.personService = personService;
    }

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(@RequestParam(value = "error", required = false) String error){

        List<DisplayClassesResponse> displayClassesResponseList = courseClassService.listCourseClasses();

        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("displayClassesResponseList", displayClassesResponseList);
        modelAndView.addObject("addNewCourseClassRequest", new AddNewCourseClassRequest());
//        modelAndView.addObject("displayClassesResponse", new DisplayClassesResponse());
        if(error != null){
            String errorMessage = "Name must be at least 3 characters long";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(@Valid @ModelAttribute("addNewCourseClassRequest") AddNewCourseClassRequest addNewCourseClassRequest,
                                    Errors errors){

        if(errors.hasErrors()){
            log.error("Contact form validation failed due to: " + errors);
            return new ModelAndView("redirect:/admin/displayClasses?error=true");
        }

        courseClassService.addNewCourseClass(addNewCourseClassRequest);
        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(@RequestParam Integer id){

        courseClassService.deleteCourseClass(id);

        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @GetMapping("/displayClassStudents")
    public ModelAndView displayClassStudents(@RequestParam(value = "id") Integer id,
            /*HttpSession httpSession,*/
                                             @RequestParam(value = "error", required = false) String error){

        ModelAndView modelAndView = new ModelAndView("class_students.html");

        DisplayStudentsResponseClassOrCourseDto courseClass = courseClassService.findCourseClassById(id);

        modelAndView.addObject("courseClass", courseClass);
        modelAndView.addObject("emailDto", new EmailDto());

        // bunu addStudentToClass'ta kullanmak için gönderiyor,
//        modelAndView.addObject("person", new DisplayStudentsResponsePersonDto());

//        httpSession.setAttribute("courseClass", courseClassOptional.get());

        if(error != null){
            String errorMessage = "Entered invalid email";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @PostMapping("/addStudentToClass")
    public ModelAndView addStudentToClass(@RequestParam(value = "classId") Integer classId,
                                          @Valid @ModelAttribute("emailDto") EmailDto emailDto

            /*HttpSession httpSession*/){


        ModelAndView modelAndView = new ModelAndView();

//        CourseClass courseClass = (CourseClass)httpSession.getAttribute("courseClass");

        Person personFromDb = personService.findPersonByEmail(emailDto.getEmail());

        if(personFromDb == null || !(personFromDb.getId() > 0)){
            modelAndView.setViewName("redirect:/admin/displayClassStudents?id=" + classId + "&error=true");
            return modelAndView;
        }

        Optional<CourseClass> courseClass = courseClassService.findCourseClassWithId(classId);
        personService.enrollStudentInTheClass(personFromDb, classId, courseClass);

        courseClassService.addNewStudentToClass(classId, personFromDb);

        modelAndView.setViewName("redirect:/admin/displayClassStudents?id=" + classId);

        return modelAndView;
    }

    @RequestMapping("/deleteStudentFromClass")
    public ModelAndView deleteStudentFromClass(@RequestParam(value = "personId") Integer personId,
                                               @RequestParam(value = "classId") Integer classId
            /*HttpSession httpSession*/){

//        CourseClass courseClass = (CourseClass) httpSession.getAttribute("courseClass");

        personService.disenrollStudentInTheClass(personId);

        courseClassService.removeStudentFromClass(personId, classId);

//        httpSession.setAttribute("courseClass", savedCourseClass);

        return new ModelAndView("redirect:/admin/displayClassStudents?id=" + classId);


    }
}
