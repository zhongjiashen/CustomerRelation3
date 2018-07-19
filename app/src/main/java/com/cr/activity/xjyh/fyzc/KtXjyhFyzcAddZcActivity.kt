package com.cr.activity.xjyh.fyzc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.cr.activity.BaseActivity
import com.cr.activity.common.CommonXzzdActivity
import com.cr.tools.ServerURL
import com.cr.tools.ShareUserInfo
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
    private var mTaxrate: Double = 0.0//税率
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xjyh_fyzc_add_zc)
        setOnClick()
        addFHMethod()
        if (this.getIntent().hasExtra("object")) {
            bt_view.visibility = VISIBLE
            var data = this.intent.extras!!.getSerializable("object") as Map<String, String>
            fymc_edittext.setText(data.get("name"))
            fymcId=data.get("ietypeid")
            et_csje.setText(data.get("initamt"))
            mTaxrate = data.get("taxrate")!!.toDouble()
            et_jshj.setText(data.get("amount"))

        } else {
            mTaxrate = activity.getIntent().getStringExtra("taxrate").toDouble()
            bt_view.visibility = GONE
        }
        et_sl.setText(mTaxrate.toString())

        et_csje.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {


            }

            override fun afterTextChanged(s: Editable) {
                if (et_csje.isFocused) {
                    // 输入后的监听
                    if (!TextUtils.isEmpty(s)) {
                        val csje: Double = s.toString().toDouble() * (mTaxrate + 100) / 100
                        et_jshj.setText(csje.toString())
                    }
                }
            }
        })
        et_sl.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化")
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入")

            }

            override fun afterTextChanged(s: Editable) {
                if (et_sl.isFocused) {
                    // 输入后的监听
                    if (TextUtils.isEmpty(s)) {
                        mTaxrate = 0.0
                    } else {
                        mTaxrate = s.toString().toDouble()
                    }
                    val csje: Double = et_csje.text.toString().toDouble() * (mTaxrate + 100) / 100
                    et_jshj.setText(csje.toString())
                }
            }
        })
        et_jshj.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化")
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {


            }

            override fun afterTextChanged(s: Editable) {
                if (et_jshj.isFocused) {
                    // 输入后的监听
                    if (TextUtils.isEmpty(s)) {
                        et_csje.setText("0.00")
                    } else {
                        et_csje.setText((s.toString().toDouble() * 100 / (mTaxrate + 100)).toString())
                    }
                }

            }
        })
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
        bt_view.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
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
                intent.putExtra("initamt", et_csje.getText().toString())
                intent.putExtra("taxrate", et_sl.getText().toString())
                intent.putExtra("amount", et_jshj.getText().toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

    }


    override fun executeSuccess() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                fymcId = data.extras!!.getString("id")
                fymc_edittext.setText(data.extras!!.getString("dictmc")!!.replace(">>", ""))
            }
        }
    }
}