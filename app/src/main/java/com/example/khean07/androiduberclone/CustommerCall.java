package com.example.khean07.androiduberclone;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khean07.androiduberclone.Common.Common;
import com.example.khean07.androiduberclone.Model.FCMResponse;
import com.example.khean07.androiduberclone.Model.Notification;
import com.example.khean07.androiduberclone.Model.Sender;
import com.example.khean07.androiduberclone.Model.Token;
import com.example.khean07.androiduberclone.Remote.IFCMService;
import com.example.khean07.androiduberclone.Remote.IGoogleAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustommerCall extends AppCompatActivity {

    private static final String TAG = "CustommerCall";
    TextView txtTime,txtAddress, txtDistance;

    Button btnDecline, btnAccept;

    MediaPlayer mediaPlayer;

    IGoogleAPI mService;
    IFCMService mFCMService;

    String customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custommer_call);

        mService = Common.getGoogleAPI();
        mFCMService = Common.getFCMService();

        //InitView
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtDistance = (TextView) findViewById(R.id.txtDistance);
        txtTime = (TextView) findViewById(R.id.txtTime);

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnDecline = (Button) findViewById(R.id.btnDecline);

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(customerId))
                    cancelBooking(customerId);
            }
        });

        mediaPlayer = MediaPlayer.create(this,R.raw.army_wake_up);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        if(getIntent() != null) {
            double lat = getIntent().getDoubleExtra("lat",-1.0);
            double lng = getIntent().getDoubleExtra("lng",-1.0);
            customerId = getIntent().getStringExtra("customer");

            getDirection(lat,lng);
        }
    }

    private void cancelBooking(String customerId) {
        Token token = new Token(customerId);
        Notification notification = new Notification("Notice!","Driver has cancelled your request");
        Sender sender = new Sender(notification,token.getToken());

        mFCMService.sendMessage(sender)
                .enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                        if(response.body().success == 1) {
                            Toast.makeText(CustommerCall.this,"Cancelled",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {

                    }
                });
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
