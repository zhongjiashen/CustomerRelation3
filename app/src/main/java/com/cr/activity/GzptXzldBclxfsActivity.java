package com.cr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.gzpt.dwzl.GzptDwzlActivity;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 新增来电-保存联系方式
 *
 * @author Administrator
 */
public class GzptXzldBclxfsActivity extends BaseActivity implements
        OnClickListener {
    EditText dwmcEditText;
    EditText lxfsEditText;
    EditText dhhmEditText;
    ImageButton saveButton;
    private String dwmcId = "";
    private String lxfsId = "";
    private String dhhm = "";
    private String mTypes;//单位类型id
    private boolean isSave = false;//是否已经保存过
    /**
     * 业务录入
     */
    private Button mBtBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_xzld_bclxfs);// 设置成自己的页面文件
        initView();
        initActivity();// 该方法用于初始化Activity
        addFHMethod();
    }

    private void initView() {
        dwmcEditText = (EditText) findViewById(R.id.dwmc);
        dwmcEditText.setOnClickListener(this);
        lxfsEditText = (EditText) findViewById(R.id.lxfs);
        lxfsEditText.setOnClickListener(this);
        dhhmEditText = (EditText) findViewById(R.id.dhhm);
        mBtBottom = (Button) findViewById(R.id.bt_bottom);
        mBtBottom.setOnClickListener(this);
        saveButton = (ImageButton) findViewById(R.id.save);
        saveButton.setOnClickListener(this);
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        dhhm = this.getIntent().getExtras().getString("dhhm");
        dhhmEditText.setText(dhhm);
        if (dhhm.length() == 11) {
            lxfsEditText.setText("手机");
            lxfsId = "2";
        } else {
            lxfsEditText.setText("固定电话");
            lxfsId = "1";
        }


        // findServiceData(xzrqEditText.getText().toString());//
        // 开启子线程获取特定接口的服务器数据用于更新页面
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.save:
                if (dwmcEditText.getText().toString().equals("")) {
                    showToastPromopt("单位名称不能为空！");
                    return;
                }
                findServiceData2();
                break;
            case R.id.dwmc:
                intent.setClass(activity, CommonXzdwActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.lxfs:
                intent.putExtra("type", "LXFS");
                intent.setClass(activity, CommonXzzdActivity.class);
                startActivityForResult(intent, 1);
                break;

            /**
             * 业务录入按钮点击事件
             */
            case R.id.bt_bottom:
                /**
                 * 判断单据是否已经保存，保存过之后才能点击业务录入按钮
                 */
                if (isSave) {
                    Map map = new HashMap();
                    map.put("clientid", dwmcId);
                    map.put("types", mTypes);
                    startActivity(new Intent(this, GzptDwzlActivity.class).putExtra("object", (Serializable) map));
                } else {
                    showToastPromopt("请先保存单据");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                dwmcEditText.setText(data.getExtras().getString("name"));
                dwmcId = data.getExtras().getString("id");
                mTypes = data.getStringExtra("types");
            } else if (requestCode == 1) {
                lxfsEditText.setText(data.getExtras().getString("dictmc"));
                lxfsId = data.getExtras().getString("id");
            }
        }
    }

    // 调用网络查询出特定接口的数据
    private void findServiceData2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("id", "0");
        parmMap.put("clientid", dwmcId);
        parmMap.put("cname", dwmcEditText.getText().toString());
        parmMap.put("lxrid", "0");
        parmMap.put("lb", lxfsId);
        parmMap.put("itemno", dhhmEditText.getText().toString());
        findServiceData(0, ServerURL.LXFSSAVE, parmMap);
    }

    @Override
    public void executeSuccess() {
        // Log.v("dddd", returnSuccessType+"");
        switch (returnSuccessType) {
            case 0:
                if (returnJson.equals("")) {
                    showToastPromopt("保存成功！");
                    isSave = true;
//                    finish();
                } else {
                    showToastPromopt("保存失败；" + returnJson.substring(5));
                }
                break;
            default:
                break;
        }
    }


}