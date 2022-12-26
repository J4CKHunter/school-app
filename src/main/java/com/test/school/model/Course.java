package com.test.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity{

    private String name;

    private String fees;

    @ManyToMany(mappedBy = "courses"/*, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST*/)
    private Set<Person> persons = new HashSet<>();
}
