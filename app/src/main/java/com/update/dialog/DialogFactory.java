package com.update.dialog;

import android.content.Context;

import com.update.base.BaseDialog;

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
     * 获取添加序列号对话框
     *
     * @param context
     * @param title
     * @param mDialogClickInterface
     * @return
     */
    public static BaseDialog getAddModifySerialNumberDialog(Context context,String title, OnDialogClickInterface mDialogClickInterface) {
        ModifySerialNumberDialog numberDialog = new ModifySerialNumberDialog(context);
        numberDialog.setTitle(title);
        numberDialog.setDialogClickInterface(mDialogClickInterface);
        return numberDialog;
    }


    /**
     * 获取修改序列号对话框
     *
     * @param context
     * @param object
     * @param mDialogClickInterface
     * @return
     */
    public static BaseDialog getModifySerialNumberDialog(Context context, Object object, OnDialogClickInterface mDialogClickInterface) {
        ModifySerialNumberDialog numberDialog = new ModifySerialNumberDialog(context);
        numberDialog.setData(object);
        numberDialog.setDialogClickInterface(mDialogClickInterface);
        return numberDialog;
    }



    /**
     * 获选择文件对话框
     *
     * @param context
     * @param mDialogClickInterface
     * @return
     */
    public static BaseDialog getFileChooseDialog(Context context, OnDialogClickInterface mDialogClickInterface) {
        FileChooseDialog dialog = new FileChooseDialog(context);
        dialog.setDialogClickInterface(mDialogClickInterface);
        return dialog;
    }
    /**
     * 获选择文件对话框
     *
     * @param context
     * @param mDialogClickInterface
     * @return
     */
    public static BaseDialog getButtonDialog(Context context,String string, OnDialogClickInterface mDialogClickInterface) {
        ButtonDialog dialog = new ButtonDialog(context);
        dialog.setTitle(string);
        dialog.setDialogClickInterface(mDialogClickInterface);
        return dialog;
    }

    /**
     * 获取公有云私有云对话框
     *
     * @param context
     * @param mDialogClickInterface
     * @return
     */
    public static BaseDialog getLinkSetDialog(Context context, OnDialogClickInterface mDialogClickInterface) {
        LinkSetDialog dialog = new LinkSetDialog(context);
        dialog.setDialogClickInterface(mDialogClickInterface);
        return dialog;
    }
}
