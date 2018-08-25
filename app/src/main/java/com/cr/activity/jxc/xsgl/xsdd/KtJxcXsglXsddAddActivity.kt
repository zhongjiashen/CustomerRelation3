package com.cr.activity.jxc.xsgl.xsdd

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.cr.activity.BaseActivity
import com.cr.activity.common.*
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddXzsp2Activity
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddXzspDetailActivity
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter
import com.cr.adapter.jxc.xsgl.xsdd.JxcXsglXsddDetailAdapter
import com.cr.tools.FigureTools
import com.cr.tools.PaseJson
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.update.actiity.WeChatCaptureActivity
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
import java.text.SimpleDateFormat
import java.util.*

/**
 * 进销存-销售管理-销售订单-增加
 *
 * @author Administrator
 *
 */
class KtJxcXsglXsddAddActivity : BaseActivity() {
    var billid: String? = ""//选择完关联的单据后返回的单据的ID
    var gysId: String? = ""
    var lxrId: String? = ""
    var mTypesname: String? = null// 单位类型
    // 部门id
    var departmentid: String? = ""
    var jbrId: String? = ""

    //发票类型ID
    var billtypeid: String? = ""
    var mTaxrate: String? = ""//税率
    //项目ID
    var projectid: String? = ""
    private var time: Long = 0
    var list = ArrayList<MutableMap<String, Any?>>()
    var selectIndex: Int = 0
    var adapter = JxcXsglXsddDetailAdapter(list, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_xsgl_xsdd_add)
        setOnClick()
        addFHMethod()
        xzsp_listview.adapter = adapter
        if (this.intent.hasExtra("dwObject")) {
            val map = this.intent.extras!!.getSerializable("dwObject") as Map<String, Any>
            gys_edittext.setText(map["cname"].toString())
            gysId = map["id"].toString()
        }


        departmentid = ShareUserInfo.getKey(this, "departmentid")
        et_bm.setText(ShareUserInfo.getKey(this, "depname"))
        jbr_edittext.setText(ShareUserInfo.getKey(this, "opname"))
        jbrId = ShareUserInfo.getKey(this, "empid")


        et_fplx.setText("收据")
        billtypeid="1"
        mTaxrate="0"

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        djrq_edittext.setText(sdf.format(Date()))

        departmentid = ShareUserInfo.getKey(this, "departmentid")
        et_bm.setText(ShareUserInfo.getKey(this, "depname"))
        jbr_edittext.setText(ShareUserInfo.getKey(this, "opname"))
        jbrId = ShareUserInfo.getKey(this, "empid")
    }

    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {
        /**
         * 选择商品
         */
        xzsp_linearlayout.setOnClickListener {
            val intent = Intent()
            intent.putExtra("issj", et_fplx.getText().toString() == "收据")
            intent.putExtra("taxrate", mTaxrate)
            intent.putExtra("tabname", "tb_sorder")
            intent.setClass(this, JxcCgglCgddXzsp2Activity::class.java)
            startActivityForResult(intent, 0)
        }
        /**
         * 选择供应商
         */
        gys_edittext.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, CommonXzkhActivity::class.java)
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
                showToastPromopt("请先选择客户信息")
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
            intent.setClass(activity, JxcXsglXsddActivity::class.java)
            intent.putExtra("select", "true")
            startActivityForResult(intent, 5)
        }

        /**
         * 选择发票类型
         */
        et_fplx.setOnClickListener {
            val intent = Intent()
            intent.setClass(activity, KtXzfplxActivity::class.java)
            intent.putExtra("djlx", "1")
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
                        .putExtra("dwmc", true)
                        .putExtra("typesname", mTypesname), 9)
        }
        /**
         * 扫描选择商品
         */
        iv_scan.setOnClickListener {
            startActivityForResult(Intent(this, WeChatCaptureActivity::class.java), 11)
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
                                    val amount = (java.lang.Double.parseDouble(map2["taxunitprice"].toString()) * java.lang.Double
                                            .parseDouble(map2["sl"].toString())).toString() + ""
                                    map["amount"] = FigureTools.sswrFigure(amount + "")
                                    map["disc"] = map2["zkl"]
                                    map["batchcode"] = map2["cpph"]
                                    map["produceddate"] = map2["scrq"]
                                    map["validdate"] = map2["yxqz"]

                                    map["taxrate"] = map2["taxrate"]//税率
                                    map["taxunitprice"] = map2["taxunitprice"]//含税单价
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
                        if (gys_edittext.getText().toString() != "") {
                            if (gys_edittext.getText().toString() != data.extras!!.getString("name")) {
                                list.clear()
                                adapter.notifyDataSetChanged()
                            }
                        }
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
                        if (billtypeid == data.getStringExtra("id"))
                            return
                        et_fplx.setText(data.extras!!.getString("name"))
                        billtypeid = data.extras.getString("id")
                        mTaxrate = data.extras.getString("taxrate")
                        if (list != null) {
                            for (i in list.indices) {
                                list[i]["taxrate"] =mTaxrate
                                val csje = java.lang.Double.parseDouble(list[i]["unitprice"].toString()) * (java.lang.Double.parseDouble(mTaxrate) + 100) / 100
                                list[i].put("taxunitprice", csje.toString() + "")
                                val amount = (csje * java.lang.Double.parseDouble(list[i]["unitqty"].toString())).toString() + ""
                                list[i].put("amount", FigureTools.sswrFigure(amount + ""))
                            }
                            var je = 0.0
                            for (m in list) {
                                je += java.lang.Double.parseDouble(m["amount"].toString())
                            }

                            hjje_edittext.setText("￥" + FigureTools.sswrFigure(je.toString() + "") + "")
                            adapter.notifyDataSetChanged()
                        }
                    }
                //修改选中的商品的详情
                    4 -> {
                        if (data.extras!!.getSerializable("object")!!.toString() == "") {//说明删除了
                            list.removeAt(selectIndex)
                            adapter.notifyDataSetChanged()
                        } else {
                            val map = data.extras!!
                                    .getSerializable("object") as MutableMap<String, Any?>
                            list.removeAt(selectIndex)
                            map.put(
                                    "amount",
                                    map["taxunitprice"].toString().toDouble() * map["unitqty"].toString().toDouble())
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
                        billid = data.extras!!.getString("billid")
                        searchDate()//查询主表的数据填充
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
                //相关项目选择结果处理
                    9 -> {
                        projectid = data.getStringExtra("projectid")
                        et_xgxm.setText(data.getStringExtra("title"))
                    }

                //扫一扫选择商品
                    11->{
                        val intent = Intent()
                        intent.putExtra("issj", et_fplx.getText().toString() == "收据")
                        intent.putExtra("taxrate", mTaxrate)
                        intent.putExtra("barcode", data.getStringExtra("qr"))
                        intent.putExtra("tabname", "tb_sorder")
                        intent.setClass(this, JxcCgglCgddXzsp2Activity::class.java)
                        startActivityForResult(intent, 0)
//                        val parmMap = java.util.HashMap<String, Any>()
//                        parmMap["dbname"] = ShareUserInfo.getDbName(context)
//                        parmMap["tabname"] = "tb_sorder"
//                        parmMap["storeid"] = "0"
//                        parmMap["goodscode"] = ""
//                        parmMap["goodstype"] =""
//                        parmMap["goodsname"] = ""
//                        parmMap["goodsname"] = ""
//                        // parmMap.put("opid", ShareUserInfo.getUserId(context));
//                        parmMap["barcode"] = "12001"//新增条码
//                        parmMap["curpage"] = currentPage
//                        parmMap["pagesize"] = pageSize
//                        findServiceData2(3, ServerURL.SELECTGOODS, parmMap, false)
                    }
                }
            }
        }

    }

    override fun executeSuccess() {
        when(returnSuccessType){
            0->{
                if (returnJson == "") {
                    showToastPromopt("保存成功")
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    showToastPromopt("保存失败" + returnJson.substring(5))
                }
            }
            //扫一扫选择商品
            3->{
                var map =(PaseJson.paseJsonToObject(returnJson) as ArrayList<MutableMap<String, Any?>>)[0]
                map["unitprice"] = map["aprice"]//单价
                map["unitqty"] = "1"
                map["disc"] = "100"
                map["batchcode"] = ""
                map["produceddate"] = ""
                map["validdate"] = ""
                map["validdate"] = ""
                map["taxrate"] = mTaxrate//税率
                val csje = map["aprice"].toString().toDouble() * (mTaxrate.toString().toDouble() + 100) / 100
                map["taxunitprice"] =csje.toString()
                map["amount"] = FigureTools.sswrFigure(csje.toString())

                map["issj"] =(et_fplx.getText().toString() == "收据")
                list.add(map)
                adapter.notifyDataSetChanged()
                xzspnum_textview.setText("共选择了" + list.size + "商品")
            }
        }
        /*else if (returnSuccessType == 1) {//管理单据成功后把信息填到里面（主表）
            if (returnJson == "") {
                return
            }
            val `object` = (PaseJson
                    .paseJsonToObject(returnJson) as List<Map<String, Any>>)[0]
            gys_edittext.setText(`object`["cname"].toString())
            lxr_edittext.setText(`object`["lxrname"].toString())
            lxdh_edittext.setText(`object`["phone"].toString())
            jhdz_edittext.setText(`object`["billto"].toString())
            hjjeEditText.setText(`object`["amount"].toString())
            djrqEditText.setText(`object`["billdate"].toString())
            jbrEditText.setText(`object`["empname"].toString())
            bzxxEditText.setText(`object`["memo"].toString())
            gysId = `object`["clientid"].toString()
            lxrId = `object`["lxrid"].toString()
            jbrId = `object`["empid"].toString()
            searchDate2()//查询订单中的商品
        } else if (returnSuccessType == 2) {//管理单据成功后把信息填到里面（从表）
            list = PaseJson.paseJsonToObject(returnJson) as List<Map<String, Any>>
            adapter = JxcCgglCgddDetailAdapter(list, this)
            xzspnumTextView.setText("共选择了" + list.size + "商品")
            listview.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }*/
    }

    /**
     * 连接网络的操作(保存)
     */
    private fun searchDateSave() {
        if (gys_edittext.getText().toString() == "") {
            showToastPromopt("请选择客户")
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
//        if (jbr_edittext.getText().toString() == "") {
//            showToastPromopt("请选择业务员")
//            return
//        }
        val arrayMaster = JSONArray()
        val arrayDetail = JSONArray()
        try {
            val jsonObject = JSONObject()
            jsonObject.put("billid", "0")
            jsonObject.put("code", "")
            jsonObject.put("billdate", djrq_edittext.getText().toString())
            jsonObject.put("clientid", gysId)//供应商ID
            jsonObject.put("linkmanid", lxrId)//联系人ID
            jsonObject.put("phone", lxdh_edittext.getText().toString())
            jsonObject.put("billtypeid", billtypeid)//发票类型ID
            jsonObject.put("takedate", et_jhrq.getText().toString())//交货日期
            jsonObject.put("projectid", projectid)//项目ID
            jsonObject.put("departmentid", departmentid)//部门ID
            jsonObject.put("exemanid", jbrId)//经办人
            jsonObject.put("shipto", jhdz_edittext.getText().toString())
            jsonObject.put("amount", hjje_edittext.getText().toString().replace("￥", ""))
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
                jsonObject2.put("batchrefid", if (map["batchrefid"] == null) "" else map["batchrefid"].toString())
                jsonObject2.put("refertype", "")
                jsonObject2.put("referbillid ", "")
                jsonObject2.put("referitemno ", "")
                jsonObject2.put("taxrate", map["taxrate"].toString())//税率%
                jsonObject2.put("taxunitprice",  map["taxunitprice"].toString())//含税单价
                jsonObject2.put("memo", "")//备注
                arrayDetail.put(jsonObject2)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //代表新增
        val parmMap = HashMap<String, Any>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        parmMap["tabname"] = "tb_sorder"
        parmMap["parms"] = "XSDD"
        parmMap["master"] = arrayMaster.toString()
        parmMap["detail"] = arrayDetail.toString()
        findServiceData2(0, ServerURL.BILLSAVE, parmMap, false)
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
    private fun searchDate() {
        val parmMap = HashMap<String, Any?>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)
        parmMap["parms"] = "CGDD"
        parmMap["billid"] = billid
        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false)
    }
}