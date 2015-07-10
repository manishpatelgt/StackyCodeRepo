package com.order.dialogs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.TextView;
import com.order.bms.R;

public class ProgressDialogHelper {

    private static ProgressDialog progressDialog;

    public static void initialize(final Activity parent, final String message, final int layoutResourceId) {

        if (parent == null)
            return;

        progressDialog = new ProgressDialog(parent);
        progressDialog.setCancelable(false);

        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
                // This has to be after show()
                progressDialog.setContentView(layoutResourceId);
                TextView progressText = (TextView) progressDialog.findViewById(R.id.progressDialogMessageText);
                progressText.setText(message);
            }
        });

    }

    public static void update(final Activity parent, final String message) {
        if (parent == null)
            return;

        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage(message);
            }
        });
    }

    public static void dismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private ProgressDialogHelper() {}
}
