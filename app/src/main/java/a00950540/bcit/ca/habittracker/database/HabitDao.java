package a00950540.bcit.ca.habittracker.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.sql.Time;
import java.util.List;

@SuppressWarnings("unused")
@Dao
public interface HabitDao {
    @Query("SELECT * FROM habit")
    List<Habit> getAll();

    @Query("SELECT * FROM habit WHERE habitId IN (:habitIds)")
    List<Habit> loadAllByIds(int[] habitIds);

    @Query("SELECT * FROM habit WHERE habit_name LIKE :name " + " LIMIT 1")
    Habit findByName(String name);

    @Query("SELECT * FROM habit WHERE habitId LIKE :id " + " LIMIT 1")
    Habit findById(int id);

    @Query("UPDATE habit SET habit_tracking = :habitTracking WHERE habitId = :id")
    void updateHabitTracking(int id, String habitTracking);

    @Query("UPDATE habit SET time_notification_on = :on WHERE habitId = :id")
    void updateTimeNotificationOn(int id, boolean on);

    @Query("UPDATE habit SET time_notification = :time WHERE habitId = :id")
    void updateTimeNotification(int id, Time time);

    @Query("UPDATE habit SET location_notification_on = :on WHERE habitId = :id")
    void updateLocationNotificationOn(int id, boolean on);

    @Query("UPDATE habit SET location_notification_latitude = :latitude WHERE habitId = :id")
    void updateLocationNotificationLatitude(int id, float latitude);
    @Query("UPDATE habit SET location_notification_longitude = :longitude WHERE habitId = :id")
    void updateLocationNotificationLongitude(int id, float longitude);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Habit... habits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Habit habit);

    @Delete
    void delete(Habit habit);
}
