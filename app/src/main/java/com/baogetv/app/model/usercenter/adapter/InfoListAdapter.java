package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.bean.SystemInfoBean;
import com.baogetv.app.util.TimeUtil;
import com.chalilayang.scaleview.ScaleCalculator;

import java.lang.ref.SoftReference;

public class InfoListAdapter extends BaseItemAdapter<SystemInfoBean, InfoListAdapter.ViewHolder>
        implements ItemViewHolder.ItemDeleteListener<SystemInfoBean> {

    private int margin_8px;
    private int margin_15px;
    private int margin_20px;
    private int margin_30px;
    private int margin_160px;
    private String videoCountFormat;
    protected SoftReference<ItemViewHolder.ItemDeleteListener<SystemInfoBean>> mDeleteRef;
    public void setItemDeleteListener(ItemViewHolder.ItemDeleteListener<SystemInfoBean> listener) {
        if (listener != null) {
            mDeleteRef = new SoftReference<>(listener);
        }
    }

    public InfoListAdapter(Context context) {
        super(context);
        margin_8px = ScaleCalculator.getInstance(mContext).scaleWidth(8);
        margin_15px = ScaleCalculator.getInstance(mContext).scaleWidth(15);
        margin_20px = ScaleCalculator.getInstance(mContext).scaleWidth(20);
        margin_30px = ScaleCalculator.getInstance(mContext).scaleWidth(30);
        margin_160px = ScaleCalculator.getInstance(mContext).scaleWidth(160);
        videoCountFormat = mContext.getString(R.string.video_count_format);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.system_info_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.setItemDeleteListener(this);
        return holder;
    }

    @Override
    public void onDelete(SystemInfoBean data, int pos) {
        if (mDeleteRef != null && mDeleteRef.get() != null) {
            mDeleteRef.get().onDelete(data, pos);
        }
    }

    public class ViewHolder extends ItemViewHolder<SystemInfoBean> {
        protected SoftReference<ItemDeleteListener> mDeleteRef;
        public final View contentRoot;
        public final TextView title;
        public final TextView updateTime;
        public final TextView deleteBtn;
        public final TextView infoTv;
        public final View infoTip;
        @Override
        public void bindData(SystemInfoBean data, int pos) {
            title.setText(data.getTitle());
            updateTime.setText(TimeUtil.getTimeStateNew(data.getAdd_time()));
            int isUnRead = 1;
            try {
                isUnRead = Integer.parseInt(data.getIs_unread());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            infoTip.setVisibility(isUnRead == 0 ? View.INVISIBLE:View.VISIBLE);
            infoTv.setText(data.getContent());
        }

        public ViewHolder(View view) {
            super(view);
            contentRoot = view.findViewById(R.id.item_content_view);
            contentRoot.setOnClickListener(this);
            contentRoot.setPadding(margin_30px, 0, margin_30px, 0);
            title = (TextView) view.findViewById(R.id.info_title);
            infoTip = view.findViewById(R.id.info_tip);
            updateTime = (TextView) view.findViewById(R.id.info_time);
            infoTv = (TextView) view.findViewById(R.id.info_content);
            deleteBtn = (TextView) view.findViewById(R.id.btn_delete);
            deleteBtn.getLayoutParams().width = margin_160px;
            deleteBtn.setOnClickListener(this);
        }

        public void setItemDeleteListener(ItemDeleteListener<SystemInfoBean> listener) {
            if (listener != null) {
                mDeleteRef = new SoftReference<ItemDeleteListener>(listener);
            }
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            if (mRef != null && mRef.get() != null && view == contentRoot) {
                mRef.get().onItemClick(mData, position);
            } else if (view == deleteBtn && mDeleteRef != null && mDeleteRef.get() != null) {
                mDeleteRef.get().onDelete(mData, position);
            }
        }
    }
}
