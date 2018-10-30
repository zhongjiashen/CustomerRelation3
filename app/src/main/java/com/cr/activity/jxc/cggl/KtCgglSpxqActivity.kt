package com.cr.activity.jxc.cggl

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.cr.activity.common.CommonXzphActivity
import com.cr.activity.jxc.ckgl.kcpd.KtSerialNumberAddActivity
import com.cr.tools.FigureTools
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.model.Serial
import com.update.utils.EditTextHelper
import com.update.utils.LogUtils
import kotlinx.android.synthetic.main.activity_cggl_spxq.*
import java.io.Serializable


/**
 * 采购管理商品详情（采购收货、采购退货、销售开单、销售退货）
 */
class KtCgglSpxqActivity : BaseActivity<BaseP>() {

    var data = HashMap<String, Any>()
    override fun initVariables() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_cggl_spxq
    }

    override fun init() {

        if (intent.hasExtra("object")) {
            data = intent.getExtras().getSerializable("object") as HashMap<String, Any>
            if (data["name"] == null) {
                tv_spmc.text = "名称：" + data["goodsname"].toString()
            } else {
                tv_spmc.text = "名称：" + data["name"].toString()
            }
            if (data["name"] == null) {
                tv_spbm.text = "编码：" + data["goodscode"].toString()
            } else {
                tv_spbm.text = "编码：" + data["code"].toString()
            }
            tv_spgg.text = "规格：" + data["specs"].toString()
            tv_spxh.text = "型号：" + data["model"].toString()
            if (data["onhand"] != null) {
                tv_spkz.text = "库存：" + data["onhand"]!!.toString().toDouble() + data["unitname"].toString()
            }
            //是批次商品的会显示批号、生产日期、有效日期
            if (data["batchctrl"].toString().equals("T")) {
                ll_pcsp.visibility = View.VISIBLE
                et_cpph.setText(data["batchcode"].toString())
                et_scrq.setText(data["produceddate"].toString())
                et_yxqz.setText(data["validdate"].toString())
            } else {
                ll_pcsp.visibility = View.GONE
            }
            //严格序列号商品处理
            if (data["serialctrl"].toString().equals("T")) {
                LogUtils.e("严格序列商品")
                slv_sl.visibility = View.GONE
                tv_sl.visibility = View.VISIBLE
            } else {
                slv_sl.visibility = View.VISIBLE
                tv_sl.visibility = View.GONE
            }
            tv_sl.text = data["unitqty"].toString()
            slv_sl.sl = data["unitqty"].toString().toDouble()
            et_dj.setText(data["unitprice"].toString())//单价
            et_sl.setText(data["taxrate"].toString())//税率
            EditTextHelper.EditTextEnable(!intent.getBooleanExtra("issj", true), et_sl)
            tv_hsdj.setText(data["taxunitprice"].toString())//含税单价
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
                    data["taxunitprice"] = csje.toString()
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
                            showShortToast("商品数量不能为0")
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

        tv_serial_number.setOnClickListener {
            val intent = Intent(mActivity, KtSerialNumberAddActivity::class.java)
            intent.putExtra("itemno", "0")
            intent.putExtra("uuid", data["serialinfo"].toString())
            intent.putExtra("position", 0)


            intent.putExtra("storied", this.intent.getStringExtra("rkckId"))
            LogUtils.e(intent.getStringExtra("rkckId"))
            intent.putExtra("goodsid", data["goodsid"].toString())
            intent.putExtra("isStrict", data["serialctrl"].toString().equals("T"))
            intent.putExtra("DATA", Gson().toJson(data["serials"]))
            startActivityForResult(intent, 11)
        }

        //产品批号
        ll_cpph.setOnClickListener {
            val intent = Intent()
            intent.setClass(mActivity, CommonXzphActivity::class.java)
            intent.putExtra("storied", getIntent().getExtras().getString("rkckId"))
            intent.putExtra("goodsid", data["goodsid"].toString())
            if (getIntent().getStringExtra("type") != null) {
                intent.putExtra("type", getIntent().getStringExtra("type"))
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
                    this.data["batchrefid"] = data.getExtras().getString("id")
                    this.data["batchcode"] = data.getExtras().getString("name")
                    this.data["produceddate"] = data.getExtras()!!.getString("scrq")
                    this.data["validdate"] = data.getExtras()!!.getString("yxrq")
                    et_cpph.setText(this.data["batchcode"].toString())
                    et_scrq.setText(this.data["produceddate"].toString())
                    et_yxqz.setText(this.data["validdate"].toString())
//                cpphId = data.getExtras()!!.getString("id")
                }
                11 -> {
                    var serials = Gson().fromJson<List<Serial>>(data.extras!!.getString("DATA"), object : TypeToken<List<Serial>>() {
                    }.type)
                    this.data["serials"] = serials
                    if (this.data["serialctrl"].toString().equals("T")) {
                        tv_sl.text = serials.size.toString()
                    }
                }
            }
        }
    }
}