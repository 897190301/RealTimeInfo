package com.jlnu.lang.realtimeinfo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FilmFragment extends Fragment implements IGetFilmsView{

    @Bind(R.id.webView)
    WebView mWebView;

    private GetFilmsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_film, container, false);
        ButterKnife.bind(this, contentView);
        initData();
        setWebView();
        loadData();
        return contentView;
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
        getActivity().runOnUiThread(new Runnable() {
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
