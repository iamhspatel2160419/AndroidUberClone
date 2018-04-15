package com.example.khean07.androiduberclone.Common;

import android.location.Location;

import com.example.khean07.androiduberclone.Remote.FCMClient;
import com.example.khean07.androiduberclone.Remote.IFCMService;
import com.example.khean07.androiduberclone.Remote.IGoogleAPI;
import com.example.khean07.androiduberclone.Remote.RetrofitClient;

/**
 * Created by Khean07 on 4/1/2018.
 */

public class Common {

    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInformation";
    public static final String user_rider_tbl = "RidersInformation";
    public static final String pickup_request_tbl = "PickupRequest";
    public static final String token_tbl = "Tokens";

    public static Location mLastLocation = null;


    public static final String baseUrl = "https://maps.googleapis.com";
    public static final String fcmUrl = "https://fcm.googleapis.com/";
    public static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseUrl).create(IGoogleAPI.class);
    }

    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(fcmUrl).create(IFCMService.class);
    }


}
