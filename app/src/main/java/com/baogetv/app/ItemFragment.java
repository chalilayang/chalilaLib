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
import com.baogetv.app.bean.BeanConvert;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoListBean;
import com.baogetv.app.bean.VideoRankListBean;
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageItemData;
import com.chalilayang.customview.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class ItemFragment extends BaseItemFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<VideoListAdapter.IVideoData>{

    private static final String TAG = "ItemFragment";
    private static final String PAGE_DATA = "PAGE_DATA";
    private PageItemData pageData;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private View contentView;
    private RecyclerView.LayoutManager layoutManager;
    private VideoListAdapter recyclerViewAdapter;
    private List<VideoListAdapter.IVideoData> iVideoDatas;
    private RecyclerViewDivider recyclerViewDivider;

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
                recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            } else if (view instanceof SwipeRefreshLayout ){
                refreshLayout = (SwipeRefreshLayout) view;
                recyclerView = refreshLayout.findViewById(R.id.list);
                recyclerViewDivider
                        = new RecyclerViewDivider(getActivity(),
                        LinearLayoutManager.HORIZONTAL, 20,
                        getResources().getColor(R.color.white));
                recyclerView.addItemDecoration(recyclerViewDivider);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
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
    public void onItemClick(VideoListAdapter.IVideoData data, int position) {
        Log.i(TAG, "onItemClick: " + position);
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        intent.putExtra(VideoDetailActivity.KEY_VIDEO_ID, data.getVideoID());
        getActivity().startActivity(intent);
    }

    public void getVideoList(PageItemData itemData) {
        if (itemData.getType() == PageItemData.TYPE_ALL_VIDEO) {
            VideoListService listService
                    = RetrofitManager.getInstance().createReq(VideoListService.class);
            Call<ResponseBean<List<VideoListBean>>> listBeanCall
                    = listService.getVideoList(null, null, null, null, null);
            if (listBeanCall != null) {
                listBeanCall.enqueue(new CustomCallBack<List<VideoListBean>>() {
                    @Override
                    public void onSuccess(List<VideoListBean> listBeen) {
                        iVideoDatas.clear();
                        if (listBeen != null) {
                            for (int index = 0, count = listBeen.size(); index < count; index ++) {
                                VideoListBean bean = listBeen.get(index);
                                VideoListAdapter.IVideoData iVideoData
                                        = BeanConvert.getIVideoData(bean);
                                iVideoDatas.add(iVideoData);
                            }
                        }
                        recyclerViewAdapter.update(iVideoDatas);
                    }
                    @Override
                    public void onFailed(String error) {
                        Log.i(TAG, "onFailure: " + error);
                    }
                });
            }
        } else if (itemData.getType() == PageItemData.TYPE_RANK_VIDEO
                || itemData.getType() == PageItemData.TYPE_RANK_VIDEO_WEEK
                || itemData.getType() == PageItemData.TYPE_RANK_VIDEO_MONTH) {
            VideoListService listService
                    = RetrofitManager.getInstance().createReq(VideoListService.class);
            Call<ResponseBean<List<VideoRankListBean>>> listBeanCall = null;
            switch (itemData.getType()) {
                case PageItemData.TYPE_RANK_VIDEO:
                    listBeanCall = listService.getRankVideoList(0);
                    break;
                case PageItemData.TYPE_RANK_VIDEO_WEEK:
                    listBeanCall = listService.getRankVideoList(1);
                    break;
                case PageItemData.TYPE_RANK_VIDEO_MONTH:
                    listBeanCall = listService.getRankVideoList(2);
                    break;

            }
            if (listBeanCall != null) {
                listBeanCall.enqueue(new CustomCallBack<List<VideoRankListBean>>() {
                    @Override
                    public void onSuccess(List<VideoRankListBean> listBeen) {
                        iVideoDatas.clear();
                        if (listBeen != null) {
                            for (int index = 0, count = listBeen.size(); index < count; index ++) {
                                VideoRankListBean bean = listBeen.get(index);
                                VideoListAdapter.IVideoData iVideoData
                                        = BeanConvert.getIVideoData(bean);
                                iVideoDatas.add(iVideoData);
                            }
                        }
                        recyclerViewAdapter.update(iVideoDatas);
                    }

                    @Override
                    public void onFailed(String error) {
                        Log.i(TAG, "onFailure: " + error);
                    }
                });
            }
        } else if (itemData.getType() == PageItemData.TYPE_SEARCH_RELATIVE
                || itemData.getType() == PageItemData.TYPE_SEARCH_PLAY_MOST
                || itemData.getType() == PageItemData.TYPE_SEARCH_LATEST_PUBLISH) {
            VideoListService listService
                    = RetrofitManager.getInstance().createReq(VideoListService.class);
            Call<ResponseBean<List<VideoListBean>>> listBeanCall
                    = listService.getVideoList(null, null, null, null, null);
            if (listBeanCall != null) {
                listBeanCall.enqueue(new CustomCallBack<List<VideoListBean>>() {
                    @Override
                    public void onSuccess(List<VideoListBean> listBeen) {
                        iVideoDatas.clear();
                        if (listBeen != null) {
                            for (int index = 0, count = listBeen.size(); index < count; index++) {
                                VideoListBean bean = listBeen.get(index);
                                VideoListAdapter.IVideoData iVideoData
                                        = BeanConvert.getIVideoData(bean);
                                iVideoDatas.add(iVideoData);
                            }
                        }
                        recyclerViewAdapter.update(iVideoDatas);
                    }

                    @Override
                    public void onFailed(String error) {
                        Log.i(TAG, "onFailure: " + error);
                    }
                });
            }
        }
    }
}
