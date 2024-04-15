package com.return0.boida;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        WebView webview = findViewById(R.id.mainWebView);
        webview.java
        webview.settings.javaScriptEnabled = true;


        //BlackJin 명의 JavascriptInterface 를 추가해 줍니다.
        webview.addJavascriptInterface(WebAppInterface(this), "BlackJin")


        //assets에 있는 sample.html을 로딩합니다.
        webview.loadUrl("file:///android_asset/sample.html")


    }
}