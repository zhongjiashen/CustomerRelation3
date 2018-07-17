package com.cr.activity.jxc.cggl.cgsh

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import com.cr.activity.BaseActivity
import com.cr.activity.common.CommonXzdwActivity
import com.cr.activity.common.CommonXzyyActivity
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddXzspActivity
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddXzspDetailActivity

import com.cr.adapter.jxc.cggl.cgsh.JxcCgglCgshAddAdapter
import com.cr.tools.FigureTools
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.update.actiity.choose.ChooseDepartmentActivity
import com.update.actiity.choose.SelectSalesmanActivity


import kotlinx.android.synthetic.main.activity_jxc_cggl_cgsh_add.*

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.util.ArrayList
import java.util.HashMap

class KtJxcCgglCgshAddActivity: BaseActivity() {
    var time: Long = 0
    var gysId: String? = ""
    var lxrId = ""
    var jbrId: String? = ""
    var rkckId: String? = ""
    var jsfsId: String? = ""
    var zjzhId: String? = ""
    var xmId: String? = ""
    var mTypesname: String? = ""// 单位类型
    var mDepartmentid: String? = ""//部门ID
    var fklxId: String? = ""
    // 部门id
    var departmentid: String? = ""
    var selectIndex: Int = 0
    var list = ArrayList<Map<String, Any?>>()
    var yyList: MutableList<Map<String, Any>> = ArrayList()
    var adapter = JxcCgglCgshAddAdapter(list, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_cggl_cgsh_add)
        setOnClick()
        addFHMethod()
        xzsp_listview.adapter = adapter

        fklxId = "0"
        
    }

    /**
     * 连接网络的操作(保存)
     */
    private fun searchDateSave() {
        if (rkck_edittext.getText().toString() == "") {
            showToastPromopt("请选择入库仓库")
            return
        } else if (gys_edittext.getText().toString() == "") {
            showToastPromopt("请选择供应商")
            return
        } else if (list.size == 0) {
            showToastPromopt("请选择商品")
            return
        } else if (fklx_edittext.getText().toString() == "") {
            showToastPromopt("请选择付款类型")
            return
        } else if (fklxId == "1") {
            val hjje = java.lang.Double.parseDouble(if (hjje_edittext.getText().toString().replace("￥", "") == "") "0" else hjje_edittext.getText().toString().replace("￥", ""))
            val fkje = java.lang.Double.parseDouble(if (fkje_edittext.getText().toString().replace("￥", "") == "") "0" else fkje_edittext.getText().toString().replace("￥", ""))
            if (fkje <= 0 || fkje > hjje) {
                showToastPromopt("付款金额不在范围内！")
                return
            } else if (zjzh_edittext.getText().toString() == "") {
                showToastPromopt("请选择资金账户")
                return
            }
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
            jsonObject.put("billdate", djrq_edittext.getText().toString())
            jsonObject.put("storeid", rkckId)
            jsonObject.put("ispp", fklxId)
            jsonObject.put("paytypeid", jsfsId)
            jsonObject.put("bankid", zjzhId)
            jsonObject.put("receipt", fkje_edittext.getText().toString())
            jsonObject.put("privilege", "")
            val hjje = hjje_edittext.getText().toString()
            jsonObject.put("totalamt", hjje.replace("￥", ""))
            jsonObject.put("clientid", gysId)//供应商ID
            jsonObject.put("linkmanid", lxrId)//联系人ID
            jsonObject.put("phone", lxdh_edittext.getText().toString())
            jsonObject.put("projectid", xmId)
            //            jsonObject.put("billto", jhdz_edittext.getText().toString());
            jsonObject.put("departmentid", mDepartmentid)
            jsonObject.put("exemanid", jbrId)
            //            String hjje = hjje_edittext.getText().toString();
            //            jsonObject.put("amount", hjje.replace("￥", ""));
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
                jsonObject2.put("disc", disc)
                jsonObject2.put("amount", map["amount"].toString())
                jsonObject2.put("batchcode", map["batchcode"].toString())
                jsonObject2.put("produceddate", map["produceddate"].toString())
                jsonObject2.put("validdate", map["validdate"].toString())
                jsonObject2.put("refertype", if (map["refertype"] == null) "" else map["refertype"].toString())
                jsonObject2.put("batchrefid", if (map["batchrefid"] == null) "" else map["batchrefid"].toString())
                jsonObject2.put("referbillid ", if (map["referbillid"] == null) "" else map["referbillid"].toString())
                jsonObject2.put("referitemno ", if (map["referitemno"] == null) "" else map["referitemno"].toString())
                arrayDetail.put(jsonObject2)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //代表新增
        val parmMap = HashMap<String, Any>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap["tabname"] = "tb_received"
        parmMap["parms"] = "CGSH"
        parmMap["master"] = arrayMaster.toString()
        parmMap["detail"] = arrayDetail.toString()
        findServiceData2(0, ServerURL.BILLSAVE, parmMap, false)
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
        //单据日期
        djrq_edittext.setOnClickListener {
            date_init(djrq_edittext)
        }
        xzxsdd_linearlayout.setOnClickListener {
            val intent = Intent()
            if (rkck_edittext.getText().toString() == "") {
                showToastPromopt("请先选择仓库")

            } else {
                intent.putExtra("rkckId", rkckId)
                intent.putExtra("tabname", "tb_received")
                intent.setClass(this, JxcCgglCgddXzspActivity::class.java)
                startActivityForResult(intent, 0)

            }
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
        /**
         * 选择的商品条目点击事件
         */
        xzsp_listview.setOnItemClickListener { parent, view, position, id ->
            selectIndex = position
            val intent = Intent()
            intent.setClass(activity, JxcCgglCgddXzspDetailActivity::class.java)
            intent.putExtra("rkckId", rkckId)
            intent.putExtra("object", list[position] as Serializable)
            startActivityForResult(intent, 4)
        }
        //部门选择
        et_bm.setOnClickListener {
            startActivityForResult(Intent(this, ChooseDepartmentActivity::class.java), 7)
        }
        //业务员选择
        jbr_edittext.setOnClickListener {

            if (TextUtils.isEmpty(departmentid))
                showToastPromopt("请先选择部门")
            else
                startActivityForResult(Intent(this, SelectSalesmanActivity::class.java).putExtra("depid", departmentid), 8)

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
                            val map = cpList[i]
                            if (map["isDetail"] == "0") {
                                if (map["ischecked"] == "1") {
                                    val map2 = cpList[i + 1]
                                    map["unitprice"] = map2["dj"]
                                    map["unitqty"] = map2["sl"]
                                    val amount = (java.lang.Double.parseDouble(map2["dj"].toString()) * java.lang.Double.parseDouble(map2["sl"].toString())).toString() + ""
                                    map["amount"] = FigureTools.sswrFigure(amount + "")
                                    map["disc"] = map2["zkl"]
                                    map["batchcode"] = map2["cpph"]
                                    map["produceddate"] = map2["scrq"]
                                    map["validdate"] = map2["yxqz"]
                                    list.add(map)
                                }
                            }
                        }
                        for (m in list) {
                            zje += java.lang.Double.parseDouble(m["amount"].toString())
                        }
                        xzspnum_textview.setText("共选择了" + list.size + "商品")
                        hjje_edittext.setText("￥" + FigureTools.sswrFigure(zje.toString() + "") + "")
                        adapter.notifyDataSetChanged()
                    }

                    1 -> {
                        if (gys_edittext.getText().toString() != "") {
                            if (gys_edittext.getText().toString() != data.extras!!.getString("name")) {
                                list.removeAll(yyList)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        if (gys_edittext.getText().toString() != data.extras!!.getString("name")) {
                            lxr_edittext.setText("")
                            lxrId = ""
                            lxdh_edittext.setText("")
                            gys_edittext.setText(data.extras!!.getString("name"))
                            gysId = data.extras!!.getString("id")
                        }
                        mTypesname = data.getStringExtra("typesname")
                        gys_edittext.setText(data.extras!!.getString("name"))
                        gysId = data.extras!!.getString("id")
                        gysqk_edittext.setText(data.extras!!.getString("qk"))
                        // 清楚項目
                        xm_edittext.setText("")
                        xmId = ""

                    }
                // 联系人
                    2 -> {
                        lxr_edittext.setText(data.getExtras()!!.getString("name"))
                        lxrId = data.getExtras()!!.getString("id")
                        lxdh_edittext.setText(data.getExtras()!!.getString("phone"))
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
                //部门选择结果处理
                    7 -> {
                        val id = data.getStringExtra("CHOICE_RESULT_ID")
                        if (!departmentid.equals(id)) {
                            departmentid = id
                            et_bm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"))
                            jbr_edittext.setText("")
                            jbrId = ""
                        }
                    }
                // 经办人
                    8 -> {
                        jbr_edittext.setText(data.getStringExtra("CHOICE_RESULT_TEXT"))
                        jbrId = data.getStringExtra("CHOICE_RESULT_ID")
                    }
                }
            }
        }

    }

    override fun executeSuccess() {
        if (returnSuccessType == 0) run {
            if (returnJson == "") {
                showToastPromopt("保存成功")
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                showToastPromopt("保存失败" + returnJson.substring(5))
            }
        }
    }

}