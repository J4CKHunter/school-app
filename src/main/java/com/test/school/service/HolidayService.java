package com.test.school.service;

import com.test.school.model.Holiday;
import com.test.school.repository.HolidayRepository;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }


    public Iterable<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }
}
