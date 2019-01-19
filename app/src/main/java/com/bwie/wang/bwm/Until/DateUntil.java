package com.bwie.wang.bwm.Until;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUntil {
    public static class DateUtils {

        private static SimpleDateFormat mSimpleDateFormat = null;

        //获取系统时间
        public static String getCurrentDate() {
            Date d = new Date();
            mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return mSimpleDateFormat.format(d);
        }

        /*时间戳转换成字符窜*/
        public static String getDateToString(long time) {
            Date d = new Date(time);
            mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            return mSimpleDateFormat.format(d);
        }

        /*将字符串转为时间戳*/
        public static long getStringToDate(String time) {
            mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒", Locale.getDefault());
            Date date = new Date();
            try {
                date = mSimpleDateFormat.parse(time);
            } catch (ParseException e) {

                e.printStackTrace();
            }
            return date.getTime();
        }

    }
}