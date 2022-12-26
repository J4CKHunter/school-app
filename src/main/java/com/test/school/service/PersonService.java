package com.test.school.service;

import com.test.school.dto.CreateNewUserRequest;
import com.test.school.dto.DisplayAllStudentsResponse;
import com.test.school.dto.ProfileDto;
import com.test.school.dto.converter.DisplayAllStudentsResponseConverter;
import com.test.school.dto.converter.PersonConverter;
import com.test.school.model.Course;
import com.test.school.model.CourseClass;
import com.test.school.model.Person;
import com.test.school.model.Role;
import com.test.school.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final RoleService roleService;
    private final PersonConverter personConverter;

    private final DisplayAllStudentsResponseConverter displayAllStudentsResponseConverter;

    public PersonService(PersonRepository personRepository,
                         RoleService roleService,
                         PersonConverter personConverter,
                         DisplayAllStudentsResponseConverter displayAllStudentsResponseConverter) {

        this.personRepository = personRepository;
        this.roleService = roleService;
        this.personConverter = personConverter;
        this.displayAllStudentsResponseConverter = displayAllStudentsResponseConverter;
    }


    public void disenrollStudentInTheClass(Integer personId){
        Optional<Person> personOptional = personRepository.findById(personId);
        personOptional.get().setCourseClass(null);

        //burayı /deleteClass methodunda yazmış, /deleteStudent(fromClass) methodunda yazmamış
        personRepository.save(personOptional.get());
    }

    public Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public void enrollStudentInTheClass(Person person, Integer classId, Optional<CourseClass> courseClass){
        person.setCourseClass(courseClass.get());
        personRepository.save(person);
    }

    public Optional<Person> findPersonById(Integer id) {
        return personRepository.findById(id);
    }

    public void enrollStudentToCourse(Person person, Integer courseId, Optional<Course> course) {
        person.getCourses().add(course.get());
        personRepository.save(person);

    }


    public void disenrollStudentFromCourse(Integer personId, Course course) {
        Optional<Person> personOptional = findPersonById(personId);
        personOptional.get().getCourses().remove(course);
        personRepository.save(personOptional.get());
    }


    public boolean createNewUser(CreateNewUserRequest createPerson) {

        boolean isSaved = false;

        Person person = personConverter.convert(createPerson);

        Role role = roleService.getByRoleType(Role.RoleType.STUDENT);
        person.setRole(role);

        Person savedPerson = personRepository.save(person);

        if(savedPerson != null && person.getId() > 0)
            isSaved = true;

        return isSaved;



    }

    public Person updatePersonUsingProfile(Person person, ProfileDto profile) {
        return personConverter.update(person, profile);
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }


    public void deletePerson(Integer personId) {
        personRepository.deleteById(personId);
    }

    public List<DisplayAllStudentsResponse> findAllStudents() {

        Role studentRole = roleService.getByRoleType(Role.RoleType.STUDENT);
        List<Person> studentList = personRepository.findAllByRole(studentRole);

        return studentList.stream()
                .map(displayAllStudentsResponseConverter::convert)
                .collect(Collectors.toList());
    }
}
