package com.example.mareu.controller;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.RefreshMeetingEvent;
import com.example.mareu.model.Salle;
import com.example.mareu.service.DI;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class NewMeetingDialogFragment extends DialogFragment {
    private Spinner mSalles;
    private EditText mSujet;
    private EditText mParticipants;
    private EditText mName;
    private Button mButton;

    private MeetingApiService apiService;

    private List<Salle> salles;
    private Boolean creation = true;
    private Meeting newMeeting;
    private NumberPicker timeHour;
    private NumberPicker timeMinute;
    private NumberPicker dateDay;
    private NumberPicker dateYear;
    private NumberPicker dateMonth;


    public NewMeetingDialogFragment() {
    }

    public static NewMeetingDialogFragment newInstance(String title) {
        NewMeetingDialogFragment frag = new NewMeetingDialogFragment();
        Bundle args = new Bundle();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = DI.getMeetingApiService();
        salles = apiService.getSalles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_fragment_new_meeting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mParticipants = (EditText) view.findViewById(R.id.participants);
        mName = (EditText) view.findViewById(R.id.name);
        mSujet = (EditText) view.findViewById(R.id.sujet);
        mSalles = (Spinner) view.findViewById(R.id.salle);
        mButton = (Button) view.findViewById(R.id.newMeeting);
        timeHour = (NumberPicker) view.findViewById(R.id.picker_hour);
        timeMinute = (NumberPicker) view.findViewById(R.id.picker_minute);
        dateDay = (NumberPicker) view.findViewById(R.id.picker_day);
        dateMonth = (NumberPicker) view.findViewById(R.id.picker_month);
        dateYear = (NumberPicker) view.findViewById(R.id.picker_year);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                if (creation == true) {
                    apiService.addMeeting(newMeeting);
                    EventBus.getDefault().post(new RefreshMeetingEvent());
                    dismiss();
                }
            }
        });
        setSpinner();
        setNumberPicker();
        setNumberPickerDate();
    }

    private void addData() {
        if (mSujet.getText() != null && mParticipants.getText() != null && mName.getText() != null && mParticipants.getText().toString().contains("@")) {
            newMeeting = new Meeting(
                    ConvertSalle(mSalles.getSelectedItem().toString()),
                    mName.getText().toString(),
                    mSujet.getText().toString(),
                    mParticipants.getText().toString(),
                    timeHour.getValue(),
                    timeMinute.getValue(),
                    dateYear.getValue(),
                    dateMonth.getValue(),
                    dateDay.getValue());

            creation = true;
        } else creation = false;

        if ((mSujet.getText() == null || mParticipants.getText() == null || mName.getText() == null || !mParticipants.getText().toString().contains("@"))) {
            Context context = getContext();
            CharSequence text = "Veuillez remplir correctement toutes les informations";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private Salle ConvertSalle(String string) {
        for (int i = 0; i < salles.size(); i++) {
            if (salles.get(i).getName().contentEquals(string)) {
                return salles.get(i);
            }
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow();
        }
    }

    public void setSpinner() {
        List<String> categories = new ArrayList<String>();
        categories.add(salles.get(0).getName().toString());
        categories.add(salles.get(1).getName().toString());
        categories.add(salles.get(2).getName().toString());
        categories.add(salles.get(3).getName().toString());
        categories.add(salles.get(4).getName().toString());
        categories.add(salles.get(5).getName().toString());
        categories.add(salles.get(6).getName().toString());
        categories.add(salles.get(7).getName().toString());
        categories.add(salles.get(8).getName().toString());
        categories.add(salles.get(9).getName().toString());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSalles.setAdapter(dataAdapter);
    }


    private void setNumberPicker() {
        timeHour.setMinValue(0);
        timeHour.setMaxValue(23);
        timeMinute.setMaxValue(3);
        timeMinute.setMinValue(00);

        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value * 15;
                return "" + temp;
            }
        };
        timeMinute.setFormatter(formatter);
    }

    private void setNumberPickerDate() {
        dateDay.setMinValue(1);
        dateDay.setMaxValue(31);
        dateMonth.setMinValue(1);
        dateMonth.setMaxValue(12);
        dateYear.setMinValue(2020);
        dateYear.setMaxValue(2030);
    }

}