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
import com.baogetv.app.downloader.domain.DownloadInfo;
import com.baogetv.app.model.usercenter.MyDownloadListener;
import com.baogetv.app.model.usercenter.customview.CacheInfoView;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;

import java.lang.ref.SoftReference;
import java.sql.SQLException;

import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_COMPLETED;
import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_REMOVED;
import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_WAIT;

public class MyCacheAdapter
        extends BaseItemAdapter<MyBusinessInfo, MyCacheAdapter.ViewHolder>
        implements ItemViewHolder.ItemDeleteListener<MyBusinessInfo> {
    private int margin_30px;
    private int margin_160px;
    private final DownloadManager downloadManager;
    private DBController dbController;

    public MyCacheAdapter(Context context) {
        super(context);
        margin_30px = ScaleCalculator.getInstance(mContext).scaleWidth(30);
        margin_160px = ScaleCalculator.getInstance(mContext).scaleWidth(160);
        downloadManager = DownloadService.getDownloadManager(mContext.getApplicationContext());
        try {
            dbController = DBController.getInstance(mContext.getApplicationContext());
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
        public final View contentRoot;
        public final ImageView mImageView;
        public final TextView deleteBtn;
        private CacheInfoView cacheInfoView;
        private DownloadInfo downloadInfo;
        protected SoftReference<ItemDeleteListener> mDeleteRef;

        @Override
        public void bindData(MyBusinessInfo data, int pos) {
            Glide.with(mContext).load(data.getIcon()).placeholder(R.mipmap.pic_loding).crossFade().into(mImageView);
            cacheInfoView.setTitleTv(data.getName());
            downloadInfo = downloadManager.getDownloadById(data.getUrl().hashCode());
            if (downloadInfo != null) {
                cacheInfoView.setControlListener(new CacheInfoView.ControlListener() {
                    @Override
                    public void onCacheBtnClick(View view) {
                        switch (downloadInfo.getStatus()) {
                            case DownloadInfo.STATUS_NONE:
                            case DownloadInfo.STATUS_PAUSED:
                            case DownloadInfo.STATUS_ERROR:
                                downloadManager.resume(downloadInfo);
                                break;
                            case DownloadInfo.STATUS_DOWNLOADING:
                            case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                            case DownloadInfo.STATUS_WAIT:
                                downloadManager.pause(downloadInfo);
                                break;
//                            case DownloadInfo.STATUS_COMPLETED:
//                                downloadManager.remove(downloadInfo);
//                                break;
                        }
                    }
                });
                downloadInfo.setDownloadListener(
                        new MyDownloadListener(new SoftReference(ViewHolder.this)) {
                            @Override
                            public void onRefresh() {
                                notifyDownloadStatus();
                                if (getUserTag() != null && getUserTag().get() != null) {
                                    ViewHolder viewHolder = (ViewHolder) getUserTag().get();
                                    viewHolder.refresh();
                                }
                            }
                        });
            }
            refresh();
        }

        public ViewHolder(View view) {
            super(view);
            contentRoot = view.findViewById(R.id.item_content_view);
            contentRoot.setOnClickListener(this);
            contentRoot.setPadding(margin_30px, 0, 0, 0);
            mImageView = view.findViewById(R.id.video_item_icon);
            cacheInfoView = view.findViewById(R.id.cache_info);
            deleteBtn = (TextView) view.findViewById(R.id.btn_delete);
            deleteBtn.getLayoutParams().width = margin_160px;
            deleteBtn.setOnClickListener(this);
        }

        private void notifyDownloadStatus() {
            if (downloadInfo.getStatus() == STATUS_REMOVED) {
                try {
                    dbController.deleteMyDownloadInfo(downloadInfo.getUri().hashCode());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        private void refresh() {
            if (downloadInfo == null) {
                return;
            }
            long curSize = downloadInfo.getProgress();
            long fileSize = downloadInfo.getSize();
            cacheInfoView.update(curSize, fileSize);
            switch (downloadInfo.getStatus()) {
                case DownloadInfo.STATUS_PAUSED:
                    cacheInfoView.setCacheState(CacheInfoView.STATE_PAUSED);
                    break;
                case DownloadInfo.STATUS_ERROR:
                    cacheInfoView.setCacheState(CacheInfoView.STATE_ERROR);
                    break;
                case DownloadInfo.STATUS_DOWNLOADING:
                case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                    cacheInfoView.setCacheState(CacheInfoView.STATE_DOWNLOADING);
                    break;
                case STATUS_COMPLETED:
                    cacheInfoView.setCacheState(CacheInfoView.STATE_DOWNLOADED);
                    break;
                case STATUS_WAIT:
                    cacheInfoView.setCacheState(CacheInfoView.STATE_WAITING);
                    break;
                default:
                    break;
            }
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

    protected SoftReference<ItemViewHolder.ItemDeleteListener<MyBusinessInfo>> mDeleteRef;

    public void setItemDeleteListener(ItemViewHolder.ItemDeleteListener<MyBusinessInfo> listener) {
        if (listener != null) {
            mDeleteRef = new SoftReference<>(listener);
        }
    }
}
