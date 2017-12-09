package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.AdvBean;
import com.baogetv.app.bean.AdvListBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.CommentCountEvent;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import retrofit2.Call;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class VideoDetailFragment extends PagerFragment {

    private VideoDetailData videoDetailData;
    private TextView advBtn;
    private AdvBean advBean;
    public static VideoDetailFragment newInstance(VideoDetailData data) {
        VideoDetailFragment fragment = new VideoDetailFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_video_detail, container, false);
        init(root);
        getAdvList();
        return root;
    }

    @Subscribe
    public void handleCommentCount(CommentCountEvent event) {
        if (tabLayout != null) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            if (tab != null) {
                String content = getString(R.string.comment) + "(" + event.count + ")";
                tab.setText(content);
            }
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void init(View root) {
        super.init(root);
        advBtn = root.findViewById(R.id.ad_link);
        advBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (advBean != null) {
                    advClick(advBean.getId());
                }
            }
        });
    }

    @Override
    public BaseFragment createFragment(int pageIndex) {
        if (pageIndex == 1) {
            return CommentListFragment.newInstance(videoDetailData);
        } else {
            return VideoInfoFragment.newInstance(videoDetailData);
        }
    }

    private void getAdvList() {
        VideoListService service = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<List<AdvListBean>>> call = service.getAdvList(null, "3");
        if (call != null) {
            call.enqueue(new CustomCallBack<List<AdvListBean>>() {
                @Override
                public void onSuccess(List<AdvListBean> data, String msg, int state) {
                    if (data != null) {
                        getAdvDetail(data.get(0).getId());
                    }
                }

                @Override
                public void onFailed(String error, int state) {

                }
            });
        }
    }

    private void getAdvDetail(String id) {
        VideoListService service = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<AdvBean>> call = service.getAdvDetail(id);
        if (call != null) {
            call.enqueue(new CustomCallBack<AdvBean>() {
                @Override
                public void onSuccess(AdvBean data, String msg, int state) {
                    if (data != null) {
                        advBean = data;
                        advBtn.setText(data.getTitle());
                    }
                }

                @Override
                public void onFailed(String error, int state) {

                }
            });
        }
    }

    private void advClick(String id) {
        VideoListService service = RetrofitManager.getInstance().createReq(VideoListService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<List<Object>>> call = service.advClick(token, id);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<Object>>() {
                @Override
                public void onSuccess(List<Object> data, String msg, int state) {

                }

                @Override
                public void onFailed(String error, int state) {

                }
            });
        }
    }
}
