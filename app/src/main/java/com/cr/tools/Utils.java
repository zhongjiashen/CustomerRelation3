package com.cr.tools;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 经常用到的小工具方法
 * @author xXzX
 *
 */
public class Utils {  
    public static void CopyStream(InputStream is, OutputStream os)  
    {  
        final int buffer_size=1024;  
        try  
        {  
            byte[] bytes=new byte[buffer_size];  
            for(;;)  
            {  
              int count=is.read(bytes, 0, buffer_size);  
              if(count==-1)  
                  break;  
             os.write(bytes, 0, count);  
              is.close();  
              os.close();  
            }  
        }  
        catch(Exception ex){}  
    }  
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("[0-9]*"); 
        return pattern.matcher(str).matches();    
     } 
	/**
	 * 判断是否为手机号
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNum(String mobiles)
	  {
	   Pattern p = Pattern
	     .compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
	   Matcher m = p.matcher(mobiles); 
	   return m.matches();
	  }

}  

