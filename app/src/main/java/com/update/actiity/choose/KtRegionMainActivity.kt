package com.update.actiity.choose


import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.ViewGroup
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.base.BaseRecycleAdapter


import com.update.model.KtFplxData
import com.update.utils.LogUtils
import com.update.viewholder.ViewHolderFactory

import kotlinx.android.synthetic.main.activity_region_main.*

import java.util.*
import kotlin.collections.ArrayList

/**
 * 选择发票类型
 */
class KtRegionMainActivity : BaseActivity<BaseP>() {
    var provinceId:String=""//
    var cityId:String=""//
    var areaId:String=""//
    override fun initVariables() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_region_main
    }

    override fun init() {
        titlebar.setTitleText(this, "地区")
        ll_sf.setOnClickListener {

            startActivityForResult(Intent(mActivity,KtRegionSelectionActivity::class.java)
                    .putExtra("levels", "2")
                    .putExtra("parentid", "0")
                    .putExtra("title", "省份选择"),11)
        }
        ll_sj.setOnClickListener {
            if(TextUtils.isEmpty(provinceId)){
                showShortToast("请先选择省")
                return@setOnClickListener
            }
            startActivityForResult(Intent(mActivity,KtRegionSelectionActivity::class.java)
                    .putExtra("levels", "3")
                    .putExtra("parentid", provinceId)
                    .putExtra("title", "市级选择"),12)
        }
        ll_xq.setOnClickListener {
            if(TextUtils.isEmpty(cityId)){
                showShortToast("请先选选市级")
                return@setOnClickListener
            }
            startActivityForResult(Intent(mActivity,KtRegionSelectionActivity::class.java)
                    .putExtra("levels", "4")
                    .putExtra("parentid", cityId)
                    .putExtra("title", "区县选择"),13)
        }
        bt_ok.setOnClickListener {
            if(TextUtils.isEmpty(provinceId)){
                showShortToast("请选择省")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(cityId)){
                showShortToast("请选择市级")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(areaId)){
                showShortToast("请选择县区")
                return@setOnClickListener
            }
            setResult(Activity.RESULT_OK,
                    intent.putExtra("provinceId", provinceId)
                            .putExtra("provinceName", tv_sf.text.toString())
                            .putExtra("cityId", cityId)
                            .putExtra("cityName", tv_sj.text.toString())
                            .putExtra("areaId", areaId)
                            .putExtra("areaName", tv_xq.text.toString())
                            .putExtra("text",tv_sf.text.toString()+"/"+tv_sj.text.toString()+"/"+tv_xq.text.toString())
            )
            finish()
        }
    }

    override fun onMyActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onMyActivityResult(requestCode, resultCode, data)
        if(data!=null){
            when(requestCode){
                11->{
                    provinceId=data.getStringExtra("id")
                    tv_sf.text=data.getStringExtra("name")
                }
                12->{
                    cityId=data.getStringExtra("id")
                    tv_sj.text=data.getStringExtra("name")
                }
                13->{
                    areaId=data.getStringExtra("id")
                    tv_xq.text=data.getStringExtra("name")
                }
            }
        }
    }
}