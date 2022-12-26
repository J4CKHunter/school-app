package com.test.school.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
public class Holiday extends BaseEntity{

    private String day;
    private String reason;

    @Enumerated(EnumType.STRING)
    private HolidayType holidayType;

    public enum HolidayType{
        FEDERAL, FESTIVAL
    }
}


