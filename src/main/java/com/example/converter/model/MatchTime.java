package com.example.converter.model;

public class MatchTime {

    private int minutes;
    private int seconds;
    private int milliseconds;
    private Period period;
    private String matchTime;

    public MatchTime(int minutes, int seconds, int milliseconds, Period period) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        this.period = period;

        timeToMatchTime(period, minutes, seconds, milliseconds);
    }

    public MatchTime() {
        this.matchTime = "INVALID";
    }


    public String toString() {
        return this.matchTime;
    }

    private String secondMillisecondConverter(int seconds, int milliseconds){
        int aux;

        if (milliseconds < 500) {
            aux = seconds;
        }
        else {
            aux = seconds + 1;
        }
        if (aux >= 10)
            return String.valueOf(aux);

        return "0" + aux;
    }

    private void timeToMatchTime(Period period, int minutes, int seconds, int milliseconds) {
        if (period == null){
            this.matchTime = "INVALID";
            return;
        }

        String time = switch (period) {
            case PM -> {
                if (minutes != 0 || seconds != 0 || milliseconds != 0)
                    yield "INVALID";

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
                if (minutes != 45 || seconds != 0 || milliseconds != 0)
                    yield "INVALID";
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
                if (minutes != 90 || seconds != 0 || milliseconds != 0)
                    yield "INVALID";
                yield "90:00 +00:00";
            }
        };

        this.matchTime = time.equals("INVALID")
            ? "INVALID"
            : time + " - " + period.label;
    }
}
