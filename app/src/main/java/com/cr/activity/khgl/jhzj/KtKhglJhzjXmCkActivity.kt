package com.cr.activity.khgl.jhzj

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.view.View
import com.airsaid.pickerviewlibrary.TimePickerView
import com.bumptech.glide.Glide
import com.cr.model.JHRW
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo

import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jph.takephoto.model.TResult
import com.update.actiity.choose.NetworkDataSingleOptionActivity
import com.update.actiity.project.ChoiceProjectActivity
import com.update.actiity.sales.ChoiceOpportunitiesActivity
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.dialog.DialogFactory
import com.update.utils.*


import kotlinx.android.synthetic.main.activity_khgl_jhzj_xmzx.*

import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


import java.io.File

import java.util.*

/**
 * 计划总结-项目查看
 * @author Administrator
 *
 */
class KtKhglJhzjXmCkActivity : BaseActivity<BaseP>() {
    var jhid: String? = ""
    var itemid: String? = ""
    var zjlx: String? = ""
    var clientid: String? = ""
    var gmid: String? = ""
    var chanceid: String? = ""
    var isused = "0"
    var startdate: String? = ""
    var starttime: String? = ""
    var issy = "0"
    var syrq: String? = ""
    var path: String? = ""
    var mTimePickerView: TimePickerView? = null
    var jhrw: JHRW? = null
    var attfilesList= ArrayList<KtFiles>()
    override fun initVariables() {
        jhrw = intent.extras!!.getSerializable("object") as JHRW
        presenter = BaseP(this, this)
        var mParmMap = HashMap<String, Any?>()
        mParmMap.put("dbname", ShareUserInfo.getDbName(this))
        mParmMap.put("itemid", jhrw!!.getId())
        presenter.post(0, ServerURL.JHRWGZZJITEMXX, mParmMap)
    }

    override fun getLayout(): Int {
        return R.layout.activity_khgl_jhzj_xmzx
    }

    override fun init() {
        titlebar.setTitleText(mActivity, "项目执行")

        //初始化时计划项目不可编辑
        EditTextHelper.EditTextEnable(false, et_jhxm)
        ll_txsj_choice.visibility = View.GONE
        ll_sysj_choice.visibility = View.GONE
        iv_delete.visibility = View.GONE
    }









    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    override fun returnData(requestCode: Int, data: Any) {
        super.returnData(requestCode, data)
        when (requestCode) {
            0 -> {
                if (data.toString() != "") {
                    var ktJhzjXmzxData = Gson().fromJson<ArrayList<KtJhzjXmzxData>>(data.toString(),
                            object : TypeToken<ArrayList<KtJhzjXmzxData>>() {
                            }.type)[0]
                    itemid= ktJhzjXmzxData.itemid.toString()
                    jhid= ktJhzjXmzxData.jhid.toString()
                    zjlx = ktJhzjXmzxData.zjlx
                    clientid = ktJhzjXmzxData.clientid
                    chanceid = ktJhzjXmzxData.chanceid
                    gmid = ktJhzjXmzxData.gmid
                    isused = ktJhzjXmzxData.isused.toString()
                    issy = ktJhzjXmzxData.issy.toString()
                    tv_jhlx.setText(ktJhzjXmzxData.zjlxname)
                    if(ktJhzjXmzxData.zjlxname.equals( "工作任务")){
                        //此时的计划项目可以手动输入内容,不可以点击
                        EditTextHelper.EditTextEnable(true, et_jhxm)
                        ll_jhxm_choice.isEnabled = false
                    }else{
                        EditTextHelper.EditTextEnable(false, et_jhxm)
                        ll_jhxm_choice.isEnabled = true
                    }

                    et_jhxm.setText(ktJhzjXmzxData.items)
                    tv_khmc.setText(ktJhzjXmzxData.cname)
                    tv_dqjd.setText(ktJhzjXmzxData.gmmc)
                    et_jhnr.setText(ktJhzjXmzxData.jhnr)
                    et_zdcl.setText(ktJhzjXmzxData.zdcl)
                    et_zxqk.setText(ktJhzjXmzxData.zxcl)
                    et_yyfx.setText(ktJhzjXmzxData.zxyy)
                    tv_zxjg.setText(ktJhzjXmzxData.zxjgname.toString())
                    when (ktJhzjXmzxData.isused) {
                        0 -> {
                            ll_txsj_choice.visibility = View.GONE
                            cb_sftx.isChecked = false

                        }
                        1 -> {
                            cb_sftx.isChecked = true
                            ll_txsj_choice.visibility = View.VISIBLE
                            startdate = ktJhzjXmzxData.startdate
                            starttime = ktJhzjXmzxData.starttime
                            tv_txsj.setText(startdate + "  " + starttime)
                        }
                    }
                    when (ktJhzjXmzxData.issy) {
                        0 -> {
                            ll_sysj_choice.visibility = View.GONE
                            cb_sfsy.isChecked = false

                        }
                        1 -> {
                            cb_sfsy.isChecked = true
                            ll_sysj_choice.visibility = View.VISIBLE
                            syrq = ktJhzjXmzxData.syrq.toString()
                            tv_sysj.setText(syrq)
                        }
                    }
                }
                //获取文件列表
                var  map = HashMap<String, Any?>()

                map.put("dbname", ShareUserInfo.getDbName(this))
                map.put("attcode", "GZZJ")
                map.put("billid", jhrw!!.getId())
                map.put("curpage", "0")
                map.put("pagesize", "100")
                presenter.post(2, "attfilelist", map)
            }
            1 -> {
                val result = data as String
                if (result == "false") {
                    LogUtils.e("失败")
                    titlebar.setTvRightEnabled(true)
                    showShortToast(data.toString())
                } else {
                    LogUtils.e("失败")
                    showShortToast("添加成功")
                    finish()
                }
            }
            2->{
                 attfilesList = Gson().fromJson<ArrayList<KtFiles>>(data as String,
                        object : TypeToken<ArrayList<KtFiles>>() {
                        }.type)
                if (attfilesList != null && attfilesList!!.size > 0) {
                    saveFile()

                }
            }
        }

    }

    private fun saveFile() {
        Observable.create(Observable.OnSubscribe<String> { subscriber ->
            try {
                for (i in attfilesList) {
                    FileUtils.decoderBase64File(i.xx, mActivity, FileUtils.getPath(mActivity, "AZDJ/", i.filenames), Context.MODE_PRIVATE)

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            subscriber.onNext("")
            subscriber.onCompleted()
        })
                .subscribeOn(Schedulers.computation()) // 指定 subscribe() 发生在 运算 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(object : Observer<String> {

                    override fun onNext(s: String) {//主线程执行的方法
                        LogUtils.e(s)
                        for (i in attfilesList) {
                            if(i.lb=="0"){
                                Glide.with(mActivity).load(FileUtils.getPath(mActivity, "AZDJ/",  i.filenames)).error(R.mipmap.ic_file).into(siv_fjck)
                                siv_fjck.setOnClickListener{
                                    SeeFileUtils.openFile(mActivity, FileUtils.getPath(mActivity, "AZDJ/",  i.filenames))
                                }
                            }else{
                                Glide.with(mActivity).load(FileUtils.getPath(mActivity, "AZDJ/",  i.filenames)).error(R.mipmap.ic_file).into(siv_image)
                                siv_image .setOnClickListener{
                                            SeeFileUtils.openFile(mActivity, FileUtils.getPath(mActivity, "AZDJ/",  i.filenames))
                                        }
                            }

                        }
                    }

                    override fun onCompleted() {
                        LogUtils.e("-------------1---------------")


                    }

                    override fun onError(e: Throwable) {
                        //

                    }
                })

    }
}
