package com.cr.activity.jxc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.dialog.OnDialogClickInterface;
import com.update.dialog.XzphDialog;
import com.update.viewbar.TitleBar;
import com.update.viewholder.JxcXzphHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 选择批号
 */
public class JxcXzphActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    private Map<String, Object> mParmMap;


    private List<JxcXzphData> mList;

    private boolean mIsXz;//是否允许新增
    private boolean mIsCbj;//是否需要填写成本价

    private int mPosition;

    @Override
    protected void initVariables() {
        mParmMap = new HashMap<>();
        presenter = new BaseP(this, this);
        http();
        mIsXz = getIntent().getBooleanExtra("isxz", false);
        mIsCbj = getIntent().getBooleanExtra("iscbj", false);
        mPosition=getIntent().getIntExtra("position",0);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_jxc_xzph;
    }

    @Override
    protected void init() {
        setTitlebar();
        rcvList.setLayoutManager(new LinearLayoutManager(mActivity));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<JxcXzphHolder.ViewHolder, JxcXzphData>(mList) {
            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return JxcXzphHolder.getViewHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(JxcXzphHolder.ViewHolder holder, final JxcXzphData data) {
                holder.tvPh.setText("批号：" + data.getBatchcode());
                holder.tvScrq.setText("生产日期：" + data.getProduceddate());
                holder.tvYxrq.setText("有效日期：" + data.getValiddate());
                holder.tvKysl.setText("可用数量：" + data.getOnhand());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("id", data.getId()+"");
                        intent.putExtra("name", data.getBatchcode());
                        intent.putExtra("scrq", data.getProduceddate());
                        intent.putExtra("yxrq", data.getValiddate());
                        intent.putExtra("onhand", data.getOnhand());
                        intent.putExtra("position", mPosition);
                        intent.putExtra("cbj","");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }
        });

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择批号");
        if (mIsXz) {
            titlebar.setRightText("新增");
            titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
                @Override
                public void onClick(int i) {
                    XzphDialog xzphDialog=new XzphDialog(mActivity);
                    xzphDialog.setCbj(mIsCbj);
                    xzphDialog.setDialogClickInterface(new OnDialogClickInterface() {
                        @Override
                        public void OnClick(int requestCode, Object object) {
                            Map<String,String> map = (Map<String, String>) object;
                            Intent intent = new Intent();
                            intent.putExtra("id", "0");
                            intent.putExtra("name", map.get("ph"));
                            intent.putExtra("scrq", "");
                            intent.putExtra("yxrq", "");
                            intent.putExtra("onhand", "0");
                            intent.putExtra("cbj", map.get("cbj"));
                            intent.putExtra("position", mPosition);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    xzphDialog.show();
                }
            });
        }
    }

    private void http() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("storeid", getIntent().getStringExtra("storeid"));//仓库ID
        parmMap.put("goodsid", getIntent().getStringExtra("goodsid"));//商品ID
        presenter.post(0, ServerURL.GETGOODSBATCH, parmMap);
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        mList = mPGson.fromJson((String) data,
                new TypeToken<List<JxcXzphData>>() {
                }.getType());
        mAdapter.setList(mList);
    }
}
