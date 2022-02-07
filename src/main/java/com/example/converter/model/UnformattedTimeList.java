package com.example.converter.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class UnformattedTimeList {
    private List<String> timeList = new LinkedList<>();
}
