package com.jlnu.lang.realtimeinfo.listener;

/**
 * Created by qn on 2017/4/1.
 */

public interface IGetFilmsListener {
    void getFilmsSuccess(String result);
    void getFilmsFail(String msg);
}
