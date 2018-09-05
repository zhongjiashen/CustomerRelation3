package com.cr.activity.khgl.jhzj

import android.text.TextUtils
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.update.base.BaseActivity
import com.update.base.BaseP



import kotlinx.android.synthetic.main.activity_khgl_jhzj_zj.*
import java.util.HashMap

/**
 * 计划总结-总结界面（今日总结、本周总结、本月总结、本年总结）
 */
class KtZjActivity: BaseActivity<BaseP>(){
    var kind :Int=0;
    var title:String="日总结"
    override fun initVariables() {
        presenter = BaseP(this, this)
        kind=intent.getIntExtra("kind",0)
    }

    override fun getLayout(): Int {
        return R.layout.activity_khgl_jhzj_zj
    }

    override fun init() {
        when(kind){
            0->{
                title="日总结"
                tv_jjh.text="今日工作计划"
                et_jjh.hint="填写今日工作计划"
                tv_zj.text="今日工作总结"
                et_zj.hint="填写今日工作总结"
                tv_mjh.text="明日工作计划"
                et_mjh.hint="填写明日工作计划"
            }
            1->{
                title="周总结"
                tv_jjh.text="本周工作计划"
                et_jjh.hint="填写本周工作计划"
                tv_zj.text="本周工作总结"
                et_zj.hint="填写本周工作总结"
                tv_mjh.text="下周工作计划"
                et_mjh.hint="填写下周工作计划"
            }
            2->{
                title="月总结"
                tv_jjh.text="本月工作计划"
                et_jjh.hint="填写本月工作计划"
                tv_zj.text="本月工作总结"
                et_zj.hint="填写本月工作总结"
                tv_mjh.text="下月工作计划"
                et_mjh.hint="填写下月工作计划"
            }
            3->{
                title="年总结"
                tv_jjh.text="本年工作计划"
                et_jjh.hint="填写本年工作计划"
                tv_zj.text="本年工作总结"
                et_zj.hint="填写本年工作总结"
                tv_mjh.text="下年工作计划"
                et_mjh.hint="填写下年工作计划"
            }
        }
        titlebar.setTitleText(mActivity,title)
        titlebar.setRightText("保存")
        titlebar.setTitleOnlicListener {
            if(TextUtils.isEmpty(et_jjh.text.toString())){
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
            mParmMap.put("jhid", intent.getStringExtra("mid"))
            mParmMap.put("jhinfo", et_jjh.text.toString())//今日计划（本周、本月、本年）
            mParmMap.put("zwzj", et_zj.text.toString())// 总结内容
            mParmMap.put("nextjhinfo", et_mjh.text.toString())
            presenter.post(0, ServerURL.JHRWGZZJZWZJSAVE, mParmMap)
        }

    }
    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    override fun returnData(requestCode: Int, data: Any) {
        showShortToast("保存成功！")
        finish()
    }
}