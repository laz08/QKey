package dev.laz.qkeycounter.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import dev.laz.qkeycounter.R;

/**
 * Reset confirmation dialog.
 */
public class ResetConfirmationDialog extends DialogFragment {

    private ResetListener mResetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {

            mResetListener = (ResetListener) getActivity();
        } catch (Exception e) {

            throw new ClassCastException("Calling Fragment must implement OnPlaceMarkerListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        FragmentActivity act = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage(R.string.reset_confirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        mResetListener.resetNumberOfQKeys(getContext());
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dismiss();
                    }
                });

        return builder.create();
    }
}
