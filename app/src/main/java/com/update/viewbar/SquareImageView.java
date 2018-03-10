package com.update.viewbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/11/6 0006 下午 4:24
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/11/6 0006         申中佳               V1.0
 */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
