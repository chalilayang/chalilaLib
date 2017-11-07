package com.reshape.app.model.homepage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalilayang.PagerFragment;
import com.chalilayang.parcelables.PageData;
import com.reshape.app.R;

/**
 * Created by chalilayang on 2017/11/7.
 */

public class HomeFragment extends PagerFragment {

    public static HomeFragment newInstance(PageData data) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(PAGE_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init(root);
        return root;
    }
}
