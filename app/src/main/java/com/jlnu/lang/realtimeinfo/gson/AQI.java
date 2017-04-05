package com.jlnu.lang.realtimeinfo.gson;

/**
 * Created by lilang on 2017/4/5.
 */

public class AQI {

    public AQICity city;
    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
