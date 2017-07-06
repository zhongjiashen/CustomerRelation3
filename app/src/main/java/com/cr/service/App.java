package com.cr.service;

import android.view.Menu;

/**
 * hnjz.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */

/**
 * 系统使用的一些常量信息
 * 
 * @author wumi
 * @version $Id: App.java, v 0.1 Jan 3, 2013 10:25:14 AM wumi Exp $
 */
public interface App {

    String   INFO           = "环保";
    String   SZ             = "设置";
    String   OK             = "确定";
    String   LOGOUT         = "退出";
    String   ZX             = "注销";
    String   CANCEL         = "取消";
    String   LOADATA        = "正在查询中......";
    String   SUBMIT         = "提交中......";
    String   LOGINMSG       = "系统登录中...";
    String   LIST_EMPTY     = "请选择一条记录";
    String   DATA_EMPTY     = "没有数据";
    String   DOWNLOAD       = "下载中，请稍后...";
    String   DOWNLOAD_FAIL  = "下载失败";
    String   CLIENT_CHECK   = "检查是否更新...";
    String   CLIENT_NEW     = "发现新版本，是否下载";
    String   CLIENT_LATEST  = "当前为最新版本";
    String   CLIENT_INSTALL = "下载完成，是否安装";
    String   UPLOAD         = "文件上传中...";
    String   NETERROR       = "无法连接到当前服务器，请确保系统设置中的服务器地址正确";
    String   NET_NOUSE      = "使用此应用程序需要链接网络，请在设置中开启移动网络或无线网络";
    String   PASSERROR      = "用户名或密码错误！";
    String   TIMEOUT        = "返回数据超时或网络设置错误！";
    

    String HOME = "hnjz_hb";
    String FILE_DIR = "hnjz_hb/files/";
    
    // --常用菜单
    /** 菜单组 */
    int      GROUP_SITE            = 0;
    int      MENU_ALWAYS_DATA      = Menu.FIRST;
    int      MENU_HISTORY_DATA     = MENU_ALWAYS_DATA + 1;
    
    /** 菜单组 */
    int      GROUP_SITE_WRY        = 0;
    /** 菜单-实时数据 */
    int      MENU_ALWAYS_DATA_WRY  = Menu.FIRST;
    /** 菜单-日数据 */
    int      MENU_HISTORY_DATA_WRY = MENU_ALWAYS_DATA_WRY + 1;
    /** 菜单-月查看 */
    int      MENU_REPORT_WRY       = MENU_HISTORY_DATA_WRY + 1;
    
    /** 实时数据，历史数据的菜单 */
    String[] WRY_MEUN              = new String[] { "日数据", "历史数据", "时间段查询数据", };
    String[] MEUN                  = new String[] { "实时数据", "日数据", "历史数据", };
    /** 废水-曲线图菜单 */
	String[] MENU_FS = new String[] { "COD", "氨氮", "流量" };
    int      GROUP_FS              = 0;
    /** COD */
    int      GROUP_FS_COD          = Menu.FIRST;
    /** 氨氮 */
    int      GROUP_FS_AD           = GROUP_FS_COD + 1;
    /** 流量 */
	int GROUP_FS_LL 			   = GROUP_FS_COD + 2;
	/** 废气-曲线图菜单 */
	String[] MENU_FQ = new String[] { "烟尘", "二氧化硫", "氮氧化物", "流量" };
    int      GROUP_FQ              = 0;
    /** 烟尘 */
    int      GROUP_FQ_YC           = Menu.FIRST + 10;
    /** 二氧化硫 */
    int      GROUP_FQ_EYHL         = GROUP_FQ_YC + 1;
    /** 氮氧化物 */
    int      GROUP_FQ_ADHW         = GROUP_FQ_EYHL + 1;
    /** 流量 */
	int GROUP_FQ_LL 			   = GROUP_FQ_ADHW + 1;
}
