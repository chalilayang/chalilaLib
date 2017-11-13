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
import com.reshape.app.model.channel.entity.ChannelData;

import java.util.ArrayList;
import java.util.List;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {

    private final List<ChannelData> mValues;
    private Context mContext;
    private int margin_8px;
    private int margin_15px;
    private int margin_20px;
    private String videoCountFormat;

    public CollectAdapter(Context context) {
        mValues = new ArrayList<>();
        mContext = context;
        margin_8px = ScaleCalculator.getInstance(mContext).scaleWidth(8);
        margin_15px = ScaleCalculator.getInstance(mContext).scaleWidth(15);
        margin_20px = ScaleCalculator.getInstance(mContext).scaleWidth(20);
        videoCountFormat = mContext.getString(R.string.video_count_format);
    }

    public void updateChannelList(List<ChannelData> list) {
        mValues.clear();
        if (list != null && list.size() > 0) {
            mValues.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_item, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleHeight(190);
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
        public final TextView videoCount;
        public final TextView desc;
        public ChannelData mItem;

        public void updateInfo() {
            title.setText(mItem.channelTitle);
            updateTime.setText(String.valueOf(mItem.updateTime));
            videoCount.setText(String.format(videoCountFormat, mItem.videoCount));
            desc.setText(mItem.description);
        }

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (ImageView) view.findViewById(R.id.channel_item_icon);
            title = (TextView) view.findViewById(R.id.channel_item_title);
            updateTime = (TextView) view.findViewById(R.id.channel_item_update_time);
            videoCount = (TextView) view.findViewById(R.id.channel_item_video_count);
            videoCount.setCompoundDrawablePadding(margin_8px);
            desc = (TextView) view.findViewById(R.id.channel_item_desc);
//            LinearLayout.LayoutParams rlp
//                    = (LinearLayout.LayoutParams) videoCount.getLayoutParams();
//            rlp.topMargin = margin_15px;
//            rlp = (LinearLayout.LayoutParams) desc.getLayoutParams();
//            rlp.topMargin = margin_20px;
        }
    }
}
