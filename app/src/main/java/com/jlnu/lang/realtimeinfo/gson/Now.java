package com.jlnu.lang.realtimeinfo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lilang on 2017/4/5.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;
    public class More {
        @SerializedName("txt")
        public String info;
    }
}
