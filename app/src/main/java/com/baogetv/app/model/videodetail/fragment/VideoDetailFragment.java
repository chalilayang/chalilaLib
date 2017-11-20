package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class VideoDetailFragment extends PagerFragment {
    public static VideoDetailFragment newInstance(PageData data) {
        VideoDetailFragment fragment = new VideoDetailFragment();
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
