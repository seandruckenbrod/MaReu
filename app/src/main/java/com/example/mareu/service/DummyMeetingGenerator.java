package com.example.mareu.service;


import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class DummyMeetingGenerator {
    private static List<Meeting> DUMMY_MEETING = Arrays.asList(
    );

    public static List<Meeting> generateMeetings() {
        return new ArrayList<Meeting>(DUMMY_MEETING);
    }
}
