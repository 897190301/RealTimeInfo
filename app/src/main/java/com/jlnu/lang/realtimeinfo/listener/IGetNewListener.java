package com.jlnu.lang.realtimeinfo.listener;

/**
 * Created by qn on 2017/1/11.
 */

public interface IGetNewListener {
    void onGetNewSuccess(String result);
    void onGetNewsFailure(String errorInfo);
}
