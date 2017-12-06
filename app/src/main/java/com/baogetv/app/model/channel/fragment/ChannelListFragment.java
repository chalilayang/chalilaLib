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
import com.baogetv.app.bean.BeanConvert;
import com.baogetv.app.bean.ChannelListBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.channel.ChannelDetailActivity;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.channel.adapter.ChannelListAdapter;
import com.baogetv.app.model.channel.entity.ChannelData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

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
    private List<ChannelData> channelDataList;

    public static ChannelListFragment newInstance() {
        ChannelListFragment fragment = new ChannelListFragment();
        return fragment;
    }

    public ChannelListFragment() {
        channelDataList = new ArrayList<>();
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
            if (channelDataList.size() <= 0) {
                getChannelList();
            }
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
        intent.putExtra(ChannelDetailActivity.KEY_CHANNEL_ID, data.channelId);
        getActivity().startActivity(intent);
    }

    private void getChannelList() {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<List<ChannelListBean>>> beanCall = listService.getChannelList(null, null);
        if (beanCall != null) {
            beanCall.enqueue(new CustomCallBack<List<ChannelListBean>>() {
                @Override
                public void onSuccess(List<ChannelListBean> listBeen, String msg, int state) {
                    channelDataList.clear();
                    if (listBeen != null) {
                        for (int index = 0, count = listBeen.size(); index < count; index ++) {
                            ChannelListBean bean = listBeen.get(index);
                            ChannelData iVideoData
                                    = BeanConvert.getChannelData(bean);
                            channelDataList.add(iVideoData);
                        }
                    }
                    recyclerViewAdapter.update(channelDataList);
                }
                @Override
                public void onFailed(String error, int state) {
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }
}
