package com.cr.activity.khgl.mstx.gztx

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.cr.activity.gzpt.dwzl.GzptDwzlActivity
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddDetailActivity
import com.cr.activity.jxc.cggl.cgdd.KtJxcCgglCgddDetailActivity
import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshDetailActivity
import com.cr.activity.jxc.xsgl.xsdd.KtJxcXsglXsddDetailActivity
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdDetailActivity
import com.cr.model.GSGG
import com.cr.tools.FigureTools
import com.cr.tools.PaseJson
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.update.actiity.contract.ContractActivity
import com.update.actiity.sales.SalesOpportunitiesActivity
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.base.BaseRecycleAdapter
import com.update.utils.LogUtils
import com.update.viewbar.refresh.PullToRefreshLayout
import com.update.viewholder.GztxXxHolder
import kotlinx.android.synthetic.main.activity_list.*
import java.io.Serializable

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class KtGztxXxActivity : BaseActivity<BaseP>(), PullToRefreshLayout.OnRefreshListener {
    var mParmMap = TreeMap<String, Any>()
    private var page_number: Int = 1//页码
    var gztxList: ArrayList<HashMap<String, Any>> = ArrayList()
    override fun initVariables() {
        presenter = BaseP(this, mActivity)
        mParmMap["dbname"] = ShareUserInfo.getDbName(mActivity)
        mParmMap["opid"] = ShareUserInfo.getUserId(mActivity)
        mParmMap["typecode"] = intent.getStringExtra("typecode")
        mParmMap["pagesize"] = "10"
        mParmMap["curpage"] = page_number.toString()
        http(0)
    }

    override fun getLayout(): Int {
        return R.layout.activity_list
    }

    override fun init() {
        titlebar.setTitleText(mActivity, intent.getStringExtra("title"))
        prl_view.setOnRefreshListener(this)

        prv_view.layoutManager = LinearLayoutManager(this)
        mAdapter = object : BaseRecycleAdapter<GztxXxHolder.ViewHolder, HashMap<String, Any>>(gztxList) {
            override fun MyonCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
                return GztxXxHolder.getViewHolder(mActivity, parent)
            }

            override fun MyonBindViewHolder(holder: GztxXxHolder.ViewHolder?, data: HashMap<String, Any>) {
                if (holder != null) {
                    when (mParmMap["typecode"]) {
                    //内部公告
                        "NBGG" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = data["title"].toString()
                            holder.itemView.setOnClickListener {
                                val gsgg = GSGG()
                                gsgg.id = data["id"].toString()
                                gsgg.title = data["title"].toString()
                                gsgg.opdate = ""
                                val intent = Intent(mActivity, MstxGsggDetailActivity::class.java)
                                intent.putExtra("object", gsgg)
                                startActivity(intent)
                            }
                        }
                    //客户生日
                        "KHSR" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = data["title"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = data["cname"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = data["lxrname"].toString() + "    生日：" + data["rq"].toString()
                            holder.itemView.setOnClickListener {
                                val map = java.util.HashMap<String, Any>()
                                map["clientid"] = data["clientid"].toString()
                                map["types"] = "1"
                                val intent = Intent(mActivity, GzptDwzlActivity::class.java)
                                intent.putExtra("object", map as Serializable)
                                startActivity(intent)
                            }
                        }
                    //员工生日
                        "YGSR" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = data["title"].toString()
//                            holder.tvB.visibility = View.VISIBLE
//                            holder.tvB.text = data["cname"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = data["lxrname"].toString() + "    生日：" + data["rq"].toString()
                            holder.itemView.setOnClickListener {
                                val map = java.util.HashMap<String, Any>()
                                map["clientid"] = data["clientid"].toString()
                                map["types"] = "5"
                                val intent = Intent(mActivity, GzptDwzlActivity::class.java)
                                intent.putExtra("object", map as Serializable)
                                startActivity(intent)
                            }
                        }
                    //实施项目
                        "SSXM" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = data["cname"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "项目名称：" + data["title"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "日期：" + data["rq"].toString()
                            holder.itemView.setOnClickListener {
                                val map = java.util.HashMap<String, Any>()
                                map["clientid"] = data["clientid"].toString()
                                map["types"] = "1"
                                val intent = Intent(mActivity, GzptDwzlActivity::class.java)
                                intent.putExtra("object", map as Serializable)
                                startActivity(intent)
                            }
                        }

                    //渠道提醒
                        "QDTX" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = data["title"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = data["cname"].toString()
                            holder.itemView.setOnClickListener {
                                val map = java.util.HashMap<String, Any>()
                                map["clientid"] = data["clientid"].toString()
                                map["types"] = "1"
                                val intent = Intent(mActivity, GzptDwzlActivity::class.java)
                                intent.putExtra("object", map as Serializable)
                                startActivity(intent)
                            }
                        }
                    //预约拜访
                        "YYBF" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "预约时间：" + data["rq"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "单位名称：" + data["cname"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "联系人名称：" + data["lxrname"].toString()
                            holder.itemView.setOnClickListener {
                                val map = java.util.HashMap<String, Any>()
                                map["clientid"] = data["clientid"].toString()
                                map["types"] = "1"
                                val intent = Intent(mActivity, GzptDwzlActivity::class.java)
                                intent.putExtra("object", map as Serializable)
                                startActivity(intent)
                            }
                        }
                    //售后回访
                        "SHHF" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = data["title"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = data["cname"].toString()
                            holder.itemView.setOnClickListener {
                                val map = java.util.HashMap<String, Any>()
                                map["clientid"] = data["clientid"].toString()
                                map["types"] = "1"
                                val intent = Intent(mActivity, GzptDwzlActivity::class.java)
                                intent.putExtra("object", map as Serializable)
                                startActivity(intent)
                            }
                        }
                    // 超期应收款
                        "CQYS" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "单据类型：" + data["billtypename"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "单据编号：" + data["code"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = data["cname"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "收款金额：￥" + FigureTools.sswrFigure(data["je"].toString())
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "限定日期：" + data["rq"].toString()
                            holder.itemView.setOnClickListener {
                                val intent = Intent(mActivity, JxcXsglXskdDetailActivity::class.java)
                                intent.putExtra("billid", data["id"].toString())
                                startActivity(intent)
                            }
                        }
                    //超期应付款
                        "CQYF" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "单据类型：" + data["billtypename"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "单据编号：" + data["code"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = data["cname"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "付款金额：￥" + FigureTools.sswrFigure(data["je"].toString())
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "限定日期：" + data["rq"].toString()
                            holder.itemView.setOnClickListener {
                                val intent = Intent(mActivity, JxcCgglCgshDetailActivity::class.java)
                                intent.putExtra("billid", data["id"].toString())
                                startActivity(intent)
                            }
                        }
                    //超期订单
                        "CQDD" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "单据类型：" + data["billtypename"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "单据编号：" + data["code"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = data["cname"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "开单日期:" + data["rq"].toString()
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "预定完成日期：" + data["wcrq"].toString()
                            holder.itemView.setOnClickListener {
                                when (data["billtypename"].toString()) {
                                    "销售订单" -> {
                                        val intent = Intent(mActivity, KtJxcXsglXsddDetailActivity::class.java)
                                        intent.putExtra("billid", data["id"].toString())
                                        startActivity(intent)
                                    }
                                    "采购订单" -> {
                                        val intent = Intent(mActivity, KtJxcCgglCgddDetailActivity::class.java)
                                        intent.putExtra("billid", data["id"].toString())
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                    //过期商品
                        "GQSP" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "商品编码：" + data["code"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "商品名称：" + data["name"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "规格：" + data["specs"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "库存下限: " + FigureTools.sswrFigure(data["downonhand"].toString())
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "当前库存: " + FigureTools.sswrFigure(data["onhand"].toString())
                            holder.tvF.visibility = View.VISIBLE
                            holder.tvF.text = "库存上限：" + FigureTools.sswrFigure(data["uponhand"].toString())
                        }
                    //库存报警
                        "KCBJ" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "商品编码：" + data["code"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "商品名称：" + data["name"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "规格：" + data["specs"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "库存下限: " + FigureTools.sswrFigure(data["downonhand"].toString())
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "当前库存: " + FigureTools.sswrFigure(data["onhand"].toString())
                            holder.tvF.visibility = View.VISIBLE
                            holder.tvF.text = "库存上限：" + FigureTools.sswrFigure(data["uponhand"].toString())
                        }
                    //零销售商品
                        "ZERO" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "名称：" + data["name"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "编码：" + data["code"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "规格：" + data["specs"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "型号:" + data["model"].toString()
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "库存：" + FigureTools.sswrFigure(data["onhand"].toString())
                        }
                    //预期库存报警
                        "PEBJ" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "名称：" + data["name"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "编码：" + data["code"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "规格：" + data["specs"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "型号:" + data["model"].toString()
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "实际库存：" + FigureTools.sswrFigure(data["onhand"].toString())
                            holder.tvF.visibility = View.VISIBLE
                            holder.tvF.text = "预期库存：" + FigureTools.sswrFigure(data["uponhand"].toString())
                        }
                    //发票提醒
                        "FPTX" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "单据编号：" + data["code"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "单位名称：" + data["cname"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "开票类型：" + data["billtypename"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "发票日期:" + data["billdate"].toString()
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "票号：" + data["checkno"].toString()
                            holder.tvF.visibility = View.VISIBLE
                            holder.tvF.text = "开票金额：" + FigureTools.sswrFigure(data["totalamt"].toString())
                        }
                    //信誉额度
                        "XYED" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "名称：" + data["name"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "编码：" + data["code"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "类型：" + data["types"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "等级:" + data["typename"].toString()
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "欠款：" + FigureTools.sswrFigure(data["oweamt"].toString())
                            holder.tvF.visibility = View.VISIBLE
                            holder.tvF.text = "信誉额度：" + FigureTools.sswrFigure(data["xyed"].toString())
                            holder.itemView.setOnClickListener {
                                val map = HashMap<String, Any>()
                                map["clientid"] = data["id"].toString()
                                map["types"] = data["typesid"].toString()
                                val intent = Intent(mActivity, GzptDwzlActivity::class.java)
                                intent.putExtra("object", map as Serializable)
                                startActivity(intent)
                            }
                        }
                    //合同提醒
                        "HTTX" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "单据编号：" + data["code"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "单位名称：" + data["cname"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "合同名称：" + data["title"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "单据日期:" + data["billdate"].toString()
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "起始日期：" + data["qsrq"].toString()
                            holder.tvF.visibility = View.VISIBLE
                            holder.tvF.text = "截止日期：" + data["zzrq"].toString()
                            holder.itemView.setOnClickListener {
                                startActivity(Intent(mActivity, ContractActivity::class.java)
                                        .putExtra("billid", data["id"].toString()))
                            }
                        }
                    //销售机会提醒
                        "JHTX" -> {
                            holder.tvA.visibility = View.VISIBLE
                            holder.tvA.text = "单据编号：" + data["code"].toString()
                            holder.tvB.visibility = View.VISIBLE
                            holder.tvB.text = "单位名称：" + data["cname"].toString()
                            holder.tvC.visibility = View.VISIBLE
                            holder.tvC.text = "机会名称：" + data["title"].toString()
                            holder.tvD.visibility = View.VISIBLE
                            holder.tvD.text = "单据日期:" + data["billdate"].toString()
                            holder.tvE.visibility = View.VISIBLE
                            holder.tvE.text = "开始日期：" + data["qsrq"].toString()
                            holder.tvF.visibility = View.VISIBLE
                            holder.tvF.text = "预计成交日期：" + data["zzrq"].toString()
                            holder.itemView.setOnClickListener {
                                startActivity(Intent(mActivity, SalesOpportunitiesActivity::class.java)
                                        .putExtra("billid", data["id"].toString()))
                            }
                        }
                    }
                }
            }

        }
        prv_view.adapter = mAdapter
    }

    override fun onRefresh(pullToRefreshLayout: PullToRefreshLayout?) {
        page_number = 1
        mParmMap["curpage"] = page_number.toString()
        http(0)
    }

    override fun onLoadMore(pullToRefreshLayout: PullToRefreshLayout?) {
        mParmMap["curpage"] = (page_number + 1).toString()
        http(1)
    }

    fun http(i: Int) {
        presenter.post(i, ServerURL.BROADCASTPROMPTXX, mParmMap)
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    override fun returnData(requestCode: Int, data: Any) {
        LogUtils.e(data.toString())
        if (TextUtils.isEmpty(data.toString())) {
            showShortToast("没有更多内容")
            return
        }

        var list = PaseJson.paseJsonToObject(data.toString()) as ArrayList<HashMap<String, Any>>
        if (list == null || list.size == 0) {
            showShortToast("没有更多内容")

        } else if (requestCode == 0)
            gztxList.clear()
        else
            page_number = page_number + 1
        gztxList.addAll(list)
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