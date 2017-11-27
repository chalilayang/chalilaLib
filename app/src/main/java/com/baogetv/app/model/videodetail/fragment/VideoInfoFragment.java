package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.adapter.CollectListAdapter;
import com.baogetv.app.model.usercenter.entity.VideoData;
import com.baogetv.app.model.videodetail.adapter.VideoInfoListAdapter;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class VideoInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View contentView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private VideoInfoListAdapter recyclerViewAdapter;

    public static VideoInfoFragment newInstance() {
        VideoInfoFragment fragment = new VideoInfoFragment();
        Bundle args = new Bundle();
//        args.putParcelable(PAGE_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_video_info, container, false);
            init(contentView);
        }
        return contentView;
    }

    public void init(View root) {
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.video_list_container);
        recyclerView = (RecyclerView) root.findViewById(R.id.video_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(getActivity().getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        int margin_30px = ScaleCalculator.getInstance(
                getActivity().getApplicationContext()).scaleWidth(30);
        divider.setMargin(margin_30px);
        recyclerView.addItemDecoration(divider);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new VideoInfoListAdapter(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);

        List<VideoData> datas = new ArrayList<>();
        for (int index = 0; index < 10; index ++) {
            datas.add(new VideoData("", "" + index, System.currentTimeMillis()));
        }
        recyclerViewAdapter.updateList(datas);
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {

    }
}
