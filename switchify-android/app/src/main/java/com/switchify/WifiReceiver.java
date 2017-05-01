package com.switchify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by abhishekbatra on 11/04/17.
 */

public class WifiReceiver extends BroadcastReceiver {

    public WifiReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        if (networkInfo.isConnected()) {
            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            String ssid = wifiInfo.getSSID();
            EventBus.getDefault().post(new MainActivity.MessageEvent(ssid));

        }
    }


}
