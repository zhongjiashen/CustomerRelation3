package com.cr.activity.khgl.jhzj

import android.text.TextUtils
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.utils.LogUtils


import kotlinx.android.synthetic.main.activity_khgl_jhzj_zj.*
import java.util.HashMap

/**
 * 计划总结-总结界面（今日总结、本周总结、本月总结、本年总结）
 */
class KtZjActivity : BaseActivity<BaseP>() {
    var kind: Int = 0;
    var title: String = "日总结"

    var jhid: String = "";
    override fun initVariables() {
        presenter = BaseP(this, this)
        kind = intent.getIntExtra("kind", 0)
        jhid = intent.getStringExtra("mid")
        var mParmMap = HashMap<String, Any?>()
        mParmMap.put("dbname", ShareUserInfo.getDbName(this))
        mParmMap.put("jhid", jhid)
        presenter.post(0, ServerURL.JHRWGZZJZWZJREAD, mParmMap)
    }

    override fun getLayout(): Int {
        return R.layout.activity_khgl_jhzj_zj
    }

    override fun init() {
        when (kind) {
            0 -> {
                title = "日总结"
                tv_jjh.text = "今日工作计划"
                et_jjh.hint = "填写今日工作计划"
                tv_zj.text = "今日工作总结"
                et_zj.hint = "填写今日工作总结"
                tv_mjh.text = "明日工作计划"
                et_mjh.hint = "填写明日工作计划"
            }
            1 -> {
                title = "周总结"
                tv_jjh.text = "本周工作计划"
                et_jjh.hint = "填写本周工作计划"
                tv_zj.text = "本周工作总结"
                et_zj.hint = "填写本周工作总结"
                tv_mjh.text = "下周工作计划"
                et_mjh.hint = "填写下周工作计划"
            }
            2 -> {
                title = "月总结"
                tv_jjh.text = "本月工作计划"
                et_jjh.hint = "填写本月工作计划"
                tv_zj.text = "本月工作总结"
                et_zj.hint = "填写本月工作总结"
                tv_mjh.text = "下月工作计划"
                et_mjh.hint = "填写下月工作计划"
            }
            3 -> {
                title = "年总结"
                tv_jjh.text = "本年工作计划"
                et_jjh.hint = "填写本年工作计划"
                tv_zj.text = "本年工作总结"
                et_zj.hint = "填写本年工作总结"
                tv_mjh.text = "下年工作计划"
                et_mjh.hint = "填写下年工作计划"
            }
        }
        titlebar.setTitleText(mActivity, title)
        if (!intent.getStringExtra("shzt").equals("1")) {//根据审核状态
            titlebar.setRightText("保存")
            titlebar.setTitleOnlicListener {
                if (TextUtils.isEmpty(et_jjh.text.toString())) {
                    showShortToast(et_jjh.hint.toString())
                    return@setTitleOnlicListener
                }
//            if(TextUtils.isEmpty(et_zj.text.toString())){
//                showShortToast(et_zj.hint.toString())
//                return@setTitleOnlicListener
//            }
//            if(TextUtils.isEmpty(et_mjh.text.toString())){
//                showShortToast(et_mjh.hint.toString())
//                return@setTitleOnlicListener
//            }
                var mParmMap = HashMap<String, Any?>()
                mParmMap.put("dbname", ShareUserInfo.getDbName(this))
//            mParmMap.put("mid", intent.getStringExtra("mid"))
                mParmMap.put("jhid", jhid)
                mParmMap.put("jhinfo", et_jjh.text.toString())//今日计划（本周、本月、本年）
                mParmMap.put("zwzj", et_zj.text.toString())// 总结内容
                mParmMap.put("nextjhinfo", et_mjh.text.toString())
                presenter.post(1, ServerURL.JHRWGZZJZWZJSAVE, mParmMap)
            }
        }

    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    override fun returnData(requestCode: Int, data: Any) {
        when (requestCode) {
            0 -> {
                if (data.toString() != "") {
                    LogUtils.e(data.toString())
                    var ktJhzjData = Gson().fromJson<ArrayList<KtJhzjData>>(data.toString(),
                            object : TypeToken<ArrayList<KtJhzjData>>() {
                            }.type)[0]
                    et_jjh.setText(ktJhzjData.jhinfo)
                    et_zj.setText(ktJhzjData.zwzj)
                    et_mjh.setText(ktJhzjData.nextjhinfo)
                }
            }
            1 -> {
                showShortToast("保存成功！")
                finish()
            }
        }

    }
}