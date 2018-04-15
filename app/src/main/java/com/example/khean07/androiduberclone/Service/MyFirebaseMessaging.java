package com.example.khean07.androiduberclone.Service;

import android.content.Intent;

import com.example.khean07.androiduberclone.CustommerCall;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

/**
 * Created by Khean07 on 4/14/2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Because i will send the firebase message with contain lat and lng from rider app
        //So i need covert message to LatLng
        LatLng customer_location = new Gson().fromJson(remoteMessage.getNotification().getBody(),LatLng.class);

        Intent intent = new Intent(getBaseContext(), CustommerCall.class);
        intent.putExtra("lat",customer_location.latitude);
        intent.putExtra("lng",customer_location.longitude);
//        getApplicationContext().startActivity(intent);
        //-- TODO error Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
