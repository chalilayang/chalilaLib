package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.db.DBController;
import com.baogetv.app.db.domain.MyBusinessInfo;
import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.downloader.callback.DownloadManager;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;

import java.lang.ref.SoftReference;
import java.sql.SQLException;

public class MyCacheAdapter
        extends BaseItemAdapter<MyBusinessInfo, MyCacheAdapter.ViewHolder>
        implements ItemViewHolder.ItemDeleteListener<MyBusinessInfo>{

    private int margin_8px;
    private int margin_15px;
    private int margin_20px;
    private int margin_30px;
    private int margin_160px;
    private DownloadManager downloadManager;
    private DBController dbController;

    protected SoftReference<ItemViewHolder.ItemDeleteListener<MyBusinessInfo>> mDeleteRef;
    public void setItemDeleteListener(ItemViewHolder.ItemDeleteListener<MyBusinessInfo> listener) {
        if (listener != null) {
            mDeleteRef = new SoftReference<>(listener);
        }
    }

    public MyCacheAdapter(Context context) {
        super(context);
        margin_8px = ScaleCalculator.getInstance(mContext).scaleWidth(8);
        margin_15px = ScaleCalculator.getInstance(mContext).scaleWidth(15);
        margin_20px = ScaleCalculator.getInstance(mContext).scaleWidth(20);
        margin_30px = ScaleCalculator.getInstance(mContext).scaleWidth(30);
        margin_160px = ScaleCalculator.getInstance(mContext).scaleWidth(160);
        downloadManager = DownloadService.getDownloadManager(mContext);
        try {
            dbController = DBController.getInstance(mContext);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cache_list_item, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleHeight(200);
        ViewHolder holder = new ViewHolder(view);
        holder.setItemDeleteListener(this);
        return holder;
    }

    @Override
    public void onDelete(MyBusinessInfo data, int pos) {
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

    public class ViewHolder extends ItemViewHolder<MyBusinessInfo> {
        public final View mView;
        public final View contentRoot;
        public final ImageView mImageView;
        public final TextView title;
        public final TextView progressTv;
        public final TextView stateTv;
        public final TextView startBtn;
        public final TextView deleteBtn;
        protected SoftReference<ItemDeleteListener> mDeleteRef;
        @Override
        public void bindData(MyBusinessInfo data, int pos) {
            title.setText(data.getName());
            Glide.with(mContext).load(data.getIcon()).crossFade().into(mImageView);
        }

        public ViewHolder(View view) {
            super(view);
            mView = view;
            contentRoot = view.findViewById(R.id.item_content_view);
            contentRoot.setOnClickListener(this);
            contentRoot.setPadding(margin_30px, 0, 0, 0);
            mImageView = (ImageView) view.findViewById(R.id.video_item_icon);
            title = (TextView) view.findViewById(R.id.video_title);
            progressTv = (TextView) view.findViewById(R.id.download_progress_tv);
            stateTv = (TextView) view.findViewById(R.id.download_progress_state);
            startBtn = (TextView) view.findViewById(R.id.download_progress_btn);
            deleteBtn = (TextView) view.findViewById(R.id.btn_delete);
            deleteBtn.getLayoutParams().width = margin_160px;
            deleteBtn.setOnClickListener(this);
        }

        public void setItemDeleteListener(ItemDeleteListener<MyBusinessInfo> listener) {
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