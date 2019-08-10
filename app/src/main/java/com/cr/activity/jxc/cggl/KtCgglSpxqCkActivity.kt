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
import com.update.actiity.SerialNumberDetailsActivity
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.model.Serial
import com.update.utils.EditTextHelper
import kotlinx.android.synthetic.main.activity_cggl_spxq_ck.*
import java.io.Serializable


/**
 * 采购管理商品详情查看
 */
class KtCgglSpxqCkActivity : BaseActivity<BaseP>() {

    var data = HashMap<String, Any>()
    override fun initVariables() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_cggl_spxq_ck
    }

    override fun init() {

        if (intent.hasExtra("object")) {
            data = intent.getExtras().getSerializable("object") as HashMap<String, Any>
            tv_spmc.text = "名称：" + data["goodsname"].toString()
            tv_spbm.text = "编码：" + data["goodscode"].toString()
            tv_spgg.text = "规格：" + data["specs"].toString()
            tv_spxh.text = "型号：" + data["model"].toString()
            et_bz.setText(data["memo"].toString())
            if (data["onhand"] != null) {
                tv_spkz.text = "库存：" + FigureTools.sswrFigure(data["onhand"].toString()) + data["unitname"].toString()
            } else {
                tv_spkz.visibility = View.GONE
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
            slv_sl.sl = data["unitqty"].toString().toDouble()
            et_dj.setText(FigureTools.sswrFigure(data["unitprice"].toString()))//单价
            et_sl.setText(FigureTools.sswrFigure(data["taxrate"].toString()))//税率
            EditTextHelper.EditTextEnable(false, et_sl)
            tv_hsdj.setText(FigureTools.sswrFigure(data["taxunitprice"].toString()))//含税单价
        }

        titlebar.setTitleText(mActivity, "商品详情")



        tv_serial_number.setOnClickListener {
            startActivity(Intent(mActivity, SerialNumberDetailsActivity::class.java)
                    .putExtra("billid", intent.getStringExtra("billid"))
                    .putExtra("serialinfo", data["serialinfo"].toString())
                    .putExtra("tabname", intent.getStringExtra("tabname")))
        }


    }

    override fun onMyActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onMyActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {

            }
        }
    }
}