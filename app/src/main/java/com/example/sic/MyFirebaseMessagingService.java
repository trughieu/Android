package com.example.sic;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.d("token", "onNewToken: "+token);
        SharedPreferences.Editor editor=getSharedPreferences("FirebaseID",MODE_PRIVATE).edit();
        editor.putString("firebaseID",token);
        editor.apply();
    }
}
//dvo8D82VRFmvt6snrDj-cy:APA91bHcXhxNC2q-sbfYJqGWM3bRcjuhyVwu14MsJnEE4Gi96MVavqlzy5xMkrUr_HnTJGJDnILgYTXzTqLPFl0Jy1gHLxfXnrZ51nVk6t7qL_fy6ZqwD2o4G0rg-H3W5Erp2soe_NZK