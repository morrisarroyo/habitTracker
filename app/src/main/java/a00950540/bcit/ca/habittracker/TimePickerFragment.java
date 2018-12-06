package a00950540.bcit.ca.habittracker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import static android.icu.util.Calendar.*;

public class TimePickerFragment extends DialogFragment{
    private TimePickerDialog.OnTimeSetListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = getInstance();
        int hour = c.get(HOUR_OF_DAY);
        int minute = c.get(MINUTE);
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), listener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void SetListener(TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }
}