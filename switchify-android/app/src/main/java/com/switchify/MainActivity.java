package com.switchify;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String PASSWORD = "password123";

    private WebView mWebView;
    private String mNetwordSsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNetwordSsid = getIntent().getStringExtra("SSID");
        Toast.makeText(this, "Connecting to " + mNetwordSsid, Toast.LENGTH_SHORT).show();


        mWebView = (WebView) findViewById(R.id.webview);
        connectToWifi();

        WebViewClient webViewClient = new WebViewClient();
        mWebView.setWebViewClient(webViewClient);

        WebChromeClient webChromeClient = new WebChromeClient();
        mWebView.setWebChromeClient(webChromeClient);

    }

    private void connectToWifi() {

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + mNetwordSsid + "\"";
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + mNetwordSsid + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
//        mWebView.loadUrl("file:///android_asset/index.html");
        Intent intent = new Intent(MainActivity.this, ControlSwitchActivity.class);
        startActivity(intent);
        finish();
    };

    public static class MessageEvent {

        private String mSsid;

        public MessageEvent(String ssid) {
            mSsid = ssid;
        }

        public String getSsid() {
            return mSsid;
        }
    }


}
