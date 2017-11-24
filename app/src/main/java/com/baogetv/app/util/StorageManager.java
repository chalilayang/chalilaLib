package com.baogetv.app.util;

import android.annotation.TargetApi;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chalilayang on 2017/7/31.
 */

public class StorageManager {

    private static final String TAG = StorageManager.class.getSimpleName();
    private static long MAX = 100 * 1024 * 1024;
    private static long MAX_ELAPSE =  24 * 3600 * 1000L; //ms

    /**
     * default value: 1 day
     * @param maxDuration millisecond
     */
    public static void setMaxElapse(long maxDuration) {
        MAX_ELAPSE = maxDuration;
    }

    /**
     * default value: 100MB
     * @param max byte
     */
    public static void setMaxSize(long max) {
        MAX = max;
    }

    public long getMaxElapse() {
        return MAX_ELAPSE;
    }

    public long getMaxSize() {
        return MAX;
    }

    public static File getDownloadFolder() {
        File result = null;
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
        File file1 = new File(dir + File.separator + "豹哥健身");
        result = file1;
        if (!result.exists()) {
            result.mkdirs();
        }
        return result;
    }

    @TargetApi(18)
    public static boolean checkSpaceAvalible() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSizeLong();
            long blockCount = sf.getBlockCountLong();
            long availCount = sf.getAvailableBlocksLong();
            long availSize = availCount * blockSize;
            if (availSize < 50 * 1024 * 1024) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取文件保存路径
     *
     * @return
     */
    public static synchronized File getDownloadFile(String filename) {
        File file = null;
        if (!TextUtils.isEmpty(filename)) {
            File fileFolder = getDownloadFolder();
            if (fileFolder != null) {
                file = new File(fileFolder, filename);
            }
        } else {
            Log.d(TAG, "StorageEngine.getDownloadFile()  forderName or fileName is empty!");
        }
        return file;
    }

    public static String getTempDir() {
        File dir = new File(getDownloadFolder(), "temp");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }
    public static File getTempFileDir() {
        File dir = new File(getDownloadFolder(), "temp");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String getTempRecordDir() {
        File dir = new File(getDownloadFolder(), "record");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }
    public static File getTempRecordFileDir() {
        File dir = new File(getDownloadFolder(), "record");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    public static String getLogDir() {
        File dir = new File(getDownloadFolder(), "logs");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    /**
     * get directory path for saving video file
     * @return
     */
    public static String getSavePath() {
        File dir = getDownloadFolder();
        if (dir == null) {
            return null;
        } else {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getAbsolutePath();
        }
    }

    /**
     *
     * @param vid return false if vid <= 0
     * @param videoPath only support file, return false if videoPath represent a directory
     * @return
     */
    public static boolean putVideo(String vid, String videoPath) {
        Log.i(TAG, "putVideo: vid " + vid + " path " + videoPath);
        boolean result = false;
        if (TextUtils.isEmpty(vid)) {
            return false;
        }
        String dirPath = getSavePath();
        if (TextUtils.isEmpty(dirPath)) {
            return false;
        }
        if (FileUtils.isValid(videoPath)) {
            File videoFile = new File(videoPath);
            if (videoFile.isDirectory()) {
                return false;
            }
            String vidFile = dirPath + File.separator + String.valueOf(vid) + ".mp4";
            result = FileUtils.renameFile(videoPath, vidFile);
        }
        return result;
    }

    /**
     * get video file path by vid
     * @param vid must >= 0, else return null
     * @return video file path
     */
    public static String getVideo(String vid) {
        String result = null;
        if (TextUtils.isEmpty(vid)) {
            return null;
        }
        String dirPath = getSavePath();
        if (TextUtils.isEmpty(dirPath)) {
            return null;
        }
        String vidFile = dirPath + File.separator + String.valueOf(vid) + ".mp4";
        File file = new File(vidFile);
        if (file.exists() && !file.isDirectory()) {
            result = vidFile;
        }
        return result;
    }

    /**
     * delete all mp4 files under this directory
     * @param dirPath directory path
     */
    public static void clearMp4InDir(String dirPath) {
        if (FileUtils.isValid(dirPath)) {
            File dir = new File(dirPath);
            if (dir.isDirectory()) {
                File[] fileList = dir.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname != null
                                && pathname.exists()
                                && !pathname.isDirectory()
                                && pathname.getName().toLowerCase().endsWith("mp4");
                    }
                });
                if (fileList != null) {
                    for (int index = 0, count = fileList.length; index < count; index ++) {
                        File file = fileList[index];
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
            }
        }
    }

    public static String generateFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    public static void recursionDeleteFile(File file, String s) {
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                if(!s.equals(f.getPath())){
                    recursionDeleteFile(f,s);
                }
            }
            file.delete();
        }
    }

    private static long getElapseTime(String path) {
        if (FileUtils.isValid(path)) {
            File file = new File(path);
            long time = file.lastModified();
            return System.currentTimeMillis() - time;
        } else {
            return -1;
        }
    }

    private static boolean exceed(String path) {
        long time = getElapseTime(path);
        Log.i(TAG, "exceed: " + time / (24 * 3600 * 1000) + " days");
        if (time > 0) {
            return time > MAX_ELAPSE;
        } else {
            return false;
        }
    }
}
