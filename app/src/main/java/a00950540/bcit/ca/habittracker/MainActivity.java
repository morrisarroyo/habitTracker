package a00950540.bcit.ca.habittracker;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import a00950540.bcit.ca.habittracker.NewHabitDialogFragment.NoticeDialogListener;
import a00950540.bcit.ca.habittracker.database.AppDatabase;
import a00950540.bcit.ca.habittracker.database.Habit;

public class MainActivity extends AppCompatActivity implements NoticeDialogListener {

    private static final String TAG = "MainActivity";

    //private ArrayList<String> habitNames = new ArrayList<>();
    //private ArrayList<String> habitDaysList = new ArrayList<>();
    //private TextView[] dayHeaders;
    private HabitRecyclerViewAdapter habitRecyclerViewAdapter;
    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //RecyclerView recyclerView =  findViewById(R.id.recyclerView_habit_main);
        setSupportActionBar(toolbar);
        db = AppDatabase.getInstance(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoticeDialog();
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)

                        .setAction("Action", null).show();
                        */
            }
        });
        initDayHeaders();
        //initHabitNames();
        //initHabitDaysList();
        initRecyclerView();
    }

    private void initDayHeaders()
    {
        TextView[] dayHeaders = new TextView[5];
        dayHeaders[0] = findViewById(R.id.textView_day0_header);
        dayHeaders[1] = findViewById(R.id.textView_day1_header);
        dayHeaders[2] = findViewById(R.id.textView_day2_header);
        dayHeaders[3] = findViewById(R.id.textView_day3_header);
        dayHeaders[4] = findViewById(R.id.textView_day4_header);
        String myFormat = "EEE\nMMM dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar cal = Calendar.getInstance();
        for (TextView dayHeader : dayHeaders) {
            Date now = cal.getTime();
            dayHeader.setText(sdf.format(now));
            cal.add(Calendar.DATE, -1);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    /*
    private void initHabitNames() {
        habitNames.add("Wake-up");
        habitNames.add("Fix bed");
        habitNames.add("Brush");
        habitNames.add("Shower");
        habitNames.add("Eat");
    }

    private void initHabitDaysList() {
        habitDaysList.add("10101010101");
        habitDaysList.add("10111010101");
        habitDaysList.add("11101010101");
        habitDaysList.add("11011010101");
        habitDaysList.add("11111010101");
    }
*/

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView =  findViewById(R.id.recyclerView_habit_main);
        List<Habit> habits = db.habitDao().getAll();

        for(Habit habit: habits) {
            Log.d("initRecyclerView-MainActivity", habit.getHabitName());
        }
        habitRecyclerViewAdapter = new HabitRecyclerViewAdapter(habits, this);
        recyclerView.setAdapter(habitRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new NewHabitDialogFragment();
        dialog.show(getSupportFragmentManager(), "NewHabitDialogFragment");
        Log.d("showNoticeDialog", "Open dialog");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d("onDialogPositiveClick", "Enter");

        final Habit newHabit = new Habit(((NewHabitDialogFragment)dialog).getHabitName(), Habit.NEW_HABIT_TRACKING, new Date());
        Log.d("onDialogPositiveClick", newHabit.getHabitName());
        db.habitDao().insert(newHabit);

        habitRecyclerViewAdapter.reload();

        //habitRecyclerViewAdapter.habits.add(newHabit);
        //habitRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
