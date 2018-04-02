package com.update.actiity.project;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.widget.EditText;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;

import java.util.Date;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/28 0028 下午 3:04
 * Description:项目管理列表
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/28 0028         申中佳               V1.0
 */
public class ProjectManagementActivity extends BaseActivity implements
        PullToRefreshLayout.OnRefreshListener {

    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.prv_view)
    PullableRecyclerView prvView;
    @BindView(R.id.prl_view)
    PullToRefreshLayout prlView;


    private Map<String, Object> mParmMap;
    private Date mDate;
    private Gson mGson;
    /*上传参数变量*/
    private int page_number;//页码
    private String mQsrq;//开始日期
    private String mZzrq;//截止日期
    private String mShzt;//审核状态 (0未审 1已审 2 审核中   9全部)
    private String mGmid;//阶段ID
    private String mEmpid;//业务员ID
    private String cname;//供应商名称（模糊查询用）

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mParmMap = new ArrayMap<>();
        mGson = new Gson();
        mDate=new Date();

        mQsrq = DateUtil.DateToString(mDate, "yyyy-MM-") + "01";
        mZzrq = DateUtil.DateToString(mDate, "yyyy-MM-dd");
        mShzt = "9";
        mGmid = "0";
        mEmpid= "0";
        mParmMap.put("opid", ShareUserInfo.getUserId(this));//登录操作员ID
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("tabname", "tb_project");
        mParmMap.put("clientid", "0");
        mParmMap.put("depid", "0");//
        mParmMap.put("pagesize", "10");//每页加载数据大小
        http();
    }

    private void http() {
        page_number = 1;
        mParmMap.put("empid", mEmpid);//业务员ID (没有的话传空或0)
        mParmMap.put("qsrq", mQsrq);//
//        mParmMap.put("zzrq", mZzrq);//
        mParmMap.put("zzrq", "2018-05-02");//
        mParmMap.put("shzt", mShzt);//审核状态
        mParmMap.put("empid", mEmpid);//审核状态
        mParmMap.put("gmid", mGmid);//项目单阶段ID（0全部）
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, ServerURL.BILLLIST, mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_list;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "项目");
        titlebar.setIvRightTwoImageResource(R.drawable.oper);
        titlebar.setIvRightImageResource(R.mipmap.ic_add);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://增加安装登记
                        startActivity(new Intent(ProjectManagementActivity.this, AddProjectActivity.class));
                        break;
                    case 1://打开右边侧滑菜单
                        startActivity(new Intent(ProjectManagementActivity.this, ProjectActivity.class).putExtra("billid","31"));
//                        startActivityForResult(new Intent(ProjectManagementActivity.this, ScreeningActivity.class)
//                                .putExtra("kind", 2), 11);
                        break;
                }
            }
        });
    }

    /**
     * 下拉刷新
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    /**
     * 上拉加载
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }

    /**
     * 网络请求结束
     */
    @Override
    public void httpFinish(int requestCode) {
        switch (requestCode) {
            case 0:
                prlView.refreshFinish(true);//刷新完成
                break;
            case 1:
                prlView.loadMoreFinish(true);//加载完成
                break;
        }

    }
}
