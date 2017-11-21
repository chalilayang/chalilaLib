package com.baogetv.app.model.videodetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.entity.VideoData;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

public class VideoInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<VideoData> mValues;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEAD = 1;
    private Context mContext;
    private int margin_8px;
    private int margin_15px;
    private int margin_20px;
    private int margin_30px;
    private int margin_160px;
    private String videoCountFormat;

    public VideoInfoListAdapter(Context context) {
        mValues = new ArrayList<>();
        mContext = context;
        margin_8px = ScaleCalculator.getInstance(mContext).scaleWidth(8);
        margin_15px = ScaleCalculator.getInstance(mContext).scaleWidth(15);
        margin_20px = ScaleCalculator.getInstance(mContext).scaleWidth(20);
        margin_30px = ScaleCalculator.getInstance(mContext).scaleWidth(30);
        margin_160px = ScaleCalculator.getInstance(mContext).scaleWidth(160);
        videoCountFormat = mContext.getString(R.string.video_count_format);
    }

    public void updateList(List<VideoData> list) {
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
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
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

        public void updateInfo() {
        }

        public HeadViewHolder(View view) {
            super(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView title;
        public final TextView updateTime;
        public VideoData mItem;

        public void updateInfo() {
            title.setText(mItem.videoTitle);
            updateTime.setText(String.valueOf(mItem.updateTime));
        }

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.video_item_icon);
            title = (TextView) view.findViewById(R.id.video_title);
            updateTime = (TextView) view.findViewById(R.id.video_time);
        }
    }
}
