package com.update.base;

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
public interface BaseV {
    /**
     * 显示显示吐司
     *
     * @param text 吐司显示文本
     */
    void showShortToast(String text);
    /**
     * 网络请求成功返回数据
     *
     * @param data 数据
     * @param requestCode 请求码

     */
    void returnData(int requestCode, Object data);
    /**
     * 网络请求失败
     * @param requestCode 请求码
     */
    void httpfaile(int requestCode);

    /**
     * 网络请求结束
     */
    void httpFinish(int requestCode);
}
