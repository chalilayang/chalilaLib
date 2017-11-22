package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.entity.VideoData;
import com.baogetv.app.model.videodetail.adapter.VideoInfoListAdapter;
import com.baogetv.app.model.videodetail.customview.VideoPlayerView;
import com.baogetv.app.model.videodetail.entity.PlayData;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class PlayerFragment extends BaseFragment {

    private View contentView;
    private VideoPlayerView playerView;
    private ImageView playPauseBtn;

    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
//        args.putParcelable(PAGE_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_player, container, false);
            init(contentView);
        }
        return contentView;
    }

    public void init(View root) {
        playerView = root.findViewById(R.id.player_surface);
        playerView.setData(new PlayData(PlayData.NET, "http://videos.baoge.tv/Uploads/Download/2017-11-19/5a114d32969a5.mp4"));
        playPauseBtn = (ImageView) root.findViewById(R.id.pause_btn);
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerView.isPlaying()) {
                    playPauseBtn.setImageResource(R.mipmap.play_start);
                    playerView.pause();
                } else {
                    playPauseBtn.setImageResource(R.mipmap.play_pause);
                    playerView.resume();
                }
            }
        });
    }

    public void release() {
        if (playerView != null) {
            playerView.release();
        }
    }
}
