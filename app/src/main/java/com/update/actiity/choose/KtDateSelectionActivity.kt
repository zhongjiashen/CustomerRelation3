package com.update.actiity.choose


import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.ViewGroup
import com.airsaid.pickerviewlibrary.TimePickerView
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.base.BaseRecycleAdapter


import com.update.model.KtFplxData
import com.update.utils.DateUtil
import com.update.utils.LogUtils
import com.update.viewholder.ViewHolderFactory
import kotlinx.android.synthetic.main.activity_date_selection.*


import java.text.SimpleDateFormat

import java.util.*
import kotlin.collections.ArrayList

/**
 * 选择发票类型
 */
class KtDateSelectionActivity : BaseActivity<BaseP>() {
    private var mTimePickerView: TimePickerView? = null
    private var mQsrq: String = ""//开始日期
    private var mZzrq: String = ""//截止日期
    override fun initVariables() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_date_selection
    }

    override fun init() {
        mTimePickerView = TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY)//时间选择弹窗
        mQsrq = intent.getStringExtra("qsrq")
        mZzrq = intent.getStringExtra("zzrq")
        tv_start_time.setText(mQsrq)

        tv_end_time.setText(mZzrq)
        titlebar.setTitleText(this, "筛选")

        ll_start_time.setOnClickListener {
            selectTime(0)
        }
        ll_end_time.setOnClickListener {
            selectTime(1)
        }

        bt_ok.setOnClickListener {
            setResult(Activity.RESULT_OK,
                    intent.putExtra("qsrq", tv_start_time.text.toString())
                            .putExtra("zzrq", tv_end_time.text.toString())
            )
            finish()
        }

    }

    fun selectTime(i: Int) {
        mTimePickerView!!.setTime(Date())
        mTimePickerView!!.setOnTimeSelectListener(TimePickerView.OnTimeSelectListener { date ->
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            when (i) {
                0 -> tv_start_time.setText(DateUtil.DateToString(date, "yyyy-MM-dd"))
                1 -> tv_end_time.setText(DateUtil.DateToString(date, "yyyy-MM-dd"))
            }
        })
        mTimePickerView!!.show()
    }

}