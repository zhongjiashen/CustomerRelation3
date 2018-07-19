package com.cr.activity.xjyh.fyzc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.cr.activity.BaseActivity
import com.cr.activity.common.CommonXzzdActivity
import com.crcxj.activity.R
import kotlinx.android.synthetic.main.activity_xjyh_fyzc_add_zc.*

/**
 * 现金银行-费用支出-添加支出项
 *
 * @author Administrator
 *
 */
class KtXjyhFyzcAddZcActivity : BaseActivity() {
    private var fymcId: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xjyh_fyzc_add_zc)
        setOnClick()
        addFHMethod()
        if (activity.getIntent().getIntExtra("type", 0) == 1) {
            bt_view.visibility = VISIBLE
        } else bt_view.visibility = GONE

    }

    /**
     * 界面点击事件设置
     */
    open fun setOnClick() {
        fymc_edittext.setOnClickListener {
            intent.putExtra("type", "ZCLB")
            intent.setClass(activity, CommonXzzdActivity::class.java)
            startActivityForResult(intent, 0)
        }
        save.setOnClickListener {
            if (fymc_edittext.getText().toString() == "") {
                showToastPromopt("请选择费用名称")

            } else if (et_csje.getText().toString() == "") {
                showToastPromopt("请填写初始金额信息")

            } else {
                val intent = Intent()
                intent.putExtra("name", fymc_edittext.getText().toString())
                intent.putExtra("ietypeid", fymcId)
                intent.putExtra("amount", et_csje.getText().toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

    }

    override fun executeSuccess() {
        when (returnSuccessType) {
            0 -> {
                if (returnJson == "") {
                    showToastPromopt("操作成功")
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    showToastPromopt("保存失败" + returnJson.substring(5))
                }
            }
            else -> {
            }
        }
    }

}