package com.cr.activity.jxc.cggl.cgfk

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.widget.BaseAdapter
import com.cr.activity.BaseActivity
import com.cr.activity.common.CommonXzdwActivity

import com.cr.adapter.jxc.cggl.cgfk.JxcCgglCgfkAddAdapter
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.update.actiity.choose.ChooseDepartmentActivity
import com.update.actiity.choose.SelectSalesmanActivity



import kotlinx.android.synthetic.main.activity_jxc_cggl_cgfk_add.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.util.ArrayList
import java.util.HashMap

class KtJxcCgglCgfkAddActivity : BaseActivity() {
    var time: Long = 0
    var gysId: String? = ""
    var lxrId = ""
    var jbrId:String? = ""
    var rkckId:String? = ""
    var fklxId:String? = ""
    var jsfsId:String? = ""
    var zjzhId:String? = ""
    // 部门id
    var departmentid: String? = ""
    var selectIndex: Int = 0
    var list = ArrayList<Map<String, Any?>>()
    var adapter = JxcCgglCgfkAddAdapter(list, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_cggl_cgfk_add)
        setOnClick()
        addFHMethod()
        xzsp_listview.adapter = adapter
        xzspnum_textview.setText("共选择了" + list.size + "引用")
        fklx_edittext.setText("应付账款")
        fklxId = "0"
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
        //选择引用单号
        xzyydh_linearlayout.setOnClickListener {
            val intent = Intent()
            if (gysId == "") {
                showToastPromopt("请选择供应商")
            } else {
                intent.putExtra("type", "CGFK_BILL")
                intent.putExtra("clientid", gysId)
                intent.setClass(this, JxcCgglCgfkXzyyActivity::class.java)
                startActivityForResult(intent, 0)
            }
        }
        //供应商选择
        gys_edittext.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, CommonXzdwActivity::class.java)
            intent.putExtra("type", "2")
            startActivityForResult(intent, 1)
        }
        xzsp_listview.setOnItemClickListener { parent, view, position, id ->
            selectIndex = position
            val intent = Intent()
            intent.setClass(activity, JxcCgglCgfkXzyyDetailActivity::class.java)
            intent.putExtra("object", list[position] as Serializable)
            startActivityForResult(intent, 4)
        }
        //单据日期
        djrq_edittext.setOnClickListener {
            date_init(djrq_edittext)
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
                //选择引用单号结果处理
                    0 -> {
                        val cpList = data?.getSerializableExtra("object") as List<MutableMap<String, Any?>>
                        list.addAll(cpList)
                        xzspnum_textview.setText("共选择了" + list.size + "引用")
                        var b = 0.0
                        for (map in list) {
                            b += java.lang.Double.parseDouble(
                                    if (map["bcjs"] == null)
                                        map["amount"].toString()
                                    else
                                        map["bcjs"].toString())
                        }
                        fkje_edittext.setText(b.toString() + "")
                        adapter.notifyDataSetChanged()
                    }
                //供应商选择结果处理
                    1 -> {
                        if (gys_edittext.getText().toString() != "") {
                            if (gys_edittext.getText().toString() != data.extras!!.getString("name")) {
                                list.clear()
                                adapter.notifyDataSetChanged()
                            }
                        }
                        gys_edittext.setText(data.extras!!.getString("name"))
                        gysId = data.extras!!.getString("id")
                        dqyf_edittext.setText(data.extras!!.getString("yf"))
                        dqyf2_edittext.setText(data.extras!!.getString("yf2"))
                    }
                //修改选中的引用的详情
                    4 -> {
                        if (data.extras!!.getSerializable("object")!!.toString() == "") {// 说明删除了
                            list.removeAt(selectIndex)
                            adapter.notifyDataSetChanged()
                        } else {
                            val map = data.extras!!
                                    .getSerializable("object") as MutableMap<String, Any>
                            list.removeAt(selectIndex)
                            // map.put(
                            // "amount",
                            // map.put("amount",
                            // Double.parseDouble(map.get("unitprice").toString())
                            // * Double.parseDouble(map.get("unitqty").toString())));
                            list.add(selectIndex, map)
                            adapter.notifyDataSetChanged()
                        }
                        xzspnum_textview.setText("共选择了" + list.size + "引用")
                        var b = 0.0
                        for (map in list) {
                            b += java.lang.Double.parseDouble(
                                    if (map["bcjs"] == null)
                                        map["amount"].toString()
                                    else
                                        map["bcjs"].toString())
                        }
                        fkje_edittext.setText(b.toString() + "")
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
            showToastPromopt("请选择供应商！")
            return
        } else if (fklx_edittext.getText().toString() == "") {
            showToastPromopt("请选择付款类型")
            return
        }

        if (zjzh_edittext.getText().toString() == "") {
            showToastPromopt("请选择资金账户")
            return
        }
        if (fklxId == "0") {
            if (list.size == 0) {
                showToastPromopt("请选择引用单号")
                return
            }
        }
        if (djrq_edittext.getText().toString() == "") {
            showToastPromopt("请选择单据日期")
            return
        }
        if (fkje_edittext.getText().toString() == "") {
            showToastPromopt("付款金额不能为空！")
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
            // jsonObject.put("storeid", rkckId);
            jsonObject.put("ispp", fklxId)
            jsonObject.put("paytypeid", jsfsId)
            jsonObject.put("bankid", zjzhId)
            jsonObject.put("clientid", gysId)// 供应商ID
            jsonObject.put("factamount", fkje_edittext.getText().toString())
            jsonObject.put("exemanid", jbrId)
            jsonObject.put("amount", fkje_edittext.getText().toString())
            jsonObject.put("memo", bzxx_edittext.getText().toString())
            jsonObject.put("opid", ShareUserInfo.getUserId(context))
            arrayMaster.put(jsonObject)
            for (map in list) {
                val jsonObject2 = JSONObject()
                jsonObject2.put("billid", "0")
                jsonObject2.put("itemno", "0")
                jsonObject2.put("refertype", map["billtypeid"].toString())
                jsonObject2.put("referbillid ", map["billid"].toString())
                jsonObject2.put("amount ", map["bcjs"].toString())
                arrayDetail.put(jsonObject2)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        // 代表新增
        val parmMap = HashMap<String, Any>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap["tabname"] = "tb_pay"
        parmMap["parms"] = "CGFK"
        parmMap["master"] = arrayMaster.toString()
        if (list.size == 0) {
            parmMap["detail"] = ""
        } else {
            parmMap["detail"] = arrayDetail.toString()
        }
        findServiceData2(0, ServerURL.BILLSAVE, parmMap, false)
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
