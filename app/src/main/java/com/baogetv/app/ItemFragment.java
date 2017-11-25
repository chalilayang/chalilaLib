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
import com.chalilayang.customview.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private BaseItemAdapter recyclerViewAdapter;
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
                    VideoListBean reponseBean = response.body();
                    if (reponseBean != null) {
                        List<VideoListBean.DataBean> listBeen = reponseBean.getData();
                        iVideoDatas.clear();
                        if (listBeen != null) {
                            for (int index = 0, count = listBeen.size(); index < count; index ++) {
                                VideoListBean.DataBean bean = listBeen.get(index);
                                VideoListAdapter.IVideoData data = new VideoData(
                                        bean.getPic_url(),
                                        bean.getTitle(),
                                        bean.getAdd_time(),
                                        bean.getPlay(),
                                        bean.getLength()
                                );
                                iVideoDatas.add(data);
                            }
                        }
                        recyclerViewAdapter.update(iVideoDatas);
                    }
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
        private final String title;
        private final String publishTime;
        private final String playCount;
        private final String videoDuration;
        public VideoData(String pic, String video_title, String publish_time, String count, String duration) {
            this.picUrl = pic;
            this.title = video_title;
            this.publishTime = publish_time;
            this.playCount = count;
            this.videoDuration = duration;
        }

        @Override
        public String getPicUrl() {
            return picUrl;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getPublishTime() {
            return publishTime;
        }

        @Override
        public String getDuration() {
            return videoDuration;
        }

        @Override
        public String getPlayCount() {
            return playCount;
        }
    }
}
