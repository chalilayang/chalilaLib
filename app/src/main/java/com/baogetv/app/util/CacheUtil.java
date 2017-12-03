package com.baogetv.app.util;

import android.content.Context;
import android.text.TextUtils;

import com.baogetv.app.bean.VideoDetailBean;
import com.baogetv.app.db.DBController;
import com.baogetv.app.db.domain.MyBusinessInfLocal;
import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.downloader.callback.DownloadManager;
import com.baogetv.app.downloader.domain.DownloadInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by chalilayang on 2017/12/3.
 */

public class CacheUtil {
    private static final String TAG = "CacheUtil";
    public static DownloadInfo getDownloadInfo(Context context, String url) {
        DownloadInfo result = null;
        try {
            DBController dbController = DBController.getInstance(context);
            List<DownloadInfo> downloadingList
                    = dbController.findAllDownloading();
            List<DownloadInfo> downloadedList
                    = dbController.findAllDownloaded();
            for (int index = 0, count = downloadedList.size(); index < count; index ++) {
                DownloadInfo info = downloadedList.get(index);
                if (info.getUri().equals(url)) {
                    result = info;
                    break;
                }
            }
            if (result == null) {
                for (int index = 0, count = downloadingList.size(); index < count; index ++) {
                    DownloadInfo info = downloadingList.get(index);
                    if (info.getUri().equals(url)) {
                        result = info;
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean cacheVideo(Context context, VideoDetailBean bean) {
        boolean result = false;
        if (TextUtils.isEmpty(bean.getFile_url())) {
            return result;
        }
        DBController dbController;
        try {
            dbController = DBController.getInstance(context);
            String path = StorageManager.getDownloadFile(
                    StorageManager.generateFileName() + ".mp4").getAbsolutePath();
            DownloadInfo.Builder builder = new DownloadInfo.Builder();
            builder.setUrl(bean.getFile_url()).setPath(path);
            DownloadInfo downloadInfo = builder.build();
            DownloadManager downloadManager
                    = DownloadService.getDownloadManager(context.getApplicationContext());
            downloadManager.download(downloadInfo);
            MyBusinessInfLocal myBusinessInfLocal = new MyBusinessInfLocal(
                    bean.getFile_url().hashCode(),
                    bean.getTitle(), bean.getPic_url(), bean.getFile_url());
            dbController.createOrUpdateMyDownloadInfo(myBusinessInfLocal);
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
