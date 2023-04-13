package com.example.sic;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.annotation.NonNull;

public class WaitingTaskDialog extends AlertDialog {

    private Activity activity;

    public WaitingTaskDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public void onBackPressed() {
        activity.moveTaskToBack(true);
    }

}