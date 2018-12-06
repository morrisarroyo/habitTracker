package a00950540.bcit.ca.habittracker;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NewHabitDialogFragment extends DialogFragment {
    // Use this instance of the interface to deliver action events
    private NoticeDialogListener mListener;
    private EditText habitNameEditText;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    @SuppressWarnings("unused")
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.habit_dialog, null);

        habitNameEditText = view.findViewById(R.id.editTextHabitName);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
            .setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
               // NewHabitDialogFragment.this.getDialog().dismiss();
                mListener.onDialogPositiveClick(NewHabitDialogFragment.this);
                /*
                Log.d("onDialogPositiveClick", "Enter");

                Habit newHabit = new Habit(getHabitName(), Habit.NEW_HABIT_TRACKING, new Date());
                Log.d("onDialogPositiveClick", newHabit.getHabitName());

                //habits.add(newHabit);
                MainActivity.db.habitDao().insertAll(newHabit);
                */
                }

            })
            .setNegativeButton(R.string.string_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    NewHabitDialogFragment.this.getDialog().cancel();
                }
            });
        return builder.create();
    }



    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public String getHabitName() {
        return habitNameEditText.getText().toString();
    }

}
