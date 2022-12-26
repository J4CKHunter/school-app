package com.test.school.repository;

import com.test.school.model.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseClassRepository extends JpaRepository<CourseClass, Integer> {
}
