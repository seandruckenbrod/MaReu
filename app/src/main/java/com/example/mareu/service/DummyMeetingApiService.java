package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Salle;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;


public class DummyMeetingApiService implements MeetingApiService {
    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private List<Salle> salles = DummySallesGenerator.generateSalles();
    private List<Meeting> meetingsStock = DummyMeetingGenerator.generateMeetings();
    private Boolean filter = false;


    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeetings(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void clearMeetingsList() {
        meetings.clear();
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public List<Salle> getSalles() {
        return salles;
    }

    @Override
    public void sortTimeMeetings(LocalTime minTime, LocalTime maxTime) {
        filter = true;
        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getTime().isBefore(minTime) || meetings.get(i).getTime().isAfter(maxTime)) {
                meetingsStock.add(meetings.get(i));
                meetings.remove(meetings.get(i));
                i--;
            }
        }
    }

    @Override
    public void sortDateMeetings(LocalDate minDate, LocalDate maxDate) {
        filter = true;
        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getDate().isBefore(minDate) || meetings.get(i).getDate().isAfter(maxDate)) {
                meetingsStock.add(meetings.get(i));
                meetings.remove(meetings.get(i));
                i--;
            }
        }
    }


    @Override
    public void sortSalleMeetings(String salleName) {
        filter = true;
        for (int i = 0; i < meetings.size(); i++) {
            if (!meetings.get(i).getSalle().getName().contains(salleName)) {
                meetingsStock.add(meetings.get(i));
                meetings.remove(meetings.get(i));
                i--;
            }
        }
    }

    @Override
    public void resetFilter() {
        if (filter) {
            meetings.addAll(meetingsStock);
            meetingsStock.removeAll(meetingsStock);
            filter = false;
        }
    }
}



