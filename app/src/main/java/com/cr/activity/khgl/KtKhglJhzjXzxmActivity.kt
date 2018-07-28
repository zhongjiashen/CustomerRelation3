package com.cr.activity.khgl

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.View
import com.airsaid.pickerviewlibrary.TimePickerView
import com.bumptech.glide.Glide
import com.cr.tools.ShareUserInfo

import com.crcxj.activity.R
import com.jph.takephoto.model.TResult
import com.update.actiity.choose.NetworkDataSingleOptionActivity
import com.update.actiity.project.ChoiceProjectActivity
import com.update.actiity.sales.ChoiceOpportunitiesActivity
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.dialog.DialogFactory
import com.update.dialog.OnDialogClickInterface
import com.update.model.FileChooseData
import com.update.utils.DateUtil
import com.update.utils.EditTextHelper
import com.update.utils.FileUtils
import com.update.utils.LogUtils
import com.update.viewbar.TitleBar
import kotlinx.android.synthetic.main.activity_jxc_cggl_cgdd_add.*

import kotlinx.android.synthetic.main.activity_khgl_jhzj_xzxm.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 计划总结-新增项目
 * @author Administrator
 *
 */
class KtKhglJhzjXzxmActivity : BaseActivity<BaseP>() {
    var zjlx: String? = ""
    var clientid: String? = ""
    var gmid: String? = ""
    var chanceid: String? = ""
    var isused = "0"
    var startdate: String? = ""
    var starttime: String? = ""
    var path: String? = ""
    var mTimePickerView: TimePickerView? = null
    override fun initVariables() {
        presenter = BaseP(this, this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_khgl_jhzj_xzxm
    }

    override fun init() {
        titlebar.setTitleText(mActivity, "新增项目")
        titlebar.setRightText("保存")
        setOnClick()
        //初始化时计划项目不可编辑
        EditTextHelper.EditTextEnable(false, et_jhxm)
        ll_txsj_choice.visibility = View.GONE
    }


    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {

        titlebar.setTitleOnlicListener { i ->
            when (i) {
            //单据保存
                2 -> {
                    save()
                }
            }

        }
        //计划类型选择
        ll_jhlx_choice.setOnClickListener {
            startActivityForResult(Intent(mActivity, NetworkDataSingleOptionActivity::class.java)
                    .putExtra("zdbm", "JHLX").putExtra("title", "计划类型选择"), 0)
        }
        //计划项目点击事件
        ll_jhxm_choice.setOnClickListener {
            when (tv_jhlx.text.toString()) {
                "" -> {
                    showShortToast("请先选择计划类型")
                }
                "项目任务" -> {
                    startActivityForResult(Intent(this, ChoiceProjectActivity::class.java), 1)
                }
                "机会任务" -> {
                    startActivityForResult(Intent(this, ChoiceOpportunitiesActivity::class.java), 2)
                }
            }
        }
        ll_dqjd_choice.setOnClickListener {
            when (tv_jhlx.text.toString()) {
                "" -> {
                    showShortToast("请先选择计划类型")
                }
                "项目任务" -> {
                    startActivityForResult(Intent(this, NetworkDataSingleOptionActivity::class.java)
                            .putExtra("zdbm", "XMJD").putExtra("title", "阶段选择"), 3)
                }
                "机会任务" -> {
                    startActivityForResult(Intent(this, NetworkDataSingleOptionActivity::class.java)
                            .putExtra("zdbm", "XMGM").putExtra("title", "阶段选择"), 3)
                }
            }

        }
        //是否提醒点击事件
        cb_sftx.setOnClickListener {
            if (cb_sftx.isChecked) {
                ll_txsj_choice.visibility = View.VISIBLE
                isused = "1"
            } else {
                ll_txsj_choice.visibility = View.GONE
                isused = "0"
            }
        }
        //提醒时间点击事件
        ll_txsj_choice.setOnClickListener {
            selectTime(0)
        }
        siv_image.setOnClickListener {
            DialogFactory.getFileChooseDialog(mActivity) { requestCode, `object` ->
                when (requestCode) {
                    0 -> {
                        val file = File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg")
                        if (!file.parentFile.exists()) file.parentFile.mkdirs()
                        val imageUri = Uri.fromFile(file)
                        takePhoto.onPickFromCapture(imageUri)
                    }
                    1 -> takePhoto.onPickFromGallery()
                    2 -> {
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "*/*"
                        intent.addCategory(Intent.CATEGORY_OPENABLE)
                        try {
                            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 4)
                        } catch (ex: ActivityNotFoundException) {
                            // Potentially direct the user to the Market with a Dialog
                            showShortToast("Please install a File Manager.")

                        }

                    }
                }
            }.show()
        }
        iv_delete.setOnClickListener {
            path=""
            Glide.with(mActivity).load(R.mipmap.add).into(siv_image)
            iv_delete.visibility=View.GONE
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
                        zjlx = data.getStringExtra("CHOICE_RESULT_ID")
                        val jhlx = data.getStringExtra("CHOICE_RESULT_TEXT")
                        tv_jhlx.setText(jhlx)
                        when (jhlx) {
                        //计划类型如果选择了工作任务，客户名称和当前阶段默认为空且不可修改，此时的计划项目可以手动输入内容
                            "工作任务" -> {
                                //此时的计划项目可以手动输入内容,不可以点击
                                EditTextHelper.EditTextEnable(true, et_jhxm)
                                et_jhxm.setHint("请输入计划项目")
                                ll_jhxm_choice.isEnabled = false
                                et_jhxm.setText("")

                                tv_khmc.setText("")

                                tv_dqjd.setText("")
                                ll_dqjd_choice.isEnabled = false
                                chanceid = ""
                                clientid = ""
                                gmid = ""


                            }
                        //如果计划类型选择了项目或机会，此时的计划项目不可以手动输入内容，
                        // 点击进入选择项目或机会界面（界面参见文档最后一条），
                        // 选择项目或机会后返回到此界面，会把客户名称、当前阶段信息带过来，
                        // 客户名称不可修改，当前阶段可以修改；
                            "项目任务" -> {
                                //此时的计划项目不可以手动输入内容
                                EditTextHelper.EditTextEnable(false, et_jhxm)
                                ll_jhxm_choice.isEnabled = true
                                et_jhxm.setHint("请选择计划项目")
                                et_jhxm.setText("")

                                tv_khmc.setText("")

                                tv_dqjd.setText("")
                                chanceid = ""
                                clientid = ""
                                gmid = ""
                            }
                            "机会任务" -> {
                                //此时的计划项目不可以手动输入内容
                                EditTextHelper.EditTextEnable(false, et_jhxm)
                                ll_jhxm_choice.isEnabled = true
                                et_jhxm.setHint("请选择计划机会")
                                et_jhxm.setText("")

                                tv_khmc.setText("")

                                tv_dqjd.setText("")
                                chanceid = ""
                                clientid = ""
                                gmid = ""
                            }
                        }
                    }
                    1 -> {
                        chanceid = data.getStringExtra("projectid")
                        et_jhxm.setText(data.getStringExtra("title"))

                        tv_khmc.setText(data.getStringExtra("khmc"))
                        clientid = data.getStringExtra("clientid")
                        gmid = data.getStringExtra("gmid")
                        tv_dqjd.setText(data.getStringExtra("gmmc"))
                    }
                    2 -> {
                        chanceid = data.getStringExtra("projectid")
                        et_jhxm.setText(data.getStringExtra("title"))

                        tv_khmc.setText(data.getStringExtra("khmc"))
                        clientid = data.getStringExtra("clientid")
                        gmid = data.getStringExtra("gmid")
                        tv_dqjd.setText(data.getStringExtra("gmmc"))
                    }
                    3 -> {
                        gmid = data.getStringExtra("CHOICE_RESULT_ID")
                        tv_dqjd.setText(data.getStringExtra("CHOICE_RESULT_TEXT"))
                    }
                    4 -> {
                        val uri = data.data
                        LogUtils.e("File Uri: " + uri!!.toString())
                        // Get the path
                        path = FileUtils.getUrlPath(this, uri)
                        LogUtils.e("File Path: $path")
                        setImage()
//                        val fileChooseData = FileChooseData()
//                        fileChooseData.type = 1
//                        fileChooseData.url = path
//                        mFileChooseDatas.add(fileChooseData)
//                        mFileChooseAdapter.setList(mFileChooseDatas)
                    }
                }
            }
        }
    }

    /**
     * 时间选择器
     *
     * @param i
     */
    fun selectTime(i: Int) {
        if (mTimePickerView == null)
            mTimePickerView = TimePickerView(this, TimePickerView.Type.ALL)

        mTimePickerView!!.setTime(Date())
        mTimePickerView!!.setOnTimeSelectListener(TimePickerView.OnTimeSelectListener { date ->

            when (i) {
                0 -> {
                    startdate = DateUtil.DateToString(date, "yyyy-MM-dd")
                    starttime = DateUtil.DateToString(date, "HH:mm:ss")
                    tv_txsj.setText(DateUtil.DateToString(date, "yyyy-MM-dd HH:mm:ss"))
                }
            }
        })
        mTimePickerView!!.show()

    }

    override fun takeSuccess(result: TResult) {
        LogUtils.e(result.image.toString())
        path=result.image.compressPath
//        val fileChooseData = FileChooseData()
//        fileChooseData.type = 0
//        fileChooseData.url = result.image.compressPath
//        mFileChooseDatas.add(fileChooseData)
//        mFileChooseAdapter.setList(mFileChooseDatas)
        setImage()
    }
    fun setImage(){
        Glide.with(mActivity).load(path).error(R.mipmap.ic_file).into(siv_image)
        iv_delete.visibility=View.VISIBLE
    }

    fun save() {
        if(TextUtils.isEmpty(zjlx)){
            showShortToast("请选择计划类型")
            return
        }
        if(TextUtils.isEmpty(et_jhxm.text.toString())){
            showShortToast("计划项目名称不能为空")
            return
        }
        if(isused.equals("1")&&TextUtils.isEmpty(startdate)){
            showShortToast("提醒时间不能为空")
            return
        }
        var mParmMap = HashMap<String, Any?>()
        mParmMap.put("dbname", ShareUserInfo.getDbName(this))
        mParmMap.put("jhid", getIntent().getExtras().getString("jhid"))//计划主表ID
        mParmMap.put("zjlx", zjlx)//计划类型 zdbm=JHLX
        mParmMap.put("itemid", "0")
        mParmMap.put("items", et_jhxm.text.toString())
        mParmMap.put("clientid", clientid)//类别（0计划制订 1计划执行）
        mParmMap.put("chanceid", chanceid)//项目或机会ID
        mParmMap.put("jhnr", et_jhnr.getText().toString())//
        mParmMap.put("zdcl", et_zdcl.getText().toString())//
        mParmMap.put("gmid", gmid)//阶段ID
        mParmMap.put("isused", isused)//是否提醒 0否 1是
        mParmMap.put("startdate", startdate)//提醒日期
        mParmMap.put("starttime", starttime)//提醒时间
        mParmMap.put("lb", "0")//类别（0计划制订 1计划执行）
        if(!path.equals("")){
            mParmMap.put("filenames", File(path).name)
            mParmMap.put("xx", FileUtils.encodeBase64File(path))
        }
        presenter.post(0, "jhrwgzzjitemsave", mParmMap)
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    override fun returnData(requestCode: Int, data: Any) {
        super.returnData(requestCode, data)
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
}
