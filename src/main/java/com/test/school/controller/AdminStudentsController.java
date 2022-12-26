package com.test.school.controller;

import com.test.school.dto.DisplayAllStudentsResponse;
import com.test.school.dto.DisplayClassesResponse;
import com.test.school.model.Course;
import com.test.school.model.CourseClass;
import com.test.school.model.Person;
import com.test.school.service.CourseClassService;
import com.test.school.service.CourseService;
import com.test.school.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminStudentsController {

    private final PersonService personService;
    private final CourseClassService courseClassService;

    private final CourseService courseService;

    public AdminStudentsController(PersonService personService,
                                   CourseClassService courseClassService,
                                   CourseService courseService) {
        this.personService = personService;
        this.courseClassService = courseClassService;
        this.courseService = courseService;
    }

    @RequestMapping("/displayStudents")
    public ModelAndView displayStudents(@RequestParam(value = "error", required = false) String error){

        List<DisplayAllStudentsResponse> studentsResponseList = personService.findAllStudents();
        List<DisplayClassesResponse> displayClassesResponseList = courseClassService.listCourseClasses();

        ModelAndView modelAndView = new ModelAndView("students.html");
        modelAndView.addObject("studentsResponseList", studentsResponseList);
        modelAndView.addObject("displayClassesResponseList", displayClassesResponseList);
        modelAndView.addObject("classResponse", new DisplayClassesResponse());

        if(error != null){
            String errorMessage = "Please select a class from the dropdown menu";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @RequestMapping("/deleteStudent")
    public ModelAndView deleteStudent(@RequestParam(value = "personId") Integer personId){

        log.error("/deleteStudent baslangici");

        // ilgili personId'ye sahip person'un, bulunduğu tüm course'lardan silinmesi gerek.
        // ardından personService.deletePerson(personId) çağrıldığında silinecektir varsayımında bulunuyorum.
        //
        // personId ile ilgili Person nesnesi alındığında üzerinde List<Courses> var, bu liste gezilerek
        // ilgili course'ların id'leri elde edilir ve bu id'ler kullanılarak personService.disenrollStudentFromCourse()
        // ve courseService.removeStudentFromCourse() methodları kullanılır her bir Course için.

        log.error("personService.findPersonById(personId) baslangici");
        Optional<Person> personOptional = personService.findPersonById(personId);
        Person person = personOptional.get();

        List<Course> personCoursesList = person.getCourses().stream().toList();

        List<Integer> personCoursesIdList = personCoursesList.stream().map(Course::getId).collect(Collectors.toList());

        log.error("personService.disenrollStudentFromCourse(personId, course) baslangici");
        personCoursesList.forEach(course -> {
                    personService.disenrollStudentFromCourse(personId, course);
        });

        log.error("courseService.removeStudentFromCourse(id, personId) baslangici");
        personCoursesIdList.forEach(id -> {
                    courseService.removeStudentFromCourse(id, personId);
                });

        log.error("personService.deletePerson(personId) baslangici");
        personService.deletePerson(personId);

        return new ModelAndView("redirect:/admin/displayStudents");
    }

    @PostMapping("/setStudentClass")
    public ModelAndView addStudentToClass(/*@RequestParam(value = "classId") Integer classId,*/
                                          @ModelAttribute(value = "classResponse") DisplayClassesResponse classResponse,
                                          @RequestParam("email") String email){

        if(classResponse.getName() == null && classResponse.getId() == 0){
            log.error("Setting student class value failed due to: No class name value selected");
            return new ModelAndView("redirect:/admin/displayStudents?error=true");
        }

        Integer classId = classResponse.getId();

        ModelAndView modelAndView = new ModelAndView();

        Person personFromDb = personService.findPersonByEmail(email);

        Optional<CourseClass> courseClass = courseClassService.findCourseClassWithId(classId);
        personService.enrollStudentInTheClass(personFromDb, classId, courseClass);

        courseClassService.addNewStudentToClass(classId, personFromDb);

        modelAndView.setViewName("redirect:/admin/displayStudents");

        return modelAndView;
    }
}
