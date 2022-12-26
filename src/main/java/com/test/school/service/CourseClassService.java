package com.test.school.service;

import com.test.school.dto.AddNewCourseClassRequest;
import com.test.school.dto.DisplayClassesResponse;
import com.test.school.dto.DisplayStudentsResponseClassOrCourseDto;
import com.test.school.dto.converter.DisplayClassesResponseConverter;
import com.test.school.dto.converter.DisplayStudentsResponseClassOrCourseDtoConverter;
import com.test.school.dto.converter.DisplayStudentsResponsePersonDtoConverter;
import com.test.school.model.CourseClass;
import com.test.school.model.Person;
import com.test.school.repository.CourseClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseClassService {

    private final PersonService personService;
    private final CourseClassRepository courseClassRepository;

    private final DisplayClassesResponseConverter displayClassesResponseConverter;

    private final DisplayStudentsResponseClassOrCourseDtoConverter displayStudentsResponseClassOrCourseDtoConverter;
    public CourseClassService(PersonService personService,
                              CourseClassRepository courseClassRepository,
                              DisplayClassesResponseConverter displayClassesResponseConverter,
                              DisplayStudentsResponsePersonDtoConverter displayStudentsResponsePersonDtoConverter, DisplayStudentsResponseClassOrCourseDtoConverter displayStudentsResponseClassOrCourseDtoConverter) {

        this.personService = personService;
        this.courseClassRepository = courseClassRepository;
        this.displayClassesResponseConverter = displayClassesResponseConverter;
        this.displayStudentsResponseClassOrCourseDtoConverter = displayStudentsResponseClassOrCourseDtoConverter;
    }

    public List<DisplayClassesResponse> listCourseClasses(){

        List<CourseClass> courseClasses = courseClassRepository.findAll();

        return courseClasses
                .stream()
                .map(displayClassesResponseConverter::convert)
                .collect(Collectors.toList());
    }

    public void addNewCourseClass(AddNewCourseClassRequest addNewCourseClassRequest){

        CourseClass courseClass = new CourseClass();
        courseClass.setName(addNewCourseClassRequest.getName());

        courseClassRepository.save(courseClass);
    }

    public void deleteCourseClass(Integer id){
        Optional<CourseClass> courseClassOptional = courseClassRepository.findById(id);

        for(Person person: courseClassOptional.get().getPersons()){
            personService.disenrollStudentInTheClass(person.getId());
        }

        courseClassRepository.deleteById(id);
    }

    public DisplayStudentsResponseClassOrCourseDto findCourseClassById(Integer id) {
        Optional<CourseClass> optionalCourseClass = courseClassRepository.findById(id);
        return displayStudentsResponseClassOrCourseDtoConverter.convert(optionalCourseClass.get());
    }

    public Optional<CourseClass> findCourseClassWithId(Integer id){
        return courseClassRepository.findById(id);
    }

    public void addNewStudentToClass(Integer classId, Person personFromDb) {
        Optional<CourseClass> courseClassOptional = courseClassRepository.findById(classId);
        courseClassOptional.get().getPersons().add(personFromDb);
        courseClassRepository.save(courseClassOptional.get());
    }

    public void removeStudentFromClass(Integer personId, Integer courseClassId){

        Optional<Person> personOptional = personService.findPersonById(personId);
        Optional<CourseClass> courseClassOptional = courseClassRepository.findById(courseClassId);
        courseClassOptional.get().getPersons().remove(personOptional.get());
        courseClassRepository.save(courseClassOptional.get());
    }

}
