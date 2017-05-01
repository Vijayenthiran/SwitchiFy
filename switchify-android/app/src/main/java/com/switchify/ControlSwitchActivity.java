package com.switchify;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ControlSwitchActivity extends AppCompatActivity {
    private final static String BASE_URL = "http://192.168.4.1/";
    private static final String TAG = "ControlSwitchActivity";
    private final OkHttpClient client = new OkHttpClient();


    private Switch mSwitch1;
    private Switch mSwitch2;
    private Switch mSwitch3;


    @Override
    protected void onStop() {
        super.onStop();
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_switch);

        mSwitch1 = (Switch) findViewById(R.id.switch1);
        mSwitch2 = (Switch) findViewById(R.id.switch2);
        mSwitch3 = (Switch) findViewById(R.id.switch3);

        mSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String url = BASE_URL + "16/" + (b == true ? "On" : "Off");

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        mSwitch1.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ControlSwitchActivity.this, "Unable to connect to switch 1", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String body = response.body().string();
                        mSwitch1.post(new Runnable() {
                            @Override
                            public void run() {
                                String toastText = "Switch 1 is turned " +
                                        (body.toLowerCase().contains("on") ? "On" : "Off");

                                Toast.makeText(ControlSwitchActivity.this, toastText, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        mSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String url = BASE_URL + "0/" + (b == true ? "On" : "Off");

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mSwitch2.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ControlSwitchActivity.this, "Unable to connect to switch 2", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String body = response.body().string();
                        mSwitch2.post(new Runnable() {
                            @Override
                            public void run() {
                                String toastText = "Switch 2 is turned " +
                                        (body.toLowerCase().contains("on") ? "On" : "Off");

                                Toast.makeText(ControlSwitchActivity.this, toastText, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        mSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String url = BASE_URL + "2/" + (b == true ? "On" : "Off");

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mSwitch3.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ControlSwitchActivity.this, "Unable to connect to switch 3", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String body = response.body().string();
                        Log.e(TAG, body);
                        mSwitch3.post(new Runnable() {
                            @Override
                            public void run() {
                                String toastText = "Switch 3 is turned " +
                                        (body.toLowerCase().contains("on") ? "On" : "Off");

                                Toast.makeText(ControlSwitchActivity.this, toastText, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });




    }}
