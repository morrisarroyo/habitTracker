package a00950540.bcit.ca.habittracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import a00950540.bcit.ca.habittracker.database.Habit;

import java.util.List;


public class HabitRecyclerViewAdapter extends RecyclerView.Adapter<HabitRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "HabitRecyclerViewAdapter";

    private List<Habit> habits;
    private final Context context;

    public HabitRecyclerViewAdapter(List<Habit> habits, Context context) {
        this.context = context;
        this.habits = habits;
    }

    private Drawable getDayValueDrawable(char dayValue) {
        if (dayValue == '1') {
            return context.getDrawable(R.drawable.ic_check);
        } else {
            return context.getDrawable(R.drawable.ic_x);
        }
    }

    @NonNull
    @Override
    public HabitRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        final Habit habit = habits.get(position);
        final String habitTracking = habit.getHabitTracking();
        holder.habitName.setText(habits.get(position).getHabitName());
        holder.days[0].setImageDrawable(getDayValueDrawable(habitTracking.charAt(0)));
        holder.days[1].setImageDrawable(getDayValueDrawable(habitTracking.charAt(1)));
        holder.days[2].setImageDrawable(getDayValueDrawable(habitTracking.charAt(2)));
        holder.days[3].setImageDrawable(getDayValueDrawable(habitTracking.charAt(3)));
        holder.days[4].setImageDrawable(getDayValueDrawable(habitTracking.charAt(4)));

        for (int i = 0; i < holder.days.length; ++i) {
            final int j = i;
            /*
            char dayValue = habitTracking.charAt(j);
            if (dayValue == '0') {
                holder.days[j].setContentDescription("Yes");
            } else {
                holder.days[j].setContentDescription("No");
            }
            */
            holder.days[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Update habitDaysList string in database

                    char dayValue = habitTracking.charAt(j);
                    if (dayValue == '0') {
                        //holder.days[j].setContentDescription("Yes");
                        habit.setHabitTracking(replace(habitTracking, j, '1'));
                    } else {
                        //holder.days[j].setContentDescription("No");
                        habit.setHabitTracking(replace(habitTracking, j, '0'));
                    }
                    MainActivity.db.habitDao().updateHabitTracking(habit.getHabitId(), habit.getHabitTracking());
                    holder.days[j].setImageDrawable(getDayValueDrawable(habit.getHabitTracking().charAt(j)));
                    reload();
                }

                private Drawable getDayValueDrawable(char c) {
                    if (c == '1') {
                        return context.getDrawable(R.drawable.ic_check);
                    } else {
                        return context.getDrawable(R.drawable.ic_x);
                    }
                }

                private String replace(String str, int index, char replace){
                    if(str==null){
                        return "";
                    }else if(index<0 || index>=str.length()){
                        return str;
                    }
                    char[] chars = str.toCharArray();
                    chars[index] = replace;
                    return String.valueOf(chars);
                }
            });
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + habits.get(holder.getAdapterPosition()).getHabitName());
                Intent i = new Intent(context, StatisticActivity.class);
                i.putExtra("habitId", habits.get(holder.getAdapterPosition()).getHabitId());
                context.startActivity(i);
                Toast.makeText(context, habits.get(holder.getAdapterPosition()).getHabitName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView habitName;
        final ImageView[] days = new ImageView[5];
        final ConstraintLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.textView_habitName);

            days[0] = itemView.findViewById(R.id.imageView_day0);
            days[1] = itemView.findViewById(R.id.imageView_day1);
            days[2] = itemView.findViewById(R.id.imageView_day2);
            days[3] = itemView.findViewById(R.id.imageView_day3);
            days[4] = itemView.findViewById(R.id.imageView_day4);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public void reload()
    {
        habits = MainActivity.db.habitDao().getAll();
        notifyDataSetChanged();
    }

}
