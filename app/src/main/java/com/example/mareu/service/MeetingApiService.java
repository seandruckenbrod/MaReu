package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Salle;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;


public interface MeetingApiService {

    List<Meeting> getMeetings();

    void deleteMeetings(Meeting meeting);

    void addMeeting(Meeting meeting);

    void clearMeetingsList();

    List<Salle> getSalles();

    void sortTimeMeetings(LocalTime minDate, LocalTime maxDate);

    void sortDateMeetings(LocalDate minDate, LocalDate maxDate);

    void sortSalleMeetings(String SalleName);

    void resetFilter();
}
