package com.jlnu.lang.realtimeinfo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.bean.RefreshEvent;
import com.jlnu.lang.realtimeinfo.gson.Forecast;
import com.jlnu.lang.realtimeinfo.gson.Weather;
import com.jlnu.lang.realtimeinfo.util.HttpUtil;
import com.jlnu.lang.realtimeinfo.util.Utility;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by qn on 2017/4/5.
 */

public class WeatherFragment extends Fragment {

    @Bind(R.id.weather_layout)
    ScrollView weatherLayout;
    @Bind(R.id.title_city)
    TextView titleCity;
    @Bind(R.id.title_update_time)
    TextView titleUpdateTime;
    @Bind(R.id.degree_text)
    TextView degreeText;
    @Bind(R.id.weather_info_text)
    TextView weatherInfoText;
    @Bind(R.id.forecast_layout)
    LinearLayout forecastLayout;
    @Bind(R.id.aqi_text)
    TextView aqiText;
    @Bind(R.id.pm25_text)
    TextView pm25Text;
    @Bind(R.id.comfort_text)
    TextView comfortText;
    @Bind(R.id.car_wash_text)
    TextView carWashText;
    @Bind(R.id.sport_text)
    TextView sportText;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_button)
    Button navButton;
    @Bind(R.id.bing_pic_img)
    ImageView bingPicImg;

    private String weatherId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefresh.setColorSchemeResources(R.color.title_black);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String weatherString  = prefs.getString("weather", null);

        String bingPic =prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }

        if (!TextUtils.isEmpty(weatherString)) {
            //有缓存是直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            weatherId = "CN101010100"; //设置默认城市为北京
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void loadBingPic() {
        String requestUrl = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPicString = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext())
                        .edit();
                editor.putString("bing_pic", bingPicString);
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(bingPicString).into(bingPicImg);
                    }
                });
            }
        });
    }

    private void requestWeather(final String weather_Id) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weather_Id + "&key=f6847a6be492463c8ed830e524e573cc";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                try {
                    weatherId = weather.basic.weatherId;
                }catch (Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "暂无此城市天气信息", Toast.LENGTH_LONG).show();
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });

        loadBingPic();
    }

    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "°";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        navButton.setVisibility(View.VISIBLE);
        titleUpdateTime.setText(updateTime);
        titleUpdateTime.setVisibility(View.VISIBLE);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);

        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            //dateText.setTextColor(Color.BLACK);
            infoText.setText(forecast.more.info);
            //infoText.setTextColor(Color.BLACK);
            maxText.setText(forecast.temperature.max);
            //maxText.setTextColor(Color.BLACK);
            minText.setText(forecast.temperature.min);
            //minText.setTextColor(Color.BLACK);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "汽车指数：" + weather.suggestion.carWash.info;
        String sport = "运动建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    public void onEvent(RefreshEvent event) {
        drawerLayout.closeDrawers();
        swipeRefresh.setRefreshing(true);
        requestWeather(event.getWeatherId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
