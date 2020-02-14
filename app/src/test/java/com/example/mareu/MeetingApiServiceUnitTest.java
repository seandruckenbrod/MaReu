package com.example.mareu;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Salle;
import com.example.mareu.service.DI;
import com.example.mareu.service.DummySallesGenerator;
import com.example.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingApiServiceUnitTest {
    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingApiService();
        service.addMeeting(new Meeting(service.getSalles().get(0), "Réunion A", "Sujet test1", "Test1", 11, 30, 2020, 9, 9));
        service.addMeeting(new Meeting(service.getSalles().get(0), "Réunion B", "Sujet test2", "Test2", 18, 45, 2020, 2, 1));
        service.addMeeting(new Meeting(service.getSalles().get(0), "Réunion C", "Sujet test3", "Test3", 22, 15, 2020, 10, 16));
    }


    @Test
    public void addAndGetMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Meeting expectedMeeting = new Meeting(service.getSalles().get(0), "Réunion A", "Sujet test1", "Test1", 11, 30, 2020, 9, 9);
        service.addMeeting(expectedMeeting);
        assertTrue(meetings.contains(expectedMeeting));
    }

    @Test
    public void getSallesWithSuccess() {
        List<Salle> salles = service.getSalles();
        List<Salle> expectedSalles = DummySallesGenerator.generateSalles();
        assertThat(salles, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedSalles.toArray()));
    }

    @Test
    public void deleteMeetingsWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeetings(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void sortMeetingsBySalleWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Meeting expectedMeeting = new Meeting(service.getSalles().get(0), "Réunion A", "Sujet test1", "Test1", 8, 30, 2020, 9, 9);
        Meeting notExpectedMeeting = new Meeting(service.getSalles().get(1), "Réunion B", "Sujet test1", "Test1", 8, 30, 2020, 9, 9);
        meetings.add(expectedMeeting);
        meetings.add(notExpectedMeeting);
        assertTrue(meetings.contains(expectedMeeting));
        assertTrue(meetings.contains(notExpectedMeeting));
        String SalleToSort = "Mario";
        service.sortSalleMeetings(SalleToSort);
        assertTrue(meetings.contains(expectedMeeting));
        assertFalse(meetings.contains(notExpectedMeeting));
    }

    @Test
    public void sortMeetingsbyTimeWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Meeting expectedMeeting = new Meeting(service.getSalles().get(0), "Réunion A", "Sujet test1", "Test1", 8, 30, 2020, 9, 9);
        Meeting notExpectedMeeting = new Meeting(service.getSalles().get(0), "Réunion B", "Sujet test1", "Test1", 15, 15, 2020, 9, 9);
        meetings.add(expectedMeeting);
        meetings.add(notExpectedMeeting);
        assertTrue(meetings.contains(expectedMeeting));
        assertTrue(meetings.contains(notExpectedMeeting));
        LocalTime minTime = new LocalTime(8, 0);
        LocalTime maxTime = new LocalTime(10, 0);
        service.sortTimeMeetings(minTime, maxTime);
        assertTrue(meetings.contains(expectedMeeting));
        assertFalse(meetings.contains(notExpectedMeeting));
    }

    @Test
    public void sortMeetingsbyDateWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Meeting expectedMeeting = new Meeting(service.getSalles().get(0), "Réunion A", "Sujet test1", "Test1", 8, 30, 2020, 9, 9);
        Meeting notExpectedMeeting = new Meeting(service.getSalles().get(0), "Réunion B", "Sujet test1", "Test1", 8, 30, 2020, 2, 1);
        meetings.add(expectedMeeting);
        meetings.add(notExpectedMeeting);
        assertTrue(meetings.contains(expectedMeeting));
        assertTrue(meetings.contains(notExpectedMeeting));
        LocalDate minDate = new LocalDate(2020, 9, 1);
        LocalDate maxDate = new LocalDate(2020, 9, 15);
        service.sortDateMeetings(minDate, maxDate);
        assertTrue(meetings.contains(expectedMeeting));
        assertFalse(meetings.contains(notExpectedMeeting));
    }

    @Test
    public void resetMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Meeting Meeting2 = new Meeting(service.getSalles().get(1), "Réunion A", "Sujet test1", "Test1", 8, 30, 2020, 9, 9);
        Meeting Meeting1 = new Meeting(service.getSalles().get(2), "Réunion B", "Sujet test1", "Test1", 8, 30, 2020, 9, 9);
        meetings.add(Meeting1);
        meetings.add(Meeting2);
        assertTrue(meetings.contains(Meeting1));
        assertTrue(meetings.contains(Meeting2));
        String SalleToSort = "Mario";
        service.sortSalleMeetings(SalleToSort);
        assertFalse(meetings.contains(Meeting1));
        assertFalse(meetings.contains(Meeting2));
        service.resetFilter();
        assertTrue(meetings.contains(Meeting1));
        assertTrue(meetings.contains(Meeting2));
    }
}