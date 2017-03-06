package com.jlnu.lang.realtimeinfo.model;

import android.util.Log;

import com.jlnu.lang.realtimeinfo.common.Constant;
import com.jlnu.lang.realtimeinfo.listener.IGetNewListener;
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
 * Created by qn on 2017/1/11.
 */

public class GetNewsModel implements IGetNewsModel{

    private IGetNewListener listener;
    private String url;

    public GetNewsModel(IGetNewListener listener) {
        this.listener = listener;
        url = URLs.IP;
    }

    @Override
    public void getNews(String type) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("type", type)
                .add("key", Constant.NEWS_KEY)
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
                listener.onGetNewsFailure("接口没调通");
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
                        listener.onGetNewSuccess(responseBody);
                    } else {
                        listener.onGetNewsFailure("数据解析异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
