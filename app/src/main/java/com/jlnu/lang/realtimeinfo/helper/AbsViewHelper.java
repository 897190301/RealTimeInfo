package com.jlnu.lang.realtimeinfo.helper;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jlnu.lang.realtimeinfo.R;

/**
 * Created by lilang on 2017/3/7.
 */

public class AbsViewHelper implements View.OnClickListener{

    private Context context;
    private LinearLayout rootLay;
    //数据加载
    private View dataLoadingView; //加载中
    protected View dataErrorView  = null; //网络异常
    private ImageView iv_data_load;
    private TextView tvReload = null;
    private AnimationDrawable animationDrawable;
    private OnOrderStatusListener listener;

    public AbsViewHelper(Context context, LinearLayout linearLayout) {
        this.context = context;
        this.rootLay = linearLayout;
        inflaterViews();
    }

    public void inflaterViews() {
        LayoutInflater inflater = LayoutInflater.from(context);
        dataLoadingView = inflater.inflate(R.layout.view_data_loading, null);
        dataErrorView = inflater.inflate(R.layout.view_load_error, null);
        findViews();
    }

    public void findViews() {
        iv_data_load = (ImageView) dataLoadingView.findViewById(R.id.iv_data_load);
        animationDrawable = (AnimationDrawable) iv_data_load.getBackground();
        tvReload = (TextView) dataErrorView.findViewById(R.id.tvReload);
        tvReload.setOnClickListener(this);
    }

    /**
     * 加载数据动画
     */
    public void setLoading() {
        if (rootLay == null) {
            return;
        }
        setGone(false);
        clearAllViews();
        rootLay.addView(dataLoadingView);
        animationDrawable.start();
    }

    //设置数据异常
    public void setDataError(){
        if (rootLay == null) {
            return;
        }
        clearAllViews();
        setGone(false);
        rootLay.addView(dataErrorView);
        cancelAnimation();
    }

    public void clearViewAndGone() {
        if (rootLay == null) {
            return;
        }
        cancelAnimation();
        clearAllViews();
        setGone(true);
    }

    /**
     * 关闭数据加载动画
     */
    public void cancelAnimation() {
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }


    public void setGone(boolean isGone) {
        if (rootLay == null) {
            return;
        }
        if (isGone) {
            //设置隐藏
            if (rootLay.getVisibility() != View.GONE) {
                rootLay.setVisibility(View.GONE);
            }
        } else {
            //设置可见
            if (rootLay.getVisibility() != View.VISIBLE) {
                rootLay.setVisibility(View.VISIBLE);
            }
        }
    }

    public void clearAllViews() {
        if (rootLay == null) {
            return;
        }
        if (rootLay.getChildCount() > 0) {
            rootLay.removeAllViews();
        }
    }

    /**
     * 监听按钮点击
     */
    public interface OnOrderStatusListener {
        void onClickButton();
    }

    public void setOrderStatusListener(OnOrderStatusListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReload:
                if (listener != null) {
                    listener.onClickButton();
                }

                break;
            default:
                break;
        }
    }
}
