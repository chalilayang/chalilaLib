package com.baogetv.app.model.videodetail.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.customview.LogoImageView;
import com.baogetv.app.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;

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
                videoTitle.setText(data.getTitle());
                String publish = null;
                try {
                    publish = TimeUtil.getTimeStateNew(data.getPublishTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(publish)) {
                    publish = data.getPublishTime();
                }
                String desc
                        = String.format(videoDescFormat, data.getPlayCount(), publish);
                videoDesc.setText(desc);
                videoDuration.setText(data.getDuration());
                String pic = data.getPicUrl();
                mContentView.setChnLogoVisible(data.isCHN());
                Glide.with(mContext)
                        .load(pic)
                        .crossFade()
                        .placeholder(R.mipmap.pic_loding)
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
        void setPlayCount(String count);
    }
}
