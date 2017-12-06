package com.baogetv.app.model.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.HistoryBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.db.entity.HistoryItemEntity;
import com.baogetv.app.model.usercenter.HistoryManager;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.adapter.PlayHistoryListAdapter;
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.util.TimeUtil;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;


public class PlayHistoryActivity extends BaseTitleActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<HistoryBean>, ItemViewHolder.ItemDeleteListener<HistoryBean>  {

    private static final String TAG = "PlayHistoryActivity";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PlayHistoryListAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_play_history));
        setRightTitle(getString(R.string.all_delete));
        init();
        getHistoryList();
    }

    @Override
    public void onRightClick() {
        delete(null, 0);
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_my_collect;
    }

    public void init() {
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
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
    }

    private void getHistoryList() {
        if (LoginManager.hasLogin(getApplicationContext())) {
            refreshLayout.setRefreshing(true);
            UserApiService userApiService
                    = RetrofitManager.getInstance().createReq(UserApiService.class);
            String token = LoginManager.getUserToken(getApplicationContext());
            Call<ResponseBean<List<HistoryBean>>> call = userApiService.getHistoryList(token);
            if (call != null) {
                call.enqueue(new CustomCallBack<List<HistoryBean>>() {
                    @Override
                    public void onSuccess(List<HistoryBean> data, String msg, int state) {
                        recyclerViewAdapter.update(data);
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        } else {
            List<HistoryItemEntity> list
                    = HistoryManager.getInstance(getApplicationContext()).getHistoryList();
            List<HistoryBean> beanList = new ArrayList<>();
            if (list != null && list.size() > 0) {
                for (int index = 0, count = list.size(); index < count; index ++) {
                    HistoryItemEntity entity = list.get(index);
                    HistoryBean bean = new HistoryBean();
                    bean.setAdd_time(TimeUtil.getTimeStateNew(entity.getAddTime()));
                    bean.setVideo_id(entity.getVideoId());
                    bean.setTitle(entity.getVideoTitle());
                    bean.setPic_url(entity.getPicUrl());
                    beanList.add(bean);
                }
            }
            recyclerViewAdapter.update(beanList);
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(HistoryBean data, int position) {
        Log.i(TAG, "onItemClick: ");
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra(KEY_VIDEO_ID, data.getId());
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
            String id = "";
            if (data != null) {
                id = data.getId();
            } else {
                List<HistoryBean> list = recyclerViewAdapter.getDataList();
                for (int index = 0, count = list.size(); index < count; index ++) {
                    if (index == 0) {
                        id = list.get(index).getId();
                    } else {
                        id = id + "," + list.get(index).getId();
                    }
                }
            }
            if (!TextUtils.isEmpty(id)) {
                Call<ResponseBean<List<Object>>> call
                        = userApiService.deleteHistory(token, id);
                if (call != null) {
                    call.enqueue(new CustomCallBack<List<Object>>() {
                        @Override
                        public void onSuccess(List<Object> data, String msg, int state) {
                            Log.i(TAG, "onSuccess: ");
                            getHistoryList();
                        }

                        @Override
                        public void onFailed(String error, int state) {
                            showShortToast(error);
                        }
                    });
                }
            }
        } else {
            if (data != null) {
                HistoryManager.getInstance(getApplicationContext()).deleteHistory(data.getVideo_id());
            } else {
                HistoryManager.getInstance(getApplicationContext()).clearHistory();
            }
            getHistoryList();
        }
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        getHistoryList();
    }
}
