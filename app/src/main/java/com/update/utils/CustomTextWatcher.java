package com.update.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class CustomTextWatcher implements TextWatcher {
    private UpdateTextListener mUpdateTextListener;
    public CustomTextWatcher( UpdateTextListener updateTextListener) {

        mUpdateTextListener = updateTextListener;
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString()!=null&&!s.toString().equals("")) {
            LogUtils.e("fsadfas");
            mUpdateTextListener.updateText(s.toString());
//            dataList.get(position).setCfhaoqty(Double.parseDouble(s.toString()));
        }
    }


    public interface  UpdateTextListener{
        void updateText(String string );
    }

}


