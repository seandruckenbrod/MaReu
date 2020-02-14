package com.example.mareu;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.mareu.controller.ListMeetingActivity;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.DI;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.utils.DeleteViewAction;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule = new ActivityTestRule(ListMeetingActivity.class);
    // This is fixed
    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    private Meeting meeting1 = new Meeting(mMeetingApiService.getSalles().get(1), "Réunion A", "Sujet de test3", "Test1@test.com", 10, 15, 2020, 2, 10);
    private Meeting meeting2 = new Meeting(mMeetingApiService.getSalles().get(0), "Réunion B", "Sujet de test1", "Test2@test.com", 15, 30, 2020, 8, 20);
    private Meeting meeting3 = new Meeting(mMeetingApiService.getSalles().get(2), "Réunion B", "Sujet de test2", "Test2@trst.com", 20, 45, 2020, 11, 30);
    private ListMeetingActivity mActivity;

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mMeetingApiService.addMeeting(meeting1);
        mMeetingApiService.addMeeting(meeting2);
        mMeetingApiService.addMeeting(meeting3);
    }

    @Test
    public void AddingMeeting_shouldDisplayMeeting (){
        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(matches(isDisplayed()));
        onView(withId(R.id.addMeetingButton))
                .perform(click());
        onView(withId(R.id.name))
                .perform(replaceText("Test"));
        onView(withId(R.id.participants))
                .perform(replaceText("test@gmail.com"));
        onView(withId(R.id.sujet))
                .perform(replaceText("Test"));
        onView(withId(R.id.newMeeting))
                .perform(click());
        onView(allOf(withId(R.id.list_meeting)))
                .check(withItemCount(4));
    }

    @Test
    public void FilterOption_sortByDate_shouldSortItem() {
        int ITEMS_COUNT = mMeetingApiService.getMeetings().size();
        org.joda.time.LocalDate minDate = new org.joda.time.LocalDate(2020, 2, 8);
        org.joda.time.LocalDate maxDate = new LocalDate(2020, 2, 15);
        mMeetingApiService.sortDateMeetings(minDate, maxDate);
        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT - 2));
    }

    @Test
    public void FilterOption_sortByTime_shouldSortItem() {
        int ITEMS_COUNT = mMeetingApiService.getMeetings().size();
        LocalTime minTime = new LocalTime(9, 0);
        LocalTime maxTime = new LocalTime(12, 0);
        mMeetingApiService.sortTimeMeetings(minTime, maxTime);
        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT - 2));
    }

    @Test
    public void FilterOption_sortBySalle_shouldSortItem() {
        int ITEMS_COUNT = mMeetingApiService.getMeetings().size();
        String salleToFilter = mMeetingApiService.getMeetings().get(0).getSalle().getName();
        mMeetingApiService.sortSalleMeetings(salleToFilter);
        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT - 2));
    }

    @Test
    public void FilterOption_resetFilter_shouldResetList() {
        mMeetingApiService.resetFilter();
        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(withItemCount(mMeetingApiService.getMeetings().size()));
    }

    @Test
    public void ListMeetings_deleteAction_shouldRemoveItem() {
        int ITEMS_COUNT = mMeetingApiService.getMeetings().size();

        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));

        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        onView(allOf(withId(R.id.list_meeting), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT - 1));
    }

}