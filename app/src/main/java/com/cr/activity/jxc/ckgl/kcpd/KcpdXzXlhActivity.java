package com.cr.activity.jxc.ckgl.kcpd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cr.activity.jxc.KtXzXlhData;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.Serial;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewholder.XzXlhHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 选择序列号
 */
public class KcpdXzXlhActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;


    private Map<String, Object> mParmMap;
    private int page_number = 0;//页码
    List<KtXzXlhData> list = new ArrayList<KtXzXlhData>();
    List<Serial> mSerials;
    List<Serial> mSerialsA;
    private int mPosition;
    private String mBillid;//主单id
    private String mSerialinfo;//序列号

    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("pagesize", "10");
        mParmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        mParmMap.put("parms", getIntent().getStringExtra("parms"));//类型（CGTH =采购退货,XSKD=销售开单,KCPD=库存盘点,CKDB=仓库调拨）
        mParmMap.put("storeid", getIntent().getStringExtra("storeid"));//仓库ID
        mParmMap.put("goodsid", getIntent().getStringExtra("goodsid"));//商品ID
        mParmMap.put("refertype", getIntent().getStringExtra("refertype"));// 引用类型 （采购退货有两种情况，一种手工录入商品时，传0或空；另一种是引用采购收货单时，传7 ；其他单据类型都传0或空）
        mParmMap.put("referitemno", getIntent().getStringExtra("referitemno"));//引用单明细ID（采购退货有两种情况，一种手工录入商品时，传0或空；另一种是引用采购收货单时，传引用明细的referitemno；其他单据类型都传0或空）
        mSerialsA = new Gson().fromJson(getIntent().getStringExtra("serials"), new TypeToken<List<Serial>>() {
        }.getType());
        mSerials = new ArrayList<>();
        mSerials.addAll(mSerialsA);
        mPosition = getIntent().getIntExtra("position", 0);
        mBillid = getIntent().getStringExtra("billid");
        mSerialinfo = getIntent().getStringExtra("serialinfo");
        http(0);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_kcpd_xzxlh;
    }

    @Override
    protected void init() {
        setTitlebar();

        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<XzXlhHolder.ViewHolder, KtXzXlhData>(list) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return XzXlhHolder.getViewHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final XzXlhHolder.ViewHolder holder, final KtXzXlhData data) {
                holder.cbView.setChecked(data.isCheck());
                //选择
                holder.cbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setCheck(holder.cbView.isChecked());
                    }
                });
                holder.tvSerialNumber.setText(data.getSerno());
            }
        });

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择序列号");
        titlebar.setRightText("完成");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
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
        });
    }

    private void http(int requestCode) {
        mParmMap.put("curpage", page_number + 1);//当前页
        presenter.post(requestCode, "selectcanserialinfo", mParmMap);
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        switch (requestCode) {
            case 0:
                list = new Gson().fromJson((String) data,
                        new TypeToken<List<KtXzXlhData>>() {
                        }.getType());
                if (list == null || list.size() == 0) {

                } else {
                    page_number = page_number + 1;
                    processData(list);
                    mAdapter.setList(list);
                }
                break;
            case 1:
                List<KtXzXlhData> myList = new Gson().fromJson((String) data,
                        new TypeToken<List<KtXzXlhData>>() {
                        }.getType());
                if (myList == null || myList.size() == 0) {
                    showShortToast("没有更多内容");
                } else {
                    page_number = page_number + 1;
                    list.addAll(processData(myList));
                    mAdapter.setList(list);
                }
                break;
            case 2:

                break;
        }
    }

    /**
     * 网络请求结束
     */
    @Override
    public void httpFinish(int requestCode) {
        switch (requestCode) {
            case 0:

                break;
            case 1:

                break;
        }

    }

    private List<KtXzXlhData> processData(List<KtXzXlhData> list) {
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
        }
        return list;
    }


}
