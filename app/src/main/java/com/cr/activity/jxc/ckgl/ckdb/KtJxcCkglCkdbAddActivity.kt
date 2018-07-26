package com.cr.activity.jxc.ckgl.ckdb

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.cr.activity.BaseActivity
import com.cr.activity.common.CommonXzzdActivity
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.update.actiity.choose.SelectSalesmanActivity
import kotlinx.android.synthetic.main.activity_jxc_ckgl_ckdb_add.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

/**
 * 进销存-仓库管理-仓库调拨-增加
 *
 * @author Administrator
 */
class KtJxcCkglCkdbAddActivity : BaseActivity() {
    var jbrId: String? = ""
    var rkckId: String? = ""
    var ckckId: String? = ""
    var time: Long = 0
    var mDepartmentid: String? = ""//部门ID
    var mList = ArrayList<KtJxcCkgdbXzspData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_ckgl_ckdb_add)
        setOnClick()
        addFHMethod()
    }

    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {
        //出库仓库
        ckck_edittext.setOnClickListener {
            var intent = Intent()
            intent.setClass(activity, CommonXzzdActivity::class.java)
            intent.putExtra("type", "STORE")
            startActivityForResult(intent, 0)
        }
        //入库仓库
        rkck_edittext.setOnClickListener {
            var intent = Intent()
            intent.setClass(activity, CommonXzzdActivity::class.java)
            intent.putExtra("type", "STORE")
            startActivityForResult(intent, 1)
        }
        //单据日期
        djrq_edittext.setOnClickListener {
            date_init(djrq_edittext)
        }
        xzsp_linearlayout.setOnClickListener {
            if (ckck_edittext.text.toString() == "") {
                showToastPromopt("请先选择出库仓库！")
                return@setOnClickListener
            }
            intent.putExtra("storeid", ckckId)
            intent.setClass(this, KtJxcCkglCkdbXzspActivity::class.java)
            startActivityForResult(intent, 2)
        }
        //部门
        et_bm.setOnClickListener {
            startActivityForResult(Intent(this, com.update.actiity.choose.ChooseDepartmentActivity::class.java), 4)
        }
        //业务员
        jbr_edittext.setOnClickListener {
            if (TextUtils.isEmpty(mDepartmentid))
                showToastPromopt("请先选择部门")
            else
                startActivityForResult(Intent(this, SelectSalesmanActivity::class.java)
                        .putExtra("depid", mDepartmentid), 5)
        }
        //保存
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
        if (data != null) {
            when (requestCode) {
                0 -> {
                    if (ckck_edittext.getText().toString() != data.extras!!.getString("dictmc")) {
                        ckck_edittext.setText(data.extras!!.getString("dictmc"))
                        ckckId = data.extras!!.getString("id")
                        mList.clear()
//                        adapter.notifyDataSetChanged()
                    }
                }
                1 -> {
                    rkck_edittext.setText(data.extras!!.getString("dictmc"))
                    rkckId = data.extras!!.getString("id")
                }
                2 -> {

                }
                3 -> {

                }
                4 -> {
                    mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID")
                    et_bm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"))
                    jbrId = ""
                    jbr_edittext.setText("")
                }
                5 -> {
                    jbr_edittext.setText(data.extras!!.getString("CHOICE_RESULT_TEXT"))
                    jbrId = data.extras!!.getString("CHOICE_RESULT_ID")
                }
            }
        }
    }

    /**
     * 连接网络的操作(保存)
     */
    private fun searchDateSave() {
        if (ckck_edittext.getText().toString() == "") {
            showToastPromopt("请选择出库仓库")
            return
        } else if (rkck_edittext.getText().toString() == "") {
            showToastPromopt("请选择入库仓库")
            return
        } else if (mList.size == 0) {
            showToastPromopt("请选择商品")
            return
        } else if (djrq_edittext.getText().toString() == "") {
            showToastPromopt("请选择单据日期")
            return
        } else if (rkckId == ckckId) {
            showToastPromopt("调出仓库和调入仓库不能相同！")
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
            jsonObject.put("insid", rkckId)
            jsonObject.put("outsid", ckckId)
            jsonObject.put("totalamt", "0")
            jsonObject.put("departmentid", mDepartmentid)
            jsonObject.put("exemanid", jbrId)
            jsonObject.put("memo", bzxx_edittext.getText().toString())
            jsonObject.put("opid", ShareUserInfo.getUserId(context))
            arrayMaster.put(jsonObject)
            for (data in mList) {
                val jsonObject2 = JSONObject()
                jsonObject2.put("billid", "0")
                jsonObject2.put("itemno", "0")
                jsonObject2.put("goodsid", data.goodsid.toString())
                jsonObject2.put("unitid", data.unitid.toString())
                jsonObject2.put("unitprice", "")
                jsonObject2.put("unitqty", data.sl.toString())
                jsonObject2.put("amount", "0")
                jsonObject2.put("batchcode", data.cpph.toString())
                jsonObject2.put("produceddate", data.scrq.toString())
                jsonObject2.put("validdate", data.yxqz.toString())
//                jsonObject2.put("batchrefid", data.bif (map.get("batchrefid") == null) "" else map.get("batchrefid").toString())
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
        parmMap["tabname"] = "tb_allot"
        parmMap["parms"] = "CKDB"
        parmMap["master"] = arrayMaster.toString()
        parmMap["detail"] = arrayDetail.toString()
        findServiceData2(0, "billsavenew", parmMap, false)
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
        }
    }
}
