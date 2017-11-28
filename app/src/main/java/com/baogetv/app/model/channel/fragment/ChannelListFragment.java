package com.baogetv.app.model.channel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.model.channel.ChannelDetailActivity;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.channel.adapter.ChannelListAdapter;
import com.baogetv.app.model.channel.entity.ChannelData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/11.
 */

public class ChannelListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, ItemViewHolder.ItemClickListener<ChannelData> {
    private static final String TAG = "ChannelListFragment";
    private View root;
    private View backBtn;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ChannelListAdapter recyclerViewAdapter;

    public static ChannelListFragment newInstance() {
        ChannelListFragment fragment = new ChannelListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_channel_list, container, false);
            init(root);
        }
        return root;
    }

    public void init(View root) {
        backBtn = root.findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.channel_srl);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new ChannelListAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
        List<ChannelData> datas = new ArrayList<>();
        for (int index = 0; index < 10; index ++) {
            datas.add(new ChannelData("", "" + index, 128, System.currentTimeMillis(), "精选"));
        }
        recyclerViewAdapter.update(datas);
        recyclerView = (RecyclerView) root.findViewById(R.id.channel_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(getActivity(),
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onItemClick(ChannelData data, int position) {
        Intent intent = new Intent(getActivity(), ChannelDetailActivity.class);
        getActivity().startActivity(intent);
    }

    private void getChannelList() {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }
}
