package com.baogetv.app.model.channel;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.ChannelDetailBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.channel.entity.ChannelItemData;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ChannelDetailActivity extends BaseActivity {
    public static final String KEY_CHANNEL_ID = "CHANNEL_ID";
    private PagerFragment channelFragment;
    private ChannelDetailBean detailBean;
    private String channelId;
    private int imageHeight;
    private int detailHeight;
    private int size_44px;
    private int size_180px;
    private int sumHeight;

    private AppBarLayout appBarLayout;
    private ImageView imageView;
    private View circleImageContainer;
    private ImageView circleImageView;
    private TextView channelTitle;
    private TextView channelInfo;
    private TextView channelDesc;
    private String infoFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);
        channelId = getIntent().getStringExtra(KEY_CHANNEL_ID);
        init();
        getChannelDetail(channelId);
    }

    private void init() {
        infoFormat = getString(R.string.channel_detail_info_format);
        imageHeight = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(390);
        detailHeight = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(272);
        size_44px = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(44);
        size_180px = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(180);
        sumHeight = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(682);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.getLayoutParams().height = sumHeight;
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.getLayoutParams().height = imageHeight;
        circleImageContainer = findViewById(R.id.circle_image_container);
        RelativeLayout.LayoutParams rlp
                = (RelativeLayout.LayoutParams) circleImageContainer.getLayoutParams();
        rlp.height = size_180px;
        rlp.topMargin = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(346);
        circleImageView = (ImageView) findViewById(R.id.circle_image);
        circleImageView.getLayoutParams().width = size_180px;
        circleImageView.getLayoutParams().height = size_180px;

        channelTitle = (TextView) findViewById(R.id.channel_title);
        channelInfo = (TextView) findViewById(R.id.channel_info);
        channelDesc = (TextView) findViewById(R.id.channel_desc);

        View bottomDivider = findViewById(R.id.bottom_divider);
        rlp = (RelativeLayout.LayoutParams) bottomDivider.getLayoutParams();
        rlp.height = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(20);
        rlp.topMargin = sumHeight - 2 * rlp.height;
    }

    private void showHomeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (channelFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new ChannelItemData(
                    getString(R.string.channel_hot), PageItemData.TYPE_CHANNEL_HOT, channelId);
            list.add(pageItemData);
            pageItemData = new ChannelItemData(
                    getString(R.string.channel_time), PageItemData.TYPE_CHANNEL_DATE, channelId);
            list.add(pageItemData);
            PageData pageData = new PageData(list);
            channelFragment = PagerFragment.newInstance(pageData);
        }
        transaction.replace(R.id.fragment_container, channelFragment).commit();
    }

    private void refreshInfo() {
        if (detailBean != null) {
            Glide.with(this).load(detailBean.getCover_url())
                    .placeholder(R.mipmap.mengceng).into(imageView);
            Glide.with(this).load(detailBean.getPic_url())
                    .placeholder(R.mipmap.user_default_icon).into(circleImageView);
            channelTitle.setText(detailBean.getName());
            String info = String.format(
                    infoFormat,
                    detailBean.getCount(), detailBean.getCount(), detailBean.getCount());
            channelInfo.setText(info);
            channelDesc.setText(detailBean.getIntro());
        }
    }

    private void getChannelDetail(String channelId) {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<ChannelDetailBean>> beanCall = listService.getChannelDetail(channelId);
        if (beanCall != null) {
            beanCall.enqueue(new CustomCallBack<ChannelDetailBean>() {
                @Override
                public void onSuccess(ChannelDetailBean data, String msg, int state) {
                    detailBean = data;
                    refreshInfo();
                    if (detailBean != null) {
                        showHomeFragment();
                    }
                }

                @Override
                public void onFailed(String error, int state) {
                    showError();
                }
            });
        }
    }
}
