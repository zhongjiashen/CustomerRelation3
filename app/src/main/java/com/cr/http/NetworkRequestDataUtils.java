package com.cr.http;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.cr.tools.ShareUserInfo;

import java.util.Map;

public class NetworkRequestDataUtils {


    public static Map<String, Object> getBillMaster(Context context, String parms, String billid) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("dbname", ShareUserInfo.getDbName(context));
        map.put("parms", parms);
        map.put("billid", billid);
        return map;
    }
}
