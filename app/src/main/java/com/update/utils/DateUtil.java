package com.update.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class DateUtil {
    /**
     * SimpleDateFormat函数语法：
     G 年代标志符
     y 年
     M 月
     d 日
     h 时 在上午或下午 (1~12)
     H 时 在一天中 (0~23)
     m 分
     s 秒
     S 毫秒
     E 星期
     D 一年中的第几天
     F 一月中第几个星期几
     w 一年中第几个星期
     W 一月中第几个星期
     a 上午 / 下午 标记符
     k 时 在一天中 (1~24)
     K 时 在上午或下午 (0~11)
     z 时区
     */
    public static String DateToString(Date date, String style){
        DateFormat df = new SimpleDateFormat(style);
        return df.format(date);
    }
    public static String longDateToString(long time, String style){
        DateFormat df = new SimpleDateFormat(style);
        return df.format(new Date(time));
    }
    public static long StringTolongDate(String time, String style){
        DateFormat df = new SimpleDateFormat(style);
        try {
            return df.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
