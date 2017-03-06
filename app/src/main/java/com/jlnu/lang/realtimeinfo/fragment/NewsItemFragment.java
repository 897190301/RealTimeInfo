package com.jlnu.lang.realtimeinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.adapter.NewsItemRVAdapter;
import com.jlnu.lang.realtimeinfo.application.App;
import com.jlnu.lang.realtimeinfo.bean.GetNewsResult;
import com.jlnu.lang.realtimeinfo.bean.NewsInfo;
import com.jlnu.lang.realtimeinfo.presenter.IGetNewsPresenter;
import com.jlnu.lang.realtimeinfo.presenter.impl.GetNewsPresenter;
import com.jlnu.lang.realtimeinfo.view.IGetNewsView;
import com.jlnu.lang.realtimeinfo.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by qn on 2017/1/11.
 */

public class NewsItemFragment extends Fragment implements IGetNewsView {

    @Bind(R.id.news_item_recyclerview)
    XRecyclerView mNewsRecyclerView;

    public static String NEWS_KEY = "news_key";
    private IGetNewsPresenter mGetNewsPresenter;
    private List<NewsInfo> mNewsInfoList = null;
    private NewsItemRVAdapter mRvNewsAdapter;
    private View view = null;
    private String newsType = null;

    public static NewsFragment newInstance(String key) {
        Bundle args = new Bundle();
        args.putString(NEWS_KEY, key);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news_item, container, false);
        ButterKnife.bind(this, view);
        initData();
        initRecyclerView();
        loadData();
        return view;
    }

    private void initData() {
        mGetNewsPresenter = new GetNewsPresenter(this);
        newsType = App.getNewsType(getArguments().getString(NEWS_KEY));
        Log.e("type",newsType);
    }

    private void loadData() {
        mGetNewsPresenter.getNews(newsType);
    }

    private void initRecyclerView() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mNewsRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRvNewsAdapter = new NewsItemRVAdapter(getActivity());
        mNewsRecyclerView.setAdapter(mRvNewsAdapter);
        mNewsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mGetNewsPresenter.getNews(newsType);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }


    @Override
    public void onGetNewsComplete() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNewsRecyclerView.refreshComplete();
            }
        });
    }

    @Override
    public void onGetNewsSuccess(GetNewsResult result) {
        if (result == null) {
            return;
        }
        mNewsInfoList = result.getResult().getData();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRvNewsAdapter.setData(mNewsInfoList);
            }
        });
    }

    @Override
    public void onGetNewsFailure(String errorInfo) {
        //Log.e("onGetNewsFailure", errorInfo);
    }
}
