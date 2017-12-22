package com.baogetv.app.model.videodetail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.OnLoadMoreListener;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.BeanConvert;
import com.baogetv.app.bean.ChannelDetailBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.bean.VideoListBean;
import com.baogetv.app.model.channel.ChannelDetailActivity;
import com.baogetv.app.model.search.SearchResultActivity;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.model.videodetail.adapter.VideoInfoListAdapter;
import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.AddCollectEvent;
import com.baogetv.app.model.videodetail.event.CacheEvent;
import com.baogetv.app.model.videodetail.event.FreshCacheEvent;
import com.baogetv.app.model.videodetail.event.FreshInfoEvent;
import com.baogetv.app.model.videodetail.event.ShareEvent;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.PagerFragment.PAGE_DATA;
import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class VideoInfoFragment extends BaseFragment 
        implements SwipeRefreshLayout.OnRefreshListener,
        VideoInfoListAdapter.OnClickCallBack,
        OnLoadMoreListener.DataLoadingSubject {

    private static final String TAG = "VideoInfoFragment";
    private static final int LOAD_PAGE_SIZE = 10;
    private static final int VIDEO_COUNT = 15;
    private View contentView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private VideoInfoListAdapter recyclerViewAdapter;
    private VideoDetailData videoDetailData;
    private List<VideoListAdapter.IVideoData> iVideoDatas;
    private ChannelDetailBean channelDetailBean;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean hasGotRelated;

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
        hasGotRelated = false;
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new VideoInfoListAdapter(getActivity());
        onLoadMoreListener = new OnLoadMoreListener(layoutManager, this) {
            @Override
            public void onLoadMore(int totalItemCount) {
                Log.i(TAG, "onLoadMore: " + totalItemCount);
                if (hasGotRelated) {
                    recyclerViewAdapter.setHasMoreData(false);
                } else {
                    int more = iVideoDatas.size() % LOAD_PAGE_SIZE;
                    int pageNum = iVideoDatas.size() / LOAD_PAGE_SIZE + 1;
                    if (more != 0) {
                        recyclerViewAdapter.setHasMoreData(false);
                    } else {
                        getVideoList(pageNum);
                    }
                }
            }
        };
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
        refreshLayout.setEnabled(true);
        recyclerView = (RecyclerView) root.findViewById(R.id.video_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(getActivity().getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        int margin_30px = ScaleCalculator.getInstance(
                getActivity().getApplicationContext()).scaleWidth(30);
        divider.setMargin(margin_30px);
        recyclerView.addItemDecoration(divider);
        recyclerViewAdapter.setVideoInfo(videoDetailData);
        recyclerViewAdapter.setOnClickListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(onLoadMoreListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);
        if (channelDetailBean == null) {
            getChannelDetail();
        }
        if (iVideoDatas == null || iVideoDatas.size() <= 0) {
            getVideoList(0);
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
                public void onSuccess(ChannelDetailBean data, String msg, int state) {
                    Log.i(TAG, "onSuccess: ");
                    channelDetailBean = data;
                    recyclerViewAdapter.setChannelDetailBean(data);
                }

                @Override
                public void onFailed(String error, int state) {
                    Log.i(TAG, "onFailed: ");
                    showShortToast(error);
                }
            });
        }
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

    private void getVideoList(final int pageNum) {
        String cid = videoDetailData.videoDetailBean.getChannel_id();
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<List<VideoListBean>>> call = listService.getVideoList(
                cid, null, null, null, null,
                String.valueOf(pageNum), String.valueOf(LOAD_PAGE_SIZE));
        if (call != null) {
            refreshLayout.setRefreshing(true);
            isLoadingData = true;
            call.enqueue(new CustomCallBack<List<VideoListBean>>() {
                @Override
                public void onSuccess(List<VideoListBean> listBeen, String msg, int state) {
                    if (pageNum <= 1) {
                        iVideoDatas.clear();
                    }
                    if (listBeen != null) {
                        if (listBeen.size() <= 0) {
                            if (!hasGotRelated) {
                                if (iVideoDatas.size() < VIDEO_COUNT) {
                                    getVideoRelated(VIDEO_COUNT - iVideoDatas.size());
                                }
                            } else {
                                recyclerViewAdapter.setHasMoreData(false);
                            }
                        } else {
                            for (int index = 0, count = listBeen.size(); index < count; index ++) {
                                VideoListBean bean = listBeen.get(index);
                                VideoListAdapter.IVideoData iVideoData
                                        = BeanConvert.getIVideoData(bean, true);
                                iVideoDatas.add(iVideoData);
                            }
                            if (listBeen.size() < LOAD_PAGE_SIZE) {
                                if (iVideoDatas.size() < VIDEO_COUNT) {
                                    getVideoRelated(VIDEO_COUNT - iVideoDatas.size());
                                }
                            }
                        }
                    }
                    recyclerViewAdapter.updateList(iVideoDatas);
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
        }
    }

    private void getVideoRelated(int size) {
        String vid = videoDetailData.videoDetailBean.getId();
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<List<VideoListBean>>> call = listService.getVideoListRelated(
                vid, String.valueOf(0), String.valueOf(size));
        if (call != null) {
            refreshLayout.setRefreshing(true);
            isLoadingData = true;
            call.enqueue(new CustomCallBack<List<VideoListBean>>() {
                @Override
                public void onSuccess(List<VideoListBean> listBeen, String msg, int state) {
                    recyclerViewAdapter.setHasMoreData(false);
                    hasGotRelated = true;
                    if (listBeen != null) {
                        if (listBeen.size() > 0) {
                            for (int index = 0, count = listBeen.size(); index < count; index ++) {
                                VideoListBean bean = listBeen.get(index);
                                VideoListAdapter.IVideoData iVideoData
                                        = BeanConvert.getIVideoData(bean, true);
                                iVideoDatas.add(iVideoData);
                            }
                        }
                    }
                    recyclerViewAdapter.updateList(iVideoDatas);
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
        }
    }

    private void getVideoDetail() {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        String token = null;
        if (LoginManager.hasLogin(mActivity.getApplicationContext())) {
            token = LoginManager.getUserToken(mActivity.getApplicationContext());
        }
        Call<ResponseBean<VideoDetailBean>> call = listService.getVideoDetail(
                videoDetailData.videoDetailBean.getId(), token);
        if (call != null) {
            call.enqueue(new CustomCallBack<VideoDetailBean>() {
                @Override
                public void onSuccess(VideoDetailBean data, String msg, int state) {
                    if (data != null) {
                        videoDetailData.videoDetailBean = data;
                        recyclerViewAdapter.setVideoInfo(videoDetailData);
                    }
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    @Override
    public void onChannelClick(ChannelDetailBean bean) {
        Log.i(TAG, "onChannelClick: ");
        Intent intent = new Intent(getActivity(), ChannelDetailActivity.class);
        intent.putExtra(ChannelDetailActivity.KEY_CHANNEL_ID, bean.getId());
        mActivity.startActivity(intent);
    }

    @Override
    public void onVideoClick(String vid) {
        Log.i(TAG, "onVideoClick: ");
        Intent intent = new Intent(mActivity, VideoDetailActivity.class);
        intent.putExtra(KEY_VIDEO_ID, vid);
        mActivity.startActivity(intent);
    }

    @Override
    public void onCacheClick(VideoDetailBean bean) {
        EventBus.getDefault().post(new CacheEvent());
    }

    @Override
    public void onShareClick(VideoDetailBean bean) {
        Log.i(TAG, "onShareClick: ");
        EventBus.getDefault().post(new ShareEvent());
    }

    @Override
    public void onCollectClick(VideoDetailBean bean) {
        Log.i(TAG, "onCollectClick: ");
        EventBus.getDefault().post(new AddCollectEvent());
    }

    @Override
    public void onLabelClick(String label) {
        if (!TextUtils.isEmpty(label)) {
            Intent intent = new Intent(mActivity, SearchResultActivity.class);
            intent.putExtra(SearchResultActivity.KEY_SEARCH, label);
            startActivity(intent);
        }
    }

    @Subscribe
    public void handleFreshInfoEvent(FreshInfoEvent event) {
        getVideoDetail();
    }

    @Subscribe
    public void handleFreshCacheEvent(FreshCacheEvent event) {
        recyclerViewAdapter.notifyItemChanged(0);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        hasGotRelated = false;
        getChannelDetail();
        getVideoList(0);
    }
}
