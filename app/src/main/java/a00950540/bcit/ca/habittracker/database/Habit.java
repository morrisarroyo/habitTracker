package a00950540.bcit.ca.habittracker.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.icu.util.Calendar;

import java.sql.Time;
import java.util.Date;

@Entity(indices = {@Index(value = {"habit_name"},
        unique = true)})
public class Habit {
    @Ignore
    public static final String NEW_HABIT_TRACKING = "00000";

    @PrimaryKey(autoGenerate = true)
    private int habitId;

    @ColumnInfo(name = "habit_name")
    private String habitName;

    @ColumnInfo(name = "habit_tracking")
    private String habitTracking;

    @ColumnInfo(name = "habit_start_date")
    private Date habitStartDate;

    @ColumnInfo(name = "time_notification_on")
    private boolean timeNotificationOn;

    @ColumnInfo(name = "time_notification")
    private Time timeNotification;

    @ColumnInfo(name = "location_notification_on")
    private boolean locationNotificationOn;

    @ColumnInfo(name = "location_notification_latitude")
    private float locationNotificationLatitude;

    @ColumnInfo(name = "location_notification_longitude")
    private float locationNotificationLongitude;

    @Ignore
    public Habit(String habitName
            , String habitTracking
            , Date habitStartDate
            , boolean timeNotificationOn
            , Time timeNotification
            , boolean locationNotificationOn
            , float locationNotificationLatitude
            , float locationNotificationLongitude
    )
    {
        this.habitName = habitName;
        this.habitTracking = habitTracking;
        this.habitStartDate = habitStartDate;
        this.timeNotificationOn = timeNotificationOn;
        this.timeNotification = timeNotification;
        this.locationNotificationOn = locationNotificationOn;
        this.locationNotificationLatitude = locationNotificationLatitude;
        this.locationNotificationLongitude = locationNotificationLongitude;
    }

    public Habit(String habitName
            , String habitTracking
            , Date habitStartDate
    )
    {
        this.habitName = habitName;
        this.habitTracking = habitTracking;
        this.habitStartDate = habitStartDate;
        timeNotificationOn = false;
        timeNotification = new Time(0);
        locationNotificationOn = false;
        locationNotificationLatitude = 0;
        locationNotificationLongitude = 0;
    }

    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }
    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitTracking() {
        return habitTracking;
    }

    public void setHabitTracking(String habitTracking) {
        this.habitTracking = habitTracking;
    }

    public Date getHabitStartDate() {
        return habitStartDate;
    }

    public void setHabitStartDate(Date habitStartDate) {
        this.habitStartDate = habitStartDate;
    }

    public boolean isTimeNotificationOn() {
        return timeNotificationOn;
    }

    public void setTimeNotificationOn(boolean timeNotificationOn) {
        this.timeNotificationOn = timeNotificationOn;
    }

    public Time getTimeNotification() {
        return timeNotification;
    }

    public void setTimeNotification(Time timeNotification) {
        this.timeNotification = timeNotification;
    }

    public boolean isLocationNotificationOn() {
        return locationNotificationOn;
    }

    public void setLocationNotificationOn(boolean locationNotificationOn) {
        this.locationNotificationOn = locationNotificationOn;
    }

    public float getLocationNotificationLatitude() {
        return locationNotificationLatitude;
    }

    public void setLocationNotificationLatitude(float locationNotificationLatitude) {
        this.locationNotificationLatitude = locationNotificationLatitude;
    }

    public float getLocationNotificationLongitude() {
        return locationNotificationLongitude;
    }

    public void setLocationNotificationLongitude(float locationNotificationLongitude) {
        this.locationNotificationLongitude = locationNotificationLongitude;
    }

    public static Habit[] populateData() {
        Calendar c = Calendar.getInstance();
        c.set(2018, 12, 1);
        Date date = c.getTime();
        return new Habit[] {
            new Habit("Wake-up", "10101010101", date)
            , new Habit("Fix bed", "10111010101", date)
            , new Habit("Brush", "11101010101", date)
            , new Habit("Shower", "11011010101", date)
            , new Habit("Eat", "11111010101", date)
        };
    }
}
