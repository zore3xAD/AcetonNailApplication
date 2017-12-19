package com.android.zore3x.acetonnailapplication.timetable;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.android.zore3x.acetonnailapplication.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DobrogorskiyAA on 18.12.2017.
 */

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_HOUR = "com.android.zore3x.acetonnailapplication.hour";
    public static final String EXTRA_MINUTE = "com.android.zore3x.acetonnailapplication.minute";

    private static final String ARG_TIME = "time";

    private TimePicker mTimePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        Date date = (Date)getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        mTimePicker = (TimePicker)v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Select visit time")
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getCurrentHour();
                        int minute = mTimePicker.getCurrentMinute();

                        Date dateArgs = (Date)getArguments().getSerializable(ARG_TIME);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dateArgs);
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        //Date date = new GregorianCalendar(year, month, day, hour, minute).getTime();

                        sendResult(Activity.RESULT_OK, hour, minute);
                    }
                })
                .create();
    }

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode, int hour, int minute) {
        Intent intent = new Intent();
        //intent.putExtra(EXTRA_TIME, date);
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MINUTE, minute);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
