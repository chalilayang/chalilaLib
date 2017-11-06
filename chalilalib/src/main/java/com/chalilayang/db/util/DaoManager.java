package com.chalilayang.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chalilayang.db.entity.DaoMaster;
import com.chalilayang.db.entity.DaoSession;

/**
 * Created by chalilayang on 2017/10/19.
 */

class DaoManager {
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

    private DaoManager(Context context) {
        appContext = context.getApplicationContext();
        String dbName = context.getPackageName() + "database";
        daoMaster = new DaoMaster(getWritableDatabase());
        openDevHelper = new DaoMaster.DevOpenHelper(context, dbName);
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
