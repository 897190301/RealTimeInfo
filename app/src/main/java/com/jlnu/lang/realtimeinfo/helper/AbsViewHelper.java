package com.jlnu.lang.realtimeinfo.helper;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jlnu.lang.realtimeinfo.R;

/**
 * Created by lilang on 2017/3/7.
 */

public class AbsViewHelper {

    private Context context;
    private LinearLayout rootLay;
    //数据加载
    private View dataLoadingView;
    private ImageView iv_data_load;
    private AnimationDrawable animationDrawable;

    public AbsViewHelper(Context context, LinearLayout linearLayout) {
        this.context = context;
        this.rootLay = linearLayout;
        inflaterViews();
    }

    public void inflaterViews() {
        dataLoadingView = LayoutInflater.from(context).inflate(R.layout.view_data_loading, null);
        findViews();
    }

    public void findViews() {
        iv_data_load = (ImageView) dataLoadingView.findViewById(R.id.iv_data_load);
        animationDrawable = (AnimationDrawable) iv_data_load.getBackground();
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
}
