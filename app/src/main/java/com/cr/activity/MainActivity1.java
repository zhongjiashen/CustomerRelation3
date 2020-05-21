package com.cr.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cr.dao.ClientIPDao;
import com.cr.data.BaseResponseData;
import com.cr.model.ClientIP;
import com.cr.tools.ServerRequest;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.permission.PermissionsActivity;
import com.permission.PermissionsChecker;
import com.update.utils.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 起始页
 */
public class MainActivity1 extends BaseActivity implements OnClickListener {

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_CODE = 0; // 请求码
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE
    };

    private boolean isPublic;


    private TextView mTvLjfs;
    private Spinner mSpinnerLjfs;
    private EditText mEtIp;
    private Spinner mSpinnerIp;
    private LinearLayout mLlIp;
    private EditText mEtName;
    private LinearLayout mLlName;
    private LinearLayout mLlDk;
    private EditText mEtDk;
    private ImageButton mIbtLj;
    private ImageButton mIbtTc;

    private ArrayAdapter<String> ljfsAdapter;
    private List<ClientIP> ipList;
    private ArrayAdapter<String> ipAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mPermissionsChecker = new PermissionsChecker(this);
        initView();
        init();
    }

    private void initView() {
        mTvLjfs = (TextView) findViewById(R.id.tv_ljfs);
        mSpinnerLjfs = (Spinner) findViewById(R.id.spinner_ljfs);
//        mSpinnerLjfs.setOnClickListener(this);
        mEtIp = (EditText) findViewById(R.id.et_ip);
        mSpinnerIp = (Spinner) findViewById(R.id.spinner_ip);
//        mSpinnerIp.setOnClickListener(this);
        mLlIp = (LinearLayout) findViewById(R.id.ll_ip);
        mEtName = (EditText) findViewById(R.id.et_name);
        mLlName = (LinearLayout) findViewById(R.id.ll_name);
        mEtDk = (EditText) findViewById(R.id.et_dk);
        mIbtLj = (ImageButton) findViewById(R.id.ibt_lj);
        mIbtLj.setOnClickListener(this);
        mIbtTc = (ImageButton) findViewById(R.id.ibt_tc);
        mIbtTc.setOnClickListener(this);
        mLlDk = (LinearLayout) findViewById(R.id.ll_dk);
    }

    private void init() {
        ljfsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"私有云", "公有云"});
        ljfsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerLjfs.setAdapter(ljfsAdapter);
        mSpinnerLjfs.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mTvLjfs.setText("私有云");
                        isPublic = false;
                        mLlName.setVisibility(View.GONE);
                        mLlIp.setVisibility(View.VISIBLE);
                        mLlDk.setVisibility(View.VISIBLE);
                        if (ipList == null) {
                            searchDBFroIp();
                        }
                        break;
                    case 1:
                        mTvLjfs.setText("公有云");
                        isPublic = true;
                        mLlName.setVisibility(View.VISIBLE);
                        mLlIp.setVisibility(View.GONE);
                        mLlDk.setVisibility(View.GONE);
                        mEtName.setText(ShareUserInfo.getKey(mContext, "usercode"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (ShareUserInfo.getKey(mContext, "isPublic").equals("true")) {
            mSpinnerLjfs.setSelection(1);
        } else {
            mSpinnerLjfs.setSelection(0);
        }

    }

    /**
     * 查询数据库中的IP信息
     */
    private void searchDBFroIp() {

        String msg = ShareUserInfo.getIP(mContext).replace("http://", "") + ":"
                + ShareUserInfo.getDK(mContext);
        int index = 0;
        ClientIPDao clientIPDao = new ClientIPDao(mContext);
        ipList = clientIPDao.findAllIP();
        if (ipList.size() == 0) {
            for (int j = 0; j < 2; j++) {
                ClientIP ip = new ClientIP();
                ip.setDk("8031");
                if (j == 0) {
                    ip.setIp("hengshicrm.eicp.net");
                } else if (j == 1) {
                    ip.setIp("hengvideocrm.oicp.net");
                }
                ClientIPDao cipdao = new ClientIPDao(mContext);
                cipdao.add(ip);
                ipList.add(ip);
            }
        }
        String[] bffsString = new String[ipList.size()];
        for (int i = 0; i < ipList.size(); i++) {
            bffsString[i] = ipList.get(i).getIp() + ":" + ipList.get(i).getDk();
            if (bffsString[i].equals(msg)) {
                index = i;
            }
        }
        ipAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bffsString);
        ipAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerIp.setAdapter(ipAdapter);
        mSpinnerIp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                mEtIp.setText(ipList.get(arg2).getIp());
                mEtDk.setText(ipList.get(arg2).getDk());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        mSpinnerIp.setSelection(index);
    }


    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }


    /**
     * 监听事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.ibt_lj:
                Map map = new HashMap();
                map.put("imei", getImei());
                ShareUserInfo.setKey(mContext, "isPublic", isPublic + "");
                if (isPublic) {
                    String name = mEtName.getText().toString();
                    if (TextUtils.isEmpty(name)) {
                        showToastPromopt("用户名不能为空！");
                        return;
                    }
                    ShareUserInfo.setKey(mContext, "usercode", name);
                    map.put("usercode", name);
                    map.put("vertype", "crmerp");
                    String url = "http://139.155.112.251:8088/Client/GetMobileClientRegInfo";
                    httpPost(url, map);
                } else {
                    String ip = mEtIp.getText().toString();
                    String dk = mEtDk.getText().toString();
                    if (TextUtils.isEmpty(ip)) {
                        showToastPromopt("IP地址不能为空！");
                        return;
                    }
                    if (TextUtils.isEmpty(dk)) {
                        showToastPromopt("端口号不能为空！");
                        return;
                    }
                    saveDbData(ip, dk);
                    ShareUserInfo.setDK(mContext, dk);
                    ShareUserInfo.setIP(mContext, "http://" + ip);
                    map.put("pass", "030728");
                    findServiceData3(0, "getmboileclientreginfo", map);// 查询帐套信息
                }
                break;
            case R.id.ibt_tc:
                finish();
                break;
        }
    }

    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 0:
                if (!returnJson.substring(0, 4).equals("fals")) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    showToastPromopt("产品还未注册");
                }
                LogUtils.e(returnJson.substring(0, 4));

                break;
        }
        // TODO Auto-generated method stub
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("LoginSuccess")) {
                progressDialog.dismiss();
            }
        }
    };


    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    public String getImei() {
        TelephonyManager tm = (TelephonyManager) this.mContext
                .getSystemService(this.mContext.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String Imei = tm.getDeviceId();
        return Imei;
    }

    /**
     * 调用网络查询出特定接口的数据
     */
    private void saveDbData(final String ip, final String dk) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    ClientIPDao cipdao = new ClientIPDao(mContext);
                    ClientIP clientIP = new ClientIP();
                    clientIP.setIp(ip);
                    clientIP.setDk(dk);
                    cipdao.add(clientIP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void httpPost(final String baseUrl, final Map<String, String> params) {
        new Thread(new Runnable() {
            public void run() {
                String result = ServerRequest.requestPost(baseUrl, params);
                final BaseResponseData data = new Gson().fromJson(result,
                        new TypeToken<BaseResponseData>() {
                        }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data.getStatus().equals("yes")) {
                            ShareUserInfo.setIP(mContext, "http://" + data.getIpaddress());
                            ShareUserInfo.setDK(mContext, "8031");
                            Intent intent = new Intent(activity, LoginActivity.class);
                            intent.putExtra("isPublic", true);
                            intent.putExtra("webuserid", data.getWebuserid());
                            intent.putExtra("verregids", data.getVerregids());
                            startActivity(intent);
                        } else {
                            activity.showToastPromopt(data.getMsg());
                        }

                    }
                });
                LogUtils.e(result);
            }
        }).start();
    }
}