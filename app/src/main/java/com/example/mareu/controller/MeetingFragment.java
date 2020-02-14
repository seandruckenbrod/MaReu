package com.example.mareu.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.DeleteMeetingEvent;
import com.example.mareu.model.FilterMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.RefreshMeetingEvent;
import com.example.mareu.model.ResetMeetingEvent;
import com.example.mareu.service.DI;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.view.ListMeetingRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class MeetingFragment extends Fragment {
    private MeetingApiService apiService;
    private List<Meeting> meetings;
    private RecyclerView recyclerView;

    public static MeetingFragment newInstance() {
        MeetingFragment fragment = new MeetingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = DI.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();
        return view;
    }

    public void initList() {
        meetings = apiService.getMeetings();
        recyclerView.setAdapter(new ListMeetingRecyclerViewAdapter(meetings));
    }

    @Override
    public void onStart() {
        super.onStart();
        initList();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        apiService.deleteMeetings(event.meeting);
        initList();
    }

    @Subscribe
    public void onResetMeeting(ResetMeetingEvent event) {
        apiService.resetFilter();
        initList();
    }

    @Subscribe
    public void onFilterMeeting(FilterMeetingEvent event) {
        initList();
    }


    @Subscribe
    public void OnRefreshMeeting (RefreshMeetingEvent event) {initList();} }
