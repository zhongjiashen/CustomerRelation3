package com.cr.activity.jxc.ckgl.kcpd

import android.os.Bundle
import com.cr.activity.BaseActivity
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
import com.crcxj.activity.R
import com.update.utils.LogUtils
import kotlinx.android.synthetic.main.activity_jxc_ckgl_kcpd_xzsp.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 进销存-库存管理-库存盘点-选择商品
 *
 * @author Administrator
 *
 */
@Deprecated("")
class KtJxcCkglKcpdXzspActivity:BaseActivity(){
    internal var qsrq: String?=""
    internal var jzrq:String?=""
    internal var shzt: String? = ""
    internal var cname:String? = ""
     var pdckId:String? = ""
    var code: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jxc_ckgl_kcpd_xzsp)
        setOnClick()
        addFHMethod()
        if (this.intent.hasExtra("pdckId")) {
            pdckId = this.intent.extras!!.getString("pdckId")
        }
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val sdf = SimpleDateFormat("yyyy-MM-")
        qsrq = sdf.format(Date()) + "01"
        jzrq = sdf.format(Date()) + calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        searchDate()
    }
    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {

    }

    /**
     * 连接网络的操作
     */
    private fun searchDate() {
        val parmMap = HashMap<String, Any?>()
        parmMap["dbname"] = ShareUserInfo.getDbName(context)

        parmMap["storeid"] = pdckId
        parmMap["goodscode"] = ""
        parmMap["goodstype"] = code
        parmMap["goodsname"] = search.getText().toString()
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap["curpage"] = currentPage
        parmMap["pagesize"] = pageSize
        findServiceData2(0, ServerURL.SELECTGOODSKCPD, parmMap, false)
    }

    override fun executeSuccess() {
        LogUtils.e(returnJson)

    }
}
