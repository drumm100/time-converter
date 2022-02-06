package com.example.converter.service;
import com.example.converter.model.MatchTime;
import com.example.converter.model.Period;
import com.example.converter.model.UnformattedTimeList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeConverterService {

    public List<MatchTime> convertTimeListToMatchTime(UnformattedTimeList unformattedTimeList){
        var matchTimes = new ArrayList<MatchTime>();
        unformattedTimeList.getTimeList().forEach(unformattedTime -> matchTimes.add(convertTimeToMatchTime(unformattedTime)));

        return matchTimes;
    }

    public MatchTime convertTimeToMatchTime(String unformattedTime){
        String[] splittedTime = unformattedTime.split("[\\[\\]]");

        if (splittedTime.length != 3) {
            return new MatchTime();
        }

        var minutesSecondsMilliseconds = splittedTime[2].split("[.:]");

        Period period = findPeriod(splittedTime[1]);
        int minutes = Integer.parseInt(minutesSecondsMilliseconds[0].trim());
        int seconds = Integer.parseInt(minutesSecondsMilliseconds[1]);
        int milliseconds = Integer.parseInt(minutesSecondsMilliseconds[2]);

        return new MatchTime(minutes, seconds, milliseconds, period);
    }

    private Period findPeriod(String periodName) {
        for (Period elem : Period.values()){
            if (elem.name().equals(periodName) )
                return  elem;
        }
        return null;
    }
}
