package com.example.converter.controller;

import com.example.converter.model.MatchTime;
import com.example.converter.model.UnformattedTime;
import com.example.converter.model.UnformattedTimeList;
import com.example.converter.service.TimeConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TimeConverterController {

    private final TimeConverterService timeConverterService;

    @GetMapping("/")
    public String checkIfRunning() {
        return "Hello Converter";
    }

    @PostMapping(value = "/")
    public String convertTime(@RequestBody UnformattedTime unformattedTime) {
        return timeConverterService.convertTimeToMatchTime(unformattedTime.getTime()).toString();
    }

    @PostMapping(value = "/list")
    public List<String> convertTimeList(@RequestBody UnformattedTimeList inputTime) {
        return timeConverterService.convertTimeListToMatchTime(inputTime).stream()
                .map(MatchTime::toString)
                .collect(Collectors.toList());
    }
}
