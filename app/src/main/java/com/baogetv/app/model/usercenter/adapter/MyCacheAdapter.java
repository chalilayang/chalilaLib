package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.db.DBController;
import com.baogetv.app.db.domain.MyBusinessInfLocal;
import com.baogetv.app.db.domain.MyBusinessInfo;
import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.downloader.callback.DownloadManager;
import com.baogetv.app.downloader.domain.DownloadInfo;
import com.baogetv.app.model.usercenter.MyDownloadListener;
import com.baogetv.app.util.FileUtil;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;

import java.io.File;
import java.lang.ref.SoftReference;
import java.sql.SQLException;

import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_COMPLETED;
import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_REMOVED;
import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_WAIT;

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
    private String pauseStr;
    private String continuStr;
    private String completeStr;
    private String downloadingStr;
    private String startDownloadgStr;

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
        pauseStr = mContext.getString(R.string.pause_download);
        completeStr = mContext.getString(R.string.downloaded);
        downloadingStr = mContext.getString(R.string.downloading);
        startDownloadgStr = mContext.getString(R.string.start_download);
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
        public final ProgressBar progressBar;
        private DownloadInfo downloadInfo;
        protected SoftReference<ItemDeleteListener> mDeleteRef;
        @Override
        public void bindData(final MyBusinessInfo data, int pos) {
            title.setText(data.getName());
            Glide.with(mContext).load(data.getIcon()).crossFade().into(mImageView);
            downloadInfo = downloadManager.getDownloadById(data.getUrl().hashCode());
            if (downloadInfo != null) {
                downloadInfo
                        .setDownloadListener(new MyDownloadListener(new SoftReference(ViewHolder
                                .this)) {
                            //  Call interval about one second
                            @Override
                            public void onRefresh() {
                                if (getUserTag() != null && getUserTag().get() != null) {
                                    ViewHolder viewHolder = (ViewHolder) getUserTag().get();
                                    viewHolder.refresh();
                                }
                            }
                        });

            }
            refresh();
            startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (downloadInfo != null) {

                        switch (downloadInfo.getStatus()) {
                            case DownloadInfo.STATUS_NONE:
                            case DownloadInfo.STATUS_PAUSED:
                            case DownloadInfo.STATUS_ERROR:
                                downloadManager.resume(downloadInfo);
                                break;
                            case DownloadInfo.STATUS_DOWNLOADING:
                            case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                            case STATUS_WAIT:
                                downloadManager.pause(downloadInfo);
                                break;
                            case DownloadInfo.STATUS_COMPLETED:
                                downloadManager.remove(downloadInfo);
                                break;
                        }
                    } else {
                        File d = new File(Environment.getExternalStorageDirectory()
                                .getAbsolutePath(), "d");
                        if (!d.exists()) {
                            d.mkdirs();
                        }
                        String path = d.getAbsolutePath().concat("/").concat(data.getName());
                        downloadInfo = new DownloadInfo.Builder().setUrl(data.getUrl())
                                .setPath(path)
                                .build();
                        downloadInfo.setDownloadListener(new MyDownloadListener(new SoftReference
                                        (ViewHolder.this)) {
                                    @Override
                                    public void onRefresh() {
                                        notifyDownloadStatus();
                                        if (getUserTag() != null && getUserTag().get() != null) {
                                            ViewHolder viewHolder = (ViewHolder) getUserTag().get();
                                            viewHolder.refresh();
                                        }
                                    }
                                });
                        downloadManager.download(downloadInfo);
                        MyBusinessInfLocal myBusinessInfLocal = new MyBusinessInfLocal(
                                data.getUrl().hashCode(), data.getName(), data.getIcon(), data
                                .getUrl());
                        try {
                            dbController.createOrUpdateMyDownloadInfo(myBusinessInfLocal);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
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
            progressBar = (ProgressBar) view.findViewById(R.id.download_progress);
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
                title.setText("");
                progressBar.setProgress(0);
                startBtn.setText("Download");
                stateTv.setText("not downloadInfo");
            } else {
                switch (downloadInfo.getStatus()) {
                    case DownloadInfo.STATUS_NONE:
                        startBtn.setText("Download");
                        stateTv.setText("not downloadInfo");
                        break;
                    case DownloadInfo.STATUS_PAUSED:
                    case DownloadInfo.STATUS_ERROR:
                        startBtn.setText(continuStr);
                        stateTv.setText(pauseStr);
                        try {
                            progressBar.setProgress((int) (downloadInfo.getProgress() * 100.0 /
                                    downloadInfo.getSize()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressTv.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/"
                                + FileUtil
                                .formatFileSize(downloadInfo.getSize()));
                        break;

                    case DownloadInfo.STATUS_DOWNLOADING:
                    case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                        startBtn.setText(pauseStr);
                        try {
                            progressBar.setProgress((int) (downloadInfo.getProgress() * 100.0 /
                                    downloadInfo.getSize()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressTv.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/"
                                + FileUtil
                                .formatFileSize(downloadInfo.getSize()));
                        stateTv.setText(downloadingStr);
                        break;
                    case STATUS_COMPLETED:
                        startBtn.setVisibility(View.INVISIBLE);
                        try {
                            progressBar.setProgress((int) (downloadInfo.getProgress() * 100.0 /
                                    downloadInfo.getSize()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressTv.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/"
                                + FileUtil
                                .formatFileSize(downloadInfo.getSize()));
                        stateTv.setText(completeStr);
                        break;
                    case STATUS_REMOVED:
                        progressTv.setText("");
                        progressBar.setProgress(0);
                        startBtn.setText("Download");
                        stateTv.setText("not downloadInfo");
                    case STATUS_WAIT:
                        progressTv.setText("");
                        progressBar.setProgress(0);
                        startBtn.setText(pauseStr);
                        stateTv.setText("Waiting");
                        break;
                }

            }
        }
    }
}
