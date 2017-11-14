package com.reshape.app.model.usercenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;
import com.reshape.app.BaseTitleActivity;
import com.reshape.app.R;
import com.reshape.app.model.usercenter.adapter.CollectListAdapter;
import com.reshape.app.model.usercenter.entity.VideoData;

import java.util.ArrayList;
import java.util.List;


public class MyCollectActivity extends BaseTitleActivity
        implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MyCollectActivity";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CollectListAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_collect));
        init();
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
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);

        List<VideoData> datas = new ArrayList<>();
        for (int index = 0; index < 10; index ++) {
            datas.add(new VideoData("", "" + index, System.currentTimeMillis()));
        }
        recyclerViewAdapter.updateChannelList(datas);
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }
}
