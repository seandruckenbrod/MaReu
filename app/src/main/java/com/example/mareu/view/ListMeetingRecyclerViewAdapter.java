package com.example.mareu.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.R;
import com.example.mareu.model.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMeetingRecyclerViewAdapter extends RecyclerView.Adapter<ListMeetingRecyclerViewAdapter.ViewHolder> {
    private final List<Meeting> meetings;

    public ListMeetingRecyclerViewAdapter(List<Meeting> items) {
        meetings = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Meeting meeting = meetings.get(position);
        holder.mMeetingName.setText(meeting.getName());
        if (holder.mDescription.length() < 37) {
            holder.mDescription.setText(meeting.getParticipants() + "...");
        } else if (holder.mDescription.length() >= 37) {
            holder.mDescription.setText(meeting.getParticipants());
        }
        Glide.with(holder.mAvatar.getContext())
                .load(meeting.getSalle().getColor())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_emails)
        public TextView mDescription;
        @BindView(R.id.item_list_name)
        public TextView mMeetingName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;
        @BindView(R.id.item_list_avatar)
        public ImageView mAvatar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
