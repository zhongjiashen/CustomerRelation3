package com.cr.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.cr.myinterface.SLViewValueChange;
import com.crcxj.activity.R;

/**
 * 自定义数量加减的控件
 * 
 * @author caiyanfei
 * @version $Id: PJView.java, v 0.1 2014-10-27 下午5:52:11 caiyanfei Exp $
 */
public class SLView2 extends LinearLayout implements OnClickListener{
    private ImageButton addImageView,plusImageView;//加号减号按钮
    private EditText slEditText;//得到的结果；
    private double sl=1.0;
    private SLViewValueChange change;
    public SLView2(Context context) {
        super(context);
        initView(context,null);
    }
    public SLView2(Context context,AttributeSet attr) {
        super(context,attr);
        initView(context,attr);
    }
    public double getSl() {
        return sl;
    }
    public void setSl(double sl) {
        this.sl = sl;
        slEditText.setText(sl+"");
    }
    public void setOnValueChange(SLViewValueChange slViewValueChange){
    	this.change=slViewValueChange;
    }
    /**
     * 初始化页面元素
     * 
     * @param context
     * @param attr
     */
    private void initView(Context context,AttributeSet attr){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sl_linearlayout2,this);
        addImageView=(ImageButton) findViewById(R.id.add);
        addImageView.setOnClickListener(this);
        plusImageView=(ImageButton) findViewById(R.id.plus);
        plusImageView.setOnClickListener(this);
        slEditText=(EditText) findViewById(R.id.spsl);
        slEditText.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            
            @Override
            public void afterTextChanged(Editable arg0) {
            	try {
            		sl=Double.parseDouble(slEditText.getText().toString().equals("")?"0":slEditText.getText().toString());
				} catch (Exception e) {
					e.printStackTrace();
					sl=0;
				}
                if(change!=null){
                    change.onValueChange(sl);
                }
            }
        });
    }
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.add:
                sl++;
//                setSl(sl);
                break;
            case R.id.plus:
               if(sl>1){
                   sl--;
               }
               break;
            default:
                break;
        }
        if(change!=null){
            change.onValueChange(sl);
        }
        slEditText.setText(sl+"");
    }
}
