package dev.laz.qkeycounter.dialog;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Dialog Helper.
 */
public class DialogHelper {

    public static final String TAG = "DialogHelper";

    private static DialogFragment mDialog;

    /**
     * Shows reset dialog.
     */
    public static void showResetDialog(FragmentManager fmtManager) {

        mDialog = new ResetConfirmationDialog();
        mDialog.show(fmtManager, TAG);
        mDialog.setCancelable(false);
    }
}
