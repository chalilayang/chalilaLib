package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.OnLoadMoreListener;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.HistoryBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.SystemInfoBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.adapter.InfoListAdapter;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;
import com.xiao.nicevideoplayer.LogUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class SystemNotifyActivity extends BaseTitleActivity
        implements ItemViewHolder.ItemClickListener<SystemInfoBean>,
        SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemDeleteListener<SystemInfoBean>,
        OnLoadMoreListener.DataLoadingSubject {

    private static final String TAG = "SystemNotifyActivity";
    private static final int LOAD_PAGE_SIZE = 20;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private InfoListAdapter recyclerViewAdapter;
    private OnLoadMoreListener onLoadMoreListener;
    private List<SystemInfoBean> historyBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.system_notify));
        setRightTitle(getString(R.string.all_tag_read));
        init();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_system_notify;
    }

    @Override
    public void onRightClick() {
        tagRead(null, 0);
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

    public void init() {
        historyBeanList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.info_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        int margin_30px = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(30);
        divider.setMargin(margin_30px);
        recyclerView.addItemDecoration(divider);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new InfoListAdapter(this);
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
                    getInfoList(pageNum, LOAD_PAGE_SIZE);
                }
            }
        };
        recyclerView.addOnScrollListener(onLoadMoreListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.info_list_container);
        refreshLayout.setOnRefreshListener(this);
    }

    private void getInfoList(final int pageNum, final int pageSize) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<List<SystemInfoBean>>> call = userApiService.getSystemInfoList(
                token, String.valueOf(pageNum), String.valueOf(pageSize));
        if (call != null) {
            refreshLayout.setRefreshing(true);
            isLoadingData = true;
            call.enqueue(new CustomCallBack<List<SystemInfoBean>>() {
                @Override
                public void onSuccess(List<SystemInfoBean> data, String msg, int state) {
                    if (pageNum <= 1) {
                        historyBeanList.clear();
                    }
                    if (data != null) {
                        if (data.size() <= 0) {
                            recyclerViewAdapter.setHasMoreData(false);
                        } else {
                            for (int index = 0, count = data.size(); index < count; index ++) {
                                SystemInfoBean bean = data.get(index);
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

    @Override
    public void onItemClick(SystemInfoBean data, int position) {
        Log.i(TAG, "onItemClick: ");
    }

    @Override
    public void onDelete(SystemInfoBean data, int pos) {
        Log.i(TAG, "onDelete: " + pos);
        tagRead(data, pos);
    }

    private void tagRead(final SystemInfoBean data, final int pos) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        String id = "";
        if (data != null) {
            id = data.getId();
        } else {
            List<SystemInfoBean> list = recyclerViewAdapter.getDataList();
            for (int index = 0, count = list.size(); index < count; index ++) {
                if (index == 0) {
                    id = list.get(index).getId();
                } else {
                    id = id + "," + list.get(index).getId();
                }
            }
        }
        Log.i(TAG, "tagRead: " + id);
        if (!TextUtils.isEmpty(id)) {
            Call<ResponseBean<List<Object>>> call
                    = userApiService.delelteInfo(id, token);
            if (call != null) {
                refreshLayout.setRefreshing(true);
                isLoadingData = true;
                call.enqueue(new CustomCallBack<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data, String msg, int state) {
                        Log.i(TAG, "onSuccess: ");
                        refreshLayout.setRefreshing(false);
                        isLoadingData = false;
                        if (data == null) {
                            getInfoList(0, 2 * historyBeanList.size());
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
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        getInfoList(0, LOAD_PAGE_SIZE);
    }
}
