package com.cr.customer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.cr.tools.OperateMethod;

public class CustomerOnItemLongClickListener implements OnItemLongClickListener {

	private Context context;
//	private ListView listView;
//	private BaseAdapter adapter;
//	private List<?>list;
	private OperateMethod operateMethod;
	
	public CustomerOnItemLongClickListener() {
		
	}
	public CustomerOnItemLongClickListener(Context context,OperateMethod operateMethod){
		this.context=context;
//		this.listView=listView;
//		this.adapter=adapter;
//		this.list=list;
		this.operateMethod=operateMethod;
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		longClickDialog("",arg2);
		return true;
	}
	
	/**
	 * 长按事件
	 */
	private void longClickDialog(String title,final int index){
		final AlertDialog.Builder builder=new AlertDialog.Builder(context);
		title="请选择";
		builder.setTitle(title).setItems(new String[]{"删除"},new DialogInterface.OnClickListener() {  
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				switch (which) {
				case 0:
					operateMethod.operateData(index);
					break;
				default:
					break;
				}
			}
		}); 
		AlertDialog dialog=builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

}
