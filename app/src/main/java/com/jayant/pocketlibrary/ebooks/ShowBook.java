package com.jayant.pocketlibrary.ebooks;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jayant.pocketlibrary.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ShowBook extends AppCompatActivity {

    private WebView webView;
    String bookUrl, bookName;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);

        webView = findViewById(R.id.pdf_web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        bookUrl = getIntent().getStringExtra("bookUrl");
        bookName = getIntent().getStringExtra("bookName");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(bookName);
        pd.setMessage("Opening...");


        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url = "";

        try {
            url = URLEncoder.encode(bookUrl, "UTF-8");
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new Callback());

//        pdfWebView.getSettings().getJavaScriptEnabled();
        webView.loadUrl("https://docs.google.com/viewer?url=" + url);

//        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);

    }

}
 class Callback extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(
            WebView view, String url) {
        return(false);
    }
}