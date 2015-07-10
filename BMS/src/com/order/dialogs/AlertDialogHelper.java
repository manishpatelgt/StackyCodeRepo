package com.order.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogHelper {

    private Context context;

    public AlertDialogHelper(Context parent) {
        this.context = parent;
    }

    /**
     * Creates an error dialog and shows it.
     *
     * @param title   The dialog title
     * @param message The dialog message
     */
    public void showError(final String title, final String message) {
        if (context == null)
            return;

        Activity activity = (Activity) context;

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(message)
                        .setOnCancelListener(null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }

        });
    }

    /**
     * Create a dialog and shows the exception details in it.
     *
     * @param exception The exception to show in the dialog
     * @param title     The dialog title
     */
    public void showException(String title, Exception exception) {
        showError(exception.toString(), title);
    }

}
