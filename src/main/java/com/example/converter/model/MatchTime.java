package com.example.converter.model;

import java.util.Optional;

public class MatchTime {

    private final String matchTime;

    public MatchTime(int minutes, int seconds, int milliseconds, Optional<Period> period) {
        this.matchTime = period.map(p -> timeToMatchTime(p, minutes, seconds, milliseconds)).orElse("INVALID");
    }

    public MatchTime() {
        this.matchTime = "INVALID";
    }


    public String toString() {
        return this.matchTime;
    }

    private String secondMillisecondConverter(int seconds, int milliseconds) {
        int aux;

        if (milliseconds < 500) {
            aux = seconds;
        } else {
            aux = seconds + 1;
        }
        if (aux >= 10) return String.valueOf(aux);

        return "0" + aux;
    }

    private String timeToMatchTime(Period period, int minutes, int seconds, int milliseconds) {
        var time = switch (period) {
            case PM -> {
                if (minutes != 0 || seconds != 0 || milliseconds != 0) yield "INVALID";

                yield "00:00";
            }
            case H1 -> {

                String secondsTime = secondMillisecondConverter(seconds, milliseconds);
                if (minutes < 45) {
                    if (minutes > 10) {
                        yield minutes + ":" + secondsTime;
                    }
                    yield "0" + minutes + ":" + secondsTime;
                } else {
                    yield "45:00 +" + (minutes - 45) + ":" + secondsTime;
                }

            }
            case HT -> {
                if (minutes != 45 || seconds != 0 || milliseconds != 0) yield "INVALID";
                yield "45:00";
            }
            case H2 -> {
                if (minutes < 45) yield "INVALID";

                String secondsTime = secondMillisecondConverter(seconds, milliseconds);
                if (minutes < 90) {
                    yield minutes + ":" + secondsTime;
                } else {
                    yield "90:00 +" + (minutes - 90) + ":" + secondsTime;
                }
            }
            case FT -> {
                if (minutes != 90 || seconds != 0 || milliseconds != 0) yield "INVALID";
                yield "90:00 +00:00";
            }
        };

        return time.equals("INVALID") ? "INVALID" : time + " - " + period.label;
    }
}
