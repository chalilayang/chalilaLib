package com.baogetv.app.model.videodetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.R;
import com.baogetv.app.bean.ChannelDetailBean;
import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;
import com.nex3z.flowlayout.FlowLayout;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class VideoInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<VideoListAdapter.IVideoData> mValues;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEAD = 1;
    private Context mContext;
    private int margin_8px;
    private int margin_15px;
    private int margin_20px;
    private int margin_30px;
    private int margin_160px;
    private String videoCountFormat;
    private VideoDetailData videoDetailData;
    private ChannelDetailBean channelDetailBean;
    private String playCountFormat;

    public VideoInfoListAdapter(Context context) {
        mValues = new ArrayList<>();
        mContext = context;
        margin_8px = ScaleCalculator.getInstance(mContext).scaleWidth(8);
        margin_15px = ScaleCalculator.getInstance(mContext).scaleWidth(15);
        margin_20px = ScaleCalculator.getInstance(mContext).scaleWidth(20);
        margin_30px = ScaleCalculator.getInstance(mContext).scaleWidth(30);
        margin_160px = ScaleCalculator.getInstance(mContext).scaleWidth(160);
        videoCountFormat = mContext.getString(R.string.video_count_format);
        playCountFormat = mContext.getString(R.string.play_count_format);
    }

    public void setVideoInfo(VideoDetailData videoInfo) {
        videoDetailData = videoInfo;
        notifyItemChanged(0);
    }

    public void setChannelDetailBean(ChannelDetailBean channelDetailBean) {
        this.channelDetailBean = channelDetailBean;
        notifyItemChanged(0);
    }

    public void updateList(List<VideoListAdapter.IVideoData> list) {
        mValues.clear();
        if (list != null && list.size() > 0) {
            mValues.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_info_list_item, parent, false);
            view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleHeight(206);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_info_head_item, parent, false);
            return new HeadViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            ViewHolder normalViewHolder = (ViewHolder) holder;
            normalViewHolder.mItem = mValues.get(position-1);
            normalViewHolder.mImageView.setImageResource(R.mipmap.user_default_icon);
            normalViewHolder.updateInfo();
        } else {
            ((HeadViewHolder) holder).updateInfo();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView playCount;
        private TextView desc;

        private TextView share;
        private TextView like;
        private TextView cache;

        private FlowLayout flowLayout;
        private ImageView channelImage;
        private TextView channelTitle;
        private TextView channelUpdate;
        private TextView channelDesc;

        public void updateInfo() {
            if (videoDetailData != null) {
                VideoDetailBean bean = videoDetailData.videoDetailBean;
                title.setText(bean.getTitle());
                playCount.setText(String.format(playCountFormat, bean.getPlay()));
                desc.setText(bean.getIntro());
                share.setText(bean.getShares());
                like.setText(bean.getLikes());
                List<VideoDetailBean.TagsBean> labels = bean.getTags();
                flowLayout.removeAllViews();
                if (labels != null) {
                    int count = labels.size();
                    if (count > 0) {
                        addLabel(labels);
                    }
                }
            }
            if (channelDetailBean != null) {
                channelTitle.setText(channelDetailBean.getName());
                channelUpdate.setText(channelDetailBean.getUpdate_time());
                channelDesc.setText(channelDetailBean.getIntro());
                Glide.with(mContext).load(channelDetailBean.getPic_url()).into(channelImage);
            }
        }

        private void addLabel(List<VideoDetailBean.TagsBean> mData) {
            int paddingLeft
                    = ScaleCalculator.getInstance(mContext).scaleTextSize(10);
            int paddingTop
                    = ScaleCalculator.getInstance(mContext).scaleTextSize(10);
            flowLayout.measure(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for (int index = 0, count = mData.size(); index < count; index ++) {
                final TextView view = new TextView(mContext);
                view.setText(mData.get(index).getName());
                view.setTextColor(mContext.getResources().getColor(R.color.search_label_text));
                view.setGravity(Gravity.CENTER);
                view.setIncludeFontPadding(false);
                view.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
                view.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        ScaleCalculator.getInstance(mContext).scaleTextSize(26));
                view.setBackgroundResource(R.drawable.search_label_bg);
                flowLayout.addView(view);
            }
        }

        public HeadViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.video_name);
            playCount = (TextView) view.findViewById(R.id.video_play_count);
            desc = (TextView) view.findViewById(R.id.video_desc);
            share = (TextView) view.findViewById(R.id.video_share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRef != null && mRef.get() != null) {
                        mRef.get().onShareClick(videoDetailData.videoDetailBean);
                    }
                }
            });
            like = (TextView) view.findViewById(R.id.video_heart);
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRef != null && mRef.get() != null) {
                        mRef.get().onCollectClick(videoDetailData.videoDetailBean);
                    }
                }
            });
            cache = (TextView) view.findViewById(R.id.video_cache);
            cache.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRef != null && mRef.get() != null) {
                        mRef.get().onCacheClick(videoDetailData.videoDetailBean);
                    }
                }
            });
            flowLayout = (FlowLayout) view.findViewById(R.id.video_info_label_layout);
            int space = ScaleCalculator.getInstance(mContext).scaleWidth(20);
            flowLayout.setChildSpacing(space);
            flowLayout.setRowSpacing(space);
            channelImage = (ImageView) view.findViewById(R.id.video_info_list_icon);
            channelTitle = (TextView) view.findViewById(R.id.channel_title);
            channelUpdate = (TextView) view.findViewById(R.id.channel_update_time);
            channelDesc = (TextView) view.findViewById(R.id.channel_desc);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRef != null && mRef.get() != null) {
                        mRef.get().onChannelClick(channelDetailBean);
                    }
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView title;
        public final TextView updateTime;
        public VideoListAdapter.IVideoData mItem;

        public void updateInfo() {
            title.setText(mItem.getTitle());
            updateTime.setText(String.valueOf(mItem.getPublishTime()));
            Glide.with(mContext).load(mItem.getPicUrl()).into(mImageView);
        }

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.video_item_icon);
            title = (TextView) view.findViewById(R.id.video_title);
            updateTime = (TextView) view.findViewById(R.id.video_time);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRef != null && mRef.get() != null) {
                        mRef.get().onVideoClick(mItem.getVideoID());
                    }
                }
            });
        }
    }


    private SoftReference<OnClickCallBack> mRef;
    public void setOnClickListener(OnClickCallBack listener) {
        if (listener != null) {
            mRef = new SoftReference<OnClickCallBack>(listener);
        }
    }
    public interface OnClickCallBack {
        void onChannelClick(ChannelDetailBean bean);
        void onCacheClick(VideoDetailBean bean);
        void onShareClick(VideoDetailBean bean);
        void onCollectClick(VideoDetailBean bean);
        void onVideoClick(String vid);
    }
}
