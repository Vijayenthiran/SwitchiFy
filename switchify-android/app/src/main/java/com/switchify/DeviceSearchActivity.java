package com.switchify;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeviceSearchActivity extends AppCompatActivity {

    private static final String TAG = "DeviceSearchActivity";
    private WifiManager mWifiManager;
    private List<ScanResult> mScanResults;

    private LinearLayout mTurnWifiOnLayout;
    private LinearLayout mDeviceLayout;
    private ListView mMayaListView;

    private BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {

            mScanResults = mWifiManager.getScanResults();
            populateDeviceList();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_search);
        mTurnWifiOnLayout = (LinearLayout) findViewById(R.id.linearLayout_turn_wifi_on);
        mDeviceLayout = (LinearLayout) findViewById(R.id.linearlayout_device_list);
        mMayaListView = (ListView) findViewById(R.id.listview_maya_device_list);

        mMayaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ScanResult scanResult = mScanResults.get(i);

            }
        });

        checkForPermission();


        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        if (mWifiManager.isWifiEnabled()) {
            Log.d(TAG, "Wifi is enabled");
            hideWifiEnableBox();
            startWifiScan();
        }
    }

    private void hideWifiEnableBox() {
        mTurnWifiOnLayout.setVisibility(View.GONE);
        mDeviceLayout.setVisibility(View.VISIBLE);
    }

    private void startWifiScan() {
        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mWifiManager.startScan();
    }

    private void populateDeviceList() {

        final ArrayList<String> strings = new ArrayList<>();
        List<ScanResult> scanResults = new ArrayList<>();
        for (ScanResult scanResult : mScanResults) {

            if (scanResult.SSID.startsWith("Swit")) {
                strings.add(scanResult.SSID);
                scanResults.add(scanResult);
            }
        }
        mScanResults = scanResults;
        ArrayAdapter stringArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strings);
        mMayaListView.setAdapter(stringArrayAdapter);

        mMayaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String wifiSsd = strings.get(i);
                Toast.makeText(DeviceSearchActivity.this, wifiSsd, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DeviceSearchActivity.this, MainActivity.class);
                intent.putExtra("SSID", wifiSsd);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        if (mWifiManager.isWifiEnabled()) {
            try {
                unregisterReceiver(mWifiScanReceiver);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
        }

    }
}
