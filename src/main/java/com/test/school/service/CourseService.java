package com.test.school.service;

import com.test.school.dto.AddNewCourseRequest;
import com.test.school.dto.DisplayCoursesResponse;
import com.test.school.dto.DisplayStudentsResponseClassOrCourseDto;
import com.test.school.dto.converter.DisplayCoursesResponseConverter;
import com.test.school.dto.converter.DisplayStudentsResponseClassOrCourseDtoConverter;
import com.test.school.model.Course;
import com.test.school.model.CourseClass;
import com.test.school.model.Person;
import com.test.school.repository.CourseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final PersonService personService;
    private final CourseRepository courseRepository;

    private final DisplayStudentsResponseClassOrCourseDtoConverter displayStudentsResponseClassOrCourseDtoConverter;

    private final DisplayCoursesResponseConverter displayCoursesResponseConverter;

    public CourseService(PersonService personService,
                         CourseRepository courseRepository,
                         DisplayStudentsResponseClassOrCourseDtoConverter displayStudentsResponseClassOrCourseDtoConverter,
                         DisplayCoursesResponseConverter displayCoursesResponseConverter) {
        this.personService = personService;
        this.courseRepository = courseRepository;
        this.displayStudentsResponseClassOrCourseDtoConverter = displayStudentsResponseClassOrCourseDtoConverter;
        this.displayCoursesResponseConverter = displayCoursesResponseConverter;
    }

    public List<DisplayCoursesResponse> listCourses(){

//        return courseRepository.findAll(Sort.by("name").ascending());

        List<Course> courses = courseRepository.findAll(Sort.by("name").ascending());

        return courses
                .stream()
                .map(displayCoursesResponseConverter::convert)
                .collect(Collectors.toList());
    }

    public Course addNewCourse(AddNewCourseRequest addNewCourseRequest){

        Course course = new Course();
        course.setName(addNewCourseRequest.getName());
        course.setFees(addNewCourseRequest.getFees());

        return courseRepository.save(course);
    }

    public DisplayStudentsResponseClassOrCourseDto findCourseById(Integer id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return displayStudentsResponseClassOrCourseDtoConverter.convert(optionalCourse.get());
    }

    public Optional<Course> findCourseWithId(Integer id) {
        return courseRepository.findById(id);
    }

    public void addNewStudentToCourse(Integer courseId, Person person) {
        Optional<Course> course = courseRepository.findById(courseId);
        course.get().getPersons().add(person);
        courseRepository.save(course.get());
    }


    public void removeStudentFromCourse(Integer courseId, Integer personId) {
        Optional<Person> personOptional = personService.findPersonById(personId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        courseOptional.get().getPersons().remove(personOptional.get());
        courseRepository.save(courseOptional.get());
    }

    public void deleteCourse(Integer id) {


        Optional<Course> courseOptional = courseRepository.findById(id);

        for(Person person: courseOptional.get().getPersons()){
            personService.disenrollStudentFromCourse(person.getId(), courseOptional.get());
        }

        courseRepository.deleteById(id);
    }
}
