package com.leonmontealegre.triptracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePicker.OnDateChangedListener onDateChangedListener;
    private DialogInterface.OnCancelListener onCancelListener;

    private Date currentDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.onDateChangedListener.onDateChanged(view, year, month, day);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        this.onCancelListener.onCancel(dialog);
    }

    public void setDate(Date date) {
        this.currentDate = date;
    }

    public void setOnDateChangedListener(DatePicker.OnDateChangedListener listener) {
        this.onDateChangedListener = listener;
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener listener) {
        this.onCancelListener = listener;
    }

}