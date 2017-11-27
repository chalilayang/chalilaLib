package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.BeanConvert;
import com.baogetv.app.bean.ChannelDetailBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoListBean;
import com.baogetv.app.model.videodetail.adapter.VideoInfoListAdapter;
import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.PagerFragment.PAGE_DATA;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class VideoInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "VideoInfoFragment";
    private View contentView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private VideoInfoListAdapter recyclerViewAdapter;
    private VideoDetailData videoDetailData;
    private List<VideoListAdapter.IVideoData> iVideoDatas;
    private ChannelDetailBean channelDetailBean;

    public VideoInfoFragment() {
        iVideoDatas = new ArrayList<>();
    }
    public static VideoInfoFragment  newInstance(VideoDetailData data) {
        VideoInfoFragment  fragment = new VideoInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(PAGE_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoDetailData = getArguments().getParcelable(PAGE_DATA);
        }
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
        refreshLayout.setEnabled(false);
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
        recyclerViewAdapter.setVideoInfo(videoDetailData);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);
        if (channelDetailBean == null) {
            getChannelDetail();
        }
        if (iVideoDatas == null || iVideoDatas.size() <= 0) {
            getVideoList();
        }
    }

    private void getChannelDetail() {
        String cid = videoDetailData.videoDetailBean.getChannel_id();
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<ChannelDetailBean>> call = listService.getChannelDetail(cid);
        if (call != null) {
            call.enqueue(new CustomCallBack<ChannelDetailBean>() {
                @Override
                public void onSuccess(ChannelDetailBean data) {
                    Log.i(TAG, "onSuccess: ");
                    channelDetailBean = data;
                    recyclerViewAdapter.setChannelDetailBean(data);
                }

                @Override
                public void onFailed(String error) {
                    Log.i(TAG, "onFailed: ");
                }
            });
        }
    }

    private void getVideoList() {
        String cid = videoDetailData.videoDetailBean.getChannel_id();
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<List<VideoListBean>>> call = listService.getVideoList(cid, null, null, null, null);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<VideoListBean>>() {
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
                    recyclerViewAdapter.updateList(iVideoDatas);
                }

                @Override
                public void onFailed(String error) {

                }
            });
        }
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {

    }
}
