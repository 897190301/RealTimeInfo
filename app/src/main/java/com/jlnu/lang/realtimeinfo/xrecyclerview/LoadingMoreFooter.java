package com.jlnu.lang.realtimeinfo.xrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.util.ScreenUtils;


public class LoadingMoreFooter extends LinearLayout {

    private ImageView progressCon;
    private Context mContext;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    private TextView mText;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        mContext = context;
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(mContext, 40)));
        //自定义progressbar的样式
        progressCon = new ImageView(mContext);
        progressCon.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressCon.setImageResource(R.drawable.xrecyclerview_progressloading);
        addView(progressCon);
        mText = new TextView(context);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.textandiconmargin), 0, 0, 0);
        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                progressCon.setVisibility(VISIBLE);
                mText.setText("正在加载");
                mText.setVisibility(VISIBLE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText("加载完成");
                mText.setVisibility(VISIBLE);
                progressCon.setVisibility(INVISIBLE);
                this.setVisibility(INVISIBLE);
                break;
            default:
                break;
        }

    }
}
