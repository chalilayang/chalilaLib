package com.reshape.app.model.homepage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalilayang.BaseFragment;
import com.chalilayang.PagerFragment;
import com.chalilayang.parcelables.PageData;
import com.chalilayang.parcelables.PageItemData;
import com.reshape.app.R;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public BaseFragment createFragment(int pageIndex) {
        if (pageIndex == 1) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.week_ranking), 0);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.month_ranking), 0);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.all_ranking), 0);
            list.add(pageItemData);
            return PagerFragment.newInstance(new PageData(list));
        } else {
            return super.createFragment(pageIndex);
        }

    }
}
