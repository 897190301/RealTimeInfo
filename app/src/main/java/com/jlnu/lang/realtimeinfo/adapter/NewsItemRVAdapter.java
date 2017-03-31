package com.jlnu.lang.realtimeinfo.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.bean.NewsInfo;
import com.jlnu.lang.realtimeinfo.network.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lilang on 2017/1/11.
 */

public class NewsItemRVAdapter extends RecyclerView.Adapter<NewsItemRVAdapter.ViewHolder>{

    private List<NewsInfo> mNewsInfoList;
    private LayoutInflater mInflater;
    private OnItemClickListener listener;


    public NewsItemRVAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mNewsInfoList = new ArrayList<>();
    }

    public void setData(List<NewsInfo> mNewsInfos) {
        if (mNewsInfos == null || mNewsInfos.size() == 0) {
            return;
        }
        mNewsInfoList.clear();
        mNewsInfoList.addAll(mNewsInfos);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_news_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setPosition(position);
        NewsInfo newsInfo = mNewsInfoList.get(position);
        if (newsInfo == null) {
            return;
        }
        if (TextUtils.isEmpty(newsInfo.getThumbnail_pic_s02())) {

            holder.rl_one_pic.setVisibility(View.VISIBLE);
            holder.rl_three_pic.setVisibility(View.GONE);

            holder.tv_one_pic_title.setText(newsInfo.getTitle());
            holder.getTv_one_pic_author.setText(newsInfo.getAuthor_name());
            holder.getTv_one_pic_date.setText(newsInfo.getDate());
            ImageUtils.loadToImageView(newsInfo.getThumbnail_pic_s(), holder.iv_one_pic);

        } else {
            holder.rl_one_pic.setVisibility(View.GONE);
            holder.rl_three_pic.setVisibility(View.VISIBLE);
            holder.tv_three_pic_title.setText(newsInfo.getTitle());
            holder.tv_three_pic_author.setText(newsInfo.getAuthor_name());
            holder.tv_three_pic_date.setText(newsInfo.getDate());
            ImageUtils.loadToImageView(newsInfo.getThumbnail_pic_s(), holder.iv_three_pic_first);
            ImageUtils.loadToImageView(newsInfo.getThumbnail_pic_s02(), holder.iv_three_pic_second);
            if (!TextUtils.isEmpty(newsInfo.getThumbnail_pic_s03())) {
                holder.iv_three_pic_third.setVisibility(View.VISIBLE);
                ImageUtils.loadToImageView(newsInfo.getThumbnail_pic_s03(), holder.iv_three_pic_third);
            } else {
                holder.iv_three_pic_third.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNewsInfoList == null ? 0 : mNewsInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.iv_one_pic)
        ImageView iv_one_pic;
        @Bind(R.id.tv_one_pic_title)
        TextView tv_one_pic_title;
        @Bind(R.id.tv_one_pic_author)
        TextView getTv_one_pic_author;
        @Bind(R.id.tv_one_pic_date)
        TextView getTv_one_pic_date;
        @Bind(R.id.iv_three_pic_first)
        ImageView iv_three_pic_first;
        @Bind(R.id.iv_three_pic_second)
        ImageView iv_three_pic_second;
        @Bind(R.id.iv_three_pic_third)
        ImageView iv_three_pic_third;
        @Bind(R.id.tv_three_pic_title)
        TextView tv_three_pic_title;
        @Bind(R.id.tv_three_pic_author)
        TextView tv_three_pic_author;
        @Bind(R.id.tv_three_pic_date)
        TextView tv_three_pic_date;
        @Bind(R.id.rl_one_pic)
        RelativeLayout rl_one_pic;
        @Bind(R.id.rl_three_pic)
        RelativeLayout rl_three_pic;
        @Bind(R.id.ll_news_item)
        LinearLayout ll_news_item;

        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            ll_news_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(mNewsInfoList.get(position).getUrl());
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String url);
    }


}
