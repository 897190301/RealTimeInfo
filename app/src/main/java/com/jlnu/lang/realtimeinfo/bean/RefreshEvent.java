package com.jlnu.lang.realtimeinfo.bean;

/**
 * Created by qn on 2017/4/6.
 */

public class RefreshEvent {

    private String weatherId;

    public RefreshEvent(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherId() {
        return weatherId;
    }
}
