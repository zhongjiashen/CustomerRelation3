package com.cr.activity.jxc.cggl.cgfk

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cr.activity.BaseActivity
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcActivity
import com.cr.adapter.jxc.cggl.cgfk.JxcCgglCgfkAddAdapter
import com.cr.tools.PaseJson
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R

import kotlinx.android.synthetic.main.activity_jxc_cggl_cgfk_detail.*
import java.io.Serializable

import java.util.HashMap

class KtJxcCgglCgfkDetailActivity:BaseActivity(){
  var billid: String? = null // 选择完关联的单据后返回的单据的ID
    var gysId: String? = ""
    var lxrId:String? = ""
    var jbrId:String? = ""
    var rkckId = ""
    var fklxId:String? = ""
    var jsfsId:String? = ""
    var zjzhId:String? = ""
    var selectIndex: Int = 0
    var list = ArrayList<MutableMap<String, Any?>>()
    var data = HashMap<String, Any>()
    var adapter= JxcCgglCgfkAddAdapter(list, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_cggl_cgfk_detail)
        billid = this.intent.extras!!.getString("billid")
        searchDate()
        setOnClick()
        addFHMethod()
        xzsp_listview.adapter=adapter

    }

    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {
        xzsp_listview.setOnItemClickListener { parent, view, position, id ->
            selectIndex = position
            val intent = Intent()
            intent.setClass(activity, JxcCgglCgfkXzyyDetail2Activity::class.java)
            val map = list[position]
            map["cname"] = gys_edittext.getText().toString()
            intent.putExtra("object", map as Serializable)
            startActivityForResult(intent, 4)
        }
        sh_button.setOnClickListener {
            intent.putExtra("tb", "tb_pay")
            intent.putExtra("opid", data["opid"].toString())
            intent.putExtra("billid",
                    this.intent.extras!!.getString("billid"))
            intent.setClass(activity, JxcCgglCgddShlcActivity::class.java)
            startActivityForResult(intent, 10)
        }

        sd_button.setOnClickListener {
            if (ShareUserInfo.getKey(activity, "sc") != "1") {
                showToastPromopt("你没有该权限，请向管理员申请权限！")
                return@setOnClickListener
            }
            AlertDialog.Builder(activity)
                    .setTitle("确定要删除当前记录吗？")
                    .setNegativeButton("删除"
                    ) { arg0, arg1 -> searchDateSd() }.setPositiveButton("取消", null).show()
        }



    }
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            when (requestCode) {
                10 -> {
                    searchDate()
                    setResult(Activity.RESULT_OK)
                }

            }
        }
    }
    /**
     * 连接网络的操作(查询主表的内容)
     */
    private fun searchDate() {
        val parmMap = HashMap<String, Any?>()
        parmMap["dbname"] = ShareUserInfo.getDbName(mContext)
        parmMap["parms"] = "CGFK"
        parmMap["billid"] = billid
        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false)
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private fun searchDate2() {
        val parmMap = HashMap<String, Any?>()
        parmMap["dbname"] = ShareUserInfo.getDbName(mContext)
        parmMap["parms"] = "CGFK"
        parmMap["billid"] = billid
        findServiceData2(2, ServerURL.BILLDETAIL, parmMap, false)
    }

    /**
     * 连接网络的操作(删单)
     */
    private fun searchDateSd() {
        val parmMap = HashMap<String, Any>()
        parmMap["dbname"] = ShareUserInfo.getDbName(mContext)
        parmMap["opid"] = ShareUserInfo.getUserId(mContext)
        parmMap["tabname"] = "tb_pay"
        parmMap["pkvalue"] = this.intent.extras!!.getString("billid")
        findServiceData2(3, ServerURL.BILLDELMASTER, parmMap, false)
    }
    override fun executeSuccess() {
        if (returnSuccessType == 1) {// 管理单据成功后把信息填到里面（主表）
            if (returnJson != "") {
                data = (PaseJson
                        .paseJsonToObject(returnJson) as List<HashMap<String, Any>>)[0]
                gys_edittext.setText(data.get("cname").toString())
                if (data.get("ispp").toString() == "0") {
                    fklx_edittext.setText("应付账款")
                    show_yyd_layout.setVisibility(View.VISIBLE)
                    fkje_edittext.setEnabled(false)
                } else if (data.get("ispp").toString() == "1") {
                    fklx_edittext.setText("预付账款")
                    show_yyd_layout.setVisibility(View.GONE)
                    fkje_edittext.setEnabled(true)
                }
                jsfs_edittext.setText(data.get("paytypename").toString())
                zjzh_edittext.setText(data.get("bankname").toString())
                dqyf_edittext.setText(data.get("suramt").toString())
                dqyf2_edittext.setText(data.get("balance").toString())
                fkje_edittext.setText(data.get("amount").toString())
                cyfje_edittext.setText(data.get("yufuje").toString())
                sfje_edittext.setText(data.get("factamount").toString())
                djrq_edittext.setText(data.get("billdate").toString())
                et_bm.setText(data.get("depname").toString())//部门
                jbr_edittext.setText(data.get("empname").toString())
                bzxx_edittext.setText(data.get("memo").toString())
                gysId = data.get("clientid").toString()
                fklxId = data.get("ispp").toString()
                jsfsId = data.get("paytypeid").toString()
                zjzhId = data.get("bankid").toString()
                jbrId = data.get("exemanid").toString()
                djbh_textview.setText(data.get("code").toString())
                if (data.get("shzt").toString() == "0") {
                    sh_imageview.setBackgroundResource(R.drawable.wsh)
                } else if (data.get("shzt").toString() == "1") {
                    sh_imageview.setBackgroundResource(R.drawable.ysh)
                } else if (data.get("shzt").toString() == "2") {
                    sh_imageview.setBackgroundResource(R.drawable.shz)
                }
                showZdr(data)
                searchDate2()// 查询订单中的商品
            }
        } else if (returnSuccessType == 2) {// 管理单据成功后把信息填到里面（从表）
            if (returnJson == "") {
                return
            }
            list.clear()
            list.addAll(PaseJson
                    .paseJsonToObject(returnJson) as List<MutableMap<String, Any?>>)
            //			list=(List<Map<String, Object>>) PaseJson
            //					.paseJsonToObject(returnJson);
            xzspnum_textview.setText("共选择了" + list.size + "引用")
            adapter.notifyDataSetChanged()
            var b = 0.0
            for (map in list) {
                b += java.lang.Double.parseDouble(
                        if (map.get("bcjs") == null)
                            map.get("amount").toString()
                        else
                            map.get("bcjs").toString())
            }
//            fkje_edittext.setText(b.toString() + "")
        } else if (returnSuccessType == 3) {
            if (returnJson == "") {
                showToastPromopt("删除成功")
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                showToastPromopt("删除失败" + returnJson.substring(5))
            }
        }
    }
}
