package com.example.mareu.model;

public class DeleteMeetingEvent {

    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
