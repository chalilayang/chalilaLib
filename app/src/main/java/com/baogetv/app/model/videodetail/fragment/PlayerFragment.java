package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.model.videodetail.player.PlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_DETAIL;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class PlayerFragment extends BaseFragment {
    private static final String TAG = "PlayerFragment";
    private View contentView;
    private VideoDetailBean videoDetailBean;

    private NiceVideoPlayer mNiceVideoPlayer;
    public static PlayerFragment newInstance(VideoDetailBean videoDetailBean) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_VIDEO_DETAIL, videoDetailBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoDetailBean = getArguments().getParcelable(KEY_VIDEO_DETAIL);
        }
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_player, container, false);
            init(contentView);
        }
        return contentView;
    }

    public void init(View root) {
        mNiceVideoPlayer = (NiceVideoPlayer) root.findViewById(R.id.player_surface);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        if (videoDetailBean != null) {
            mNiceVideoPlayer.setUp(videoDetailBean.getFile_url(), null);
            Log.i(TAG, "init: " + videoDetailBean.getFile_url());
            NiceVideoPlayerController controller = new PlayerController(this.getActivity());
            mNiceVideoPlayer.setController(controller);
            mNiceVideoPlayer.continueFromLastPosition(false);
            controller.setTitle(videoDetailBean.getTitle());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        mNiceVideoPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        mNiceVideoPlayer.pause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
    }

    public void release() {
        if (mNiceVideoPlayer != null) {
            mNiceVideoPlayer.release();
        }
    }
}
