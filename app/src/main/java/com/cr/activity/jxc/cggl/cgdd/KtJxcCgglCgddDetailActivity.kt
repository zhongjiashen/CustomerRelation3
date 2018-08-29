package com.cr.activity.jxc.cggl.cgdd

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.cr.activity.BaseActivity
import com.cr.activity.jxc.cggl.KtCgglSpxqCk2Activity
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter
import com.cr.tools.PaseJson
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import kotlinx.android.synthetic.main.activity_jxc_cggl_cgdd_detail.*
import java.io.Serializable
import java.util.HashMap

class KtJxcCgglCgddDetailActivity : BaseActivity() {
    var data = HashMap<String, Any>()
    var gysId: String? = null
    var lxrId: String? = null
    var jbrId: String? = null
    var shzt: String? = null // 社会状态
    var selectIndex: Int = 0
    var list = ArrayList<MutableMap<String, Any?>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_cggl_cgdd_detail)
        searchDate()
        setOnClick()
        addFHMethod()
    }

    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {
        xzsp_listview.setOnItemClickListener { parent, view, position, id ->
            selectIndex = position
            val intent = Intent()
//            intent.setClass(activity, JxcCgglCgddXzspDetail2Activity::class.java)
            intent.setClass(activity, KtCgglSpxqCk2Activity::class.java)
            intent.putExtra("object", list.get(position) as Serializable)
            startActivityForResult(intent, 4)
        }

        sh_button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("billid",
                    this.intent.extras!!.getString("billid"))
            intent.putExtra("tb", "tb_porder")
            intent.putExtra("opid", data.get("opid").toString())
            intent.setClass(activity, JxcCgglCgddShlcActivity::class.java)
            startActivityForResult(intent, 5)
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
                5 -> {
                    searchDate()
                }

            }
        }
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
    private fun searchDate() {
        val parmMap = HashMap<String, Any>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        parmMap["parms"] = "CGDD"
        parmMap["billid"] = this.intent.extras!!.getString("billid")
        findServiceData2(0, ServerURL.BILLMASTER, parmMap, false)
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private fun searchDate2() {
        val parmMap = HashMap<String, Any>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        parmMap["parms"] = "CGDD"
        parmMap["billid"] = this.intent.extras!!.getString("billid")
        findServiceData2(1, ServerURL.BILLDETAIL, parmMap, false)
    }

    /**
     * 连接网络的操作(删单)
     */
    private fun searchDateSd() {
        val parmMap = HashMap<String, Any>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        parmMap["opid"] = ShareUserInfo.getUserId(context)
        parmMap["tabname"] = "tb_porder"
        parmMap["pkvalue"] = this.intent.extras!!.getString("billid")
        findServiceData2(2, ServerURL.BILLDELMASTER, parmMap, false)
    }

    override fun executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson == "") {
                return
            }
            data = (PaseJson
                    .paseJsonToObject(returnJson) as List<HashMap<String, Any>>)[0]
            gys_edittext.setText(data.get("cname").toString())
            lxr_edittext.setText(data.get("lxrname").toString())
            lxdh_edittext.setText(data.get("phone").toString())
            jhdz_edittext.setText(data.get("billto").toString())
            et_fplx.setText(data.get("billtypename").toString())//发票类型
            et_xgxm.setText(data.get("projectname").toString())//相关项目
            et_jhrq.setText(data.get("revdate").toString())//交货日期
            hjje_edittext.setText(data.get("amount").toString())
            djrq_edittext.setText(data.get("billdate").toString())
            et_bm.setText(data.get("depname").toString())//部门
            jbr_edittext.setText(data.get("empname").toString())
            bzxx_edittext.setText(data.get("memo").toString())
            bzxx_edittext.setOnTouchListener(View.OnTouchListener { view, event ->
                // TODO Auto-generated method stub
                view.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(
                            false)
                }
                false
            })
            djbh_textview.setText(data.get("code").toString())
            if (data.get("shzt").toString() == "0") {
                sh_imageview.setBackgroundResource(R.drawable.wsh)
            } else if (data.get("shzt").toString() == "1") {
                sh_imageview.setBackgroundResource(R.drawable.ysh)
            } else if (data.get("shzt").toString() == "2") {
                sh_imageview.setBackgroundResource(R.drawable.shz)
            }
            gysId = data.get("clientid").toString()
            lxrId = data.get("lxrid").toString()
            jbrId = data.get("empid").toString()
            shzt = data.get("shzt").toString()
            showZdr(data)
            searchDate2()// 查询订单中的商品
        } else if (returnSuccessType == 1) {
            list = PaseJson
                    .paseJsonToObject(returnJson) as ArrayList<MutableMap<String, Any?>>
            var adapter = JxcCgglCgddDetailAdapter(list, this)
            xzspnum_textview.setText("共选择了" + list.size + "商品")
            xzsp_listview.adapter = adapter
            adapter.notifyDataSetChanged()
        } else if (returnSuccessType == 2) {
            if (returnJson == "") {
                showToastPromopt("删除成功")
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                showToastPromopt("删除失败" + returnJson.substring(5))
            }
        } else if (returnSuccessType == 3) {
            if (returnJson == "") {
                showToastPromopt("操作成功")
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                showToastPromopt("操作失败" + returnJson.substring(5))
            }
        }
    }
}
