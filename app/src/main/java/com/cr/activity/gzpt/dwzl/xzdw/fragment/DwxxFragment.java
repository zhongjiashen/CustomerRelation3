package com.cr.activity.gzpt.dwzl.xzdw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cr.activity.common.CommonXzzdActivity;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.base.BaseFragment;
import com.update.dialog.AreaSelectionDialog;
import com.update.dialog.OnDialogClickInterface;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 单位信息
 */
public class DwxxFragment extends BaseFragment {
    @BindView(R.id.khbh_edittext)
    EditText khbhEdittext;
    @BindView(R.id.khlb_edittext)
    EditText khlbEdittext;
    @BindView(R.id.khmc_edittext)
    EditText khmcEdittext;
    @BindView(R.id.khdj_edittext)
    EditText khdjEdittext;
    @BindView(R.id.khlx_edittext)
    EditText khlxEdittext;
    @BindView(R.id.hylx_edittext)
    EditText hylxEdittext;
    @BindView(R.id.khly_edittext)
    EditText khlyEdittext;
    @BindView(R.id.dq_edittext)
    EditText dqEdittext;
    @BindView(R.id.frdb_edittext)
    EditText frdbEdittext;
    @BindView(R.id.cz_edittext)
    EditText czEdittext;
    @BindView(R.id.dz_edittext)
    EditText dzEdittext;
    @BindView(R.id.wz_edittext)
    EditText wzEdittext;
    @BindView(R.id.bz_edittext)
    EditText bzEdittext;
    @BindView(R.id.khbh_linearlayout)
    LinearLayout khbhLinearlayout;
    @BindView(R.id.khbh_view)
    View khbhView;


    private String khdjId = "", khlxId = "", hylxId = "", khlyId = "",
            khlbId = "", clientId = "", tel = "";


    private AreaSelectionDialog mAreaSelectionDialog;

    @Override
    protected int getLayout() {
        return R.layout.fragment_dwxx;
    }

    @Override
    protected void init() {

        clientId = "0";
//        if (clientId.equals("0")) {//新增单位隐藏客户编号
        khbhLinearlayout.setVisibility(View.GONE);
        khbhView.setVisibility(View.GONE);
//        } else {
//            khbhLinearlayout.setVisibility(View.VISIBLE);
//            khbhView.setVisibility(View.VISIBLE);
//        }
        if (getActivity().getIntent().hasExtra("khlbid")) {
            khlbId = getActivity().getIntent().getExtras().getString("khlbid");
            switch (khlbId) {
                case "1":
                    khlbEdittext.setText("客户");
                    break;
                case "2":
                    khlbEdittext.setText("供应商");
                    break;
                case "3":
                    khlbEdittext.setText("竞争对手");
                    break;
                case "4":
                    khlbEdittext.setText("渠道");
                    break;
                case "5":
                    khlbEdittext.setText("员工");
                    break;
                case "6":
                    khlbEdittext.setText("物流");
                    break;
            }

        }

    }


    @OnClick({R.id.khlb_edittext, R.id.khdj_edittext, R.id.khlx_edittext, R.id.hylx_edittext, R.id.khly_edittext, R.id.dq_edittext})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.khlb_edittext://客户类别
                intent.setClass(mActivity, CommonXzzdActivity.class);
                intent.putExtra("type", "KHLB");
                intent.putExtra("isDwlb", true);
                startActivityForResult(intent, 1);
                break;
            case R.id.khdj_edittext://客户等级
                intent.setClass(mActivity, CommonXzzdActivity.class);
                switch (khlbId) {
                    case "1"://客户
                    case "4"://渠道
                        intent.putExtra("type", "KHDJ");
                        break;
                    case "2"://供应商
                        intent.putExtra("type", "GYSDJ");
                        break;
                    case "3"://竞争对手
                        intent.putExtra("type", "DSDJ");
                        break;

                    case "5"://员工

                        break;
                    case "6"://物流
                        intent.putExtra("type", "WLDJ");
                        break;
                }

                startActivityForResult(intent, 2);
                break;
            case R.id.khlx_edittext://客户类型
                intent.setClass(mActivity, CommonXzzdActivity.class);
                intent.putExtra("type", "KHLX");
                startActivityForResult(intent, 3);
                break;
            case R.id.hylx_edittext://行业类型
                intent.setClass(mActivity, CommonXzzdActivity.class);
                intent.putExtra("type", "HYLX");
                startActivityForResult(intent, 4);
                break;
            case R.id.khly_edittext://客户来源
                intent.setClass(mActivity, CommonXzzdActivity.class);
                intent.putExtra("type", "KHLY");
                startActivityForResult(intent, 5);
                break;
            case R.id.dq_edittext://地区
                mAreaSelectionDialog = new AreaSelectionDialog(mActivity, AreaSelectionDialog.Type.ALL, new OnDialogClickInterface() {
                    @Override
                    public void OnClick(int requestCode, Object object) {
                        Map<String, String> map = (Map<String, String>) object;
                        dqEdittext.setText(map.get("province") + "/" + map.get("city") + "/" + map.get("area"));
                    }
                });
                mAreaSelectionDialog.showDialog();
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                khlbId = data.getExtras().getString("id");
                khlbEdittext.setText(data.getExtras().getString("dictmc"));
                break;
            case 2:
                khdjId = data.getExtras().getString("id");
                khdjEdittext.setText(data.getExtras().getString("dictmc"));
                break;
            case 3:
                khlxId = data.getExtras().getString("id");
                khlxEdittext.setText(data.getExtras().getString("dictmc"));
                break;
            case 4:
                hylxId = data.getExtras().getString("id");
                hylxEdittext.setText(data.getExtras().getString("dictmc"));
                break;
            case 5:
                khlyId = data.getExtras().getString("id");
                khlyEdittext.setText(data.getExtras().getString("dictmc"));
                break;
        }
    }


    public Map getData() {
        Map<String, Object> map = new ArrayMap<>();
        if (khmcEdittext.getText().toString().equals("")) {
            showShortToast("单位不能为空！");
            return map;
        } else if (khlbEdittext.getText().toString().equals("")) {
            showShortToast("请选择客户类别！");
            return map;
        } else if (khdjEdittext.getText().toString().equals("")) {
            showShortToast("请选择客户等级！");
            return map;
        }
        map.put("dbname", ShareUserInfo.getDbName(mActivity));
        map.put("opid", ShareUserInfo.getUserId(mActivity));
        map.put("clientid", clientId);
        map.put("types", khlbId);
        map.put("cname", khmcEdittext.getText().toString());
        map.put("areafullname", dqEdittext.getText().toString());
        map.put("areatypeid", "");
        map.put("typeid", khdjId);
        map.put("hytypeid", hylxId);
        map.put("csourceid", khlyId);
        map.put("khtypeid", khlxId);
        map.put("frdb", frdbEdittext.getText().toString());
        map.put("cnet", wzEdittext.getText().toString());
        map.put("fax", czEdittext.getText().toString());
        map.put("address", dzEdittext.getText().toString());
        return map;
    }

}