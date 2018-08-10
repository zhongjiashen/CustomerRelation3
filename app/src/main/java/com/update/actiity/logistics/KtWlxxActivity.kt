package com.update.actiity.logistics


import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.cr.activity.common.CommonXzzdActivity
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.actiity.choose.LocalDataSingleOptionActivity
import com.update.actiity.choose.NetworkDataSingleOptionActivity
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.model.KtFplxData
import com.update.model.KtWlxxData
import com.update.utils.EditTextHelper

import kotlinx.android.synthetic.main.activity_wlxx.*

class KtWlxxActivity : BaseActivity<BaseP>() {

    var wlxxData = KtWlxxData()
    override fun initVariables() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_wlxx
    }

    override fun init() {

        titleBar.setTitleText(mActivity, "物流信息")
        titleBar.setRightText("保存")
        //保存
        titleBar.setTitleOnlicListener { i ->
            when (i) {
                2 -> {
                    if(TextUtils.isEmpty(wlxxData.logisticname)){
                        showShortToast("请选择物流公司")
                        return@setTitleOnlicListener
                    }
                    wlxxData.shipno=etWldh.text.toString()
                    wlxxData.amount=etYfje.text.toString()
                    setResult(Activity.RESULT_OK,intent.putExtra("data",Gson().toJson(wlxxData)))
                    finish()

                }
            }
        }
        //选择物流公司
        llWlgsXz.setOnClickListener {
            startActivityForResult(Intent(mActivity, ChooseLogisticsCompanyActivity::class.java)
                    .putExtra("kind", 3), 11)
        }
        //运输方式选择
        llYsfsXz.setOnClickListener {
            startActivityForResult(Intent(this, NetworkDataSingleOptionActivity::class.java)
                    .putExtra("zdbm", "YSFS")
                    .putExtra("title", "运输方式选择"),
                    12)
        }
        //运费承担
        llYfcdXz.setOnClickListener {
            startActivityForResult(Intent(this, LocalDataSingleOptionActivity::class.java)
                    .putExtra("kind", 4), 13)
        }
        //付款类型
        llFklxXz.setOnClickListener {
            startActivityForResult(Intent(this, CommonXzzdActivity::class.java)
                    .putExtra("type", "FKLX"), 14)
        }
        //付款账户
        llFkzhXz.setOnClickListener {
            startActivityForResult(Intent(this, CommonXzzdActivity::class.java)
                    .putExtra("type", "BANK"), 15)
        }
        //通知放货
        llTzfhXz.setOnClickListener {
            startActivityForResult(Intent(this, LocalDataSingleOptionActivity::class.java)
                    .putExtra("kind", 2), 16)
        }
        var data=intent.getStringExtra("data")
        if(data=="null"||data==null) {
            //运费承担默认我方，付款类型默认往来结算，通知放货默认为否，运费金额（要求大于等于0）默认0.00，其他默认为空，
            wlxxData.beartype = "0"
            tvYfcd.setText("我方")//运费承担默认我方
            wlxxData.isnotice = "0"
            tvTzfh.setText("否")//通知放货默认否
            etYfje.setText("0.00")//运费金额（要求大于等于0）默认为0.00
            wlxxData.logisticispp = "0"
            tvFklx.text = "往来结算"
        }else{
            wlxxData=Gson().fromJson<KtWlxxData>(data ,
                    object : TypeToken<KtWlxxData>() {
                    }.type)
            tvWlgs.text=wlxxData.logisticname
            etWldh.setText(wlxxData.shipno)
            tvYsfs.text =  wlxxData.shiptypename
            when(wlxxData.beartype){
                "0"->tvYfcd.text="我方"
                "1"->tvYfcd.text="对方"
            }
            when(wlxxData.logisticispp){
                "0"->tvFklx.text="往来结算"
                "1"->tvFklx.text="现款结算"
            }
            tvFkzh.text=wlxxData.logisticbankaccount
            etYfje.setText(wlxxData.amount)
            when(wlxxData.isnotice){
                "0"->tvTzfh.text="否"
                "1"->tvTzfh.text="是"
            }
            yfcd()
        }

    }

    override fun onMyActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onMyActivityResult(requestCode, resultCode, data)
        when (requestCode) {
        //选择物流公司
            11 -> {
                wlxxData.logisticname=data?.getStringExtra("name").toString()
                wlxxData.logisticid = data?.getStringExtra("id").toString()
                tvWlgs.setText(wlxxData.logisticname)
            }
        //运输方式选择
            12 -> {
                wlxxData.shiptype = data?.getStringExtra("CHOICE_RESULT_ID").toString()
                wlxxData.shiptypename = data?.getStringExtra("CHOICE_RESULT_TEXT").toString()
                tvYsfs.text =  wlxxData.shiptypename
            }
        //运费承担
            13 -> {
                wlxxData.beartype = data?.getStringExtra("CHOICE_RESULT_ID").toString()
                tvYfcd.text = data?.getStringExtra("CHOICE_RESULT_TEXT")
                yfcd()

            }
        //付款类型
            14 -> {
                wlxxData.logisticispp = data?.getStringExtra("id").toString()
                tvFklx.text = data?.getStringExtra("dictmc")
                if (wlxxData.beartype.equals("0")) {
                    fklx()
                }
            }
        //付款账户
            15 -> {
                wlxxData.logisticbankid = data?.getStringExtra("id").toString()
                wlxxData.logisticbankaccount = data?.getStringExtra("dictmc").toString()
                tvFkzh.text = wlxxData.logisticbankaccount
            }
        //通知放货
            16 -> {
                wlxxData.isnotice = data?.getStringExtra("CHOICE_RESULT_ID").toString()
                tvTzfh.text = data?.getStringExtra("CHOICE_RESULT_TEXT")

            }
        }
    }

    fun yfcd() {
        //如果运费承担为对方，则付款类型、付款账户、运费金额恢复默认且均不可修改；
        if (wlxxData.beartype.equals("1")) {
            //付款类型
            tvFklx.text = "往来结算"
            wlxxData.logisticispp = "0"
            llFklxXz.isEnabled = false

            //付款账户
            tvFkzh.text = ""
            wlxxData.logisticbankid = ""
            wlxxData.logisticbankaccount = ""
            llFklxXz.isEnabled = false

            //运费金额
            etYfje.setText("0.00")//运费金额（要求大于等于0）默认为0.00
            EditTextHelper.EditTextEnable(false, etYfje)

        } else {
            fklx()
        }
    }

    fun fklx() {
        //如果运费承担为我方，付款类型为往来结算时，则付款账户置空且不可修改，运费金额可修改；
        if (wlxxData.logisticispp.equals("0")) {
            //付款账户
            tvFkzh.text = ""
            wlxxData.logisticbankid = ""
            wlxxData.logisticbankaccount = ""
            llFklxXz.isEnabled = false
            //运费金额
            EditTextHelper.EditTextEnable(true, etYfje)
        }
        //如果运费承担为我方，付款类型为现款结算时，则付款账户、运费金额都可修改；
        else {
            //付款账户
            llFklxXz.isEnabled = true
            //运费金额
            EditTextHelper.EditTextEnable(true, etYfje)
        }

    }
}