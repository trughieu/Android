package com.example.sic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sic.Activity.Home.Inbox.InboxConfirm;
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
            Intent intent = new Intent(getBaseContext(), InboxConfirm.class);
            Log.d("mes", "onMessageReceived: "+message.getData());
            intent.putExtra("transactionId", message.getData().get("transactionID"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }


}
