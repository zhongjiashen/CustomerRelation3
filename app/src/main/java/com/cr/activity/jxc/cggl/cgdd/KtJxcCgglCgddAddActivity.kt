package com.cr.activity.jxc.cggl.cgdd

import android.annotation.SuppressLint
import android.app.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.BaseAdapter


import com.cr.activity.BaseActivity
import com.cr.activity.common.*
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddAddAdapter
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter
import com.cr.tools.FigureTools
import com.cr.tools.PaseJson
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.update.actiity.choose.ChooseDepartmentActivity
import com.update.actiity.choose.KtXzfplxActivity
import com.update.actiity.choose.SelectSalesmanActivity
import com.update.actiity.project.ChoiceProjectActivity
import com.update.utils.LogUtils

import kotlinx.android.synthetic.main.activity_jxc_cggl_cgdd_add.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

import java.util.*

class KtJxcCgglCgddAddActivity : BaseActivity() {
    var gysId: String? = ""
    var lxrId: String? = ""
    var mTypesname: String? = null// 单位类型
    // 部门id
    var departmentid: String? = ""
    var jbrId: String? = ""

    //发票类型ID
    var billtypeid: String? = ""
    //项目ID
    var projectid: String? = ""
    private var time: Long = 0
    internal var billid: String? = null                                  //选择完关联的单据后返回的单据的ID
    var list = ArrayList<Map<String, Any?>>()
    private var selectIndex: Int = 0
    var adapter: BaseAdapter = JxcCgglCgddAddAdapter(list, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_cggl_cgdd_add)
        setOnClick()
        xzsp_listview.adapter = adapter;
    }


    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {
        bzxx_edittext.setOnTouchListener { v, event ->
            v.getParent().requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.getParent().requestDisallowInterceptTouchEvent(
                        false)
            }
            false
        }

        mTogBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                add_scrollview.setVisibility(View.GONE)
                gldjcg_linearlayout.setVisibility(View.VISIBLE)
            } else {
                add_scrollview.setVisibility(View.VISIBLE)
                gldjcg_linearlayout.setVisibility(View.GONE)
            }
        }

        /**
         * 选择商品
         */
        xzsp_linearlayout.setOnClickListener {
            val intent = Intent()
            intent.putExtra("rkckId", "0")
            intent.putExtra("tabname", "tb_porder")
            intent.setClass(this, JxcCgglCgddXzsp2Activity::class.java)
            startActivityForResult(intent, 0)
        }
        /**
         * 选择供应商
         */
        gys_edittext.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, CommonXzdwActivity::class.java)
            intent.putExtra("type", "2")
            startActivityForResult(intent, 1)
        }
        //交货日期
        et_jhrq.setOnClickListener {
            date_init(et_jhrq)
        }
        //单据日期
        djrq_edittext.setOnClickListener {
            date_init(djrq_edittext)
        }
        //联系人选择
        lxr_edittext.setOnClickListener {
            val intent = Intent()
            if (gysId == "") {
                showToastPromopt("请先选择供应商信息")
            } else {
                intent.setClass(activity, CommonXzlxrActivity::class.java)
                intent.putExtra("clientid", gysId)
                startActivityForResult(intent, 2)
            }
        }
        //保存单据
        save_imagebutton.setOnClickListener {
            if (time == 0L || System.currentTimeMillis() - time > 5000) {
                searchDateSave()//保存
                time = System.currentTimeMillis()
            } else {
                showToastPromopt("请不要频繁点击，防止重复保存")

            }

        }
        xzxsdd_linearlayout.setOnClickListener {
            val intent = Intent()
            if (rkck_edittext.getText().toString() == "") {
                showToastPromopt("请先选择仓库")

            } else {
                intent.putExtra("type", "CGDD_XSDD")
                intent.putExtra("clientid", "0")
                intent.putExtra("reftypeid", "1")
                intent.setClass(activity, CommonXzyyActivity::class.java)
                startActivityForResult(intent, 5)
            }
        }
        //关联单据仓库选择
        rkck_edittext.setOnClickListener {
            val intent = Intent()
            intent.setClass(activity, CommonXzzdActivity::class.java)
            intent.putExtra("type", "STORE")
            startActivityForResult(intent, 6)
        }
        /**
         * 选择发票类型
         */
        et_fplx.setOnClickListener {
            val intent = Intent()
            intent.setClass(activity, KtXzfplxActivity::class.java)
            intent.putExtra("djlx", "0")
            startActivityForResult(intent, 3)
        }
        //部门选择
        et_bm.setOnClickListener {
            startActivityForResult(Intent(this, ChooseDepartmentActivity::class.java), 7)
        }
        //业务员选择
        jbr_edittext.setOnClickListener {
            val intent = Intent()
            if (TextUtils.isEmpty(departmentid))
                showToastPromopt("请先选择部门")
            else
                startActivityForResult(Intent(this, SelectSalesmanActivity::class.java).putExtra("depid", departmentid), 8)
//            intent.setClass(activity, CommonXzjbrActivity::class.java)
//            startActivityForResult(intent, 8)
        }
        /**
         * 选择的商品条目点击事件
         */
        xzsp_listview.setOnItemClickListener { parent, view, position, id ->
            selectIndex = position
            val intent = Intent()
            intent.setClass(activity, JxcCgglCgddXzspDetailActivity::class.java)
//                intent.putExtra("rkckId", value)
            intent.putExtra("object", list!!.get(position) as Serializable)
            startActivityForResult(intent, 4)
        }
        et_xgxm.setOnClickListener {
            if (TextUtils.isEmpty(gysId))
                showToastPromopt("请先选择供应商")
            else
                startActivityForResult(Intent(this, ChoiceProjectActivity::class.java)
                        .putExtra("clientid", gysId)
                        .putExtra("clientname", gys_edittext.text.toString())
                        .putExtra("typesname", mTypesname), 9)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (data != null) {
                when (requestCode) {
                // 选择商品
                    0 -> {
                        val cpList = data?.getSerializableExtra("object") as List<MutableMap<String, Any?>>
                        var zje = 0.0
                        for (i in cpList.indices) {
                            var map = cpList[i]
                            if (map["isDetail"] == "0") {
                                if (map["ischecked"] == "1") {
                                    var map2 = cpList[i + 1]
                                    map["unitprice"] = map2["dj"]
                                    map["unitqty"] = map2["sl"]
                                    val amount = (java.lang.Double.parseDouble(map2["dj"].toString()) * java.lang.Double
                                            .parseDouble(map2["sl"].toString())).toString() + ""
                                    map["amount"] = FigureTools.sswrFigure(amount + "")
                                    map["disc"] = map2["zkl"]
                                    map["batchcode"] = map2["cpph"]
                                    map["produceddate"] = map2["scrq"]
                                    map["validdate"] = map2["yxqz"]
                                    list.add(map)
                                    //                            zje += Double.parseDouble(map.get("amount").toString());
                                }
                            }
                        }
                        for (m in list) {
                            LogUtils.e(m.toString())
                            zje += java.lang.Double.parseDouble(m["amount"].toString())
                        }

                        xzspnum_textview.text = "共选择了" + list.size + "商品"
                        hjje_edittext.setText("￥" + FigureTools.sswrFigure(zje))
                        adapter.notifyDataSetChanged()
                    }
                //供应商
                    1 -> {
                        if (gys_edittext.getText().toString() != data!!.getExtras()!!.getString("name")) {
                            lxr_edittext.setText(data.getStringExtra("lxrname"))
                            lxrId = data.getStringExtra("lxrid");
                            lxdh_edittext.setText(data.getStringExtra("phone"))
                            gys_edittext.setText(data!!.getExtras()!!.getString("name"))
                            if (data != null) {
                                mTypesname = data.getStringExtra("typesname")
                                gysId = data.getExtras()!!.getString("id")
                            }
                        }
                    }
                // 联系人
                    2 -> {
                        lxr_edittext.setText(data.getExtras()!!.getString("name"))
                        lxrId = data.getExtras()!!.getString("id")
                        lxdh_edittext.setText(data.getExtras()!!.getString("phone"))
                    }
                //选择发票类型
                    3 -> {
                        et_fplx.setText(data.extras!!.getString("name"))
                        billtypeid = data.extras.getString("id")
                    }
                //修改选中的商品的详情
                    4 -> {
                        if (data.extras!!.getSerializable("object")!!.toString() == "") {//说明删除了
                            list.removeAt(selectIndex)
                            adapter.notifyDataSetChanged()
                        } else {
                            val map = data.extras!!
                                    .getSerializable("object") as MutableMap<String, Any>
                            list.removeAt(selectIndex)
                            map.put(
                                    "amount",
                                    java.lang.Double.parseDouble(map["unitprice"].toString()) * java.lang.Double.parseDouble(map["unitqty"].toString()))
                            list.add(selectIndex, map)
                            adapter.notifyDataSetChanged()
                        }
                        xzspnum_textview.setText("共选择了" + list.size + "商品")
                        var zje = 0.0
                        for (i in list.indices) {
                            val map = list[i]
                            zje += java.lang.Double.parseDouble(map["amount"].toString())
                        }
                        hjje_edittext.setText("￥" + FigureTools.sswrFigure(zje.toString() + "") + "")
                    }
                //选中单据成功后返回
                    5 -> {
                        add_scrollview.setVisibility(View.VISIBLE)//隐藏关联销售单据的Linearlayout
                        gldjcg_linearlayout.setVisibility(View.GONE)//显示展示详情的Linearlayout信息
                        mTogBtn.setSelected(false)
                        list.clear()
                        list.addAll(data.extras!!.getSerializable("list") as List<Map<String, Any>>)
                        xzspnum_textview.setText("共选择了" + list.size + "商品")

                        adapter.notifyDataSetChanged()
                        hjje_edittext.setText(data.extras!!.getString("totalAmount"))
                    }
                //关联单据仓库选择
                    6 -> {
                        rkck_edittext.setText(data.extras!!.getString("dictmc"))
                    }

                //部门选择结果处理
                    7 -> {
                        departmentid = data.getStringExtra("CHOICE_RESULT_ID")
                        et_bm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"))
                        jbr_edittext.setText("")
                        jbrId = ""
                    }
                // 经办人
                    8 -> {
                        jbr_edittext.setText(data.getStringExtra("CHOICE_RESULT_TEXT"))
                        jbrId = data.getStringExtra("CHOICE_RESULT_ID")
                    }
                    //相关项目选择结果处理
                    9 -> {
                        projectid = data.getStringExtra("projectid")
                        et_xgxm.setText(data.getStringExtra("title"))
                    }

                }
            }
        }

    }

    /**
     * 连接网络的操作(保存)
     */
    private fun searchDateSave() {
        if (gys_edittext.getText().toString() == "") {
            showToastPromopt("供应商不能为空")
            return
        }
        if (list.size == 0) {
            showToastPromopt("请选择商品")
            return
        }
        if (djrq_edittext.getText().toString() == "") {
            showToastPromopt("请选择单据日期")
            return
        }
        if (jbr_edittext.getText().toString() == "") {
            showToastPromopt("请选择业务员")
            return
        }
        val arrayMaster = JSONArray()
        val arrayDetail = JSONArray()
        try {
            val jsonObject = JSONObject()
            jsonObject.put("billid", "0")
            jsonObject.put("code", "")
            jsonObject.put("clientid", gysId)//供应商ID
            jsonObject.put("linkmanid", lxrId)//联系人ID
            jsonObject.put("phone", lxdh_edittext.getText().toString())//电话
            jsonObject.put("billto", jhdz_edittext.getText().toString())//送货地址
            jsonObject.put("billtypeid", billtypeid)//发票类型ID
            jsonObject.put("revdate", et_jhrq.getText().toString())//交货日期
            jsonObject.put("projectid", projectid)//项目ID
            jsonObject.put("billdate", djrq_edittext.getText().toString())//单据日期
            val hjje = hjje_edittext.getText().toString()
            jsonObject.put("amount", hjje.replace("￥", ""))
            jsonObject.put("departmentid", departmentid)//部门ID
            jsonObject.put("exemanid", jbrId)//经办人
            jsonObject.put("memo", bzxx_edittext.getText().toString())
            jsonObject.put("opid", ShareUserInfo.getUserId(context))
            arrayMaster.put(jsonObject)
            for (map in list) {
                val jsonObject2 = JSONObject()
                jsonObject2.put("billid", "0")
                jsonObject2.put("itemno", "0")
                jsonObject2.put("goodsid", map["goodsid"].toString())
                jsonObject2.put("unitid", map["unitid"].toString())
                jsonObject2.put("unitprice", map["unitprice"].toString())
                jsonObject2.put("unitqty", map["unitqty"].toString())
                val disc = map["disc"].toString()
                //                if(disc.length()>0){
                //                	jsonObject2.put("disc", disc.substring(0,disc.lastIndexOf(".")));
                //                }else{
                //                	jsonObject2.put("disc", "");
                //                }
                jsonObject2.put("disc", disc)
                jsonObject2.put("amount", map["amount"].toString())
                jsonObject2.put("batchcode", map["batchcode"].toString())
                jsonObject2.put("produceddate", map["produceddate"].toString())
                jsonObject2.put("validdate", map["validdate"].toString())
                jsonObject2.put("refertype", if (map["refertype"] == null) "" else map["refertype"].toString())
                jsonObject2.put("batchrefid", if (map["batchrefid"] == null) "" else map["batchrefid"].toString())
                jsonObject2.put("referbillid ", if (map["referbillid"] == null) "" else map["referbillid"].toString())
                jsonObject2.put("referitemno ", if (map["referitemno"] == null) "" else map["referitemno"].toString())
                jsonObject2.put("taxrate", "17.00")//税率%
                jsonObject2.put("taxunitprice", "117.00")//含税单价
                jsonObject2.put("memo", "")//备注
                arrayDetail.put(jsonObject2)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //代表新增
        val parmMap = HashMap<String, Any>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap["tabname"] = "tb_porder"
        parmMap["parms"] = "CGDD"
        parmMap["master"] = arrayMaster.toString()
        parmMap["detail"] = arrayDetail.toString()
        findServiceData2(0, ServerURL.BILLSAVE, parmMap, false)
    }

    override fun executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson == "") {
                showToastPromopt("保存成功")
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                showToastPromopt("保存失败" + returnJson.substring(5))
            }
        } else if (returnSuccessType == 1) {//管理单据成功后把信息填到里面（主表）
            if (returnJson == "") {
                return
            }
            val `object` = (PaseJson
                    .paseJsonToObject(returnJson) as List<Map<String, Any>>)[0]
            gys_edittext.setText(`object`["cname"].toString())
            lxr_edittext.setText(`object`["lxrname"].toString())
            lxdh_edittext.setText(`object`["phone"].toString())
            jhdz_edittext.setText(`object`["billto"].toString())
            hjje_edittext.setText(`object`["amount"].toString())
            djrq_edittext.setText(`object`["billdate"].toString())
            jbr_edittext.setText(`object`["empname"].toString())
            bzxx_edittext.setText(`object`["memo"].toString())
            gysId = `object`["clientid"].toString()
            lxrId = `object`["lxrid"].toString()
            jbrId = `object`["empid"].toString()
            searchDate2()//查询订单中的商品
        } else if (returnSuccessType == 2) {//管理单据成功后把信息填到里面（从表）
            list = PaseJson.paseJsonToObject(returnJson) as ArrayList<Map<String, Any?>>
            adapter = JxcCgglCgddDetailAdapter(list, this)
            xzspnum_textview.setText("共选择了" + list.size + "商品")
            xzsp_listview.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private fun searchDate2() {
        val parmMap = HashMap<String, Any?>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        parmMap["parms"] = "CGDD"
        parmMap["billid"] = billid
        findServiceData2(2, ServerURL.REFBILLDETAIL, parmMap, false)
    }

}

