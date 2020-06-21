package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1363655717 on 2017-07-15.
 * 单位资料
 */

public class UnitView extends BaseView{
    private TextView            khbhTextView, khmcTextView, khdjTextView, khlxTextView,
            hylxTextView, khlyTextView, dqTextView, frdbTextView, czTextView, dzTextView,
            wzTextView, bzTextView;
    private RelativeLayout      lxfsRelativeLayout;
    private TextView            bjdwTextView, xzdwTextView;
    private LinearLayout        bjdwLayout;
    public UnitView(Activity activity) {
        super(activity);
    }
    public void Visibility(){
        bjdwLayout.setVisibility(View.GONE);
    }
    @Override
    protected void initViews() {
        view=  View.inflate(activity, R.layout.activity_gzpt_dwzl_dw, null);
        khbhTextView = (TextView) view.findViewById(R.id.khbh_textview);
        khmcTextView = (TextView) view.findViewById(R.id.khmc_textview);
        khdjTextView = (TextView) view.findViewById(R.id.khdj_textview);
        khlxTextView = (TextView) view.findViewById(R.id.khlx_textview);
        hylxTextView = (TextView) view.findViewById(R.id.hylx_textview);
        khlyTextView = (TextView) view.findViewById(R.id.khly_textview);
        dqTextView = (TextView) view.findViewById(R.id.dq_textview);
        frdbTextView = (TextView) view.findViewById(R.id.frdb_textview);
        czTextView = (TextView) view.findViewById(R.id.cz_textview);
        dzTextView = (TextView) view.findViewById(R.id.dz_textview);
        wzTextView = (TextView) view.findViewById(R.id.wz_textview);
        bzTextView = (TextView) view.findViewById(R.id.bz_textview);

        lxfsRelativeLayout = (RelativeLayout) view.findViewById(R.id.lxfs_relativelayout);
        lxfsRelativeLayout.setOnClickListener(activity);
        bjdwTextView = (TextView) view.findViewById(R.id.bjdw_textview);
        bjdwTextView.setOnClickListener(activity);
        xzdwTextView = (TextView) view.findViewById(R.id.xzdw_textview);
        xzdwTextView.setOnClickListener(activity);
        bjdwLayout = (LinearLayout) view.findViewById(R.id.bjdw_layout);
    }

    @Override
    public void initData() {
        searchDateDw(0);
        isFirst=true;
    }

    @Override
    public  void setData(List<Map<String, Object>> list) {
        if(list!=null) {
            khbhTextView.setText(list.get(0).get("code").toString());
            khmcTextView.setText(list.get(0).get("cname").toString());
            khdjTextView.setText(list.get(0).get("typename").toString());
            khlxTextView.setText(list.get(0).get("khtypename").toString());
            hylxTextView.setText(list.get(0).get("hylxname").toString());
            khlyTextView.setText(list.get(0).get("khlyname").toString());
            dqTextView.setText(list.get(0).get("areafullname").toString());
            frdbTextView.setText(list.get(0).get("frdb").toString());
            czTextView.setText(list.get(0).get("fax").toString());
            dzTextView.setText(list.get(0).get("address").toString());
            wzTextView.setText(list.get(0).get("cnet").toString());
            bzTextView.setText(list.get(0).get("memo").toString());
        }
    }
    /**
     * 连接网络的操作(单位)
     */
    public void searchDateDw(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(activity));
        parmMap.put("clientid", activity.clientId);
        activity.findServiceData(type, ServerURL.CLIENTINFO, parmMap);
    }
}
