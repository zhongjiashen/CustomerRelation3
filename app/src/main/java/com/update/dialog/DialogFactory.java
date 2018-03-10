package com.update.dialog;

import android.content.Context;
import android.view.LayoutInflater;

import com.crcxj.activity.R;
import com.update.base.BaseDialog;
import com.update.viewholder.ViewHolderFactory;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/10 0010 上午 9:41
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/10 0010         申中佳               V1.0
 */
public class DialogFactory {
    /**
     * 获取修改序列号对话框
     * @param context
     * @param object
     * @param mDialogClickInterface
     * @return
     */
    public static BaseDialog getModifySerialNumberDialog(Context context,Object object,OnDialogClickInterface mDialogClickInterface) {
        ModifySerialNumberDialog numberDialog=  new ModifySerialNumberDialog(context);
        numberDialog.setData(object);
        numberDialog.setDialogClickInterface(mDialogClickInterface);
        return numberDialog;
    }
}
