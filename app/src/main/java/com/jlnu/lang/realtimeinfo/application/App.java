package com.jlnu.lang.realtimeinfo.application;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lilang on 2017/2/26.
 */

public class App extends Application {

    private static App instance;
    private static Map<String, String> typeMap;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initMap();
    }

    private void initMap() {
        typeMap = new HashMap<>();
        typeMap.put("头条","top");
        typeMap.put("社会","shehui");
        typeMap.put("国内","guonei");
        typeMap.put("国际","guoji");
        typeMap.put("娱乐","yule");
        typeMap.put("体育","tiyu");
        typeMap.put("军事","junshi");
        typeMap.put("科技","keji");
        typeMap.put("财经","caijing");
        typeMap.put("时尚","shishang");

    }

    public static App getInstance() {
        if (instance != null) {
            return instance;
        }
        return new App();
    }

    public static String getNewsType(String key) {
        return typeMap.get(key);
    }
}
