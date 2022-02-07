package com.example.converter.service;

import com.example.converter.model.MatchTime;
import com.example.converter.model.Period;
import com.example.converter.model.UnformattedTimeList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.util.stream.Stream.of;

@Service
public class TimeConverterService {

    public List<MatchTime> convertTimeListToMatchTime(UnformattedTimeList unformattedTimeList) {
        return unformattedTimeList.getTimeList().stream()
                .map(this::convertTimeToMatchTime)
                .collect(Collectors.toList());
    }

    public MatchTime convertTimeToMatchTime(String unformattedTime) {
        var splittedTime = unformattedTime.split("[\\[\\]]");

        if (splittedTime.length != 3) {
            return new MatchTime();
        }

        var minutesSecondsMilliseconds = splittedTime[2].split("[.:]");

        if (minutesSecondsMilliseconds.length != 3) {
            return new MatchTime();
        }

        var period = findPeriod(splittedTime[1]);
        var minutes = parseInt(minutesSecondsMilliseconds[0].trim());
        var seconds = parseInt(minutesSecondsMilliseconds[1]);
        var milliseconds = parseInt(minutesSecondsMilliseconds[2]);

        return new MatchTime(minutes, seconds, milliseconds, period);
    }

    private Optional<Period> findPeriod(String periodName) {
        return of(Period.values())
                .filter(p -> p.name().equals(periodName))
                .findFirst();
    }
}
