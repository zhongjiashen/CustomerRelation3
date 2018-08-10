package com.cr.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.cr.activity.common.CommonXzczyActivity;
import com.cr.model.UserLogin;
import com.cr.model.ZT;
import com.cr.service.SocThread;
import com.cr.service.SocketService;
import com.cr.tools.MyApplication;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private String               dbName;
    private EditText             bhEditText;
    private String               bhId;            //选择的编号的ID
    private EditText             mmEditText;
    private ImageButton          loginButton;
    private CheckBox             saveMsgCheckBox;
    private ArrayAdapter<String> adapter;
    private Spinner              ztSpinner;
    private List<ZT>             ztList;
    private UserLogin            userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initActivity();
        findServiceData3(0, ServerURL.ACCSET, null);// 查询帐套信息
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        ztSpinner = (Spinner) findViewById(R.id.zt_spinner);
        bhEditText = (EditText) findViewById(R.id.login_bh);
//        bhEditText.setText(ShareUserInfo.getKey(context, "bh"));
        bhId=ShareUserInfo.getKey(context, "bhId");
        bhEditText.setText( bhId);
        bhEditText.setOnClickListener(this);
        mmEditText = (EditText) findViewById(R.id.login_mm);
        mmEditText.setText(ShareUserInfo.getKey(context, "mm"));
        saveMsgCheckBox = (CheckBox) findViewById(R.id.login_savemsg);
        if (ShareUserInfo.getKey(context, "zt").equals("")) {
            saveMsgCheckBox.setChecked(false);
        } else {
            saveMsgCheckBox.setChecked(true);
        }
        saveMsgCheckBox.setOnClickListener(this);
        loginButton = (ImageButton) findViewById(R.id.login_login);
        loginButton.setOnClickListener(this);
        addFHMethod();
        ztSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                dbName = ztList.get(arg2).getDbName();
                if (!dbName.equals(ShareUserInfo.getDbName(context))) {
                    bhEditText.setText("");
                    mmEditText.setText("");
                    ShareUserInfo.setKey(context, "bh", "");
                    ShareUserInfo.setKey(context, "mm", "");
                    ShareUserInfo.setKey(context, "bhId", "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("LoginMax");
        // 注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    /**
     * 数据返回成功执行的方法
     * 
     * @see BaseActivity#executeSuccess()
     */
    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 0:
                int index = 0;
                LogUtils.e(returnJson);
                ztList = ZT.paseJsonToObject(returnJson);
                LogUtils.e(ShareUserInfo.getKey(context, "zt"));
                String[] ztString = new String[ztList.size()];
                for (int i = 0; i < ztList.size(); i++) {
                    ztString[i] = ztList.get(i).getName();
                    if (ztList.get(i).getName().equals(ShareUserInfo.getKey(context, "zt"))) {
                        index = i;
                    }
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    ztString);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ztSpinner.setAdapter(adapter);
                ztSpinner.setSelection(index);
                break;
            case 1:
                if (returnJson.substring(0, 5).equals("false")) {
                    showToastPromopt("编号或密码错误！");
                } else {

                    userLogin = UserLogin.parseJsonToObject(returnJson);
                    if (userLogin == null) {
                        showToastPromopt("编号或密码错误！");
                    } else {
                        ShareUserInfo.setUserId(context, userLogin.getId());
                        ShareUserInfo.setUserName(context, userLogin.getOpname());
                        ShareUserInfo.setKey(context, "bhId",  bhEditText.getText().toString());
                        ShareUserInfo.setKey(context, "zt", ztSpinner.getSelectedItem().toString());
                        ShareUserInfo.setDbName(context, dbName);
                        String s = "#loguserinfo#" + userLogin.getId() + ","
                                   + userLogin.getOpname() + ","
                                   + ztSpinner.getSelectedItem().toString() + "," + dbName + ",1";
                        //					Log.v("dddd", s);
                        SocketService.sendMsg(s);
                        Intent intent = new Intent(this, IndexActivity.class);
                        startActivity(intent);
                        // Intent intent2=new Intent(this,TxService.class);
                        // startService(intent2);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onExecuteFh() {
        // TODO Auto-generated method stub
        super.onExecuteFh();
        try {
            SocThread.client.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /* 返回键 */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            try {
                if (SocThread.client!=null){
                    SocThread.client.close();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void onExecuteFailed() {
        new AlertDialog.Builder(LoginActivity.this).setTitle("提示信息！")
            .setMessage("IP地址或端口设置错误，请返回后重新设置！")
            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            }).show();
    }

    /**
     * 监听事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_login:
                if (validDataMsg()) {
                    if (saveMsgCheckBox.isChecked()) {
                        ShareUserInfo.setKey(context, "bh", bhEditText.getText().toString());
                        ShareUserInfo.setKey(context, "mm", mmEditText.getText().toString());
                        ShareUserInfo.setKey(context, "zt", "1");
                    } else {
                        ShareUserInfo.setKey(context, "bh", "");
                        ShareUserInfo.setKey(context, "mm", "");
                        ShareUserInfo.setKey(context, "zt", "");
                    }
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("dbname", dbName);
                   /* params.put("usercode", bhId);*/
                    params.put("usercode", bhEditText.getText().toString());
                    params.put("pwd", mmEditText.getText().toString());
                    findServiceData3(1, "login", params);
                }
                break;
            case R.id.login_savemsg:

                break;
            case R.id.login_bh:
//                Intent intent = new Intent();
//                intent.putExtra("dbname", dbName);
//                intent.setClass(activity, CommonXzczyActivity.class);
//                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 验证用户输入的信息
     */
    private boolean validDataMsg() {
        if (dbName == null || dbName.equals("")) {
            showToastPromopt("请选择帐套信息！");
            return false;
        } else if (bhEditText.getText().toString().equals("")) {
           /* showToastPromopt("请选择编号信息！");*/
            showToastPromopt("请输入择编号信息！");
            return false;
        }
        // else if(mmEditText.getText().toString().equals("")){
        // showToastPromopt("请输入密码信息！");
        // return false;
        // }
        return true;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
                                                     @Override
                                                     public void onReceive(Context context,
                                                                           Intent intent) {
                                                         String action = intent.getAction();
                                                         if (action.equals("LoginMax")) {
                                                             AlertDialog.Builder builder = new AlertDialog.Builder(
                                                                 context);
                                                             builder.setTitle("提示信息");
                                                             builder.setMessage("与服务器断开连接，您将被迫推出！");
                                                             builder
                                                                 .setPositiveButton(
                                                                     "确定退出",
                                                                     new DialogInterface.OnClickListener() {
                                                                         public void onClick(DialogInterface dialog,
                                                                                             int whichButton) {
                                                                             //								Intent intent2=new
                                                                             //										 Intent(LoginActivity.this,MainActivity.class);
                                                                             //										 startActivity(intent2);
                                                                             MyApplication
                                                                                 .getInstance()
                                                                                 .exit();
                                                                         }
                                                                     });
                                                             builder.show();
                                                         }
                                                     }

                                                 };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                bhEditText.setText(data.getExtras().getString("name"));
                bhId=data.getExtras().getString("id");
                ShareUserInfo.setKey(context, "empid", data.getExtras().getString("empid"));//设置empid，报表的时候使用
                ShareUserInfo.setKey(context, "empname", data.getExtras().getString("name"));//设置empid，报表的时候使用
                ShareUserInfo.setKey(context, "opname", data.getExtras().getString("opname"));//设置empid，报表的时候使用
                ShareUserInfo.setKey(context, "departmentid", data.getExtras().getString("departmentid"));//设置departmentid，安装登记、维修登记单据的部门业务员没有带过来
                ShareUserInfo.setKey(context, "depname", data.getExtras().getString("depname"));//设置empid，报表的时候使用

            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}