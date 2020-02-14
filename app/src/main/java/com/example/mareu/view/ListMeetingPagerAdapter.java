package com.example.mareu.view;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mareu.controller.MeetingFragment;


public class ListMeetingPagerAdapter extends FragmentPagerAdapter {

    public ListMeetingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MeetingFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}