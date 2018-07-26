package com.cr.activity.jxc.ckgl.kcpd

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.actiity.WeChatCaptureActivity
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.base.BaseRecycleAdapter
import com.update.dialog.DialogFactory
import com.update.dialog.OnDialogClickInterface
import com.update.model.Serial
import com.update.viewholder.ViewHolderFactory

import kotlinx.android.synthetic.main.activity_serial_number_add.*
import java.util.ArrayList

class KtSerialNumberAddActivity : BaseActivity<BaseP>() {
    var mGson = Gson()
    var position = 0
    var serials = ArrayList<Serial>()
    private var serialinfo: String? = ""    //序列号GUID
    private var itemno: String? = ""    //主单据ID
    override fun initVariables() {
        itemno = intent.getStringExtra("itemno")
        serialinfo = intent.getStringExtra("uuid")
        var data=intent.getStringExtra("DATA")
        if (data!="")
            serials = mGson.fromJson<ArrayList<Serial>>(intent.getStringExtra("DATA"), object : TypeToken<ArrayList<Serial>>() {
            }.type)
        position = intent.getIntExtra("position", 0)

    }

    override fun getLayout(): Int {
        return R.layout.activity_serial_number_add
    }

    override fun init() {
        titlebar.setTitleText(this, "查看序列号")
        titlebar.setRightText("保存")
        titlebar.setTitleOnlicListener { i ->
            when (i) {
                2 -> {
                    setResult(Activity.RESULT_OK, Intent()
                            .putExtra("position", position)
                            .putExtra("DATA", mGson.toJson(serials)))
                    finish()
                }
            }
        }
        iv_scan.setOnClickListener {
            startActivityForResult(Intent(this, WeChatCaptureActivity::class.java), 11)
        }
        bt_add.setOnClickListener {
            val serial_number = et_serial_number.getText().toString()
            if (TextUtils.isEmpty(serial_number))
                showShortToast("请添加序列号")
            else {
                addSerialNumber(serial_number)
            }

        }
        mAdapter = object : BaseRecycleAdapter<ViewHolderFactory.SerialNumberHolder, Serial>(serials) {

            override fun MyonCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                return ViewHolderFactory.getSerialNumberHolder(mActivity, parent)
            }

            override fun MyonBindViewHolder(holder: ViewHolderFactory.SerialNumberHolder, data: Serial) {
                holder.tvSerialNumber.text = data.serno
                holder.ivEditor.setOnClickListener {
                    DialogFactory.getModifySerialNumberDialog(mActivity, data.serno) { requestCode, `object` ->
                        data.serno = `object` as String
                        holder.tvSerialNumber.text = data.serno
                    }.show()
                }
                holder.ivDelete.setOnClickListener {
                    //删除该序列号
                    DialogFactory.getButtonDialog(mActivity, "确定删除该序列号吗？") { requestCode, `object` ->
                        serials.removeAt(holder.layoutPosition)
                        mAdapter.list = serials
                    }.show()
                }
            }
        }
        rcv_list.setLayoutManager(LinearLayoutManager(this))
        rcv_list.setAdapter(mAdapter);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                11 -> {
                    addSerialNumber(data.getStringExtra("qr"))
                }
            }
        }
    }

    private fun addSerialNumber(serial_number: String) {
        if (serials == null)
            serials = ArrayList()
        else {
            for (i in serials.indices) {
                if (serials[i].serno == serial_number) {
                    showShortToast("不能录入重复序列号！")
                    return
                }
            }
        }
        val serial = Serial()
        serial.billid = itemno
        serial.serialinfo = serialinfo
        serial.serno = serial_number

        serials.add(serial)
        mAdapter.list = serials
        et_serial_number.setText("")

    }
}