package com.example.mareu.model;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


public class Meeting {
    private LocalTime time;
    private LocalDate date;
    private Salle salle;
    private String name;
    private String sujet;
    private String participants;

    public Meeting(Salle salle, String name, String sujet, String participants, int Hour, int Minute, int Year, int Month, int Day) {
        this.salle = salle;
        this.name = name;
        this.sujet = sujet;
        this.participants = participants;
        this.time = new LocalTime(Hour, Minute);
        this.date = new LocalDate(Year, Month, Day);
    }

    public String getName() {
        return name + " - " + time.getHourOfDay() + "h" + time.getMinuteOfHour() * 15 + " - " + salle.getName();
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }


}
