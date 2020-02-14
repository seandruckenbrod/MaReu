package com.example.mareu.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.mareu.R;
import com.example.mareu.model.FilterMeetingEvent;
import com.example.mareu.model.ResetMeetingEvent;
import com.example.mareu.service.DI;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.view.ListMeetingPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListMeetingActivity extends AppCompatActivity {
    @BindView(R.id.addMeetingButton)
    FloatingActionButton mAddButton;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.sort_menu)
    LinearLayout mSort_Menu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.day_max)
    NumberPicker mDay_max;
    @BindView(R.id.day_min)
    NumberPicker mDay_min;
    @BindView(R.id.month_max)
    NumberPicker mMonth_max;
    @BindView(R.id.month_min)
    NumberPicker mMonth_min;
    @BindView(R.id.hour_max)
    NumberPicker mHour_max;
    @BindView(R.id.hour_min)
    NumberPicker mHour_min;
    @BindView(R.id.year_max)
    NumberPicker mYear_max;
    @BindView(R.id.year_min)
    NumberPicker mYear_min;
    @BindView(R.id.minutes_max)
    NumberPicker mMinutes_max;
    @BindView(R.id.minutes_min)
    NumberPicker mMinutes_min;
    @BindView(R.id.salle_sort)
    EditText mSort_salles;
    @BindView(R.id.filter)
    Button mFilterButton;
    @BindView(R.id.reset)
    Button mResetButton;

    ListMeetingPagerAdapter pagerAdapter;
    MeetingApiService apiService = DI.getMeetingApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setNumberPicker();
        setNumberPickerDate();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewMeetingDialog();
            }
        });

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalTime minTime = new LocalTime(mHour_min.getValue(), mMinutes_min.getValue());
                LocalTime maxTime = new LocalTime(mHour_max.getValue(), mMinutes_max.getValue());
                LocalDate minDate = new LocalDate(mYear_min.getValue(), mMonth_min.getValue(), mDay_min.getValue());
                LocalDate maxDate = new LocalDate(mYear_max.getValue(), mMonth_max.getValue(), mDay_max.getValue());
                if (minTime.isBefore(maxTime))
                    apiService.sortTimeMeetings(minTime, maxTime);
                if (minDate.isBefore(maxDate))
                    apiService.sortDateMeetings(minDate, maxDate);
                if (mSort_salles.getText().length() > 1)
                    apiService.sortSalleMeetings(mSort_salles.getText().toString());
                EventBus.getDefault().post(new FilterMeetingEvent());
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ResetMeetingEvent());
            }
        });
        pagerAdapter = new ListMeetingPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sort, menu);
        return true;
    }

    private void showNewMeetingDialog() {
        FragmentManager fm = getSupportFragmentManager();

        NewMeetingDialogFragment newMeetingDialogFragment = NewMeetingDialogFragment.newInstance("New Meeting");
        newMeetingDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        newMeetingDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setNumberPicker() {
        mHour_max.setMinValue(0);
        mHour_min.setMinValue(0);
        mMinutes_max.setMinValue(0);
        mMinutes_min.setMinValue(0);
        mHour_max.setMaxValue(23);
        mHour_min.setMaxValue(23);
        mMinutes_max.setMaxValue(3);
        mMinutes_min.setMaxValue(3);

        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value * 15;
                return "" + temp;
            }
        };
        mMinutes_max.setFormatter(formatter);
        mMinutes_min.setFormatter(formatter);
    }

    private void setNumberPickerDate() {
        mDay_max.setMinValue(1);
        mDay_min.setMinValue(1);
        mMonth_max.setMinValue(1);
        mMonth_min.setMinValue(1);
        mYear_max.setMinValue(2020);
        mYear_min.setMinValue(2020);
        mDay_max.setMaxValue(31);
        mDay_min.setMaxValue(31);
        mMonth_max.setMaxValue(12);
        mMonth_min.setMaxValue(12);
        mYear_max.setMaxValue(2030);
        mYear_min.setMaxValue(2030);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                if (mSort_Menu.getVisibility() == View.VISIBLE)
                    mSort_Menu.setVisibility(View.GONE);
                else
                    mSort_Menu.setVisibility(View.VISIBLE);
                return true;
            default:

                return super.onOptionsItemSelected(item);

        }
    }

    public void onDestroy() {
        super.onDestroy();
        apiService.clearMeetingsList();
    }
}