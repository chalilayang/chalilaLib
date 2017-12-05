package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.SystemInfoBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.adapter.InfoListAdapter;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.List;

import retrofit2.Call;


public class SystemNotifyAcitvity extends BaseTitleActivity
        implements ItemViewHolder.ItemClickListener<SystemInfoBean>, ItemViewHolder.ItemDeleteListener<SystemInfoBean>{

    private static final String TAG = "SystemNotifyAcitvity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private InfoListAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.system_notify));
        setRightTitle(getString(R.string.all_tag_read));
        init();
        getInfoList();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_system_notify;
    }

    @Override
    public void onRightClick() {
        tagRead(null, 0);
    }

    public void init() {
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
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void getInfoList() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<List<SystemInfoBean>>> call = userApiService.getSystemInfoList(token);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<SystemInfoBean>>() {
                @Override
                public void onSuccess(List<SystemInfoBean> data) {
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
                call.enqueue(new CustomCallBack<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data) {
                        Log.i(TAG, "onSuccess: ");
                        getInfoList();
                    }

                    @Override
                    public void onFailed(String error) {
                        showShortToast(error);
                    }
                });
            }
        }
    }
}
