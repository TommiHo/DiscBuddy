package fi.lato.discbuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Lasse on 12.11.2015.
 */
public class AddCourseDialogFragment extends DialogFragment {
    public interface DialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name, int par, int holeCount);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    DialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.add_course_dialog, null);
        builder.setView(dialogView)
                .setTitle("Add a new Course")
                        // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // find a name and score
                        EditText editName = (EditText) dialogView.findViewById(R.id.name);
                        String name = editName.getText().toString();
                        EditText editPar = (EditText) dialogView.findViewById(R.id.par);
                        int par = Integer.valueOf(editPar.getText().toString());
                        EditText editHoles = (EditText) dialogView.findViewById(R.id.holeCount);
                        int holeCount = Integer.valueOf(editHoles.getText().toString());

                        mListener.onDialogPositiveClick(AddCourseDialogFragment.this,name,par,holeCount);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(AddCourseDialogFragment.this);
                    }
                });
        return builder.create();
    }
}