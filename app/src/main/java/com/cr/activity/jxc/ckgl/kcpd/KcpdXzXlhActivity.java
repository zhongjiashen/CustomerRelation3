package com.cr.activity.jxc.ckgl.kcpd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cr.activity.jxc.KtKcpdXzXlhData;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.dialog.DialogFactory;
import com.update.dialog.OnDialogClickInterface;
import com.update.model.Serial;
import com.update.viewbar.TitleBar;
import com.update.viewholder.KcpdXzXlhHolder;
import com.update.viewholder.XzXlhHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择序列号
 */
public class KcpdXzXlhActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.bt_view)
    Button btView;


    private Map<String, Object> mParmMap;
    List<KtKcpdXzXlhData> list = new ArrayList<KtKcpdXzXlhData>();
    List<Serial> mSerials;

    private int mPosition;
    private String mBillid;//主单id
    private String mSerialinfo;//序列号

    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("pagesize", "99999");
        mParmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        mParmMap.put("parms", getIntent().getStringExtra("parms"));//类型（CGTH =采购退货,XSKD=销售开单,KCPD=库存盘点,CKDB=仓库调拨）
        mParmMap.put("storeid", getIntent().getStringExtra("storeid"));//仓库ID
        mParmMap.put("goodsid", getIntent().getStringExtra("goodsid"));//商品ID
        mParmMap.put("refertype", getIntent().getStringExtra("refertype"));// 引用类型 （采购退货有两种情况，一种手工录入商品时，传0或空；另一种是引用采购收货单时，传7 ；其他单据类型都传0或空）
        mParmMap.put("referitemno", getIntent().getStringExtra("referitemno"));//引用单明细ID（采购退货有两种情况，一种手工录入商品时，传0或空；另一种是引用采购收货单时，传引用明细的referitemno；其他单据类型都传0或空）
        mParmMap.put("curpage", 1);//当前页
        mSerials = new Gson().fromJson(getIntent().getStringExtra("serials"), new TypeToken<List<Serial>>() {
        }.getType());

        mPosition = getIntent().getIntExtra("position", 0);
        mBillid = getIntent().getStringExtra("billid");
        mSerialinfo = getIntent().getStringExtra("serialinfo");
        presenter.post(0, "selectcanserialinfo", mParmMap);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_kcpd_xzxlh;
    }

    @Override
    protected void init() {
        setTitlebar();
        btView.setVisibility(View.VISIBLE);

        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<KcpdXzXlhHolder.ViewHolder, KtKcpdXzXlhData>(list) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return KcpdXzXlhHolder.getViewHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final KcpdXzXlhHolder.ViewHolder holder, final KtKcpdXzXlhData data) {
                holder.cbView.setChecked(data.isCheck());
                //选择
                holder.cbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setCheck(holder.cbView.isChecked());
                    }
                });
                holder.tvSerialNumber.setText(data.getSerno());
                switch (data.getType()) {
                    case 0:
                        holder.ivEditor.setVisibility(View.GONE);
                        holder.ivDelete.setVisibility(View.GONE);
                        break;
                    case 1:
                        holder.ivEditor.setVisibility(View.VISIBLE);
                        holder.ivDelete.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择序列号");

        titlebar.setRightText("添加");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                DialogFactory.getAddModifySerialNumberDialog(mActivity, "添加序列号", new OnDialogClickInterface() {
                    @Override
                    public void OnClick(int requestCode, Object object) {

                        String serno=(String) object;
                        for(int i=0;i<list.size();i++){
                            if(list.get(i).getSerno().equals(serno)){
                                showShortToast("该序列号已存在");
                               return;
                            }
                        }
                        KtKcpdXzXlhData ktKcpdXzXlhData = new KtKcpdXzXlhData();
                        ktKcpdXzXlhData.setSerno((String) object);
                        ktKcpdXzXlhData.setCheck(true);
                        ktKcpdXzXlhData.setId(0);
                        ktKcpdXzXlhData.setType(1);
                        list.add(ktKcpdXzXlhData);
                        mAdapter.setList(list);
                    }
                }).show();
            }
        });
    }


    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        switch (requestCode) {
            case 0:
                list = new Gson().fromJson((String) data,
                        new TypeToken<List<KtKcpdXzXlhData>>() {
                        }.getType());
                if (list == null || list.size() == 0) {

                } else {
                    if (mSerials == null || mSerials.size() == 0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setCheck(true);
                        }
                    } else {
                        processData(list);
                    }
                    mAdapter.setList(list);
                }
                break;

        }
    }


    private List<KtKcpdXzXlhData> processData(List<KtKcpdXzXlhData> list) {
        if (mSerials != null && mSerials.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (mSerials.size() == 0)
                    break;
                for (int l = 0; l < mSerials.size(); l++) {
                    if (list.get(i).getSerno().equals(mSerials.get(l).getSerno())) {
                        list.get(i).setCheck(true);
                        mSerials.remove(l);
                        l = l - 1;
                        break;
                    }
                }
            }
            if (mSerials.size() != 0) {
                for (int i = 0; i < mSerials.size(); i++) {
                    KtKcpdXzXlhData ktKcpdXzXlhData = new KtKcpdXzXlhData();
                    ktKcpdXzXlhData.setSerno(mSerials.get(i).getSerno());
                    ktKcpdXzXlhData.setCheck(true);
                    ktKcpdXzXlhData.setId(0);
                    ktKcpdXzXlhData.setType(1);
                    list.add(ktKcpdXzXlhData);
                }
            }
        }
        return list;
    }


    @OnClick(R.id.bt_view)
    public void onClick() {
        if (mSerials == null) {
            mSerials = new ArrayList<>();
        }
        for (int l = 0; l < list.size(); l++) {
            if (list.get(l).isCheck()) {
                Serial serial = new Serial();
                serial.setBillid(mBillid);
                serial.setSerialinfo(mSerialinfo);
                serial.setSerno(list.get(l).getSerno());
                mSerials.add(serial);
            }
        }
        setResult(Activity.RESULT_OK, new Intent()
                .putExtra("position", mPosition)
                .putExtra("data", mPGson.toJson(mSerials)));
        finish();

    }
}
