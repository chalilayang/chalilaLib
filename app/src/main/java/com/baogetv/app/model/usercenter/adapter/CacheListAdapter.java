package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baogetv.app.R;
import com.baogetv.app.db.DBController;
import com.baogetv.app.db.domain.MyBusinessInfLocal;
import com.baogetv.app.db.domain.MyBusinessInfo;
import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.downloader.callback.DownloadManager;
import com.baogetv.app.downloader.domain.DownloadInfo;
import com.baogetv.app.model.usercenter.MyDownloadListener;
import com.baogetv.app.util.FileUtil;
import com.baogetv.app.util.FileUtils;
import com.baogetv.app.util.StorageManager;
import com.bumptech.glide.Glide;

import java.io.File;
import java.lang.ref.SoftReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_COMPLETED;
import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_REMOVED;
import static com.baogetv.app.downloader.domain.DownloadInfo.STATUS_WAIT;

public class CacheListAdapter extends RecyclerView.Adapter<CacheListAdapter.ViewHolder> {

    private final Context mContext;
    private final DownloadManager downloadManager;
    private DBController dbController;
    private List<MyBusinessInfo> data;

    public CacheListAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
        downloadManager = DownloadService.getDownloadManager(mContext.getApplicationContext());
        try {
            dbController = DBController.getInstance(mContext.getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCacheList(List<MyBusinessInfo> list) {
        data.clear();
        if (list != null && list.size() > 0) {
            data.addAll(list);
        }
        notifyDataSetChanged();
    }

    public MyBusinessInfo getData(int pos) {
        return data.get(pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.item_download_info, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bindData(getData(position), position, mContext);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_icon;
        private final TextView tv_size;
        private final TextView tv_status;
        private final ProgressBar pb;
        private final TextView tv_name;
        private final Button bt_action;
        private DownloadInfo downloadInfo;

        public ViewHolder(View view) {
            super(view);
            itemView.setClickable(true);
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_size = (TextView) view.findViewById(R.id.tv_size);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            pb = (ProgressBar) view.findViewById(R.id.pb);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            bt_action = (Button) view.findViewById(R.id.bt_action);
        }

        @SuppressWarnings("unchecked")
        public void bindData(final MyBusinessInfo data, int position, final Context context) {
            Glide.with(context).load(data.getIcon()).placeholder(R.mipmap.pic_loding).into(iv_icon);
            tv_name.setText(data.getName());
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
            bt_action.setOnClickListener(new View.OnClickListener() {
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
                        String path = StorageManager.getDownloadFile(
                                StorageManager.generateFileName()).getAbsolutePath();
                        downloadInfo = new DownloadInfo.Builder().setUrl(data.getUrl())
                                .setPath(path)
                                .build();
                        downloadInfo
                                .setDownloadListener(new MyDownloadListener(new SoftReference
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

                        //save extra info to my database.
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

        private void notifyDownloadStatus() {
            if (downloadInfo.getStatus() == STATUS_REMOVED) {
                try {
                    dbController.deleteMyDownloadInfo(downloadInfo.getUri().hashCode());
                    String filePath = downloadInfo.getPath();
                    if (FileUtils.isValid(filePath)) {
                        File file = new File(filePath);
                        file.delete();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        private void refresh() {
            if (downloadInfo == null) {
                tv_size.setText("");
                pb.setProgress(0);
                bt_action.setText("Download");
                tv_status.setText("not downloadInfo");
            } else {
                switch (downloadInfo.getStatus()) {
                    case DownloadInfo.STATUS_NONE:
                        bt_action.setText("Download");
                        tv_status.setText("not downloadInfo");
                        break;
                    case DownloadInfo.STATUS_PAUSED:
                    case DownloadInfo.STATUS_ERROR:
                        bt_action.setText("Continue");
                        tv_status.setText("paused");
                        try {
                            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 /
                                    downloadInfo.getSize()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/"
                                + FileUtil
                                .formatFileSize(downloadInfo.getSize()));
                        break;

                    case DownloadInfo.STATUS_DOWNLOADING:
                    case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                        bt_action.setText("Pause");
                        try {
                            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 /
                                    downloadInfo.getSize()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/"
                                + FileUtil
                                .formatFileSize(downloadInfo.getSize()));
                        tv_status.setText("downloading");
                        break;
                    case STATUS_COMPLETED:
                        bt_action.setText("Delete");
                        try {
                            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 /
                                    downloadInfo.getSize()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/"
                                + FileUtil
                                .formatFileSize(downloadInfo.getSize()));
                        tv_status.setText("success");
                        break;
                    case STATUS_REMOVED:
                        tv_size.setText("");
                        pb.setProgress(0);
                        bt_action.setText("Download");
                        tv_status.setText("not downloadInfo");
                    case STATUS_WAIT:
                        tv_size.setText("");
                        pb.setProgress(0);
                        bt_action.setText("Pause");
                        tv_status.setText("Waiting");
                        break;
                }

            }
        }
    }
}
