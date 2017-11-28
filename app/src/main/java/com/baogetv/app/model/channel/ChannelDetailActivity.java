package com.baogetv.app.model.channel;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import static com.baogetv.app.R.id.error;

public class ChannelDetailActivity extends BaseActivity {
    public static final String KEY_CHANNEL_ID = "CHANNEL_ID";
    private PagerFragment searchResultFragment;
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

    private void refreshInfo() {
        if (detailBean != null) {
            Glide.with(this).load(detailBean.getPic_url()).into(imageView);
            Glide.with(this).load(detailBean.getPic_url()).into(circleImageView);
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
                public void onSuccess(ChannelDetailBean data) {
                    detailBean = data;
                    refreshInfo();
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
