package com.update.actiity.choose


import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.update.base.BaseActivity
import com.update.base.BaseP
import com.update.base.BaseRecycleAdapter
import com.update.model.KtAreaData


import com.update.model.KtFplxData
import com.update.model.KtRegionData
import com.update.utils.LogUtils
import com.update.viewholder.ViewHolderFactory
import kotlinx.android.synthetic.main.activity_state_audit_choice.*

import java.util.*
import kotlin.collections.ArrayList

/**
 * 选择区域
 */
class KtRegionSelectionActivity : BaseActivity<BaseP>() {
    var mParmMap = TreeMap<String, Any>()
    var mList = ArrayList<KtRegionData>()
    override fun initVariables() {
        presenter = BaseP(this, this)
        mParmMap["dbname"] = ShareUserInfo.getDbName(this)
        mParmMap["levels"] = intent.getStringExtra("levels")
        mParmMap["parentid"] = intent.getStringExtra("parentid")
        presenter.post(0, ServerURL.AREALIST, mParmMap)
    }

    override fun getLayout(): Int {
        return R.layout.activity_state_audit_choice
    }

    override fun init() {
        titlebar.setTitleText(this,intent.getStringExtra("title"))
        rv_list.layoutManager = LinearLayoutManager(this)
        mAdapter = object : BaseRecycleAdapter<ViewHolderFactory.StateAuditChoiceHolder, KtRegionData>(mList) {
            override fun MyonCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
                return ViewHolderFactory.getStateAuditChoiceHolder(mActivity, parent)
            }

            override fun MyonBindViewHolder(holder: ViewHolderFactory.StateAuditChoiceHolder?, data: KtRegionData) {
                if (holder != null) {
                    holder.tvText.setText(data.cname)
                    holder.itemView.setOnClickListener {
                        setResult(Activity.RESULT_OK,
                                intent.putExtra("id", data.id)
                                        .putExtra("name", data.cname)
                        )
                        finish()
                    }
                }
            }

        }
        rv_list.adapter = mAdapter
    }


    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    override fun returnData(requestCode: Int, data: Any) {
        LogUtils.e(data.toString())
        val gson = Gson()
        mList!!.addAll(gson.fromJson<ArrayList<KtRegionData>>(data as String,
                object : TypeToken<ArrayList<KtRegionData>>() {
                }.type))
        mAdapter.notifyDataSetChanged()

//
    }


}