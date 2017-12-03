package com.baogetv.app.model.videodetail.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.fragment.CommentDetailFragment;

import static com.baogetv.app.PagerFragment.PAGE_DATA;

public class CommentDetailActivity extends BaseTitleActivity {

    private CommentDetailFragment fragment;
    private CommentData commentData;
    private VideoDetailData videoDetailData;
    public static final String KEY_COMMENT_DATA = "COMMENT_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.comment_detail));
        commentData = getIntent().getParcelableExtra(KEY_COMMENT_DATA);
        videoDetailData = getIntent().getParcelableExtra(PAGE_DATA);
        showFragment(videoDetailData, commentData);
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_response;
    }
    private void showFragment(VideoDetailData v, CommentData c) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = CommentDetailFragment.newInstance(v, c);
        }
        transaction.replace(R.id.root_container, fragment).commit();
    }
}
