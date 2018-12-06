package a00950540.bcit.ca.habittracker;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import a00950540.bcit.ca.habittracker.database.Habit;

public class StatisticActivity extends AppCompatActivity implements HabitSettingsDialogFragment.NoticeDialogListener {

    public Habit habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Toolbar toolbar = findViewById(R.id.statistic_toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        habit = MainActivity.db.habitDao().findById(i.getIntExtra("habitId", 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new HabitSettingsDialogFragment();
        dialog.show(getSupportFragmentManager(), "HabitSettingsDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        HabitSettingsDialogFragment hs = ((HabitSettingsDialogFragment)dialog);
        boolean timeNotifOn = hs.getTimeNotificationOn();
        boolean locationNotifOn = hs.getLocationNotificationOn();
        MainActivity.db.habitDao()
                .updateTimeNotificationOn(habit.getHabitId(), timeNotifOn);
        MainActivity.db.habitDao()
                .updateLocationNotificationOn(habit.getHabitId(), locationNotifOn);
        if (timeNotifOn) {
            MainActivity.db.habitDao()
                    .updateTimeNotification(habit.getHabitId(), hs.getTime());
        }
        if (locationNotifOn) {
            MainActivity.db.habitDao()
                    .updateLocationNotificationLatitude(habit.getHabitId(), hs.getLocationLatitude());
            MainActivity.db.habitDao()
                    .updateLocationNotificationLongitude(habit.getHabitId(), hs.getLocationLongitude());
        }
        habit =  MainActivity.db.habitDao().findById(habit.getHabitId());
    }
}
