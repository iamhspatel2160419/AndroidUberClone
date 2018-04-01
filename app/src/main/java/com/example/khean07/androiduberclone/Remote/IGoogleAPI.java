package com.example.khean07.androiduberclone.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Khean07 on 4/1/2018.
 */

public interface IGoogleAPI {

    @GET
    Call<String> getPath(@Url String url);
}
