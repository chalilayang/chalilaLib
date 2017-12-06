package com.baogetv.app.model.usercenter;

import android.content.Context;

import com.baogetv.app.bean.HistoryBean;
import com.baogetv.app.db.entity.HistoryItemEntity;
import com.baogetv.app.db.entity.HistoryItemEntityDao;
import com.baogetv.app.db.util.DaoManager;

import java.util.List;

/**
 * Created by chalilayang on 2017/12/6.
 */

public class HistoryManager {
    private static volatile HistoryManager instance;
    private HistoryItemEntityDao entityDao;
    private HistoryManager(Context context) {
        entityDao = DaoManager.getInstance(context)
                .getDaoSession().getHistoryItemEntityDao();
    }

    public static HistoryManager getInstance(Context context) {
        if (instance == null) {
            synchronized (HistoryManager.class) {
                if (instance == null) {
                    instance = new HistoryManager(context);
                }
            }
        }
        return instance;
    }

    public boolean isInHistory(String vid) {
        List<HistoryItemEntity> list = entityDao.queryBuilder()
                .where(HistoryItemEntityDao.Properties.VideoId.eq(vid)).build().list();
        return list != null && list.size() > 0;
    }

    public void saveHistory(String vid, String title, String url) {
        List<HistoryItemEntity> list = entityDao.queryBuilder()
                .where(HistoryItemEntityDao.Properties.VideoId.eq(vid)).build().list();
        entityDao.deleteInTx(list);
        entityDao.insert(
                new HistoryItemEntity(vid, title, url, String.valueOf(System.currentTimeMillis())));
    }

    public List<HistoryItemEntity> getHistoryList(String vid, String title, String url) {
        return entityDao.queryBuilder().build().list();
    }
}
