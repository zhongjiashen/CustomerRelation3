package com.cr.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 重写ListView，作用是当和ScrollView一起使用的时候不会出现滚动条的冲突
 * @author xXzX
 *
 */
public class CustomListView extends ListView{

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
				MeasureSpec.AT_MOST);      
		super.onMeasure(widthMeasureSpec, expandSpec);  
	}  
}
