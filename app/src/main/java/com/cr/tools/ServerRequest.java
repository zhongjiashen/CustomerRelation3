package com.cr.tools;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.update.utils.LogUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 获得服务器数据的统一请求方法
 *
 * @author xXzX
 */
public class ServerRequest {


    public static String requestPost(String baseUrl, Map<String, String> paramsMap) {
        try {
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            // 请求的参数转换为byte数组
            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(baseUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 开始连接
            urlConn.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                InputStream inStream = urlConn.getInputStream();
                byte[] data = readInputStream(inStream);
                String json = new String(data);
                // System.out.println("json=" + json);
                // LogUtils.e( json);
                return json;
//				Log.e(TAG, "Post方式请求成功，result--->" + result);
            } else {
//				Log.e(TAG, "Post方式请求失败");
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
//			Log.e(TAG, e.toString());
        }
        return "";
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    private static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {

            return null;
        }
    }


    /**
     * 发送post请求
     *
     * @param path
     * @param params
     * @param enc
     * @return 如果正确得到结果，如果错误，得到null
     * @throws
     */
    public static String sendPost(String path, Map<String, Object> params,
                                  String enc) throws Exception {
        StringBuilder sb = new StringBuilder();
        // JSONObject parmJson=new JSONObject();
        StringBuilder psb = new StringBuilder();
        // sb.append(ServerURL.sid);
        // psb.append("{\"sid\":\""+ServerURL.sid+"\"");
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                // 如果传入的参数不为null
                Object obj = entry.getValue();
                if (obj != null) {
                    sb.append(obj.toString());
                    // parmJson.put(entry.getKey(), entry.getValue());
                    if (obj instanceof String) {
                        psb.append(",\"" + entry.getKey() + "\":\""
                                + entry.getValue() + "\"");
                    } else {
                        psb.append(",\"" + entry.getKey() + "\":"
                                + entry.getValue());
                    }
                }
            }
        }
        // sb.append(ServerURL.secret_key);
        psb.append(",\"token\":\"" + MD5.getMD5(sb.toString()) + "\"}");
        // LogUtils.e(sb.toString());

        // parmJson.put("token",MD5.getMD5(sb.toString()));
        // parmJson.put("sid", ServerURL.sid);
        // LogUtils.e( psb.toString());
        byte[] entitydata = ("{\"REQUEST_BODY\":" + psb.toString() + "}")
                .getBytes();// 得到实体的二进制数据
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        // 设置5s的连接时间
        conn.setConnectTimeout(5 * 1000);
        // 设置10s得到输入结果
        conn.setReadTimeout(10 * 1000);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length",
                String.valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(entitydata);
        outStream.flush();
        outStream.close();
        // System.out.println("服务器返回码=" + conn.getResponseCode());
        if (conn.getResponseCode() == 200) {
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);
            String json = new String(data);
            // System.out.println("json=" + json);
            // LogUtils.e( json);
            return json;
        }
        // 服务器返回错误信息
        return null;
    }

    /**
     * 发送GET请求
     *
     * @param path
     * @return 如果正确得到结果，如果错误，得到null
     * @throws
     */
    public static String sendGet(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            // conn.getResponseMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inStream = conn.getInputStream();
                byte[] data = readInputStream(inStream);
                String json = new String(data);
                // System.out.println("json=" + json);
                // LogUtils.e( json);
                return json;
            } else {
                return null;
            }
        } catch (Exception e) {
//			Log.e(TAG, e.toString());
        }
		return "";
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 发送webserver请求
     */
    public static String webServicePost(String methodName,
                                        Map<String, Object> params, Context context) {
        // dndPoint通常是将WSDL地址末尾的"?wsdl"去除后剩余的部分；
        // soapAction通常为命名空间 + 调用的方法名称。
        String nameSpace = ServerURL.nameSpace;
        // 调用的方法名称
        // String methodName = "GetJhbList";
        String endPoint = ShareUserInfo.getIP(context) + ":" + ShareUserInfo.getDK(context) + ServerURL.endPoint;
        LogUtils.e("url:" + endPoint);
        String soapAction = ServerURL.nameSpace + "/" + methodName;
        LogUtils.e("调用接口：" + soapAction);
        LogUtils.e("调用接口：" + methodName);
        LogUtils.e("请求参数：" + JSON.toJSONString(params));
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        String result = "";
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                // 如果传入的参数不为null
                Object obj = entry.getValue();
                if (obj != null) {
                    if (entry.getValue() == null) {
                        rpc.addProperty(entry.getKey(), "");
                    } else {
                        rpc.addProperty(entry.getKey(), entry.getValue().toString());
                    }
                    LogUtils.e(entry.getKey() + ":"
                            + entry.getValue().toString());
                }
            }
        }
        rpc.addProperty("pass", ServerURL.pass);// 尾部的校验码

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用
            transport.call(soapAction, envelope);
            transport.debug = true;
            // 获取返回的数据
            LogUtils.e(envelope.bodyIn + "");
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 获取返回的结果
            LogUtils.e("ddd" + object.toString());
            result = object.getProperty(methodName + "Result").toString();
//			if(Character.isDigit(result.charAt(0))){
//			    return "";
//			}
            // String result = object.toString();
            if (result.equals("anyType{}")) {
                LogUtils.e("执行结果：");
                return "";
            }
            if (result.length() > 6 && result.substring(0, 6).equals("false1")) {
                LogUtils.e("执行结果：" + result);
                return "nmyqx";
            }
            LogUtils.e("执行结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result.replace("null", "\"\"");
    }

    private void aa() {

    }
}
