package com.test.school.repository;

import com.test.school.model.Person;
import com.test.school.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByEmail(String email);
    List<Person> findAllByRole(Role role);
}
