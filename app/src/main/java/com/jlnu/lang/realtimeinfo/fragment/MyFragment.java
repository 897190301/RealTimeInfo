package com.jlnu.lang.realtimeinfo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlnu.lang.realtimeinfo.R;

/**
 * Created by qn on 2017/2/26.
 */

public class MyFragment extends Fragment {

    public static MyFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text",text);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        String text = getArguments().getString("text");
        TextView tv_show = (TextView) view.findViewById(R.id.tv_show);
        tv_show.setText(text);
        return view;
    }
}
