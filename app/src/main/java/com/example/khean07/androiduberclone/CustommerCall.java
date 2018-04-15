package com.example.khean07.androiduberclone;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khean07.androiduberclone.Common.Common;
import com.example.khean07.androiduberclone.Remote.IFCMService;
import com.example.khean07.androiduberclone.Remote.IGoogleAPI;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustommerCall extends AppCompatActivity {

    private static final String TAG = "CustommerCall";
    TextView txtTime,txtAddress, txtDistance;

    MediaPlayer mediaPlayer;

    IGoogleAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custommer_call);

        mService = Common.getGoogleAPI();

        //InitView
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtDistance = (TextView) findViewById(R.id.txtDistance);
        txtTime = (TextView) findViewById(R.id.txtTime);

        mediaPlayer = MediaPlayer.create(this,R.raw.army_wake_up);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        if(getIntent() != null) {
            double lat = getIntent().getDoubleExtra("lat",-1.0);
            double lng = getIntent().getDoubleExtra("lng",-1.0);

            getDirection(lat,lng);
        }
    }


    private void getDirection(double lat, double lng) {
        String requestApi = null;
        try{
            requestApi = "https://maps.googleapis.com/maps/api/directions/json?"+
                    "mode=driving&"+
                    "transit_routing_preference=less_driving&"+
                    "origin="+ Common.mLastLocation.getLatitude()+","+Common.mLastLocation.getLongitude()+"&"+
                    "destination="+lat+","+lng+"&"+
                    "key="+getResources().getString(R.string.google_direction_api);
            Log.d(TAG,requestApi);
            mService.getPath(requestApi)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                JSONArray routes = jsonObject.getJSONArray("routes");

                                // after get routes, just get first element of routes
                                JSONObject object = routes.getJSONObject(0);

                                //after get first elementm we need to get array with name "legs"
                                JSONArray legs = object.getJSONArray("legs");

                                //and get first element of legs array
                                JSONObject legsObject = legs.getJSONObject(0);

                                //get Distance
                                JSONObject distance = legsObject.getJSONObject("distance");
                                txtDistance.setText(distance.getString("text"));

                                // get time
                                JSONObject time = legsObject.getJSONObject("duration");
                                txtTime.setText(time.getString("text"));

                                // get address
                                String address = legsObject.getString("end_address");
                                txtAddress.setText(address);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(CustommerCall.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        mediaPlayer.release();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mediaPlayer.release();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }


}
