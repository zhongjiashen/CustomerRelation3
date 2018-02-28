package com.cr.tools;

import android.content.Context;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 获得服务器数据的统一请求方法
 * 
 * @author xXzX
 * 
 */
public class ServerRequest {
	/**
	 * 发送post请求
	 * 
	 * @param path
	 * @param params
	 * @param enc
	 * @return 如果正确得到结果，如果错误，得到null
	 * @throws Exception
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
		// Log.v("dddd",sb.toString());

		// parmJson.put("token",MD5.getMD5(sb.toString()));
		// parmJson.put("sid", ServerURL.sid);
		// Log.v("dddd", psb.toString());
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
			// Log.v("dddd", json);
			return json;
		}
		// 服务器返回错误信息
		return null;
	}

	/**
	 * 发送GET请求
	 * 
	 * @param path
	 * @param enc
	 * @return 如果正确得到结果，如果错误，得到null
	 * @throws Exception
	 */
	public static String sendPost(String path, String enc) throws Exception {
		// byte[] entitydata =
		// ("{\"REQUEST_BODY\":"+psb.toString()+"}").getBytes();//得到实体的二进制数据
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
			// Log.v("dddd", json);
			return json;
		} else {
			return null;
		}
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream
	 *            输入流
	 * @return
	 * @throws Exception
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
			Map<String, Object> params,Context context) {
		// dndPoint通常是将WSDL地址末尾的"?wsdl"去除后剩余的部分；
		// soapAction通常为命名空间 + 调用的方法名称。
		String nameSpace = ServerURL.nameSpace;
		// 调用的方法名称
		// String methodName = "GetJhbList";
		String endPoint = ShareUserInfo.getIP(context)+":"+ShareUserInfo.getDK(context)+ServerURL.endPoint;
//		Log.v("dddd","url:"+endPoint);
		String soapAction = ServerURL.nameSpace + "/" + methodName;
		Log.v("dddd", "调用接口："+soapAction);
		Log.v("dddd", "调用接口："+methodName);
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
					Log.v("dddd", entry.getKey() + ":"
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
			Log.v("dddd",envelope.bodyIn+"");
			SoapObject object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			Log.v("dddd", "ddd" + object.toString());
			result = object.getProperty(methodName + "Result").toString();
//			if(Character.isDigit(result.charAt(0))){
//			    return "";
//			}
			// String result = object.toString();
			if (result.equals("anyType{}")) {
			    Log.v("dddd", "执行结果：");
				return "";
			}
			if (result.length()>6&&result.substring(0, 6).equals("false1")) {
			    Log.v("dddd", "执行结果："+result);
				return "nmyqx";
			}
			Log.v("dddd", "执行结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	private void aa(){
		
	}
}
