package com.baogetv.app.model.videodetail.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.baogetv.app.db.DBController;
import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.downloader.callback.DownloadManager;
import com.baogetv.app.downloader.domain.DownloadInfo;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.MyDownloadListener;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.chalilayang.customview.ExpandTextView;
import com.chalilayang.scaleview.ScaleCalculator;
import com.nex3z.flowlayout.FlowLayout;

import java.lang.ref.SoftReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_COMPLETED;
import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_WAIT;

public class VideoInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "VideoInfoListAdapter";
    private final List<VideoListAdapter.IVideoData> mValues;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_FOOT = 2;
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
    private final DownloadManager downloadManager;
    private DBController dbController;

    protected String loadingMore;
    protected String noMoreData;
    protected boolean hasMoreData;
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
        loadingMore = mContext.getString(R.string.loading_more_data);
        noMoreData = mContext.getString(R.string.no_more_data);
        hasMoreData = true;
        downloadManager = DownloadService.getDownloadManager(mContext.getApplicationContext());
        try {
            dbController = DBController.getInstance(mContext.getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        notifyItemChanged(getItemCount()-1);
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
        } else if (viewType == TYPE_HEAD){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_info_head_item, parent, false);
            return new HeadViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer, parent, false);
            view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleWidth(60);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            ViewHolder normalViewHolder = (ViewHolder) holder;
            normalViewHolder.mItem = mValues.get(position-1);
            normalViewHolder.mImageView.setImageResource(R.mipmap.user_default_icon);
            normalViewHolder.updateInfo();
        } else if (getItemViewType(position) == TYPE_HEAD) {
            ((HeadViewHolder) holder).updateInfo();
        } else {
            ((FootViewHolder) holder).updateInfo();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == getItemCount()-1) {
            return TYPE_FOOT;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 2;
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView playCount;
        private TextView desc;

        private TextView share;
        private TextView collect;
        private TextView cache;

        private FlowLayout flowLayout;
        private ImageView channelImage;
        private TextView channelTitle;
        private TextView channelUpdate;
        private TextView channelDesc;
        private Drawable heartGray;
        private Drawable heartRed;
        private Drawable cacheGray;
        private Drawable cacheRed;
        private Drawable shareGray;
        private Drawable shareRed;
        private DownloadInfo downloadInfo;
        private ImageView arrow;

        public void updateInfo() {
            if (videoDetailData != null) {
                VideoDetailBean bean = videoDetailData.videoDetailBean;
                title.setText(bean.getTitle());
                playCount.setText(String.format(playCountFormat, bean.getPlay()));
                desc.setText(bean.getIntro());
                share.setText(bean.getShares());
                share.setCompoundDrawables(shareGray, null, null, null);
                cache.setText(bean.getCaches());
                if (!LoginManager.hasCommentRight(mContext)) {
                    cache.setCompoundDrawables(cacheGray, null, null, null);
                } else {
                    cache.setCompoundDrawables(cacheRed, null, null, null);
                }
                collect.setText(bean.getCollects());
                int isCollect = 0;
                try {
                    isCollect = Integer.parseInt(videoDetailData.videoDetailBean.getIs_collect());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (isCollect == 0) {
                    collect.setCompoundDrawables(heartGray, null, null, null);
                } else {
                    collect.setCompoundDrawables(heartRed, null, null, null);
                }
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
                Glide.with(mContext).load(channelDetailBean.getPic_url())
                        .dontAnimate()
                        .placeholder(R.mipmap.user_default_icon)
                        .error(R.mipmap.user_default_icon).into(channelImage);
            }

            if (downloadInfo == null) {
                downloadInfo = downloadManager.getDownloadById(
                        videoDetailData.videoDetailBean.getFile_url().hashCode());
                if (downloadInfo != null) {
                    downloadInfo.setDownloadListener(
                            new MyDownloadListener(new SoftReference(HeadViewHolder.this)) {
                                @Override
                                public void onRefresh() {
                                    if (getUserTag() != null && getUserTag().get() != null) {
                                        HeadViewHolder viewHolder = (HeadViewHolder) getUserTag().get();
                                        viewHolder.refreshCache();
                                    }
                                }
                            });
                }
            }
            refreshCache();
        }

        private void refreshCache() {
            if (downloadInfo != null) {
                Log.i(TAG, "refreshCache: " + downloadInfo.getStatus() + " " + downloadInfo.getProgress() + " " + downloadInfo.getSize());
                switch (downloadInfo.getStatus()) {
                    case DownloadInfo.STATUS_PAUSED:
                        cache.setText(mContext.getString(R.string.downloading));
                        break;
                    case DownloadInfo.STATUS_ERROR:
                        cache.setText(mContext.getString(R.string.download_error));
                        break;
                    case DownloadInfo.STATUS_DOWNLOADING:
                    case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                        cache.setText(mContext.getString(R.string.downloading));
                        break;
                    case STATUS_COMPLETED:
                        cache.setText(mContext.getString(R.string.downloaded));
                        break;
                    case STATUS_WAIT:
                        cache.setText(mContext.getString(R.string.downloading));
                        break;
                    default:
                        cache.setText(mContext.getString(R.string.cache));
                        break;
                }
            } else {
                cache.setText(mContext.getString(R.string.cache));
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
                final String label = mData.get(index).getName();
                view.setText(label);
                view.setTextColor(mContext.getResources().getColor(R.color.search_label_text));
                view.setGravity(Gravity.CENTER);
                view.setIncludeFontPadding(false);
                view.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
                view.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        ScaleCalculator.getInstance(mContext).scaleTextSize(26));
                view.setBackgroundResource(R.drawable.search_label_bg);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mRef != null && mRef.get() != null) {
                            mRef.get().onLabelClick(label);
                        }
                    }
                });
                flowLayout.addView(view);
            }
        }

        public HeadViewHolder(View view) {
            super(view);
            float scale = 1.0f;
            heartGray = mContext.getResources().getDrawable(R.mipmap.collect_big);
            int width = (int) (heartGray.getIntrinsicWidth() * scale);
            int height = (int) (heartGray.getIntrinsicHeight() * scale);
            heartGray.setBounds(0, 0, width, height);
            heartRed = mContext.getResources().getDrawable(R.mipmap.collect_big_red);
            heartRed.setBounds(0, 0, width, height);

            shareGray = mContext.getResources().getDrawable(R.mipmap.share_big);
            width = (int) (shareGray.getIntrinsicWidth() * scale);
            height = (int) (shareGray.getIntrinsicHeight() * scale);
            shareGray.setBounds(0, 0, width, height);
            shareRed = mContext.getResources().getDrawable(R.mipmap.share_big);
            shareRed.setBounds(0, 0, width, height);

            cacheGray = mContext.getResources().getDrawable(R.mipmap.cache_big);
            width = (int) (cacheGray.getIntrinsicWidth() * scale);
            height = (int) (cacheGray.getIntrinsicHeight() * scale);
            cacheGray.setBounds(0, 0, width, height);
            cacheRed = mContext.getResources().getDrawable(R.mipmap.cache_big);
            cacheRed.setBounds(0, 0, width, height);

            int size_26 = ScaleCalculator.getInstance(mContext).scaleTextSize(26);
            title = (TextView) view.findViewById(R.id.video_name);
            playCount = (TextView) view.findViewById(R.id.video_play_count);
            desc = (TextView) view.findViewById(R.id.video_desc);
            arrow = view.findViewById(R.id.arrow);
            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (playCount.getVisibility() != View.VISIBLE) {
                        playCount.setVisibility(View.VISIBLE);
                        desc.setVisibility(View.VISIBLE);
                        arrow.setRotation(180);
                    } else {
                        playCount.setVisibility(View.GONE);
                        desc.setVisibility(View.GONE);
                        arrow.setRotation(0);
                    }
                }
            });
            share = (TextView) view.findViewById(R.id.video_share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRef != null && mRef.get() != null) {
                        mRef.get().onShareClick(videoDetailData.videoDetailBean);
                    }
                }
            });
            collect = (TextView) view.findViewById(R.id.video_heart);
            collect.setOnClickListener(new View.OnClickListener() {
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
            view.findViewById(R.id.channel_info_container).setOnClickListener(new View.OnClickListener() {
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
            String time = TimeUtil.getTimeStateNew(mItem.getPublishTime());
            String count = String.format(playCountFormat, mItem.getPlayCount());
            updateTime.setText(count + " | " + time);
            Glide.with(mContext)
                    .load(mItem.getPicUrl())
                    .placeholder(R.mipmap.pic_loding)
                    .into(mImageView);
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

    public class FootViewHolder extends RecyclerView.ViewHolder {
        public final TextView loadMoreTip;

        public void updateInfo() {
            loadMoreTip.setText(hasMoreData?loadingMore : noMoreData);
        }

        public FootViewHolder(View view) {
            super(view);
            loadMoreTip = view.findViewById(R.id.load_more_tip);
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
        void onLabelClick(String label);
    }
}
