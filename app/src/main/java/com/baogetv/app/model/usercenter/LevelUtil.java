package com.baogetv.app.model.usercenter;

import android.content.Context;

import com.baogetv.app.R;
import com.baogetv.app.bean.GradeBean;
import com.baogetv.app.bean.UserDetailBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chalilayang on 2017/12/5.
 */

public class LevelUtil {

    /**
     *
     * @param bean
     * @param list
     * @return null not found
     */
    public static Level getCurLevel(UserDetailBean bean, List<GradeBean> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        List<Level> grades = new ArrayList<>();
        for (int index = 0, count = list.size(); index < count; index ++) {
            int score = Integer.parseInt(list.get(index).getScore());
            grades.add(new Level(score, index));
        }
        Collections.sort(grades);
        int userScore = Integer.parseInt(bean.getScore());
        int pos = -1;
        for (int index = 0, count = grades.size(); index < count; index ++) {
            if (userScore >= grades.get(index).score) {
                pos = index;
            }
        }
        if (pos >= 0 && pos < grades.size()) {
            return grades.get(pos);
        } else {
            return null;
        }
    }

    public static Level getNextLevel(UserDetailBean bean, List<GradeBean> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        List<Level> grades = new ArrayList<>();
        for (int index = 0, count = list.size(); index < count; index ++) {
            int score = Integer.parseInt(list.get(index).getScore());
            grades.add(new Level(score, index));
        }
        Collections.sort(grades);
        int userScore = Integer.parseInt(bean.getScore());
        int pos = -1;
        for (int index = grades.size() - 1; index >= 0; index --) {
            if (userScore < grades.get(index).score) {
                pos = index;
            }
        }
        if (pos >= 0 && pos < grades.size()) {
            return grades.get(pos);
        } else {
            return null;
        }
    }

    public static String getLevelDesc(Context context, UserDetailBean bean, List<GradeBean> list) {
        String format = context.getString(R.string.upgrade_desc_format);
        if (bean == null || list == null || context == null) {
            return format;
        }
        int curScore = 0;
        try {
            curScore = Integer.parseInt(bean.getScore());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Level nextLevel = getNextLevel(bean, list);
        String result = "";
        if (nextLevel != null) {
            int score = nextLevel.score - curScore;
            String levelName = list.get(nextLevel.index).getName();
            String metal = list.get(nextLevel.index).getMedal();
            result = String.format(format, score, levelName, metal);
        }
        return result;
    }
}
