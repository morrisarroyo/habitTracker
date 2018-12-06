package a00950540.bcit.ca.habittracker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;
import android.widget.Checkable;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import a00950540.bcit.ca.habittracker.database.Habit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.isA;

@RunWith(AndroidJUnit4.class)
public class HabitTrackerUITest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    public static ViewAction setChecked(final boolean checked) {
        return new ViewAction() {
            @Override
            public BaseMatcher<View> getConstraints() {
                return new BaseMatcher<View>() {
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeMismatch(Object item, Description mismatchDescription) {}

                    @Override
                    public void describeTo(Description description) {}
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
            }
        };
    }
    @Test
    public void ensureHabitDayToggle() {
        //onView(withId(R.id.recyclerView_habit_main)).perform(RecyclerViewActions.scrollToPosition(0));
        //onView(withId(R.id.imageView_day0)).perform(click());
        onView(withIndex(withId(R.id.imageView_day0), 0)).perform(click());
        //onView(withId(R.id.recyclerView_habit_main)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //pressBack();
        onView(withIndex(withId(R.id.imageView_day0), 0)).check(matches(withContentDescription("Today")));
    }

    @Test
    public void ensureCreateNewHabit() {
        //onView(withId(R.id.recyclerView_habit_main)).perform(RecyclerViewActions.scrollToPosition(0));
        //onView(withId(R.id.imageView_day0)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        //onView(withId(R.id.recyclerView_habit_main)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //pressBack();

        onView(withId(R.id.editTextHabitName)).perform(typeText("Jog"));
        onView(withText(R.string.string_ok)).perform(click());
        List<Habit> habits = MainActivity.db.habitDao().getAll();
        onView(withIndex(withId(R.id.textView_habitName), habits.size() -1)).check(matches(withText("Jog")));
    }

    @Test
    public void ensureTurnOnLocationNotification() {

        onView(withIndex(withId(R.id.textView_habitName), 0)).perform(click());
        onView(withId(R.id.action_edit)).perform(click());
        onView(withId(R.id.switch_locationNotification)).perform(setChecked(true));
        onView(withId(R.id.switch_locationNotification)).check(matches(isChecked()));
    }

    @Test
    public void ensureSetLocationNotification() {
        String lat = "42.0";
        String longitude = "128.0";
        onView(withIndex(withId(R.id.textView_habitName), 0)).perform(click());
        onView(withId(R.id.action_edit)).perform(click());
        onView(withId(R.id.switch_locationNotification)).perform(setChecked(true));
        onView(withId(R.id.switch_locationNotification)).check(matches(isChecked()));
        onView(withId(R.id.editText_latitude)).perform(clearText()).perform(typeText(lat));
        onView(withId(R.id.editText_longitude)).perform(clearText()).perform(typeText(longitude));
        onView(withText("DONE")).perform(click());
        onView(withId(R.id.action_edit)).perform(click());
        onView(withId(R.id.editText_latitude)).check(matches(withText(lat)));
        onView(withId(R.id.editText_longitude)).check(matches(withText(longitude)));
    }

    @Test
    public void ensureTurnOnTimeNotification() {

        onView(withIndex(withId(R.id.textView_habitName), 0)).perform(click());
        onView(withId(R.id.action_edit)).perform(click());
        onView(withId(R.id.switch_timeNotification)).perform(setChecked(true));
        onView(withId(R.id.switch_timeNotification)).check(matches(isChecked()));
    }

    @Test
    public void ensureSetTimeNotification() {
        String time = "14:00";
        onView(withIndex(withId(R.id.textView_habitName), 0)).perform(click());
        onView(withId(R.id.action_edit)).perform(click());
        onView(withId(R.id.switch_timeNotification)).perform(setChecked(true));
        onView(withId(R.id.switch_timeNotification)).check(matches(isChecked()));
        onView(withId(R.id.editText_time)).perform(clearText()).perform(typeText(time));
        onView(withText("DONE")).perform(click());
        onView(withId(R.id.action_edit)).perform(click());
        onView(withId(R.id.editText_time)).check(matches(withText(time+":00")));
    }
}
