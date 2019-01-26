package com.cr.activity.jxc.ckgl.kcpd;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.cr.activity.jxc.JxcTjXlhActivity;
import com.cr.activity.jxc.JxcXzphActivity;
import com.cr.myinterface.SLViewValueChange;
import com.cr.tools.DateUtil;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.dialog.DateSelectDialog;
import com.update.dialog.OnDialogClickInterface;
import com.update.model.Serial;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品修改
 */
public class KcpdSpbjActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_spmc)
    TextView tvSpmc;
    @BindView(R.id.tv_spbm)
    TextView tvSpbm;
    @BindView(R.id.tv_spgg)
    TextView tvSpgg;
    @BindView(R.id.tv_spxh)
    TextView tvSpxh;
    @BindView(R.id.tv_spkz)
    TextView tvSpkz;
    @BindView(R.id.tv_sl)
    TextView tvSl;
    @BindView(R.id.slv_sl)
    SLView2 slvSl;
    @BindView(R.id.et_cpph)
    EditText etCpph;
    @BindView(R.id.et_scrq)
    EditText etScrq;
    @BindView(R.id.et_yxqz)
    EditText etYxqz;
    @BindView(R.id.ll_pcsp)
    LinearLayout llPcsp;


    @BindView(R.id.et_bz)
    EditText etBz;
    @BindView(R.id.tv_serial_number)
    TextView tvSerialNumber;
    @BindView(R.id.et_cbj)
    EditText etCbj;
    @BindView(R.id.ll_cbj)
    LinearLayout llCbj;
    @BindView(R.id.tv_zmsl)
    TextView tvZmsl;
    @BindView(R.id.ll_cpph)
    LinearLayout llCpph;
    @BindView(R.id.ll_scrq)
    LinearLayout llScrq;
    @BindView(R.id.ll_yxqz)
    LinearLayout llYxqz;
    @BindView(R.id.bt_sc)
    Button btSc;


    private Map<String, Object> mMap;


    private String mStoreid;

    @Override
    protected void initVariables() {
        mMap = (Map<String, Object>) getIntent().getSerializableExtra("data");

        mStoreid = getIntent().getStringExtra("storeid");

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_kcpd_spbj;
    }

    @Override
    protected void init() {
        setTitlebar();
        tvSpmc.setText("名称：" + mMap.get("name").toString());
        tvSpbm.setText("编码：" + mMap.get("code").toString());
        tvSpgg.setText("规格：" + mMap.get("specs").toString());
        tvSpxh.setText("编码：" + mMap.get("model").toString());
        if (mMap.get("onhand") == null || TextUtils.isEmpty(mMap.get("onhand").toString())) {
            tvSpkz.setVisibility(View.GONE);
        } else {
            tvSpkz.setVisibility(View.VISIBLE);
            tvSpkz.setText("库存：" + mMap.get("onhand").toString() + mMap.get("unitname").toString());
        }
        tvZmsl.setText(mMap.get("zmsl").toString());
        //严格序列号商品处理
        if (mMap.get("serialctrl").toString().equals("T")) {
            LogUtils.e("严格序列商品");
            slvSl.setVisibility(View.GONE);
            tvSl.setVisibility(View.VISIBLE);
            tvSl.setText(mMap.get("unitqty").toString());
        } else {
            slvSl.setVisibility(View.VISIBLE);
            tvSl.setVisibility(View.GONE);
            slvSl.setSl(Double.parseDouble(mMap.get("spsl").toString()));
        }

        slvSl.setOnValueChange(new SLViewValueChange() {
            @Override
            public void onValueChange(double sl) {
                mMap.put("spsl", sl + "");
            }
        });

        if (mMap.get("memo") == null || TextUtils.isEmpty(mMap.get("memo").toString())) {
            etBz.setText("");
        } else {
            etBz.setText(mMap.get("memo").toString());
        }

        //是批次商品的会显示批号、生产日期、有效日期
        if (mMap.get("batchctrl").toString().equals("T")) {
            llPcsp.setVisibility(View.VISIBLE);
            etCpph.setText(mMap.get("batchcode").toString());
            etScrq.setText(mMap.get("produceddate").toString());
            etYxqz.setText(mMap.get("validdate").toString());
            if (TextUtils.isEmpty(mMap.get("cbj").toString())) {
                llCbj.setVisibility(View.GONE);
            } else {
                llCbj.setVisibility(View.VISIBLE);
                etCbj.setText(mMap.get("cbj").toString());
            }


        } else {
            llPcsp.setVisibility(View.GONE);
        }


    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "商品详情");
        titlebar.setRightText("完成");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {

                //严格序列号商品处理
                if (mMap.get("serialctrl").toString().equals("T")) {
                    ArrayList<Serial> serials = (ArrayList<Serial>) mMap.get("serials");
                    if (serials == null || serials.size() == 0) {
                        showShortToast("请选择序列号");
                        return;
                    }
                }
                mMap.put("memo", etBz.getText().toString());
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra("data", (Serializable) mMap));
                finish();
            }
        });
    }

    @OnClick({R.id.tv_serial_number, R.id.ll_cpph, R.id.ll_scrq, R.id.ll_yxqz,/* R.id.xzjg_iv,*/ R.id.bt_sc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_serial_number:
                //严格序列号商品处理
                Intent intent = new Intent();
                intent.putExtra("position", 0);
                intent.putExtra("billid", "0");
                intent.putExtra("serialinfo", mMap.get("serialinfo").toString());
                intent.putExtra("serials", mPGson.toJson(mMap.get("serials")));
                intent.putExtra("goodsid", mMap.get("goodsid").toString());
                intent.putExtra("storeid", mStoreid);
                if (mMap.get("serialctrl").toString().equals("T")) {
                    LogUtils.e("严格序列商品");
                    startActivityForResult(intent.setClass(mActivity, KcpdXzXlhActivity.class)
                            .putExtra("parms","KCPD")
                            .putExtra("refertype", "0")
                            .putExtra("referitemno", "0"), 1);
                } else {
                    startActivityForResult(intent.setClass(mActivity, JxcTjXlhActivity.class), 1);
                }

                break;
            case R.id.ll_cpph:
                Intent intent1 = new Intent();
                intent1.putExtra("iscbj", (int) mMap.get("inf_costingtypeid") == 2);
                intent1.putExtra("isxz", true);
                intent1.setClass(mActivity, JxcXzphActivity/*CommonXzphActivity*/.class);
                intent1.putExtra("goodsid", mMap.get("goodsid").toString());
                intent1.putExtra("storied", mStoreid);
                intent1.putExtra("position", 0);
                startActivityForResult(intent1, 2);
                break;
            case R.id.ll_scrq:
                new DateSelectDialog(mActivity, 0, new OnDialogClickInterface() {
                    @Override
                    public void OnClick(int requestCode, Object object) {
                        mMap.put("produceddate", (String) object);
                        etScrq.setText(mMap.get("produceddate").toString());
                    }
                }).show();
                break;
            case R.id.ll_yxqz:
                if (TextUtils.isEmpty(mMap.get("produceddate").toString())) {
                    showShortToast("请先选择生产日期");
                    return;
                }
                new DateSelectDialog(mActivity, DateUtil.StringTolongDate(mMap.get("produceddate").toString(), "yyyy-MM-dd"), new OnDialogClickInterface() {
                    @Override
                    public void OnClick(int requestCode, Object object) {
                        mMap.put("validdate", (String) object);
                        etYxqz.setText(mMap.get("validdate").toString());
                    }
                }).show();
                break;
//            case R.id.xzjg_iv:
//                Intent intent2 = new Intent();
//                intent2.setClass(mActivity, TjfxKcbbSpjg2Activity.class);
//                intent2.putExtra("goodsid", mMap.get("goodsid").toString());
//                intent2.putExtra("storied", mStoreid);
//                intent2.putExtra("unitid", mMap.get("unitid").toString());
//                intent2.putExtra("clientid", "0");
//                intent2.putExtra("index", "0");
//                startActivityForResult(intent2, 3);
//                break;
            case R.id.bt_sc:
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra("data", ""));
                finish();
                break;
        }

    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                List<Serial> serials = new Gson().fromJson(data.getExtras().getString("data"), new TypeToken<List<Serial>>() {
                }.getType());
                mMap.put("serials", serials);
                //严格序列号商品处理
                if (mMap.get("serialctrl").toString().equals("T")) {
                    mMap.put("unitqty", serials.size() + ".0");
                    tvSl.setText(mMap.get("unitqty").toString());
                }

                break;
            case 2:
                mMap.put("batchrefid", data.getStringExtra("id"));//产品批号id
                mMap.put("batchcode", data.getStringExtra("name"));
                etCpph.setText(mMap.get("batchcode").toString());
                mMap.put("produceddate", data.getStringExtra("scrq"));
                etScrq.setText(mMap.get("produceddate").toString());
                mMap.put("validdate", data.getStringExtra("yxrq"));
                etYxqz.setText(mMap.get("validdate").toString());
                mMap.put("cbj", data.getStringExtra("cbj"));
                if (TextUtils.isEmpty(mMap.get("cbj").toString())) {
                    llCbj.setVisibility(View.GONE);
                } else {
                    llCbj.setVisibility(View.VISIBLE);
                    etCbj.setText(mMap.get("cbj").toString());
                }
                break;
            case 3:
                mMap.put("unitprice", data.getStringExtra("dj"));

                break;

        }
    }


}
