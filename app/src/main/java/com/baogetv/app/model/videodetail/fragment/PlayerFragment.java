package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.model.videodetail.player.PlayerController;
import com.bumptech.glide.Glide;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import static com.baogetv.app.model.videodetail.activity.VideoDetailActivity.KEY_VIDEO_DETAIL;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class PlayerFragment extends BaseFragment {

    private View contentView;
    private ImageView playPauseBtn;

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
        mNiceVideoPlayer = (NiceVideoPlayer) root.findViewById(R.id.player_surface);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        mNiceVideoPlayer.setUp("http://videos.baoge.tv/Uploads/Download/2017-11-19/5a114d32969a5.mp4", null);
        NiceVideoPlayerController controller = new PlayerController(this.getActivity());
        mNiceVideoPlayer.setController(controller);
    }

    public void release() {
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
}
