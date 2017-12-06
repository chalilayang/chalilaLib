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

import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.BeanConvert;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoListBean;
import com.baogetv.app.bean.VideoRankListBean;
import com.baogetv.app.model.channel.entity.ChannelItemData;
import com.baogetv.app.model.search.SearchItemData;
import com.baogetv.app.model.usercenter.entity.MemberItemData;
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageItemData;
import com.chalilayang.customview.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;


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
            }
            contentView = view;
        }
        getVideoList(pageData);
        Log.i(TAG, "onCreateView: " + pageData.getTitle());
        return contentView;
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        getVideoList(pageData);
    }

    @Override
    public void onItemClick(VideoListAdapter.IVideoData data, int position) {
        Log.i(TAG, "onItemClick: " + position);
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        intent.putExtra(KEY_VIDEO_ID, data.getVideoID());
        getActivity().startActivity(intent);
    }

    public void getVideoList(PageItemData itemData) {
        if (itemData.getType() == PageItemData.TYPE_ALL_VIDEO) {
            VideoListService listService
                    = RetrofitManager.getInstance().createReq(VideoListService.class);
            Call<ResponseBean<List<VideoListBean>>> listBeanCall
                    = listService.getVideoList(null, null, null, null, null);
            if (listBeanCall != null) {
                refreshLayout.setRefreshing(true);
                listBeanCall.enqueue(new CustomCallBack<List<VideoListBean>>() {
                    @Override
                    public void onSuccess(List<VideoListBean> listBeen, String msg, int state) {
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
                        refreshLayout.setRefreshing(false);
                    }
                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                        refreshLayout.setRefreshing(false);
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
                refreshLayout.setRefreshing(true);
                listBeanCall.enqueue(new CustomCallBack<List<VideoRankListBean>>() {
                    @Override
                    public void onSuccess(List<VideoRankListBean> listBeen, String msg, int state) {
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
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        } else if (itemData.getType() == PageItemData.TYPE_SEARCH_RELATIVE
                || itemData.getType() == PageItemData.TYPE_SEARCH_PLAY_MOST
                || itemData.getType() == PageItemData.TYPE_SEARCH_LATEST_PUBLISH) {
            if (!(pageData instanceof SearchItemData)) {
                return;
            }
            String searchKey = ((SearchItemData) pageData).getSearchKey();
            String orderType = "0";
            if (itemData.getType() == PageItemData.TYPE_SEARCH_PLAY_MOST) {
                orderType = "1";
            } else if (itemData.getType() == PageItemData.TYPE_SEARCH_LATEST_PUBLISH) {
                orderType = "2";
            }
            VideoListService listService
                    = RetrofitManager.getInstance().createReq(VideoListService.class);
            Call<ResponseBean<List<VideoListBean>>> listBeanCall
                    = listService.getVideoList(null, null, searchKey, orderType, "0");
            if (listBeanCall != null) {
                refreshLayout.setRefreshing(true);
                listBeanCall.enqueue(new CustomCallBack<List<VideoListBean>>() {
                    @Override
                    public void onSuccess(List<VideoListBean> listBeen, String msg, int state) {
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
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        } else if (itemData.getType() == PageItemData.TYPE_CHANNEL_HOT
                || itemData.getType() == PageItemData.TYPE_CHANNEL_DATE) {
            if (!(pageData instanceof ChannelItemData)) {
                return;
            }
            ChannelItemData channelItemData = (ChannelItemData) pageData;
            VideoListService listService
                    = RetrofitManager.getInstance().createReq(VideoListService.class);
            Call<ResponseBean<List<VideoListBean>>> listBeanCall = null;
            switch (itemData.getType()) {
                case PageItemData.TYPE_CHANNEL_HOT:
                    listBeanCall = listService.getVideoList(
                            channelItemData.getChannelId(), null, null, "1", null);
                    break;
                case PageItemData.TYPE_CHANNEL_DATE:
                    listBeanCall = listService.getVideoList(
                            channelItemData.getChannelId(), null, null, "2", null);
                    break;
            }
            if (listBeanCall != null) {
                refreshLayout.setRefreshing(true);
                listBeanCall.enqueue(new CustomCallBack<List<VideoListBean>>() {
                    @Override
                    public void onSuccess(List<VideoListBean> listBeen, String msg, int state) {
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
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        refreshLayout.setRefreshing(false);
                        showShortToast(error);
                    }
                });
            }
        } else if (itemData.getType() == PageItemData.TYPE_MEMBER_COLLECT) {
            if (!(pageData instanceof MemberItemData)) {
                return;
            }
            MemberItemData channelItemData = (MemberItemData) pageData;
            UserApiService userApiService
                    = RetrofitManager.getInstance().createReq(UserApiService.class);
            Call<ResponseBean<List<CollectBean>>> call
                    = userApiService.getCollectList(null, channelItemData.getMemberId());
            if (call != null) {
                refreshLayout.setRefreshing(true);
                call.enqueue(new CustomCallBack<List<CollectBean>>() {
                    @Override
                    public void onSuccess(List<CollectBean> data, String msg, int state) {
                        iVideoDatas.clear();
                        if (data != null) {
                            for (int index = 0, count = data.size(); index < count; index ++) {
                                CollectBean bean = data.get(index);
                                VideoListAdapter.IVideoData iVideoData
                                        = BeanConvert.getIVideoData(bean);
                                iVideoDatas.add(iVideoData);
                            }
                        }
                        recyclerViewAdapter.update(iVideoDatas);
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        refreshLayout.setRefreshing(false);
                        showShortToast(error);
                    }
                });
            }
        }
    }
}
