package com.example.khean07.androiduberclone.Remote;

import com.example.khean07.androiduberclone.Model.FCMResponse;
import com.example.khean07.androiduberclone.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Khean07 on 4/14/2018.
 */

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAtSKdx84:APA91bFRk2BXrwvh2ls6BOup_4QEBxSPD_Tazj2HGTOKPuIIHpY63xm9uR0ZdU-zi5qoHqHWH5pzvt8Kc_fbHzE457dpEj8vpr4B1wvuLgJ-XwfWJADYHHEVM21uv71c3d5BpeBxx6yq"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);
}
