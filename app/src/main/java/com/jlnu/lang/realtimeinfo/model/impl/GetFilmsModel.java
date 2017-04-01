package com.jlnu.lang.realtimeinfo.model.impl;

import android.util.Log;

import com.jlnu.lang.realtimeinfo.common.Constant;
import com.jlnu.lang.realtimeinfo.listener.IGetFilmsListener;
import com.jlnu.lang.realtimeinfo.model.IGetFilmsModel;
import com.jlnu.lang.realtimeinfo.network.StateCode;
import com.jlnu.lang.realtimeinfo.network.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by qn on 2017/4/1.
 */

public class GetFilmsModel implements IGetFilmsModel{

    private IGetFilmsListener listener;
    private String url;

    public GetFilmsModel(IGetFilmsListener listener) {
        this.listener = listener;
        url = URLs.FILMS_IP;
    }

    @Override
    public void getFilms() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("key", Constant.FILMS_KEY)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener == null) {
                    return;
                }
                listener.getFilmsFail("接口没调通");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (listener == null) {
                    return;
                }
                Log.e("Response", response.body().toString());
                String responseBody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    int returnCode = jsonObject.getInt("error_code");
                    if (returnCode == StateCode.RETURN_CODE_OK) {
                        listener.getFilmsSuccess(responseBody);
                    } else {
                        listener.getFilmsFail("数据解析异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
