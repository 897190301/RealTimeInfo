package com.jlnu.lang.realtimeinfo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.bean.GetFilmsResult;
import com.jlnu.lang.realtimeinfo.presenter.impl.GetFilmsPresenter;
import com.jlnu.lang.realtimeinfo.view.IGetFilmsView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lilang on 2017/4/1.
 */

public class FilmActivity extends AppCompatActivity implements IGetFilmsView {

    @Bind(R.id.webView)
    WebView mWebView;

    private GetFilmsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_film);
        ButterKnife.bind(this);
        initData();
        setWebView();
        loadData();
    }


    private void initData() {
        presenter = new GetFilmsPresenter(this);
    }

    private void loadData() {
        if (presenter != null) {
            presenter.getFilms();
        }
    }

    public void setWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
    }

    @Override
    public void onGetFilmsSuccess(GetFilmsResult result) {
        final String url = result.getFilmData().getH5url();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(url);
            }
        });
    }

    @Override
    public void onGetFilmsFail() {
    }
}
