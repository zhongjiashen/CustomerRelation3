package com.cr.tools;

import java.text.DecimalFormat;

/**
 * 数字的操作
 * @author Administrator
 *
 */
public class FigureTools {

    /**
     * 数字的四舍五入
     * @param str
     * @return
     */
    public static String sswrFigure(String resourceDate) {
        String sf = "#.00";//保留两位
        DecimalFormat df = new DecimalFormat(sf);

        String str = df.format(Double.parseDouble(resourceDate));
        return str;
    }

    /**
     * 数字的四舍五入
     * @param str
     * @return
     */
    public static String sswrFigure(Double resourceDate) {
        String sf = "0.00";//保留两位
        DecimalFormat df = new DecimalFormat(sf);

        String str = df.format(resourceDate);
        return str;
    }
}
