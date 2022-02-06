package com.example.converter.controller;

import com.example.converter.model.UnformattedTime;
import com.example.converter.model.UnformattedTimeList;
import com.example.converter.service.TimeConverterService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TimeConverterController {

    private final TimeConverterService timeConverterService;

    public TimeConverterController(TimeConverterService timeConverterService) {
        this.timeConverterService = timeConverterService;
    }

    @GetMapping("/")
    public String get(){
        return "Hello Converter";
    }

    @PostMapping(value = "/list")
    public List<String> create(@RequestBody UnformattedTimeList inputTime) {
        var matchTimes = timeConverterService.convertTimeListToMatchTime(inputTime);

        var printTimes = new ArrayList<String>();
        matchTimes.forEach(time -> printTimes.add(time.toString()));

        return printTimes;
    }

    @PostMapping(value = "/")
    public String create(@RequestBody UnformattedTime unformattedTime) {
        return timeConverterService.convertTimeToMatchTime(unformattedTime.getTime()).toString();
    }
}
