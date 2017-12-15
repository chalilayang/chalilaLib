package com.baogetv.app.model.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.VideoListBean;
import com.baogetv.app.model.search.SearchActivity;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by chalilayang on 2017/11/7.
 */

public class HomeFragment extends PagerFragment {

    private static final String TAG = "HomeFragment";
    private View searchBtn;
    private TabLayout tabLayout;
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
        tabLayout = root.findViewById(R.id.tab_layout);
        int padding = ScaleCalculator.getInstance(mActivity).scaleWidth(40);
        setIndicator(mActivity, tabLayout, padding, padding);
        searchBtn = root.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SearchActivity.class);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public BaseFragment createFragment(int pageIndex) {
        if (pageIndex == 1) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.week_ranking), PageItemData.TYPE_RANK_VIDEO_WEEK);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.month_ranking), PageItemData.TYPE_RANK_VIDEO_MONTH);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.all_ranking), PageItemData.TYPE_RANK_VIDEO);
            list.add(pageItemData);
            PageData pageData = new PageData(list);
            pageData.setTabStyle(1);
            return PagerFragment.newInstance(pageData);
        } else {
            return super.createFragment(pageIndex);
        }
    }
}
