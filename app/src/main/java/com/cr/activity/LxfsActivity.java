package com.cr.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.gzpt.dwzl.GzptDwzlActivity;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 联系方式
 */
public class LxfsActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_contacts_way)
    TextView tvContactsWay;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.bt_bottom)
    Button btBottom;


    private String mClientid;//客户ID
    private String mTypes;//单位类型id
    private String mTypesname;// 单位类型
    private String mClientname;// 单位名称
    private String mLxrid;//联系人ID
    private String mLxfsId;//联系方式id
    private String mTel;//电话号码
    private Map<String, Object> mParmMap = new HashMap<>();

    private boolean isSave=false;//是否已经保存过

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mTel = getIntent().getStringExtra("tel");
        presenter = new BaseP(this, this);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_lxfs;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        if (mTel.length() == 11) {
            tvContactsWay.setText("手机");
            mLxfsId = "2";
        } else {
            tvContactsWay.setText("固定电话");
            mLxfsId = "1";
        }
        etPhone.setText(mTel);
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "联系方式");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                save();
            }
        });
    }

    @OnClick({R.id.ll_unit_name_choice, R.id.ll_contacts_choice, R.id.ll_contacts_way_choice, R.id.bt_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_name_choice:
                startActivityForResult(new Intent(this, CommonXzdwActivity.class)
                        .putExtra("type", "1"), 11);
                break;
            case R.id.ll_contacts_choice:
                if (TextUtils.isEmpty(mClientid))
                    showShortToast("请先选择单位");
                else
                    startActivityForResult(new Intent(this, CommonXzlxrActivity.class).putExtra("clientid", mClientid), 12);
                break;
            case R.id.ll_contacts_way_choice:
                startActivityForResult(new Intent(mActivity, CommonXzzdActivity.class)
                        .putExtra("type", "LXFS"), 13);
                break;
            case R.id.bt_bottom:
                /**
                 * 判断单据是否已经保存，保存过之后才能点击业务录入按钮
                 */
                if(isSave) {
                    Map map = new HashMap();
                    map.put("clientid", mClientid);
                    map.put("types", mTypes);
                    startActivity(new Intent(this, GzptDwzlActivity.class).putExtra("object", (Serializable) map));
                }else {
                    showShortToast("请先保存数据");
                }
                break;
        }
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        switch (requestCode) {
            case 11://单位选择结果处理
                mClientid = data.getStringExtra("id");
                mClientname = data.getStringExtra("name");
                mTypes = data.getStringExtra("types");
                mTypesname = data.getStringExtra("typesname");
                mLxrid= data.getStringExtra("lxrid");
                tvUnitName.setText(mClientname);
                tvContacts.setText(data.getStringExtra("lxrname"));
//                etContactNumber.setText(data.getStringExtra("phone"));
//                etCustomerAddress.setText(data.getStringExtra("shipto"));
                break;
            case 12://联系人选择结果处理
                mLxrid = data.getStringExtra("id");
                tvContacts.setText(data.getStringExtra("name"));
//                etContactNumber.setText(data.getStringExtra("phone"));
                break;
            case 13:
                tvContactsWay.setText(data.getStringExtra("dictmc"));
                mLxfsId = data.getStringExtra("id");
                break;
        }
    }

    private void save() {
        if (TextUtils.isEmpty(mClientid)) {
            showShortToast("请选择单位");
            return;
        }
        String phone=etPhone.getText().toString();
        if(TextUtils.isEmpty(phone)) {
            showShortToast("电话号码不能为空");
            return;
        }
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("id", "0");
        mParmMap.put("clientid", mClientid);
        mParmMap.put("lxrid", mLxrid);
        mParmMap.put("lb", mLxfsId);
        mParmMap.put("itemno", phone);
        presenter.post(0, "lxfssave", mParmMap);
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        isSave=true;
        titlebar.setRightText("");
        showShortToast("保存成功!");
    }
}
