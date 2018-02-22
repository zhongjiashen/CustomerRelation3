package com.update.base;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/9/18 0018 下午 5:39
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/9/18 0018         申中佳               V1.0
 */
public class BaseP {
    protected BaseV view;
    protected Activity mActivity;
    static boolean run = false;


    public BaseP(BaseV view, Activity activity) {
        this.view = view;
        mActivity = activity;

    }

    /**
     * @param requestCode 请求码
     * @param url         请求路径
     * @param map         请求参数map
     * @param key         需要验证非空参数的key的数组
     * @param stringID    该参数为空提示的语句
     * @param type
     */
    protected void post(final int requestCode, String url, Map<String,Object> map, String[] key, int[] stringID, final Type type) {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                if (TextUtils.isEmpty((String)map.get(key[i]))) {
                    view.showShortToast(mActivity.getString(stringID[i]));
                    return;
                }
            }
        }
        post(requestCode, url, map, type);

    }


    protected void post(final int requestCode, String url, Map<String, Object> map, final Type type) {

    }

    protected void returnData(int requestCode, Object object) {
        view.returnData(requestCode, object);
    }



}
