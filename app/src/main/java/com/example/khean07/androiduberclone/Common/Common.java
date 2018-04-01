package com.example.khean07.androiduberclone.Common;

import com.example.khean07.androiduberclone.Remote.IGoogleAPI;
import com.example.khean07.androiduberclone.Remote.RetrofitClient;

/**
 * Created by Khean07 on 4/1/2018.
 */

public class Common {
    public static final String baseUrl = "https://maps.googleapis.com";
    public static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseUrl).create(IGoogleAPI.class);
    }


}
