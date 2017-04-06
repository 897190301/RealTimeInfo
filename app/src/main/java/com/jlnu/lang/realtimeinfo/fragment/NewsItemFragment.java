package com.jlnu.lang.realtimeinfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.activity.NewsDetailActivity;
import com.jlnu.lang.realtimeinfo.adapter.NewsItemRVAdapter;
import com.jlnu.lang.realtimeinfo.application.App;
import com.jlnu.lang.realtimeinfo.bean.GetNewsResult;
import com.jlnu.lang.realtimeinfo.bean.NewsInfo;
import com.jlnu.lang.realtimeinfo.helper.AbsViewHelper;
import com.jlnu.lang.realtimeinfo.presenter.IGetNewsPresenter;
import com.jlnu.lang.realtimeinfo.presenter.impl.GetNewsPresenter;
import com.jlnu.lang.realtimeinfo.view.IGetNewsView;
import com.jlnu.lang.realtimeinfo.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by lilang on 2017/1/11.
 */

public class NewsItemFragment extends Fragment implements IGetNewsView, NewsItemRVAdapter.OnItemClickListener, AbsViewHelper.OnOrderStatusListener {

    @Bind(R.id.news_item_recyclerview)
    XRecyclerView mNewsRecyclerView;
    @Bind(R.id.ll_cover)
    LinearLayout ll_cover;
    @Bind(R.id.ll_data)
    LinearLayout ll_data;

    public static String NEWS_KEY = "news_key";
    private IGetNewsPresenter mGetNewsPresenter;
    private List<NewsInfo> mNewsInfoList = null;
    private NewsItemRVAdapter mRvNewsAdapter;
    private View view = null;
    private String newsType = null;
    private AbsViewHelper viewHelper;

//    public static NewsFragment newInstance(String key) {
//        Bundle args = new Bundle();
//        args.putString(NEWS_KEY, key);
//        NewsFragment fragment = new NewsFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }


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
        viewHelper = new AbsViewHelper(getActivity(), ll_cover);
        viewHelper.setOrderStatusListener(this);
    }

    private void loadData() {
        setHasData(false);
        viewHelper.setLoading();
        mGetNewsPresenter.getNews(newsType);
    }

    private void setHasData(boolean hasData) {
        if (hasData) {
            if (ll_data.getVisibility() != View.VISIBLE) {
                ll_data.setVisibility(View.VISIBLE);
            }
        } else {
            if (ll_data.getVisibility() != View.GONE) {
                ll_data.setVisibility(View.GONE);
            }
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mNewsRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRvNewsAdapter = new NewsItemRVAdapter(getActivity());
        mNewsRecyclerView.setAdapter(mRvNewsAdapter);
        mRvNewsAdapter.setOnItemClickListener(this);
        mNewsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mGetNewsPresenter.getNews(newsType);
            }

            @Override
            public void onLoadMore() {
                mNewsRecyclerView.loadMoreComplete();
            }
        });
    }


    @Override
    public void onGetNewsComplete() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNewsRecyclerView.refreshComplete();
                viewHelper.clearAllViews();
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
                setHasData(true);
                mRvNewsAdapter.setData(mNewsInfoList);
            }
        });
    }

    @Override
    public void onGetNewsFailure(String errorInfo) {
        //Log.e("onGetNewsFailure", errorInfo);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setHasData(false);
                viewHelper.setDataError();
            }
        });
    }

    @Override
    public void onItemClick(String url) {
        Intent intent = NewsDetailActivity.newIntent(getActivity(), url);
        startActivity(intent);
    }

    @Override
    public void onClickButton() {
        mGetNewsPresenter.getNews(newsType);
    }
}
