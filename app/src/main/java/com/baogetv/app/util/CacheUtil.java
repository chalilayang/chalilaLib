package com.baogetv.app.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

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
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        DownloadManager downloadManager = DownloadService.getDownloadManager(context);
        DownloadInfo downloadInfo = downloadManager.getDownloadById(url.hashCode());
        return downloadInfo;
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

    public static void pauseAllCaching(Context context) {
        DownloadManager downloadManager
                = DownloadService.getDownloadManager(context.getApplicationContext());
        List<DownloadInfo> list = downloadManager.findAllDownloading();
        if (list != null) {
            for (int index = 0, count = list.size(); index < count; index ++) {
                downloadManager.pause(list.get(index));
            }
        }
    }

    public static void resumeAllCaching(Context context) {
        DownloadManager downloadManager
                = DownloadService.getDownloadManager(context.getApplicationContext());
        List<DownloadInfo> list = downloadManager.findAllDownloading();
        if (list != null) {
            for (int index = 0, count = list.size(); index < count; index ++) {
                downloadManager.resume(list.get(index));
            }
        }
    }
}
