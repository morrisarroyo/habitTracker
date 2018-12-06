package a00950540.bcit.ca.habittracker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import a00950540.bcit.ca.habittracker.database.Habit;

import static android.app.Activity.RESULT_OK;

public class HabitSettingsDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
    {
    // Use this instance of the interface to deliver action events
    private static final int PLACE_PICKER_REQUEST = 1;
    private HabitSettingsDialogFragment.NoticeDialogListener mListener;
    private Switch timeNotificationSwitch;
    private Switch locationNotificationSwitch;
    private ImageButton locationPicker;
    private ImageButton timePicker;
    private EditText latitude;
    private EditText longitude;
    private EditText time;


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Habit habit = ((StatisticActivity)getActivity()).habit;
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.habit_settings_dialog, null);
        latitude = view.findViewById(R.id.editText_latitude);
        longitude = view.findViewById(R.id.editText_longitude);
        time = view.findViewById(R.id.editText_time);
        timeNotificationSwitch = view.findViewById(R.id.switch_timeNotification);
        locationNotificationSwitch = view.findViewById(R.id.switch_locationNotification);
        if (habit.getLocationNotificationLatitude() != 0)
            latitude.setText(Float.toString(habit.getLocationNotificationLatitude()));
        if (habit.getLocationNotificationLongitude() != 0)
            longitude.setText(Float.toString(habit.getLocationNotificationLongitude()));
        if (habit.getTimeNotification() != null)
            time.setText(habit.getTimeNotification().toString());
        timeNotificationSwitch.setChecked(habit.isTimeNotificationOn());
        locationNotificationSwitch.setChecked(habit.isLocationNotificationOn());
        timePicker = view.findViewById(R.id.imageButton_timePicker);
        final TimePickerDialog.OnTimeSetListener listener = this;
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.SetListener(listener);
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });
        locationPicker = view.findViewById(R.id.imageButton_locationPicker);
        locationPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    System.err.println(e.toString());
                }
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                .setPositiveButton(R.string.string_done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(HabitSettingsDialogFragment.this);
                    }
                });
        return builder.create();
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String str = hourOfDay + ":" + minute;
        time.setText(str);
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (HabitSettingsDialogFragment.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    public boolean getLocationNotificationOn() {
        return locationNotificationSwitch.isChecked();
    }

    public boolean getTimeNotificationOn() {
        return timeNotificationSwitch.isChecked();
    }

    public float getLocationLatitude() {
        return latitude.getText().toString().length() > 0 ? Float.parseFloat(latitude.getText().toString()) : 0;
    }

    public float getLocationLongitude() {
        return longitude.getText().toString().length() > 0 ? Float.parseFloat(longitude.getText().toString()) : 0;
    }

    public Time getTime() {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm", Locale.US);
        Date temp = new Date();
        Date date = (Date)temp.clone();
        try {
           date = sf.parse(time.getText().toString());
        } catch (ParseException pe) {
            System.err.println(pe.toString());
        }
        return !date.equals(temp)?new Time(date.getTime()):new Time(0);
    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                double latitudeValue = place.getLatLng().latitude;
                double longitudeValue = place.getLatLng().longitude;
                latitude.setText(Double.toString(latitudeValue));
                longitude.setText(Double.toString(longitudeValue));

                //String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
