package com.cr.activity.jxc;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.adapter.MyMenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.cr.activity.common.CommonXzphActivity;
import com.cr.activity.tjfx.kcbb.TjfxKcbbSpjg2Activity;
import com.cr.myinterface.SLViewValueChange;
import com.cr.tools.AppData;
import com.cr.tools.DateUtil;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.WeChatCaptureActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.dialog.DateSelectDialog;
import com.update.dialog.OnDialogClickInterface;
import com.update.model.ChooseGoodsData;
import com.update.model.Serial;
import com.update.utils.CustomTextWatcher;
import com.update.utils.EditTextHelper;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.JxcXzspHolder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择商品（销售开单、采购退货、仓库调拨选择商品界面）
 */
public class JxcXzspActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.pullRecycle_view)
    PullableRecyclerView pullRecycleView;
    @BindView(R.id.mFilterContentView)
    PullToRefreshLayout mFilterContentView;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @BindView(R.id.bt_jxtj)
    Button btJxtj;

    private String code;
    private String cartypeid;
    private String barcode;//新增条码
    private String mTaxrate;//税率
    private boolean issj;//发票类型是否是收据

    private String storeid = "0";
    private int page_number = 0;//页码
    private String tabname = "";
    private Map<String, Object> mParmMap;
    List<KtXzspData> list = new ArrayList<KtXzspData>();
    List<KtXzspData> result = new ArrayList<KtXzspData>();
    private String mParms;

    @Override
    protected void initVariables() {

        presenter = new BaseP(this, this);
        fenlei();
        if (this.getIntent().hasExtra("rkckId")) {
            storeid = this.getIntent().getExtras().getString("rkckId");
        }
        if (this.getIntent().hasExtra("tabname")) {
            tabname = this.getIntent().getExtras().getString("tabname");
        }
        //判断是否是扫一扫
        if (this.getIntent().hasExtra("barcode")) {
            barcode = this.getIntent().getExtras().getString("barcode");
            btJxtj.setVisibility(View.VISIBLE);
        }
        mTaxrate = getIntent().getStringExtra("taxrate");
        issj = getIntent().getBooleanExtra("issj", true);
        mParms = getIntent().getStringExtra("parms");
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("pagesize", "10");
        mParmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        mParmMap.put("tabname", tabname);
        mParmMap.put("storeid", storeid);
        mParmMap.put("goodscode", "");
        mParmMap.put("goodstype", code);
        mParmMap.put("cartypeid", cartypeid);
        mParmMap.put("barcode", barcode);//新增条码
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_jxc_choose_good;
    }

    @Override
    protected void init() {
        setTitlebar();
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    list.clear();
                    page_number = 0;
                    http(1);
                    return true;
                }
                return false;
            }
        });


        mFilterContentView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page_number = 0;
                http(1);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                http(2);

            }
        });
        pullRecycleView.setLayoutManager(new LinearLayoutManager(this));
        pullRecycleView.setAdapter(mAdapter = new BaseRecycleAdapter<JxcXzspHolder.ViewHolder, KtXzspData>(list) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return JxcXzspHolder.getViewHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final JxcXzspHolder.ViewHolder holder, final KtXzspData data) {
                final int position = holder.getLayoutPosition();
                if (data.isCheck()) {
                    holder.cbView.setChecked(true);
                    holder.llBottom.setVisibility(View.VISIBLE);

                } else {
                    holder.cbView.setChecked(false);
                    holder.llBottom.setVisibility(View.GONE);
                }
                holder.tvMc.setText("名称：" + data.getName());
                holder.tvBh.setText("编号：" + data.getCode());
                holder.tvGg.setText("规格：" + data.getSpecs());
                holder.tvXh.setText("型号：" + data.getModel());
                holder.tvKc.setText("库存：" + data.getOnhand() + data.getUnitname());
                switch (mParms) {
                    case "CGDD"://采购订单、销售订单不带序列号（没有严格序列号商品、有批次商品）
                    case "XSDD":
                        holder.tvXlh.setVisibility(View.GONE);//隐藏序列号添加按钮
                        holder.slView.setVisibility(View.VISIBLE);
                        holder.tvSl.setVisibility(View.GONE);
                        break;
                    default:
                        if (data.getSerialctrl().equals("T")) {//判断是否是严格序列号商品
                            LogUtils.e("严格序列商品");
                            holder.slView.setVisibility(View.GONE);
                            holder.tvSl.setVisibility(View.VISIBLE);
                        } else {
                            holder.slView.setVisibility(View.VISIBLE);
                            holder.tvSl.setVisibility(View.GONE);
                        }
                        break;
                }

                holder.slView.setSl(data.getNumber());
                holder.tvSl.setText(data.getNumber() + "");
                holder.slView.setOnValueChange(new SLViewValueChange() {
                    @Override
                    public void onValueChange(double sl) {
                        data.setNumber(sl);
                    }
                });

                //是批次商品的会显示批号、生产日期、有效日期
                if (data.getBatchctrl().equals("T")) {
                    holder.llCpph.setVisibility(View.VISIBLE);
                    holder.etCpph.setText(data.getCpph());
                    holder.llScrq.setVisibility(View.VISIBLE);
                    holder.etScrq.setText(data.getScrq());
                    holder.llYxqz.setVisibility(View.VISIBLE);
                    holder.etYxqz.setText(data.getYxqz());
                    if(TextUtils.isEmpty(data.getCbj())){
                        holder.llCbj.setVisibility(View.GONE);
                    }else {
                        holder.llCbj.setVisibility(View.VISIBLE);
                        holder.etCbj.setText(data.getCbj());
                    }
                } else {
                    holder.llCpph.setVisibility(View.GONE);
                    holder.llScrq.setVisibility(View.GONE);
                    holder.llYxqz.setVisibility(View.GONE);
                    holder.llCbj.setVisibility(View.GONE);
                }
                //单价
                holder.etDj.setText(FigureTools.sswrFigure(data.getAprice()));
                holder.etDj.setTag(position);
                holder.etDj.addTextChangedListener(new CustomTextWatcher(new CustomTextWatcher.UpdateTextListener() {
                    @Override
                    public void updateText(String string) {
                        if ((Integer) holder.etDj.getTag() == position && holder.etDj.hasFocus()) {
                            if (!TextUtils.isEmpty(string)) {
                                data.setAprice(Double.parseDouble(string.toString()));
                                Double csje = data.getAprice() * (Double.parseDouble(data.getTaxrate()) + 100) / 100;
                                data.setTaxunitprice(FigureTools.sswrFigure(csje));
                                holder.tvHsdj.setText(data.getTaxunitprice());

                            }
                        }

                    }
                }));
                //税率
                if (TextUtils.isEmpty(data.getTaxrate())) {
                    data.setTaxrate(mTaxrate);
                }

                //判断发票类型是否是收据(票类型为收据选择商品时税率为0且不可修改)
                EditTextHelper.EditTextEnable(!issj, holder.etSl);
                holder.etSl.setTag(position);
                holder.etSl.addTextChangedListener(new CustomTextWatcher(new CustomTextWatcher.UpdateTextListener() {
                    @Override
                    public void updateText(String string) {
                        if ((Integer) holder.etSl.getTag() == position && holder.etSl.hasFocus() && !TextUtils.isEmpty(string)) {
                            data.setTaxrate(string);
                            Double csje = data.getAprice() * (Double.parseDouble(data.getTaxrate()) + 100) / 100;
                            data.setTaxunitprice(FigureTools.sswrFigure(csje));
                            holder.tvHsdj.setText(data.getTaxunitprice());
                        }
                    }
                }));
                holder.etSl.setText(data.getTaxrate());
                holder.etBz.setText(data.getMemo());
                holder.etBz.setTag(position);
                holder.etBz.addTextChangedListener(new CustomTextWatcher(new CustomTextWatcher.UpdateTextListener() {
                    @Override
                    public void updateText(String string) {
                        //关键点：1.给edittext设置tag，此tag用来与position做对比校验，验证当前选中的edittext是否为需要的控件;
//                            2.焦点判断：只有当前有焦点的edittext才有更改数据的权限，否则会造成数据紊乱
//                            3.edittext内数据变动直接直接更改datalist的数据值，以便滑动view时显示正确
                        if ((Integer) holder.etBz.getTag() == position && holder.etBz.hasFocus()) {
                            data.setMemo(string);
                        }
                    }
                }));
                Double csje = data.getAprice() * (Double.parseDouble(data.getTaxrate()) + 100) / 100;
                data.setTaxunitprice(FigureTools.sswrFigure(csje));
                holder.tvHsdj.setText(data.getTaxunitprice());

                //选择
                holder.cbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.cbView.isChecked()) {
                            data.setCheck(true);
                            holder.llBottom.setVisibility(View.VISIBLE);
                        } else {
                            data.setCheck(true);
                            holder.llBottom.setVisibility(View.GONE);
                        }
                    }
                });
                //产品批号
                holder.llCpph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        switch (mParms) {
                            case "XSTH"://销售退货
                                intent.putExtra("iscbj", data.getInfCostingtypeid()==2);
                            case "CGDD"://采购订单
                            case "CGSH"://采购收货
                                intent.putExtra("isxz", true);
                                break;
                            default:
                                break;
                        }

                        intent.setClass(mActivity, JxcXzphActivity.class);
                        intent.putExtra("goodsid", data.getGoodsid() + "");
                        intent.putExtra("storied", storeid);
                        intent.putExtra("position", position);
                        intent.putExtra("position", position);
                        startActivityForResult(intent, 1);
                    }
                });
                //生产日期
                holder.llScrq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DateSelectDialog(mActivity, 0, new OnDialogClickInterface() {
                            @Override
                            public void OnClick(int requestCode, Object object) {

                                data.setScrq((String) object);
                                holder.etScrq.setText(data.getScrq());
                            }
                        }).show();

                    }
                });
                //有效期至
                holder.llYxqz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(data.getScrq())) {
                            showShortToast("请先选择生产日期");
                            return;
                        }
                        new DateSelectDialog(mActivity, DateUtil.StringTolongDate(data.getScrq(), "yyyy-MM-dd"), new OnDialogClickInterface() {
                            @Override
                            public void OnClick(int requestCode, Object object) {
                                data.setYxqz((String) object);
                                holder.etYxqz.setText(data.getYxqz());
                            }
                        }).show();

                    }
                });
                holder.ivXzjg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mActivity, TjfxKcbbSpjg2Activity.class);
                        intent.putExtra("goodsid", data.getGoodsid() + "");
                        intent.putExtra("storied", storeid);
                        intent.putExtra("unitid", data.getUnitid() + "");
                        intent.putExtra("clientid", "0");
                        intent.putExtra("index", position + "");
                        startActivityForResult(intent, 4);
                    }
                });

                holder.tvXlh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        if (TextUtils.isEmpty(data.getSerialinfo())) {
                            UUID uuid = UUID.randomUUID();
                            data.setSerialinfo(uuid.toString().toUpperCase());
                        }
                        intent.putExtra("position", position);
                        intent.putExtra("billid", "0");
                        intent.putExtra("serialinfo", data.getSerialinfo());
                        intent.putExtra("serials", new Gson().toJson(data.getMSerials()));
                        intent.putExtra("goodsid", data.getGoodsid() + "");
                        intent.putExtra("storeid", storeid);
                        switch (mParms) {
                            case "XSKD"://销售开单、采购退货严格序列号商品序列号选择
                            case "CGTH":
                                if (data.getSerialctrl().equals("T")) {
                                    LogUtils.e("严格序列商品");
                                    startActivityForResult(intent.setClass(mActivity, XzXlhActivity.class)
                                            .putExtra("parms", mParms)
                                            .putExtra("refertype", "0")
                                            .putExtra("referitemno", "0"), 5);
                                } else {
                                    startActivityForResult(intent.setClass(mActivity, JxcTjXlhActivity.class), 5);
                                }
                                break;
                            case "CGSH"://采购收货录入序列号需要查重
                                intent.putExtra("rechecking", true);
                            default:
                                startActivityForResult(intent.setClass(mActivity, JxcTjXlhActivity.class), 5);
                                break;
                        }

                    }
                });
            }

        });

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择商品");
    }

    @OnClick({R.id.bt_jxtj, R.id.bt_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_jxtj:
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class), 18);

                break;
            case R.id.bt_view:
                for (int i = 0; i < list.size(); i++) {
                    KtXzspData ktXzspData = list.get(i);
                    if (ktXzspData.isCheck()) {
                        switch (mParms) {
                            case "CGDD"://采购订单、销售订单不带序列号批号商品的批号和保质期选填
                            case "XSDD":
                                break;
                            default:
                                if (ktXzspData.getBatchctrl().equals("T")) {
                                    if (TextUtils.isEmpty(ktXzspData.getCpph())) {
                                        showShortToast("选择的批号商品，必须填写批号信息");
                                        return;
                                    }
                                    if (TextUtils.isEmpty(ktXzspData.getScrq()) || TextUtils.isEmpty(ktXzspData.getYxqz())) {
                                        showShortToast("选择的批号商品，必须填写保质期");
                                        return;
                                    }
                                }
                                if (ktXzspData.getSerialctrl().equals("T")) {
                                    ArrayList<Serial> serials = (ArrayList<Serial>) ktXzspData.getMSerials();
                                    if (serials == null || serials.size() == 0) {
                                        showShortToast("商品数量不能为0");
                                        return;
                                    }
                                    if (serials.size() != ktXzspData.getNumber()) {
                                        showShortToast("商品序列号个数与数量不一致");
                                        return;
                                    }
                                }
                                break;
                        }
                        result.add(ktXzspData);
                    }
                }
                setResult(RESULT_OK, new Intent().putExtra("data", mPGson.toJson(result)));
                finish();
                break;
        }
    }

    private void fenlei() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        presenter.post(0, "multitype", parmMap);
    }

    private void http(int requestCode) {
        mParmMap.put("goodsname", search.getText().toString());
        mParmMap.put("curpage", page_number + 1);//当前页
        presenter.post(requestCode, ServerURL.SELECTGOODS, mParmMap);
    }


    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        switch (requestCode) {
            case 0:
                http(1);
                List<Map<String, Object>> fenlinList = (List<Map<String, Object>>) PaseJson
                        .paseJsonToObject((String) data);
                List<Map<String, Object>> lbList = new ArrayList<>();
                List<Map<String, Object>> cllxList = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();
                map.put("name", "全部");
                map.put("id", "0");
                map.put("lcode", "0");
                lbList.add(map);
                cllxList.add(map);
                for (int i = 0; i < fenlinList.size(); i++) {
                    switch (fenlinList.get(i).get("lb").toString()) {
                        case "2":
                            lbList.add(fenlinList.get(i));
                            break;
                        case "3":
                            cllxList.add(fenlinList.get(i));
                            break;
                    }
                }
                setMenu(lbList, cllxList);
                break;
            case 1:
                list = new Gson().fromJson((String) data,
                        new TypeToken<List<KtXzspData>>() {
                        }.getType());
                if (list == null || list.size() == 0) {

                } else {
                    page_number = page_number + 1;
                    mAdapter.setList(list);
                }
                break;
            case 2:
                ArrayList<KtXzspData> myList = new Gson().fromJson((String) data,
                        new TypeToken<List<KtXzspData>>() {
                        }.getType());
                if (myList == null || myList.size() == 0) {
                    showShortToast("没有更多内容");
                } else {
                    page_number = page_number + 1;
                    list.addAll(myList);
                    mAdapter.setList(list);
                }
                break;
        }
    }

    /**
     * 网络请求结束
     */
    @Override
    public void httpFinish(int requestCode) {
        switch (requestCode) {
            case 1:
                mFilterContentView.refreshFinish(true);//刷新完成
                break;
            case 2:
                mFilterContentView.loadMoreFinish(true);
                break;
        }

    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                int index = data.getIntExtra("position",0);
                list.get(index).setCpph(data.getExtras().getString("name"));
                list.get(index).setScrq(data.getExtras().getString("scrq"));
                list.get(index).setYxqz(data.getExtras().getString("yxrq"));
                list.get(index).setBatchrefid(data.getExtras().getString("id"));
                list.get(index).setCbj(data.getExtras().getString("cbj"));
                mAdapter.notifyDataSetChanged();
                break;
            case 4://选择价格
                int i = Integer.parseInt(data.getExtras()
                        .getString("index"));
                list.get(i).setAprice(Double.parseDouble(data.getExtras().getString("dj")));
                mAdapter.notifyDataSetChanged();
                break;
            case 5:
                List<Serial> serials = new Gson().fromJson(data.getExtras().getString("data"), new TypeToken<List<Serial>>() {
                }.getType());
                list.get(data.getExtras().getInt("position")).setMSerials(serials);
                if (list.get(data.getExtras().getInt("position")).getSerialctrl().equals("T")) {
                    list.get(data.getExtras().getInt("position")).setNumber(serials.size());
                }
                mAdapter.notifyDataSetChanged();
                break;
            case 18:
                page_number = 0;
                barcode = data.getStringExtra("qr");
                http(1);
                break;
        }
    }

    private void setMenu(List<Map<String, Object>> lbList, List<Map<String, Object>> cllxList) {
        String[] titleList;
        List<View> views = new ArrayList<>();
        views.add(createSingleListView(lbList, 0));
        if (AppData.AppType == 1) {//判断是否是汽配版
            titleList = new String[]{"类别", "车辆类别"};
            views.add(createSingleListView(cllxList, 1));
        } else {
            titleList = new String[]{"类别"};
        }
        dropDownMenu.setMenuAdapter(new MyMenuAdapter(mActivity, titleList, views));
    }

    private View createSingleListView(List<Map<String, Object>> items, final int kind) {
        SingleListView<Map<String, Object>> singleListView = new SingleListView<Map<String, Object>>(mActivity)
                .adapter(new SimpleTextAdapter<Map<String, Object>>(null, mActivity) {
                    @Override
                    public String provideText(Map<String, Object> string) {
                        return string.get("name").toString();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mActivity, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<Map<String, Object>>() {
                    @Override
                    public void onItemClick(Map<String, Object> item) {
                        dropDownMenu.close();
                        dropDownMenu.setPositionIndicatorText(kind, item.get("name").toString());
                        switch (kind) {
                            case 0://商品类别
                                code = item.get("id").toString();
                                break;
                            case 1://车辆类别
                                cartypeid = item.get("id").toString();
                                break;

                        }
                        page_number = 1;
                        list.clear();
                        http(1);

                    }
                });


        singleListView.setList(items, -1);

        return singleListView;
    }


}
