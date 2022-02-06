package com.example.converter.model;

public enum Period {
    PM("PRE_MATCH"),
    H1("FIRST_HALF"),
    HT("HALF_TIME"),
    H2("SECOND_HALF"),
    FT("FULL_TIME");

    public final String label;

    private Period(String label) {
        this.label = label;
    }
}