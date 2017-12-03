package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;
import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.adapter.CollectListAdapter;

import java.util.List;

import retrofit2.Call;


public class MyCollectActivity extends BaseTitleActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<CollectBean>, ItemViewHolder.ItemDeleteListener<CollectBean> {
    private static final String TAG = "MyCollectActivity";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CollectListAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_collect));
        setRightTitle(getString(R.string.all_delete));
        init();
        getCollectList();
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
        recyclerViewAdapter = new CollectListAdapter(this);
        recyclerViewAdapter.setItemClick(this);
        recyclerViewAdapter.setItemDeleteListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnabled(false);
    }

    private void getCollectList() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<List<CollectBean>>> call = userApiService.getCollectList(token, null);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<CollectBean>>() {
                @Override
                public void onSuccess(List<CollectBean> data) {
                    recyclerViewAdapter.update(data);
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
    }

    @Override
    public void onItemClick(CollectBean data, int position) {
        Log.i(TAG, "onItemClick: ");
    }

    @Override
    public void onDelete(CollectBean data, int pos) {
        Log.i(TAG, "onDelete: " + pos);
        delete(data, pos);
    }

    private void delete(final CollectBean data, final int pos) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        String videoId = null;
        String id = null;
        if (data != null) {
            videoId = data.getVideo_id();
            id = data.getId();
        }
        Call<ResponseBean<List<Object>>> call
                = userApiService.deleteCollect(token, videoId, id);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<Object>>() {
                @Override
                public void onSuccess(List<Object> data) {
                    Log.i(TAG, "onSuccess: ");
                    if (data == null) {
                        recyclerViewAdapter.deleteAllItem();
                    } else {
                        recyclerViewAdapter.deleteItem(pos);
                    }
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }
}
