package com.cr.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 实现MD5加密
 * @author xXzX
 *
 */
public class MD5 {

	/**
	 * 给指定的字符串进行MD5加密
	 * @param target
	 * @return
	 */
	public static String getMD5(String target){
		StringBuffer buffer=new StringBuffer();
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte[]bt=md.digest(target.getBytes());//target.getBytes()将字符串转换为字节//digest是将输入的字符转换为哈希值，用字符数组存放
			
			for(int i=0;i<bt.length;i++){
				String t=Integer.toHexString(bt[i]&0xFF);
				if(t.length()==1){
					buffer.append("0");
				}
				buffer.append(t);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
			
	}
		    
//	    public static void main(String[] args) {
//			try {
//				System.out.println("kdkdk");
//				System.out.println(MD5.getMD5("ab3528c18f251684acfc466a92ac18b0"));
//			} catch (NoSuchAlgorithmException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

}
