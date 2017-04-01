package com.jlnu.lang.realtimeinfo.presenter.impl;


import com.google.gson.Gson;
import com.jlnu.lang.realtimeinfo.bean.GetNewsResult;
import com.jlnu.lang.realtimeinfo.listener.IGetNewListener;
import com.jlnu.lang.realtimeinfo.model.impl.GetNewsModel;
import com.jlnu.lang.realtimeinfo.model.IGetNewsModel;
import com.jlnu.lang.realtimeinfo.presenter.IGetNewsPresenter;
import com.jlnu.lang.realtimeinfo.view.IGetNewsView;

/**
 * Created by qn on 2017/1/11.
 */

public class  GetNewsPresenter implements IGetNewsPresenter, IGetNewListener {

    private IGetNewsView view;
    private IGetNewsModel model;
    public GetNewsPresenter(IGetNewsView view) {
        this.view = view;
        model = new GetNewsModel(this);
    }

    @Override
    public void getNews(String type) {
        if (model != null) {
            model.getNews(type);
        }
    }

    @Override
    public void onGetNewSuccess(String result) {
        if (view == null) {
            return;
        }
        Gson gson = new Gson();
        GetNewsResult newsResult = gson.fromJson(result, GetNewsResult.class);
        if (newsResult == null) {
            onGetNewsFailure("数据解析异常");
        }
        view.onGetNewsComplete();
        view.onGetNewsSuccess(newsResult);
    }

    @Override
    public void onGetNewsFailure(String errorInfo) {
        view.onGetNewsFailure(errorInfo);
    }
}
