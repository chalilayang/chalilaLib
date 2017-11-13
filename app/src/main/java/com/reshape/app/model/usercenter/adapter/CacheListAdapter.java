package com.reshape.app.model.usercenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleCalculator;
import com.reshape.app.R;
import com.reshape.app.model.usercenter.entity.VideoData;

import java.util.ArrayList;
import java.util.List;

public class CacheListAdapter extends RecyclerView.Adapter<CacheListAdapter.ViewHolder> {

    private final List<VideoData> mValues;
    private Context mContext;
    private int margin_8px;
    private int margin_15px;
    private int margin_20px;
    private String videoCountFormat;

    public CacheListAdapter(Context context) {
        mValues = new ArrayList<>();
        mContext = context;
        margin_8px = ScaleCalculator.getInstance(mContext).scaleWidth(8);
        margin_15px = ScaleCalculator.getInstance(mContext).scaleWidth(15);
        margin_20px = ScaleCalculator.getInstance(mContext).scaleWidth(20);
        videoCountFormat = mContext.getString(R.string.video_count_format);
    }

    public void updateChannelList(List<VideoData> list) {
        mValues.clear();
        if (list != null && list.size() > 0) {
            mValues.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collect_list_item, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleHeight(200);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setImageResource(R.mipmap.user_default_icon);
        holder.updateInfo();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mContentView;
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
            mContentView = (ImageView) view.findViewById(R.id.video_item_icon);
            title = (TextView) view.findViewById(R.id.video_title);
            updateTime = (TextView) view.findViewById(R.id.video_time);
        }
    }
}
