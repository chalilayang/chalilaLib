package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;
import com.baogetv.app.R;

import java.lang.ref.SoftReference;

public class CollectListAdapter
        extends BaseItemAdapter<CollectBean, CollectListAdapter.ViewHolder>
        implements ItemViewHolder.ItemDeleteListener<CollectBean> {

    private int margin_8px;
    private int margin_15px;
    private int margin_20px;
    private int margin_30px;
    private int margin_160px;
    private String videoCountFormat;
    protected SoftReference<ItemViewHolder.ItemDeleteListener<CollectBean>> mDeleteRef;
    public void setItemDeleteListener(ItemViewHolder.ItemDeleteListener<CollectBean> listener) {
        if (listener != null) {
            mDeleteRef = new SoftReference<>(listener);
        }
    }

    public CollectListAdapter(Context context) {
        super(context);
        margin_8px = ScaleCalculator.getInstance(mContext).scaleWidth(8);
        margin_15px = ScaleCalculator.getInstance(mContext).scaleWidth(15);
        margin_20px = ScaleCalculator.getInstance(mContext).scaleWidth(20);
        margin_30px = ScaleCalculator.getInstance(mContext).scaleWidth(30);
        margin_160px = ScaleCalculator.getInstance(mContext).scaleWidth(160);
        videoCountFormat = mContext.getString(R.string.video_count_format);
        setNeedLoadMore(true);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collect_list_item, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleHeight(200);
        ViewHolder holder = new ViewHolder(view);
        holder.setItemDeleteListener(this);
        return holder;
    }

    @Override
    public ViewHolder createMoreViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleWidth(60);
        return new ViewHolder(view);
    }

    @Override
    public void onDelete(CollectBean data, int pos) {
        if (mDeleteRef != null && mDeleteRef.get() != null) {
            mDeleteRef.get().onDelete(data, pos);
        }
    }

    public void deleteItem(int pos) {
        if (pos >= 0 && pos < getItemCount()) {
            mValues.remove(pos);
            notifyItemChanged(pos);
        }
    }

    public void deleteAllItem() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends ItemViewHolder<CollectBean> {
//        public final View mView;
        public final View contentRoot;
        public final ImageView mImageView;
        public final TextView title;
        public final TextView updateTime;
        public final TextView deleteBtn;
        public final TextView loadMoreTip;
        protected SoftReference<ItemDeleteListener> mDeleteRef;
        @Override
        public void bindData(CollectBean data, int pos) {
            if (loadMoreTip != null) {
                loadMoreTip.setText(hasMoreData?loadingMore : noMoreData);
            } else {
                title.setText(data.getTitle());
                updateTime.setText(TimeUtil.getTimeStateNew(data.getAdd_time()));
                Glide.with(mContext).load(data.getPic_url()).placeholder(R.mipmap.pic_loding).crossFade().into(mImageView);
            }
        }

        public ViewHolder(View view) {
            super(view);
//            mView = view;
            loadMoreTip = view.findViewById(R.id.load_more_tip);
            contentRoot = view.findViewById(R.id.item_content_view);
            if (contentRoot != null) {
                contentRoot.setOnClickListener(this);
                contentRoot.setPadding(margin_30px, 0, 0, 0);
            }
            mImageView = (ImageView) view.findViewById(R.id.video_item_icon);
            title = (TextView) view.findViewById(R.id.video_title);
            updateTime = (TextView) view.findViewById(R.id.video_time);
            deleteBtn = (TextView) view.findViewById(R.id.btn_delete);
            if (deleteBtn != null) {
                deleteBtn.getLayoutParams().width = margin_160px;
                deleteBtn.setOnClickListener(this);
            }
        }

        public void setItemDeleteListener(ItemDeleteListener<CollectBean> listener) {
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
