package com.cr.activity;


/**
 * 定义访问网络的对象
 * 包括传过去的值和
 * 返回过来的值
 * @author caiyanfei
 * @version $Id: AccessNetwork.java, v 0.1 2014年9月19日 下午2:06:17 lenovo Exp $
 */
public interface AccessNetwork {
    public boolean validate();//验证提交的信息
    public void getSubmitData();//提交信息的参数值
    public void result(String result);//回调返回方法
}
