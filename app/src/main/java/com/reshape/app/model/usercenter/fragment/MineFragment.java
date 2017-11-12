package com.reshape.app.model.usercenter.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reshape.app.BaseFragment;
import com.reshape.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {

    private View contentView;
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }
    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            View view = inflater.inflate(R.layout.fragment_mine, container, false);
            init(view);
            contentView = view;
        }
        return contentView;
    }

    private void init(View view) {

    }

}
