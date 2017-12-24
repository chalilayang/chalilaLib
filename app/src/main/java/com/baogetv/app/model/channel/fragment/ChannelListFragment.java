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
import com.baogetv.app.OnLoadMoreListener;
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
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<ChannelData>, OnLoadMoreListener.DataLoadingSubject {
    private static final String TAG = "ChannelListFragment";
    private static final int LOAD_PAGE_SIZE = 20;
    private View root;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager layoutManager;
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
        channelDataList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new ChannelListAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
        onLoadMoreListener = new OnLoadMoreListener(layoutManager, this) {
            @Override
            public void onLoadMore(int totalItemCount) {
                Log.i(TAG, "onLoadMore: " + totalItemCount);
                int more = channelDataList.size() % LOAD_PAGE_SIZE;
                int pageNum = channelDataList.size() / LOAD_PAGE_SIZE + 1;
                if (more != 0) {
                    recyclerViewAdapter.setHasMoreData(false);
                } else {
                    getChannelList(pageNum, LOAD_PAGE_SIZE);
                }
            }
        };
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
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.channel_srl);
        recyclerView = (RecyclerView) root.findViewById(R.id.channel_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(getActivity(),
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addOnScrollListener(onLoadMoreListener);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onItemClick(ChannelData data, int position) {
        Intent intent = new Intent(getActivity(), ChannelDetailActivity.class);
        intent.putExtra(ChannelDetailActivity.KEY_CHANNEL_ID, data.channelId);
        getActivity().startActivity(intent);
    }

    @Override
    public boolean isLoading() {
        return isLoadingData;
    }

    private boolean isLoadingData;
    @Override
    public boolean needLoadMore() {
        return true;
    }

    private void getChannelList(final int pageNum, final int pageSize) {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<List<ChannelListBean>>> beanCall = listService.getChannelList(
                null, null, String.valueOf(pageNum), String.valueOf(pageSize));
        if (beanCall != null) {
            refreshLayout.setRefreshing(true);
            isLoadingData = true;
            beanCall.enqueue(new CustomCallBack<List<ChannelListBean>>() {
                @Override
                public void onSuccess(List<ChannelListBean> listBeen, String msg, int state) {
                    if (pageNum <= 1) {
                        channelDataList.clear();
                    }
                    if (listBeen != null) {
                        if (listBeen.size() <= 0) {
                            recyclerViewAdapter.setHasMoreData(false);
                        } else {
                            for (int index = 0, count = listBeen.size(); index < count; index ++) {
                                ChannelListBean bean = listBeen.get(index);
                                channelDataList.add(BeanConvert.getChannelData(bean));
                            }
                        }
                    }
                    recyclerViewAdapter.update(channelDataList);
                    refreshLayout.setRefreshing(false);
                    isLoadingData = false;
                }
                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                    refreshLayout.setRefreshing(false);
                    isLoadingData = false;
                }
            });
        } else {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        getChannelList(0, LOAD_PAGE_SIZE);
    }
}
