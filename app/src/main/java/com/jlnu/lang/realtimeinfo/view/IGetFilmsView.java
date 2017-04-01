package com.jlnu.lang.realtimeinfo.view;

import com.jlnu.lang.realtimeinfo.bean.GetFilmsResult;

/**
 * Created by qn on 2017/4/1.
 */

public interface IGetFilmsView {
    void onGetFilmsSuccess(GetFilmsResult result);
    void onGetFilmsFail();
}
