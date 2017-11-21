package com.baogetv.app.model.videodetail.activity;

import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.fragment.VideoDetailFragment;
import com.baogetv.app.parcelables.PageItemData;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailActivity extends BaseActivity {

    private VideoDetailFragment videoDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        View content = findViewById(R.id.content_view);
        Rect outRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) content.getLayoutParams();
        params.height = outRect.bottom - outRect.top;
        showHomeFragment();
    }

    private void showHomeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (videoDetailFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.simple_introduce), 0);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.comment), 0);
            list.add(pageItemData);
            videoDetailFragment = VideoDetailFragment.newInstance(new VideoDetailData(list));
        }
        transaction.replace(R.id.video_detail_fragment_container, videoDetailFragment).commit();
    }
}
