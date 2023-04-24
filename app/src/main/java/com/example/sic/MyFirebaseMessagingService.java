package com.example.sic;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.sic.Activity.Home.Inbox.Inbox_detail;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vn.mobileid.tse.model.client.requestinfo.RequestInfoModule;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        SharedPreferences.Editor editor = getSharedPreferences("FirebaseID", MODE_PRIVATE).edit();
        editor.putString("firebaseID", token);
        editor.apply();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        if (message.getData().size() > 0) {
            Intent i = new Intent(getBaseContext(), Inbox_detail.class);
            i.putExtra("transactionId", message.getData().get("transactionID"));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }


}
