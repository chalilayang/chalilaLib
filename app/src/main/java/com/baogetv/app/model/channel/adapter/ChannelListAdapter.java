package com.baogetv.app.model.channel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;
import com.baogetv.app.R;
import com.baogetv.app.model.channel.entity.ChannelData;

import java.util.ArrayList;
import java.util.List;

public class ChannelListAdapter extends BaseItemAdapter<ChannelData, ChannelListAdapter.ViewHolder> {

    private Context mContext;
    private int margin_8px;
    private int margin_15px;
    private int margin_20px;
    private String videoCountFormat;

    public ChannelListAdapter(Context context) {
        super(context);
        mContext = context;
        margin_8px = ScaleCalculator.getInstance(mContext).scaleWidth(8);
        margin_15px = ScaleCalculator.getInstance(mContext).scaleWidth(15);
        margin_20px = ScaleCalculator.getInstance(mContext).scaleWidth(20);
        videoCountFormat = mContext.getString(R.string.video_count_format);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_item, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleHeight(190);
        return new ViewHolder(view);
    }

    public class ViewHolder extends ItemViewHolder<ChannelData> {
        public final View mView;
        public final ImageView mContentView;
        public final TextView title;
        public final TextView updateTime;
        public final TextView videoCount;
        public final TextView desc;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (ImageView) view.findViewById(R.id.channel_item_icon);
            title = (TextView) view.findViewById(R.id.channel_item_title);
            updateTime = (TextView) view.findViewById(R.id.channel_item_update_time);
            videoCount = (TextView) view.findViewById(R.id.channel_item_video_count);
            videoCount.setCompoundDrawablePadding(margin_8px);
            desc = (TextView) view.findViewById(R.id.channel_item_desc);
        }

        @Override
        public void bindData(ChannelData data, int pos) {
            title.setText(data.channelTitle);
            updateTime.setText(String.valueOf(data.updateTime));
            videoCount.setText(String.format(videoCountFormat, data.videoCount));
            desc.setText(data.description);
            Glide.with(mContext).load(data.iConUrl).placeholder(R.mipmap.pic_loding).into(mContentView);
        }
    }
}
