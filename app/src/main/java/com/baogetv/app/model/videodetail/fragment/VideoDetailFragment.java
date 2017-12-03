package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;

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
