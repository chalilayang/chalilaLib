package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.CommentCountEvent;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class VideoDetailFragment extends PagerFragment {

    private VideoDetailData videoDetailData;
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
    }

    @Override
    public BaseFragment createFragment(int pageIndex) {
        if (pageIndex == 1) {
            return CommentListFragment.newInstance(videoDetailData);
        } else {
            return VideoInfoFragment.newInstance(videoDetailData);
        }
    }
}
