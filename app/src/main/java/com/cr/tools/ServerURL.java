package com.cr.tools;

import com.crcxj.activity.BuildConfig;

/**
 * 保存请求服务器的地址
 * @author xXzX
 *
 */
public class ServerURL {
    //校验码
    public final static String pass              = "030728";
    //socket端口号
    public final static int    dkh               = 3011;
    //命名空间
    public final static String nameSpace         = "http://tempurl.org";
    //命名空间
//    public final static String nameSpace         = "http://123.14.250.132";
    //URL地址
    //endPoint通常是将WSDL地址末尾的"?wsdl"去除后剩余的部分；
    //soapAction通常为命名空间 + 调用的方法名称。
    //	public static String ip="http://hengvideocrm.oicp.net";
    //	public static String dk="8031";
    //	public static String endPoint = "http://yunxiaosz.eicp.net:8030/mytest/CrmWebService.asmx";
    //普通版
    //	public static String endPoint = "/crmglxtserver/n_crmwebserver.asmx?WSDL";
    //进销存版
    //public static String       endPoint          = "/crmjxcserver/n_crmwebserver.asmx?WSDL";
    //IT企业版
//    public static String       endPoint          = "/crmenpserver/n_crmwebserver.asmx?WSDL";
    //IT专业版
//    public static String       endPoint          = "/crmitserver/n_crmwebserver.asmx?WSDL";
    //租赁企业版
//    public static String       endPoint          = "/leasenpserver/n_crmwebserver.asmx?WSDL";

    //内部版
//    public static String       endPoint          = "/crmintserver/n_crmwebserver.asmx?WSDL";

    //汽配版
//    public static String       endPoint          = "/crmcarserver/n_crmwebserver.asmx?WSDL";
    //标准专业版
//    public static String       endPoint          = "/crmjxcserver/n_crmwebserver.asmx?WSDL";


    //保全版
//        public static String       endPoint          = "/crmleaseserver/n_crmwebserver.asmx?WSDL";
    //保险版
    //	public static String endPoint = "/bxserver/n_crmwebserver.asmx?WSDL";
    //	public static String endPoint = "http://192.168.1.198:8031/crmglxtserver/n_crmwebserver.asmx?WSDL";
    //	private static String endPart="?wsdl";

    public static String       endPoint= BuildConfig.URL;

    //查询帐套的方法名
    public static String       ACCSET            = "accset";
    //秘书提醒-工作提醒
    public static String       BROADCASTPROMPT   = "broadcastprompt";
    //秘书提醒-工作提醒-详细
    public static String       BROADCASTPROMPTXX = "broadcastpromptxx";
    //秘书提醒-公司公告
    public static String       BROADCAST         = "broadcast";
    //秘书提醒-公司公告-详情
    public static String       BROADCASTXX       = "broadcastxx";
    //秘书提醒-签到
    public static String       REGISTERWRITE     = "registerwrite";
    //秘书提醒-个人日程
    public static String       RCPROMPTLIST      = "rcpromptlist";
    //秘书提醒-个人日程-详细
    public static String       RCPROMPTXX        = "rcpromptxx";
    //秘书提醒-个人日程-保存
    public static String       RCPROMPTSAVE      = "rcpromptsave";

    //工作平台-呼叫中心-选择计划
    public static String       GETJHBLIST        = "getjhblist";
    //工作平台-呼叫中心-呼叫中心
    public static String       GETJHBXX          = "getjhbxx";
    //工作平台-呼叫中心-拜访
    public static String       LXRINFO           = "lxrinfo";
    //工作平台-呼叫中心-最近
    public static String       VISITINFO         = "visitinfo";
    //工作平台-呼叫中心-项目
    public static String       ITEMLIST          = "itemlist";
    //工作平台-呼叫中心-维护
    public static String       SHWXINFO          = "shwxinfo";
    //工作平台-呼叫中心-成交记录
    public static String       SALELISTCLIENT    = "salelistclient";
    //工作平台-预约拜访
    public static String       JHRWYYBF          = "jhrwyybf";
  //工作平台-近期新增单位
    public static String       JHRWXZLD          = "jhrwxzld";
    //工作平台-售后回访
    public static String       JHRWSHHF          = "jhrwshhf";
    //工作平台-客户管理
    public static String       JHRWZDY           = "jhrwzdy";
    //工作平台-销售单据
    public static String       SALELISTOPER      = "salelistoper";
    //工作平台-销售单据-销售单-主单
    public static String       SALEMASTER        = "salemaster";
    //工作平台-销售单据-新增销售单
    public static String       SALEMASTERSAVE    = "salemastersave";
    //工作平台-销售单据-销售单-产品明细
    public static String       SALEDETAIL        = "saledetail";
    //物品的资料
    public static String       GOODSLIST         = "goodslist";
    //产品保存
    public static String       SALEDETAILSAVE    = "saledetailsave";
    //物品的类别
    public static String       GOODSTYPE         = "goodstype";
    //计划总结-日计划
    public static String       JHRWGZZJRZJ       = "jhrwgzzjrzj";
    //计划总结-日计划新增
    public static String       JHRWGZZJRZJSAVE   = "jhrwgzzjrzjsave";
    //计划总结-周计划
    public static String       JHRWGZZJZZJ       = "jhrwgzzjzzj";
    //计划总结-周计划-新增
    public static String       JHRWGZZJZZJSAVE       = "jhrwgzzjzzjsave";
    //计划总结-选择周计划列表
    public static String       JHRWSELECTWEEK       = "jhrwselectweek";
    
    //计划总结-月计划
    public static String       JHRWGZZJYZJ       = "jhrwgzzjyzj";
    //计划总结-月计划新增
    public static String       JHRWGZZJYZJSAVE   = "jhrwgzzjyzjsave";
    //计划总结-年计划
    public static String       JHRWGZZJZDY       = "jhrwgzzjzdy";
    //计划总结-年计划新增
    public static String       JHRWGZZJZDYSAVE   = "jhrwgzzjzdysave";
    //计划总结-项目列表
    public static String       JHRWGZZJITEM      = "jhrwgzzjitem";
    //计划总结-新增项目
    public static String       JHRWGZZJITEMSAVE  = "jhrwgzzjitemsave";
    //计划总结-新增项目
    public static String       JHRWGZZJZWZJSAVE  = "jhrwgzzjzwzjsave";
    //计划总结-读取项目
    public static String       JHRWGZZJZWZJREAD  = "jhrwgzzjzwzjread";
    //反馈
    public static String       SUGGESTION        = "suggestion";
    //单位查询
    public static String       CLIENTLIST        = "clientlist";
    //单位查询
    public static String       CLIENTINFO        = "clientinfo";
    //数据字典
    public static String       DATADICT          = "datadict";
    //编辑项目
    public static String       JHRWGZZJITEMXX    = "jhrwgzzjitemxx";
    //单位保存
    public static String       CLIENTSAVE        = "clientsave";
    //拜访录入
    public static String       JHRWRECORD        = "jhrwrecord";
    //地区
    public static String       AREALIST          = "arealist";
    //联系人保存
    public static String       LXRSAVE           = "lxrsave";
    //项目保存
    public static String       ITEMSAVE          = "itemsave";
    //删除数据
    public static String       DELDATA           = "deldata";

    //查询联系人信息
    public static String       LXRLIST           = "lxrlist";
    //查询联系人是否存在
    public static String       LXRINFOOFPHONE    = "lxrinfoofphone";
    //单据列表
    public static String       BILLLIST          = "billlist";
    //单据详情(主表)
    public static String       BILLMASTER        = "billmaster";
    //单据详情(从表)
    public static String       BILLDETAIL        = "billdetail";
    //单据详情(组装拆卸主表确定从表)
    public static String       SELECTGOODSCMB        = "selectgoodscmb";
    //删除主单
    public static String       BILLDELMASTER     = "billdelmaster";
    //删除明细
    public static String       BILLDELDETAIL     = "billdeldetail";
    //审核
    public static String       BILLSH            = "billsh";
    //选择批号
    public static String       GETGOODSBATCH     = "getgoodsbatch";
    //选择批号
    public static String       ITEMINFO          = "iteminfo";
    //选择经办人
    public static String       EMPLOYLIST        = "employlist";
    //选择操作员
    public static String       SELECTOPER        = "selectoper";
  //选择操作员-统计报表
    public static String       SELECTOPER_1        = "selectoper_1";
    //选择操作员-统计报表2
    public static String       SELECTEMPLOY        = "selectemploy";
    //单位联系方式
    public static String       CLIENTLXFSLIST    = "clientlxfslist";
    //单位联系方式保存
    public static String       LXFSSAVE          = "lxfssave";
    //联系人-联系信息
    public static String       LXRLXFSLIST       = "lxrlxfslist";
    //选择商品
    public static String       SELECTGOODS       = "selectgoods";
  //选择商品(根据仓库)
    public static String       SELECTGOODSKCPD       = "selectgoodskcpd";
  //选择商品组装拆卸
    public static String       SELECTGOODSZZCX       = "selectgoodszzcx";
    //选择供应商
    public static String       CLIENTLIST2       = "clientlist2";
    //单据保存
    @Deprecated
    public static String       BILLSAVE          = "billsave";
    //单据保存
    public static String       BILLSAVENEW          = "billsavenew";
    //审核流程
    public static String       BILLSHLIST        = "billshlist";
    //选择客户
    public static String       CLIENTLIST1       = "clientlist1";
    //单据引用（主表）
    public static String       REFBILLMASTER     = "refbillmaster";
    //单据引用（从表）
    public static String       REFBILLDETAIL        = "refbilldetail";
    //库存查询
    public static String       STOREQUERYRPT     = "storequeryrpt";
    //查询商品的价格
    public static String       SHOWGOODSPRICEINFO     = "showgoodspriceinfo";
    //经营状况
    public static String       ENTERPRISEWORKRPT = "enterpriseworkrpt";
    //应收应付
    public static String       CLIENTINOUTRPT    = "clientinoutrpt";
    //资金账户
    public static String       BANKRPT           = "bankrpt";
    
  //2客户拜访统计报表
    public static String       CLIENTVISITRPT    = "clientvisitrpt";
    //3新客户统计
    public static String       NEWADDCLIENTRPT    = "newaddclientrpt";
    //4.	销售机会统计报表
    public static String       SALECHANCERPT    = "salechancerpt";
    //5.	客户等级统计报表
    public static String       CLIENTTYPERPT    = "clienttyperpt";
    //5.	客户等级统计报表,明细
    public static String       CLIENTTYPEDETAILRPT    = "clienttypedetailrpt";
    //6.	销售阶段统计报表
    public static String       SALESTAGERPT    = "salestagerpt";
  //6.	销售阶段统计报表 明细
    public static String       SALESTAGEDETAILRPT    = "salestagedetailrpt";

    
    //7.	客户服务统计报表
    public static String       CLIENTSERVICERPT    = "clientservicerpt";
    //8.	销售收款汇总表
    public static String       SALECHARGERPT    = "salechargerpt";
    
    //项目列表
    public static String PROJECTLIST="projectlist";
  //项目编辑查询
    public static String PROJECTINFO="projectinfo";
  //项目保存
	public static String PROJECTSAVE = "projectsave";
}
