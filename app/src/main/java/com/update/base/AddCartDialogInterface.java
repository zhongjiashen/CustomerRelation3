package com.update.base;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/9/15 0015 下午 5:29
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/9/15 0015         申中佳               V1.0
 */
public interface AddCartDialogInterface {
    /**
     * 确定按钮点击
     */
    void OkListener(String number, String specs_id, String specs_name, String specs_imag, String price);

}
