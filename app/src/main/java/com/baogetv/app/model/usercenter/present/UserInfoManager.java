package com.baogetv.app.model.usercenter.present;

import android.content.Context;

import com.baogetv.app.bean.BeanConvert;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.db.entity.HistoryItemEntity;
import com.baogetv.app.db.entity.HistoryItemEntityDao;
import com.baogetv.app.db.entity.UserItemEntity;
import com.baogetv.app.db.entity.UserItemEntityDao;
import com.baogetv.app.db.util.DaoManager;

import java.util.List;

/**
 * Created by chalilayang on 2017/12/6.
 */

public class UserInfoManager {
    private static volatile UserInfoManager instance;
    private UserItemEntityDao entityDao;
    private UserInfoManager(Context context) {
        entityDao = DaoManager.getInstance(context)
                .getDaoSession().getUserItemEntityDao();
    }

    public static UserInfoManager getInstance(Context context) {
        if (instance == null) {
            synchronized (UserInfoManager.class) {
                if (instance == null) {
                    instance = new UserInfoManager(context);
                }
            }
        }
        return instance;
    }

    public UserDetailBean getUserDetailBean(String token) {
        List<UserItemEntity> list = entityDao.queryBuilder()
                .where(UserItemEntityDao.Properties.Token.eq(token)).build().list();
        if (list != null && list.size() > 0) {
            return BeanConvert.getUserDetailBean(list.get(0));
        } else {
            return null;
        }
    }

    public void saveUserInfo(UserDetailBean bean) {
        if (bean != null) {
            String uid = bean.getUser_id();
            List<UserItemEntity> list = entityDao.queryBuilder()
                    .where(UserItemEntityDao.Properties.User_id.eq(uid)).build().list();
            entityDao.deleteInTx(list);
            UserItemEntity entity = BeanConvert.getUserItemEntity(bean);
            entityDao.insert(entity);
        }
    }

    public void deleteUser(String uid) {
        List<UserItemEntity> list = entityDao.queryBuilder()
                .where(UserItemEntityDao.Properties.User_id.eq(uid)).build().list();
        entityDao.deleteInTx(list);
    }

    public void clearUserInfo() {
        try {
            List<UserItemEntity> list = entityDao.queryBuilder().build().list();
            entityDao.deleteInTx(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserItemEntity> getUserList() {
        return entityDao.queryBuilder().build().list();
    }
}
