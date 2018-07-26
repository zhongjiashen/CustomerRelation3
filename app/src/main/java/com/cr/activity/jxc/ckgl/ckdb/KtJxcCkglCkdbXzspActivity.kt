package com.cr.activity.jxc.ckgl.ckdb

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.cr.activity.BaseActivity
import com.cr.activity.common.CommonXzphActivity
import com.cr.activity.common.CommonXzsplbActivity
import com.cr.common.XListView
import com.cr.myinterface.SLViewValueChange
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.base.BaseRecycleAdapter
import com.update.model.KtFplxData
import com.update.utils.LogUtils
import com.update.viewbar.refresh.PullToRefreshLayout
import com.update.viewholder.ViewHolderFactory

import kotlinx.android.synthetic.main.activity_jxc_ckgl_kcpd_xzsp.*
import java.io.Serializable

import java.text.SimpleDateFormat
import java.util.*

class KtJxcCkglCkdbXzspActivity : BaseActivity(), PullToRefreshLayout.OnRefreshListener {
    override fun onRefresh(pullToRefreshLayout: PullToRefreshLayout?) {
        mList.clear()
        currentPage = 1
        searchDate()
    }

    override fun onLoadMore(pullToRefreshLayout: PullToRefreshLayout?) {
        currentPage++
        searchDate()
    }

    //    internal var qsrq: String?=""
//    internal var jzrq:String?=""
    var code: String? = ""
    var storeid: String? = ""
    var mList = ArrayList<KtJxcCkgdbXzspData>()
    var mAdapter: BaseRecycleAdapter<ViewHolderFactory.JxcCkglKcpdXzspHolder, KtJxcCkgdbXzspData>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_ckgl_kcpd_xzsp)
        setOnClick()
        addFHMethod()
        if (this.intent.hasExtra("storeid")) {
            storeid = this.intent.extras!!.getString("storeid")
        }
//        val calendar = Calendar.getInstance()
//        calendar.time = Date()
//        val sdf = SimpleDateFormat("yyyy-MM-")
//        qsrq = sdf.format(Date()) + "01"
//        jzrq = sdf.format(Date()) + calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        searchDate()
//        prl_view.setOnRefreshListener(this)
        search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                mList.clear()
                currentPage = 1
                searchDate()
                return@OnEditorActionListener true
            }
            false
        })
        mAdapter = object : BaseRecycleAdapter<ViewHolderFactory.JxcCkglKcpdXzspHolder, KtJxcCkgdbXzspData>(mList) {
            override fun MyonCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
                return ViewHolderFactory.getJxcCkglKcpdXzspHolder(activity, parent)
            }

            override fun MyonBindViewHolder(holder: ViewHolderFactory.JxcCkglKcpdXzspHolder?, data: KtJxcCkgdbXzspData) {
                if (holder != null) {
                    holder.etMc.setText("名称：" + data.name)
                    holder.etBh.setText("编号：" + data.code)
                    holder.etGg.setText("规格：" + data.specs)
                    holder.etXh.setText("型号：" + data.model)
                    holder.etKc.setText("库存：" + data.onhand + data.unitname)
                    holder.tvCpph.setText(data.cpph)
                    holder.tvScrq.setText(data.scrq)
                    holder.tvYxqz.setText(data.yxqz)
                    if (!TextUtils.isEmpty(data.sl))
                        holder.slView.sl = data.sl.toDouble()
                    holder.cbView.isChecked = data.isCheck
                    if (data.isCheck) {
                        holder.llNumber.visibility = View.VISIBLE
                        if (data.batchctrl.equals("T"))
                            holder.llV.visibility = View.VISIBLE
                    } else {
                        holder.llNumber.visibility = View.GONE
                        holder.llV.visibility = View.GONE
                    }

                    holder.slView.setOnValueChange { sl ->
                        //数量控件数量变换监听
                        data.sl = sl.toString()

                    }
                    holder.cbView.setOnClickListener {
                        data.isCheck = holder.cbView.isChecked
                        if (data.isCheck) {
                            holder.llNumber.visibility = View.VISIBLE
                            if (data.batchctrl.equals("T"))
                                holder.llV.visibility = View.VISIBLE
                        } else {
                            holder.llNumber.visibility = View.GONE
                            holder.llV.visibility = View.GONE
                        }
                    }

                    holder.llCpph.setOnClickListener {
                        val intent = Intent()
                        intent.setClass(activity, CommonXzphActivity::class.java)
                        intent.putExtra("goodsid", data.goodsid
                                .toString())
                        intent.putExtra("storied", storeid)
                        intent.putExtra("index", holder.layoutPosition)
                        startActivityForResult(intent, 0)
                    }

                }
            }

        }
//        prv_view.adapter = mAdapter
    }

    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {
        fl.setOnClickListener {
            intent.setClass(activity, CommonXzsplbActivity::class.java)
            startActivityForResult(intent, 2)
        }
        qr_textview.setOnClickListener {
            for (data in mList) {


                    if (data.isCheck ) {

                        if (data.sl == "0" || data.sl == "") {
                            Toast.makeText(activity, "请输入已选择商品的数量信息", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        if (data.batchctrl == "T" && data.cpph == "") {
                            Toast.makeText(activity, "选择的批号商品，必须填写批号信息", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        //                        map2.put("dj",FigureTools.sswrFigure(map2.get("dj").toString()) );
                    }
                }

            intent.putExtra("object", mList as Serializable)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                0 -> {
                    val index = Integer.parseInt(data.getExtras()!!
                            .getString("index"))
                    mList.get(index).cpph = data.getExtras()!!.getString("name")
                    mList.get(index).scrq = data.getExtras()!!.getString("scrq")
                    mList.get(index).yxqz = data.getExtras()!!.getString("yxrq")
                    mAdapter?.notifyDataSetChanged()
                }
                2 -> {
                    code = data?.getExtras()!!.getString("id")
                    mList.clear()
                    searchDate()
                }
            }
        }
    }

    /**
     * 连接网络的操作
     */
    private fun searchDate() {
        val parmMap = HashMap<String, Any?>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        parmMap["tabname"] = "tb_allot"
        parmMap["goodscode"] = ""
        parmMap["goodstype"] = code
        parmMap["storeid"] = storeid
        parmMap["goodsname"] = search.getText().toString()
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap["curpage"] = currentPage
        parmMap["pagesize"] = pageSize
        findServiceData2(0, ServerURL.SELECTGOODS, parmMap, false)
    }

    override fun executeSuccess() {
//        prl_view.refreshFinish(true)//刷新完成
//        prl_view.loadMoreFinish(true)//加载完成
        val gson = Gson()
        mList!!.addAll(gson.fromJson<ArrayList<KtJxcCkgdbXzspData>>(returnJson,
                object : TypeToken<ArrayList<KtJxcCkgdbXzspData>>() {
                }.type))
        mAdapter!!.notifyDataSetChanged()
    }

}