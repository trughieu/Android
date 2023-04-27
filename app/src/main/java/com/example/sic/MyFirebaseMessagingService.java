package com.example.sic;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.sic.Activity.Home.Inbox.Inbox_detail;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
            Intent intent = new Intent(getBaseContext(), Inbox_detail.class);
            intent.putExtra("transactionId", message.getData().get("transactionID"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }


}
