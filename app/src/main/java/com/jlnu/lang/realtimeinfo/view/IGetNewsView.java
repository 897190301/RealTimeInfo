package com.jlnu.lang.realtimeinfo.view;


import com.jlnu.lang.realtimeinfo.bean.GetNewsResult;

/**
 * Created by qn on 2017/1/11.
 */

public interface IGetNewsView {
    void onGetNewsComplete();
    void onGetNewsSuccess(GetNewsResult result);
    void onGetNewsFailure(String errorInfo);
}
