package com.cr.activity.tjfx.lrb

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.actiity.choose.KtDateSelectionActivity
import com.update.actiity.choose.ScreeningProjectActivity
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.base.BaseRecycleAdapter

import com.update.model.KtLrbData

import com.update.utils.DateUtil
import com.update.utils.LogUtils
import com.update.viewbar.refresh.PullToRefreshLayout
import com.update.viewholder.ViewHolderFactory

import kotlinx.android.synthetic.main.activity_tfjx_lrb.*
import java.util.*

/**
 * 利润表
 */

class KtTjfxLrbActivity : BaseActivity<BaseP>(), PullToRefreshLayout.OnRefreshListener {

    var mParmMap = TreeMap<String, Any>()
    private var mQsrq: String = ""//开始日期
    private var mZzrq: String = ""//截止日期
    var mDate = Date()
    private var page_number: Int = 1//页码
    var mList = ArrayList<KtLrbData>()

    override fun initVariables() {
        presenter = BaseP(this, mActivity)
        mQsrq = DateUtil.DateToString(mDate, "yyyy-MM-") + "01"
        mZzrq = DateUtil.DateToString(mDate, "yyyy-MM-dd")
        mParmMap["dbname"] = ShareUserInfo.getDbName(this)
        mParmMap["pagesize"] = "10"
        http(0)
    }

    override fun getLayout(): Int {
        return R.layout.activity_tfjx_lrb
    }

    override fun init() {
        titlebar.setTitleText(mActivity, "利润表 ")
        titlebar.setIvRightImageResource(R.drawable.oper)
        titlebar.setTitleOnlicListener {
            startActivityForResult(Intent(mActivity, KtDateSelectionActivity::class.java)
                    .putExtra("qsrq", mQsrq)
                    .putExtra("zzrq", mZzrq), 11)

        }
        prl_view.setOnRefreshListener(this)
        prv_view.layoutManager = LinearLayoutManager(this)
        mAdapter = object : BaseRecycleAdapter<ViewHolderFactory.LrbHolder, KtLrbData>(mList) {
            override fun MyonCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
                return ViewHolderFactory.getLrbHolder(mActivity, parent)
            }

            override fun MyonBindViewHolder(holder: ViewHolderFactory.LrbHolder?, data: KtLrbData) {
                if (holder != null) {
                    holder.tvMz.text = data.name
                    holder.tvJe.text = data.curbalance.toString()
                    holder.tvQcye.text = "期初余额:" + data.inibalance.toString()
                    holder.tvLjje.text = "累计金额:" + data.endbalance

                    holder.itemView.setOnClickListener {
                        if(data.flag==1) {
                            startActivity(Intent(mActivity, KtTjfxCwbbmxActivity::class.java)
                                    .putExtra("title", data.name)
                                    .putExtra("subid", data.subid)
                                    .putExtra("qsrq", mQsrq)
                                    .putExtra("zzrq", mZzrq)
                                 )
                        }else{
                            showShortToast("不能查看该科目明细!")
                        }
                    }
                }
            }

        }
        prv_view.adapter = mAdapter

    }

    override fun onMyActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onMyActivityResult(requestCode, resultCode, data)
        if (data != null) {
            mQsrq = data.getStringExtra("qsrq")
            mZzrq = data.getStringExtra("zzrq")
            page_number = 1
            http(0)
        }

    }

    override fun onRefresh(pullToRefreshLayout: PullToRefreshLayout?) {
        page_number = 1
        mParmMap["curpage"] = page_number.toString()
        presenter.post(0, "cwprofit", mParmMap)
    }

    override fun onLoadMore(pullToRefreshLayout: PullToRefreshLayout?) {
        mParmMap["curpage"] = (page_number + 1).toString()
        presenter.post(1, "cwprofit", mParmMap)
    }

    fun http(i: Int) {
        mParmMap["qsrq"] = mQsrq
        mParmMap["zzrq"] = mZzrq
        mParmMap["curpage"] = page_number.toString()
        presenter.post(i, "cwprofit", mParmMap)
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    override fun returnData(requestCode: Int, data: Any) {
        LogUtils.e(data.toString())
        val gson = Gson()
        var list = gson.fromJson<ArrayList<KtLrbData>>(data as String,
                object : TypeToken<ArrayList<KtLrbData>>() {
                }.type)
        if (list == null || list.size == 0) {
            showShortToast("没有更多内容")
            return
        }

        if (requestCode == 0)
            mList.clear()
        else
            page_number = page_number + 1
        mList!!.addAll(list)
        mAdapter.notifyDataSetChanged()

    }

    /**
     * 网络请求结束
     */
    override fun httpFinish(requestCode: Int) {
        when (requestCode) {
            0 -> prl_view.refreshFinish(true)//刷新完成
            1 -> prl_view.loadMoreFinish(true)//加载完成
        }

    }

}