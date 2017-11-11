package com.reshape.app.model.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reshape.app.BaseFragment;
import com.reshape.app.PagerFragment;
import com.reshape.app.R;
import com.reshape.app.model.search.SearchActivity;
import com.reshape.app.parcelables.PageData;
import com.reshape.app.parcelables.PageItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/7.
 */

public class HomeFragment extends PagerFragment {

    private View searchBtn;
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
    public void init(View root) {
        super.init(root);
        searchBtn = root.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
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
