package com.cr.activity.jxc.cggl

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.cr.activity.common.CommonXzphActivity
import com.cr.activity.jxc.ckgl.kcpd.KtSerialNumberAddActivity
import com.cr.activity.tjfx.kcbb.TjfxKcbbSpjg2Activity
import com.cr.tools.FigureTools
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.model.Serial
import com.update.utils.EditTextHelper
import kotlinx.android.synthetic.main.activity_cggl_spxq.*
import java.io.Serializable


/**
 * 采购管理商品详情(销售订单不带序列号)(采购订单、销售订单)
 */
class KtCgglSpxq2Activity : BaseActivity<BaseP>() {

    var data = HashMap<String, Any>()
    override fun initVariables() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_cggl_spxq
    }

    override fun init() {

        if (intent.hasExtra("object")) {
            data = intent.getExtras().getSerializable("object") as HashMap<String, Any>
            if(data["name"]==null){
                tv_spmc.text = "名称：" + data["goodsname"].toString()
            }else {
                tv_spmc.text = "名称：" + data["name"].toString()
            }
            if(data["name"]==null){
                tv_spbm.text = "编码：" + data["goodscode"].toString()
            }else {
                tv_spbm.text = "编码：" + data["code"].toString()
            }
            tv_spgg.text = "规格：" + data["specs"].toString()
            tv_spxh.text = "型号：" + data["model"].toString()
            if (data["onhand"] != null) {
                tv_spkz.text = "库存：" + FigureTools.sswrFigure(data["onhand"]!!.toString().toDouble()) + data["unitname"].toString()
            }

            //是批次商品的会显示批号、生产日期、有效日期
            if (data["batchctrl"].toString().equals("T")) {
//                tv_sl.visibility = View.VISIBLE
                ll_pcsp.visibility = View.VISIBLE
                et_cpph.setText(data["batchcode"].toString())
                et_scrq.setText(data["produceddate"].toString())
                et_yxqz.setText(data["validdate"].toString())
            } else {
                ll_pcsp.visibility = View.GONE
//                slv_sl.visibility = View.VISIBLE
//                tv_sl.visibility = View.GONE
            }
            slv_sl.sl = data["unitqty"].toString().toDouble()
            et_dj.setText(FigureTools.sswrFigure(data["unitprice"].toString()))//单价
            et_sl.setText(FigureTools.sswrFigure(data["taxrate"].toString()))//税率
            EditTextHelper.EditTextEnable(!intent.getBooleanExtra("issj", true), et_sl)
            tv_hsdj.setText(FigureTools.sswrFigure(data["taxunitprice"].toString()))//含税单价
        }
        et_dj.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s)) {
                    data["unitprice"] = s.toString()
                    val csje = data["unitprice"].toString().toDouble() * (data["taxrate"].toString().toDouble() + 100) / 100
                    data["taxunitprice"] = FigureTools.sswrFigure(csje)
                    tv_hsdj.setText(data["taxunitprice"].toString())//含税单价
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        et_sl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s)) {
                    data["taxrate"] = s.toString()

                    val csje = data["unitprice"].toString().toDouble() * (s.toString().toDouble() + 100) / 100
                    data["taxunitprice"] = FigureTools.sswrFigure(csje)
                    tv_hsdj.setText(data["taxunitprice"].toString())//含税单价
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        titlebar.setTitleText(mActivity, "商品详情")
        titlebar.setRightText("保存")
        //保存
        titlebar.setTitleOnlicListener { i ->
            when (i) {
                2 -> {
                    if (this.data["serialctrl"].toString().equals("T")) {
                        data["unitqty"] = tv_sl.text.toString()
                        if (data["unitqty"].toString().toInt() == 0) {
                            showShortToast("请选择序列号")
                            return@setTitleOnlicListener
                        }

                    } else {
                        data["unitqty"] = slv_sl.sl.toString()
                    }
                    val intent = Intent()
                    intent.putExtra("object", data as Serializable)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                }
            }
        }
        tv_serial_number.visibility=View.GONE
//        tv_serial_number.setOnClickListener {
//            val intent = Intent(mActivity, KtSerialNumberAddActivity::class.java)
//            intent.putExtra("itemno", "0")
//            intent.putExtra("uuid", data["serialinfo"].toString())
//            intent.putExtra("position", 0)
//            intent.putExtra("DATA", Gson().toJson(data["serials"]))
//            startActivityForResult(intent, 11)
//        }
        /**
         * 选择价格
         */
        xzjg_iv.setOnClickListener {
            val intent = Intent()
            intent.setClass(mActivity, TjfxKcbbSpjg2Activity::class.java)
            intent.putExtra("goodsid", data["goodsid"].toString())
            intent.putExtra("storied", getIntent().getExtras().getString("rkckId"))
            intent.putExtra("unitid", data["unitid"].toString())
            intent.putExtra("clientid", "0")
            intent.putExtra("index", "0")
            startActivityForResult(intent, 3)
        }
        //产品批号
        ll_cpph.setOnClickListener {
            val intent = Intent()
            intent.setClass(mActivity, CommonXzphActivity::class.java)
            intent.putExtra("storied", getIntent().getExtras().getString("rkckId"))
            intent.putExtra("goodsid", data["goodsid"].toString())
            if(getIntent().getStringExtra("type")!=null){
                intent.putExtra("type",getIntent().getStringExtra("type"))
            }
            startActivityForResult(intent, 0)
        }

        //生产日期
        ll_scrq.setOnClickListener {
            date_init(et_scrq)
        }
        //有效期至
        ll_yxqz.setOnClickListener {
            date_init(et_yxqz)
        }
        bt_sc.setOnClickListener {
            val intent = Intent()
            intent.putExtra("object", "")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun onMyActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onMyActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                0 -> {
                    this.data["batchrefid"] =data.getExtras().getString("id")
                    this.data["batchcode"] = data.getExtras().getString("name")
                    this.data["produceddate"] = data.getExtras()!!.getString("scrq")
                    this.data["validdate"] = data.getExtras()!!.getString("yxrq")
                    et_cpph.setText(this.data["batchcode"].toString())
                    et_scrq.setText(this.data["produceddate"].toString())
                    et_yxqz.setText(this.data["validdate"].toString())
//                cpphId = data.getExtras()!!.getString("id")
                }
                3 -> {
                    this.data["unitprice"] =FigureTools.sswrFigure(data.getExtras().getString("dj"))
                    et_dj.setText(this.data["unitprice"].toString())//单价
                }
                11 -> {
                   this.data["serials"]= Gson().fromJson<Any>(data.extras!!.getString("DATA"), object : TypeToken<List<Serial>>() {
                    }.type)
                }
            }
        }
    }
}