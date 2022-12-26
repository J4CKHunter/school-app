package com.test.school.controller;

import com.test.school.dto.*;
import com.test.school.model.Course;
import com.test.school.model.Person;
import com.test.school.service.CourseService;
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
public class AdminCoursesController {

    private final PersonService personService;
    private final CourseService courseService;

    public AdminCoursesController(PersonService personService, CourseService courseService) {
        this.personService = personService;
        this.courseService = courseService;
    }



    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(@RequestParam(value = "error", required = false) String error){

        List<DisplayCoursesResponse> displayCoursesResponseList = courseService.listCourses();

        ModelAndView modelAndView = new ModelAndView("courses_secure.html");

        modelAndView.addObject("displayCoursesResponseList", displayCoursesResponseList);
        modelAndView.addObject("addNewCourseRequest", new AddNewCourseRequest());
//        modelAndView.addObject("displayCoursesResponse", new DisplayCoursesResponse());

        if(error != null){
            String errorMessage = "Name must be at least 3 characters long " +
                    "and Fees value must be greater than zero";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(@Valid @ModelAttribute("course") AddNewCourseRequest addNewCourseRequest,
                                     Errors errors){

        if(errors.hasErrors()){
            log.error("Contact form validation failed due to: " + errors);
            return new ModelAndView("redirect:/admin/displayCourses?error=true");
        }

        courseService.addNewCourse(addNewCourseRequest);
        return new ModelAndView("redirect:/admin/displayCourses");
    }

    @RequestMapping("/deleteCourse")
    public ModelAndView deleteCourse(@RequestParam Integer id){
        courseService.deleteCourse(id);

        return new ModelAndView("redirect:/admin/displayCourses");
    }

    @GetMapping("/displayCourseStudents")
    public ModelAndView displayCourseStudents(@RequestParam(value = "id") Integer id,
                                              /*HttpSession httpSession,*/
                                              @RequestParam(value="error", required = false) String error){

        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");

        DisplayStudentsResponseClassOrCourseDto course = courseService.findCourseById(id);

        modelAndView.addObject("course", course);
        modelAndView.addObject("emailDto", new EmailDto());

        // bunu addStudentToClass'ta kullanmak için gönderiyor,
//        modelAndView.addObject("person", new DisplayStudentsResponsePersonDto());

//        httpSession.setAttribute("course", course.get());

        if(error != null){
            errorMessage = "Entered invalid email";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }


    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(@Valid @ModelAttribute("emailDto") EmailDto emailDto,
                                           @RequestParam(value = "id") Integer courseId
                                           /*HttpSession httpSession*/){

        ModelAndView modelAndView = new ModelAndView();

//        Course course = (Course) httpSession.getAttribute("course");

        Person personFromDb = personService.findPersonByEmail(emailDto.getEmail());

        if(personFromDb == null || !(personFromDb.getId() > 0)){
            modelAndView.setViewName("redirect:/admin/displayCourseStudents?id=" + courseId + "&error=true");
            return modelAndView;
        }

        Optional<Course> course = courseService.findCourseWithId(courseId);
        personService.enrollStudentToCourse(personFromDb, courseId, course);

        courseService.addNewStudentToCourse(courseId, personFromDb);

//        httpSession.setAttribute("course", courseFromDb);

        modelAndView.setViewName("redirect:/admin/displayCourseStudents?id=" + courseId);
        return modelAndView;
    }

    @RequestMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam(value = "personId") Integer personId,
                                                @RequestParam(value = "courseId") Integer courseId
                                                /*HttpSession httpSession*/){

//        Course course = (Course) httpSession.getAttribute("course");


        Optional<Course> optionalCourse = courseService.findCourseWithId(courseId);

        personService.disenrollStudentFromCourse(personId, optionalCourse.get());

        courseService.removeStudentFromCourse(courseId, personId);

//        httpSession.setAttribute("course", courseFromDb);

        return new ModelAndView("redirect:/admin/displayCourseStudents?id=" + courseId);
    }
}
