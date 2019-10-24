package com.tabian.tcoetkiosk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Tracking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        WebView wv= (WebView)findViewById(R.id.wvStatus);
        wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("http://www.onlinefir.sammeyinc.com/search/checkstatus.php");
    }
}
