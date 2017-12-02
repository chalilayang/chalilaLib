package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.db.DBController;
import com.baogetv.app.db.domain.MyBusinessInfLocal;
import com.baogetv.app.db.domain.MyBusinessInfo;
import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.downloader.callback.DownloadManager;
import com.baogetv.app.downloader.domain.DownloadInfo;
import com.baogetv.app.model.usercenter.adapter.CacheListAdapter;
import com.baogetv.app.model.usercenter.adapter.MyCacheAdapter;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MyCacheActivity extends BaseTitleActivity {
    private static final String TAG = "MyCollectActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CacheListAdapter recyclerViewAdapter;
    private DownloadManager downloadManager;
    private DBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_cache));
        init();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_my_cache;
    }

    public void init() {
        downloadManager = DownloadService.getDownloadManager(getApplicationContext());
        try {
            dbController = DBController.getInstance(getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recyclerView = (RecyclerView) findViewById(R.id.cache_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        int margin_30px = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(30);
        divider.setMargin(margin_30px);
        recyclerView.addItemDecoration(divider);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new CacheListAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.updateCacheList(getDownloadListData());
    }

    private List<MyBusinessInfo> getDownloadListData() {
        List<DownloadInfo> downloadingList
                = DownloadService.getDownloadManager(getApplicationContext()).findAllDownloading();
        List<DownloadInfo> downloadedList
                = DownloadService.getDownloadManager(getApplicationContext()).findAllDownloaded();
        Log.i(TAG, "getDownloadListData: ");

        ArrayList<MyBusinessInfo> myBusinessInfos = new ArrayList<>();
        for (int index = 0, count = downloadedList.size(); index < count; index ++) {
            DownloadInfo info = downloadedList.get(index);
            try {
                MyBusinessInfLocal local = dbController.findMyDownloadInfoById(info.getId());
                myBusinessInfos.add(new MyBusinessInfo(local.getName(), local.getIcon(), local.getUrl()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (int index = 0, count = downloadingList.size(); index < count; index ++) {
            DownloadInfo info = downloadingList.get(index);
            try {
                MyBusinessInfLocal local = dbController.findMyDownloadInfoById(info.getId());
                myBusinessInfos.add(new MyBusinessInfo(local.getName(), local.getIcon(), local.getUrl()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        myBusinessInfos.add(new MyBusinessInfo("QQ",
                "http://img.wdjimg.com/mms/icon/v1/4/c6/e3ff9923c44e59344e8b9aa75e948c64_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/e/b8/520c1a2208bf7724b96f538247233b8e.apk"));
        myBusinessInfos.add(new MyBusinessInfo("微信",
                "http://img.wdjimg.com/mms/icon/v1/7/ed/15891412e00a12fdec0bbe290b42ced7_256_256.png",
                "http://wdj-uc1-apk.wdjcdn.com/1/a3/8ee2c3f8a6a4a20116eed72e7645aa31.apk"));
        myBusinessInfos.add(new MyBusinessInfo("360手机卫士",
                "http://img.wdjimg.com/mms/icon/v1/d/29/dc596253e9e80f28ddc84fe6e52b929d_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/4/0b/ce61a5f6093fe81502fc0092dd6700b4.apk"));
        myBusinessInfos.add(new MyBusinessInfo("陌陌",
                "http://img.wdjimg.com/mms/icon/v1/a/6e/03d4e21876706e6a175ff899afd316ea_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/b/0a/369eec172611626efff4e834fedce0ab.apk"));
        myBusinessInfos.add(new MyBusinessInfo("美颜相机",
                "http://img.wdjimg.com/mms/icon/v1/7/7b/eb6b7905241f22b54077cbd632fe87b7_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/a/e9/618d265197a43dab6277c41ec5f72e9a.apk"));
        myBusinessInfos.add(new MyBusinessInfo("Chrome",
                "http://img.wdjimg.com/mms/icon/v1/d/fd/914f576f9fa3e9e7aab08ad0a003cfdd_256_256.png",
                "http://wdj-qn-apk.wdjcdn.com/6/0d/6e93a829b97d671ee56190aec78400d6.apk"));
        return myBusinessInfos;
    }
}
