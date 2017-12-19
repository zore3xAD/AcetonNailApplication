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
import android.widget.DatePicker;

import com.android.zore3x.acetonnailapplication.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DobrogorskiyAA on 18.12.2017.
 */

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_YEAR = "com.android.zore3x.acetonnailapplication.year";
    public static final String EXTRA_MONTH = "com.android.zore3x.acetonnailapplication.month";
    public static final String EXTRA_DAY = "com.android.zore3x.acetonnailapplication.day";

    private final static String ARG_DATE = "date";

    private DatePicker mDatePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        Date date = (Date)getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = (DatePicker)view.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Select visit date")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();

                        //Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, year, month, day);
                    }
                })
                .create();
    }

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, int year, int month, int day) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_YEAR, year);
        intent.putExtra(EXTRA_MONTH, month);
        intent.putExtra(EXTRA_DAY, day);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
