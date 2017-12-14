package com.baogetv.app.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class TimeUtil {
    /**
     * 根据时间戳来判断当前的时间是几天前,几分钟,刚刚
     *
     * @param long_time
     * @return
     */
    public static String getTimeStateNew(String long_time) {
        String long_by_13 = "1000000000000";
        String long_by_10 = "1000000000";
        if (Long.valueOf(long_time) / Long.valueOf(long_by_13) < 1) {
            if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
                long_time = long_time + "000";
            }
        }
        Timestamp time = new Timestamp(Long.valueOf(long_time));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;
        if ((time_conver / day_conver) < 3) {
            temp_conver = time_conver / day_conver;
            if (temp_conver <= 2 && temp_conver >= 1) {
                if (temp_conver == 1) {
                    return "昨天";
                } else {
                    return temp_conver + "天前";
                }
            } else {
                temp_conver = (time_conver / hour_conver);
                if (temp_conver >= 1) {
                    return temp_conver + "小时前";
                } else {
                    temp_conver = (time_conver / min_conver);
                    if (temp_conver >= 1) {
                        return temp_conver + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            }
        } else {
            return format.format(time);
        }
    }

    public static String getYear(String dateStr) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            result = calendar.get(Calendar.YEAR) +"";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String getMonth(String dateStr) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            result = (calendar.get(Calendar.MONTH)+1) +"";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String getDay(String dateStr) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            result = calendar.get(Calendar.DAY_OF_MONTH) + "";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
