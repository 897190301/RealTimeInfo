package com.jlnu.lang.realtimeinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jlnu.lang.realtimeinfo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qn on 2017/3/24.
 */

public class NewsDetailActivity extends AppCompatActivity {

    @Bind(R.id.webView)
    WebView mWebView;

    private static final String NEWS_URL = "news_url";

    public static Intent newIntent(Activity activity, String url) {
        Intent intent = new Intent(activity, NewsDetailActivity.class);
        intent.putExtra(NEWS_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        final String url = getIntent().getStringExtra(NEWS_URL);
        Log.e("url",url);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        mWebView.loadUrl(url);
    }
}
