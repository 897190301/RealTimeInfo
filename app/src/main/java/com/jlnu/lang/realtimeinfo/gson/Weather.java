package com.jlnu.lang.realtimeinfo.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lilang on 2017/4/5.
 */

public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
