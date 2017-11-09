package com.reshape.app.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.reshape.app.db.entity.DaoMaster;
import com.reshape.app.db.entity.DaoSession;

/**
 * Created by chalilayang on 2017/10/19.
 */

public class DaoManager {
    private DaoMaster.DevOpenHelper openDevHelper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context appContext;
    private static DaoManager ourInstance;

    public static DaoManager getInstance(Context context) {
        if (ourInstance == null) {
            synchronized (DaoManager.class) {
                if (ourInstance == null) {
                    ourInstance = new DaoManager(context);
                }
            }
        }
        return ourInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private DaoManager(Context context) {
        appContext = context.getApplicationContext();
        String dbName = context.getPackageName() + "database";
        openDevHelper = new DaoMaster.DevOpenHelper(context, dbName);
        daoMaster = new DaoMaster(getWritableDatabase());
        daoSession = new DaoMaster(getWritableDatabase()).newSession();
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = openDevHelper.getWritableDatabase();
        return db;
    }
}
