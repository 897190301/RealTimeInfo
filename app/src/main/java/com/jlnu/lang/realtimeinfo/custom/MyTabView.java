package com.jlnu.lang.realtimeinfo.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlnu.lang.realtimeinfo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lilang on 2017/3/23.
 */
public class MyTabView extends LinearLayout{

	private onTabSelectListener listener;
	private View contentView;
	private Context context;
	@Bind(R.id.iv_news)
	ImageView iv_news;
	@Bind(R.id.iv_film)
	ImageView iv_film;
	@Bind(R.id.iv_weather)
	ImageView iv_weather;
	@Bind(R.id.tv_news)
	TextView tv_news;
	@Bind(R.id.tv_film)
	TextView tv_film;
	@Bind(R.id.tv_weather)
	TextView tv_weather;

	@OnClick({R.id.ll_news, R.id.ll_film, R.id.ll_weather})
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.ll_news:
				changeTabStatus(R.id.ll_news);
				listener.setPosition(0);
				break;
			case R.id.ll_film:
				//changeTabStatus(R.id.ll_film);
				listener.setPosition(1);
				break;
			case R.id.ll_weather:
				changeTabStatus(R.id.ll_weather);
				listener.setPosition(2);
				break;
			default:
				break;
		}
	}



	public MyTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public MyTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setListener(onTabSelectListener listener) {
		this.listener = listener;
		init();
	}

	public void init() {

		contentView = LayoutInflater.from(context).inflate(R.layout.view_my_tab, this,false);
		ButterKnife.bind(this, contentView);
		LinearLayout.LayoutParams params = (LayoutParams) contentView.getLayoutParams();
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		params.height = 150;
		params.gravity = Gravity.CENTER;
		contentView.setLayoutParams(params);
		this.addView(contentView);
		initView();
	}

	private void initView() {
	}


	private void changeTabStatus(int id) {

		iv_news.setImageResource(R.mipmap.news_uncheck);
		iv_film.setImageResource(R.mipmap.film_uncheck);
		iv_weather.setImageResource(R.mipmap.weather_uncheck);

		tv_news.setTextColor(this.getResources().getColor(R.color.home_tab_text));
		tv_film.setTextColor(this.getResources().getColor(R.color.home_tab_text));
		tv_weather.setTextColor(this.getResources().getColor(R.color.home_tab_text));

		switch (id) {
			case R.id.ll_news:
				iv_news.setImageResource(R.mipmap.news_check);
				tv_news.setTextColor(this.getResources().getColor(R.color.home_tab_text_check));
				break;
			case R.id.ll_film:
				iv_film.setImageResource(R.mipmap.film_check);
				tv_film.setTextColor(this.getResources().getColor(R.color.home_tab_text_check));
				break;
			case R.id.ll_weather:
				iv_weather.setImageResource(R.mipmap.weather_check);
				tv_weather.setTextColor(this.getResources().getColor(R.color.home_tab_text_check));
				break;
			default:
				break;
		}
	}


	public interface onTabSelectListener {
		 void setPosition(int position);
	}
}
