package com.jlnu.lang.realtimeinfo.presenter.impl;

import com.google.gson.Gson;
import com.jlnu.lang.realtimeinfo.bean.GetFilmsResult;
import com.jlnu.lang.realtimeinfo.listener.IGetFilmsListener;
import com.jlnu.lang.realtimeinfo.model.IGetFilmsModel;
import com.jlnu.lang.realtimeinfo.model.impl.GetFilmsModel;
import com.jlnu.lang.realtimeinfo.presenter.IGetFilmsPresenter;
import com.jlnu.lang.realtimeinfo.view.IGetFilmsView;

/**
 * Created by qn on 2017/4/1.
 */

public class GetFilmsPresenter implements IGetFilmsPresenter,IGetFilmsListener {

    private IGetFilmsView view;
    private IGetFilmsModel model;

    public GetFilmsPresenter(IGetFilmsView view) {
        this.view =view;
        model = new GetFilmsModel(this);
    }

    @Override
    public void getFilms() {

        if (model != null) {
            model.getFilms();
        }

    }


    @Override
    public void getFilmsSuccess(String result) {
        if (view == null) {
            return;
        }
        Gson gson = new Gson();
        GetFilmsResult filmsResult = gson.fromJson(result, GetFilmsResult.class);
        if (filmsResult == null) {
            getFilmsFail("数据解析异常");
        }
        view.onGetFilmsSuccess(filmsResult);
    }

    @Override
    public void getFilmsFail(String msg) {
        view.onGetFilmsFail();
    }
}
