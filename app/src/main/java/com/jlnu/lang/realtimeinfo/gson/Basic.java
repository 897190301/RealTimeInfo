package com.jlnu.lang.realtimeinfo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lilang on 2017/4/5.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
