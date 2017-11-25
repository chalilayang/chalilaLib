package com.baogetv.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.VideoListBean;
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageItemData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemFragment extends BaseItemFragment
        implements SwipeRefreshLayout.OnRefreshListener, ItemViewHolder.ItemClickListener<String>{

    private static final String TAG = "ItemFragment";
    private static final String PAGE_DATA = "PAGE_DATA";
    private PageItemData pageData;
    private SwipeRefreshLayout refreshLayout;
    private View contentView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseItemAdapter recyclerViewAdapter;
    private List<VideoListAdapter.IVideoData> iVideoDatas;

    public ItemFragment() {
        iVideoDatas = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return pageData.getTitle();
    }

    public static ItemFragment newInstance(PageItemData data) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(PAGE_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageData = getArguments().getParcelable(PAGE_DATA);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewAdapter = new VideoListAdapter(getActivity());
            recyclerViewAdapter.setItemClick(this);
            getVideoList(pageData);
        }
        Log.i(TAG, "onCreate: " + pageData.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            View view = inflater.inflate(R.layout.fragment_item_list, container, false);
            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            } else if (view instanceof SwipeRefreshLayout ){
                refreshLayout = (SwipeRefreshLayout) view;
                RecyclerView child = refreshLayout.findViewById(R.id.list);
                child.setLayoutManager(layoutManager);
                child.setAdapter(recyclerViewAdapter);
                refreshLayout.setOnRefreshListener(this);
                refreshLayout.setEnabled(false);
            }
            contentView = view;
        }
        Log.i(TAG, "onCreateView: " + pageData.getTitle());
        return contentView;
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }

    @Override
    public void onItemClick(String data, int position) {
        Log.i(TAG, "onItemClick: " + position);
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        getActivity().startActivity(intent);
    }

    public void getVideoList(PageItemData itemData) {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<VideoListBean> listBeanCall = listService.getVideoList(null, null, null, null, null);
        switch (itemData.getType()) {
            case PageItemData.TYPE_ALL_VIDEO:
                listBeanCall = listService.getVideoList(null, null, null, null, null);
                break;
            case PageItemData.TYPE_RANK_VIDEO:
                listBeanCall = listService.getVideoList(null, null, null, null, null);
                break;
        }
        if (listBeanCall != null) {
            listBeanCall.enqueue(new Callback<VideoListBean>() {
                @Override
                public void onResponse(Call<VideoListBean> call, Response<VideoListBean> response) {
                    Log.i(TAG, "onResponse: ");
                    VideoListBean bean = response.body();
                    if (bean != null) {
                        List<VideoListBean.DataBean> listBeen = bean.getData();
                        iVideoDatas.clear();
                        if (listBeen != null) {
                            for (int index = 0, count = listBeen.size(); index < count; index ++) {
                                VideoListAdapter.IVideoData data
                                        = new VideoData(listBeen.get(index).getPic_url());
                                iVideoDatas.add(data);
                            }
                        }
                        recyclerViewAdapter.update(iVideoDatas);
                    }
                    Log.i(TAG, "onResponse: " + bean.getUrl());
                }

                @Override
                public void onFailure(Call<VideoListBean> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t);
                }
            });
        }
    }

    public class VideoData implements VideoListAdapter.IVideoData {
        private final String picUrl;
        public VideoData(String pic) {
            this.picUrl = pic;
        }

        @Override
        public String getPicUrl() {
            return picUrl;
        }
    }
}
