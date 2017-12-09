package com.baogetv.app.model.videodetail.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.customview.LogoImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chalilayang.scaleview.ScaleCalculator;
import com.chalilayang.scaleview.ScaleTextView;

import java.util.List;

public class VideoListAdapter
        extends BaseItemAdapter<VideoListAdapter.IVideoData, VideoListAdapter.ViewHolder> {

    private int imageHeight;
    private String videoDescFormat;
    public VideoListAdapter(Context context) {
        super(context);
        imageHeight = ScaleCalculator.getInstance(mContext).scaleWidth(420);
        videoDescFormat = mContext.getResources().getString(R.string.video_desc_format);
        setNeedLoadMore(true);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        vlp.height = imageHeight;
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder createMoreViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleWidth(60);
        return new ViewHolder(view);
    }

    public class ViewHolder extends ItemViewHolder<IVideoData> {
        public final LogoImageView mContentView;
        public final TextView videoTitle;
        public final TextView videoDesc;
        public final TextView videoDuration;

        public final TextView loadMoreTip;
        public ViewHolder(View view) {
            super(view);
            loadMoreTip = view.findViewById(R.id.load_more_tip);
            mContentView = (LogoImageView) view.findViewById(R.id.img);
            videoTitle = (TextView) view.findViewById(R.id.video_title);
            videoDesc = (TextView) view.findViewById(R.id.video_desc);
            videoDuration = (TextView) view.findViewById(R.id.video_duration);
        }

        @Override
        public void bindData(IVideoData data, int pos) {
            if (loadMoreTip != null) {
                loadMoreTip.setText(hasMoreData?loadingMore : noMoreData);
            } else {
                mContentView.setChnLogoVisible(true);
                videoTitle.setText(data.getTitle());
                String desc
                        = String.format(videoDescFormat, data.getPlayCount(), data.getPublishTime());
                videoDesc.setText(desc);
                videoDuration.setText(data.getDuration());
                String pic = data.getPicUrl();
                mContentView.setChnLogoVisible(data.isCHN());
                Glide.with(mContext)
                        .load(pic)
                        .crossFade()
                        .into(mContentView);
            }
        }
    }

    public interface IVideoData {
        String getVideoID();
        String getPicUrl();
        String getTitle();
        String getPublishTime();
        String getDuration();
        String getPlayCount();
        boolean isCHN();
        boolean isPro();
    }
}
