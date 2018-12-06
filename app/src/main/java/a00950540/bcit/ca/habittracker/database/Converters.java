package a00950540.bcit.ca.habittracker.database;

import android.arch.persistence.room.TypeConverter;

import java.sql.Time;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Time fromTimeLong(Long value) {
        return value == null ? null : new Time(value);
    }

    @TypeConverter
    public static Long timeToTimeLong(Time time) {
        return time == null ? null : time.getTime();
    }
}