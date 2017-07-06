
package com.cr.tools;

import java.util.Map;

/**
 * 数据的提交接口
 * 
 */
public interface INetWork {
    /**
     * 验证UI数据，true往下进行，false停止。注：返回false时自行处理提示信息)
     * 
     * @return  true往下进行，false停止
     */
    boolean validate();

    /**
     * 获取需要提交服务器的数据
     * 
     * @return 需要提交服务器的数据，json格式的字符串
     * @throws Exception 数据转换发生异常时，抛出
     */
    Map<String, Object>  getSubmitData() throws Exception;

    /**
     * 处理返回数据
     * 
     * @param result 服务器返回数据
     */
    void result(String result)throws Exception;
}
