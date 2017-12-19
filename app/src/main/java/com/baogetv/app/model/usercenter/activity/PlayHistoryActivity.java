package com.baogetv.app.model.usercenter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.CustomDialog;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.OnLoadMoreListener;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.HistoryBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.adapter.PlayHistoryListAdapter;
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;


public class PlayHistoryActivity extends BaseTitleActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<HistoryBean>,
        ItemViewHolder.ItemDeleteListener<HistoryBean>,
        OnLoadMoreListener.DataLoadingSubject {

    private static final String TAG = "PlayHistoryActivity";
    private static final int LOAD_PAGE_SIZE = 20;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PlayHistoryListAdapter recyclerViewAdapter;
    private OnLoadMoreListener onLoadMoreListener;
    private List<HistoryBean> historyBeanList;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_play_history));
        setRightTitle(getString(R.string.all_delete));
        init();
    }

    @Override
    public void onRightClick() {
        if (dialog == null) {
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setMessage(R.string.confirm_delete_all).setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (dialog != null) {
                                dialog.cancel();
                            }
                        }
                    }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    delete(null, 0);
                    if (dialog != null) {
                        dialog.cancel();
                    }
                }
            });
            dialog = builder.create();
        }
        dialog.show();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_my_collect;
    }

    public void init() {
        historyBeanList = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.collect_list_container);
        recyclerView = (RecyclerView) findViewById(R.id.collect_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        int margin_30px = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(30);
        divider.setMargin(margin_30px);
        recyclerView.addItemDecoration(divider);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new PlayHistoryListAdapter(this);
        recyclerViewAdapter.setItemClick(this);
        recyclerViewAdapter.setItemDeleteListener(this);
        onLoadMoreListener = new OnLoadMoreListener(layoutManager, this) {
            @Override
            public void onLoadMore(int totalItemCount) {
                Log.i(TAG, "onLoadMore: " + totalItemCount);
                int more = historyBeanList.size() % LOAD_PAGE_SIZE;
                int pageNum = historyBeanList.size() / LOAD_PAGE_SIZE + 1;
                if (more != 0) {
                    recyclerViewAdapter.setHasMoreData(false);
                } else {
                    getHistoryList(pageNum, LOAD_PAGE_SIZE);
                }
            }
        };
        recyclerView.addOnScrollListener(onLoadMoreListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
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

    private void getHistoryList(final int pageNum, final int pageSize) {
        if (LoginManager.hasLogin(getApplicationContext())) {
            UserApiService userApiService
                    = RetrofitManager.getInstance().createReq(UserApiService.class);
            String token = LoginManager.getUserToken(getApplicationContext());
            Call<ResponseBean<List<HistoryBean>>> call = userApiService.getHistoryList(
                    token, String.valueOf(pageNum), String.valueOf(pageSize));
            if (call != null) {
                refreshLayout.setRefreshing(true);
                isLoadingData = true;
                call.enqueue(new CustomCallBack<List<HistoryBean>>() {
                    @Override
                    public void onSuccess(List<HistoryBean> data, String msg, int state) {
                        if (pageNum <= 1) {
                            historyBeanList.clear();
                        }
                        if (data != null) {
                            Log.i(TAG, "onSuccess: " + data.size());
                            if (data.size() <= 0) {
                                recyclerViewAdapter.setHasMoreData(false);
                            } else {
                                for (int index = 0, count = data.size(); index < count; index ++) {
                                    HistoryBean bean = data.get(index);
                                    historyBeanList.add(bean);
                                }
                            }
                        }
                        recyclerViewAdapter.update(historyBeanList);
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
    }

    @Override
    public void onItemClick(HistoryBean data, int position) {
        Log.i(TAG, "onItemClick: ");
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra(KEY_VIDEO_ID, data.getVideo_id());
        startActivity(intent);
    }

    @Override
    public void onDelete(HistoryBean data, int pos) {
        Log.i(TAG, "onDelete: " + pos);
        delete(data, pos);
    }

    private void delete(final HistoryBean data, final int pos) {
        if (LoginManager.hasLogin(getApplicationContext())) {
            UserApiService userApiService
                    = RetrofitManager.getInstance().createReq(UserApiService.class);
            String token = LoginManager.getUserToken(getApplicationContext());
            String video_id = "";
            if (data != null) {
                video_id = data.getVideo_id();
            } else {
                List<HistoryBean> list = recyclerViewAdapter.getDataList();
                for (int index = 0, count = list.size(); index < count; index ++) {
                    if (index == 0) {
                        video_id = list.get(index).getVideo_id();
                    } else {
                        video_id = video_id + "," + list.get(index).getVideo_id();
                    }
                }
            }
            if (!TextUtils.isEmpty(video_id)) {
                refreshLayout.setRefreshing(true);
                Call<ResponseBean<List<Object>>> call
                        = userApiService.deleteHistory(token, video_id);
                if (call != null) {
                    refreshLayout.setRefreshing(true);
                    isLoadingData = true;
                    call.enqueue(new CustomCallBack<List<Object>>() {
                        @Override
                        public void onSuccess(List<Object> result, String msg, int state) {
                            Log.i(TAG, "onSuccess: ");
                            refreshLayout.setRefreshing(false);
                            isLoadingData = false;
                            if (data == null) {
                                getHistoryList(0, 2 * historyBeanList.size());
                            } else {
                                historyBeanList.remove(pos);
                                recyclerViewAdapter.removeItem(pos);
                            }
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
        } else {
//            if (data != null) {
//                HistoryManager.getInstance(getApplicationContext()).deleteHistory(data.getVideo_id());
//            } else {
//                HistoryManager.getInstance(getApplicationContext()).clearHistory();
//            }
//            getHistoryList();
        }
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        getHistoryList(0, LOAD_PAGE_SIZE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
