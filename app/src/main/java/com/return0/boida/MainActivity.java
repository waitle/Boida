package com.return0.boida;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loadUrlWithWebView("https://meta.notepads.cc");

        WebView mainWebview = findViewById(R.id.mainWebView);
        WebView avatarview = findViewById(R.id.avatarView);
        Button aiSwitch = findViewById(R.id.aiSwitch);
        Button closeButton = findViewById(R.id.close_button);
        aiSwitch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    geturl();
                    aiSwitch.setVisibility(View.GONE);
                    avatarview.setWebViewClient(webViewClient);
                    avatarview.setVisibility(View.VISIBLE);
                    closeButton.setVisibility(View.VISIBLE);

                    avatarview.loadUrl("https://meta.bubblecell.win/");
                    avatarview.getSettings().setUseWideViewPort(true); // wide viewport를 유연하게 설정하고
                    avatarview.getSettings().setLoadWithOverviewMode(true); // 컨텐츠가 웹뷰 범위에 벗어날 경우  크기에 맞게 조절

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }) ;

        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                aiSwitch.setVisibility(View.VISIBLE);
                avatarview.setVisibility(View.GONE);
                closeButton.setVisibility(View.GONE);
            }
        }) ;

        mainWebview.setWebViewClient(webViewClient);
//        mainWebview.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url){
//                view.loadUrl(url);
//                return false; // then it is not handled by default action
//            }
//        });
//        avatarview.setWebViewClient(new WebViewClient() {
//             public boolean shouldOverrideUrlLoading(WebView view, String url){
//                view.loadUrl(url);
//                return false; // then it is not handled by default action
//            }
//        });
//        mainWebview.loadUrl("https://drive.notepads.cc");
        WebSettings webSettings = mainWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebSettings avatarSettings = avatarview.getSettings();
        avatarSettings.setJavaScriptEnabled(true);
//        mainWebview.addJavascriptInterface(new WebAppInterface(this), "metaRemote");
        mainWebview.loadUrl("https://m.youtube.com");

//        avatarview.loadUrl("https://m.youtube.com");



    }

    void geturl() throws MalformedURLException {
        WebView mainWebview2 = findViewById(R.id.mainWebView);
        URL url = new URL(mainWebview2.getOriginalUrl());
        Log.wtf(TAG, "URL HERE "+url);
    }


    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e(TAG, "this is the 2 "+url);
            try {
                geturl();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            // Loading started for URL
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Redirecting to URL
            Log.i(TAG, "New vid "+url);
            view.loadUrl(url);
            return false; // then it is not handled by default action
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            // Redirecting to URL
            Log.i(TAG, "New vid "+request.getUrl().toString());

            view.loadUrl(request.getUrl().toString());
            return false; // then it is not handled by default action
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e(TAG, "this is the on "+ url);
            try {
                geturl();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            // Loading finished for URL
        }
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){
            super.shouldInterceptRequest(view,request);

            //Get the request and assign it to a string

            String requestUrl = request.getUrl().toString();

            //Get the mime-type from the string

            String extension = MimeTypeMap.getFileExtensionFromUrl(requestUrl);
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

            //make sure the mime-type isn't null
            if(mimeType != null){

                // check if any of the requestUrls contain the url of a video file

                if(mimeType.startsWith("video/") && requestUrl.contains("youtube.com")){

                    Log.e("Video File" , requestUrl);

                }
            }
            return null;
        }
    };

    public void loadUrlWithWebView(String url) {
        WebView webView = findViewById(R.id.mainWebView);
        webView.setWebViewClient(webViewClient);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}




