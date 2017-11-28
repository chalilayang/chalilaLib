package com.baogetv.app.model.channel;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.ChannelDetailBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.R.id.error;

public class ChannelDetailActivity extends BaseActivity {
    public static final String KEY_CHANNEL_ID = "CHANNEL_ID";
    private PagerFragment searchResultFragment;
    private ChannelDetailBean detailBean;
    private String channelId;
    private int imageHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);
        channelId = getIntent().getStringExtra(KEY_CHANNEL_ID);
        init();
        getChannelDetail(channelId);
    }

    private void init() {
        imageHeight = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(420);
    }

    private void showHomeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (searchResultFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.search_relative), PageItemData.TYPE_SEARCH_RELATIVE);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.search_play_most), PageItemData.TYPE_SEARCH_PLAY_MOST);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.search_latest_publish), PageItemData.TYPE_SEARCH_LATEST_PUBLISH);
            list.add(pageItemData);
            PageData pageData = new PageData(list);
            pageData.setTabStyle(1);
            searchResultFragment = PagerFragment.newInstance(pageData);
        }
        transaction.replace(R.id.fragment_container, searchResultFragment).commit();
    }

    private void getChannelDetail(String channelId) {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<ChannelDetailBean>> beanCall = listService.getChannelDetail(channelId);
        if (beanCall != null) {
            beanCall.enqueue(new CustomCallBack<ChannelDetailBean>() {
                @Override
                public void onSuccess(ChannelDetailBean data) {
                    detailBean = data;
                    if (detailBean != null) {
                        showHomeFragment();
                    }
                }

                @Override
                public void onFailed(String error) {
                    showError();
                }
            });
        }
    }
}
