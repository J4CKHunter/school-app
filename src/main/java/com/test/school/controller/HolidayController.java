package com.test.school.controller;

import com.test.school.model.Holiday;
import com.test.school.service.HolidayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/holidays/{display}")
    public ModelAndView displayHolidays(@PathVariable String display){

        ModelAndView modelAndView = new ModelAndView("holidays.html");

        if(null != display && display.equals("all")){
            modelAndView.addObject("festival",true);
            modelAndView.addObject("federal",true);
        }else if(null != display && display.equals("federal")){
            modelAndView.addObject("federal",true);
        }else if(null != display && display.equals("festival")){
            modelAndView.addObject("festival",true);
        }

        Iterable<Holiday> holidays = holidayService.getAllHolidays();

        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(), false)
                .collect(Collectors.toList());

        Holiday.HolidayType[] types = Holiday.HolidayType.values();

        for (Holiday.HolidayType type : types) {
            modelAndView.addObject(type.toString(),
                    (holidayList.stream()
                            .filter(holiday -> holiday.getHolidayType().equals(type))
                            .collect(Collectors.toList())));
        }

        return modelAndView;
    }
}
