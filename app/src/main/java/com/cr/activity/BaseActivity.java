package com.cr.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cr.activity.index.IndexActivity;
import com.cr.myinterface.SelectValueChange;
import com.cr.tools.MyApplication;
import com.cr.tools.NetworkCheck;
import com.cr.tools.ServerRequest;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.model.KtQxpdData;
import com.update.model.Serial;

@SuppressLint("HandlerLeak")
public abstract class BaseActivity extends AppCompatActivity {
	public final int SUCCESS = 0;// 获取数据成功
	public final int FIELD = 1;// 获取数据失败
	public final int EMPTY = 2;// 获取到的数据为空
	final static String noNetWork = "网络连接不可用，请检查网络！";// 没有网络的提示
	final static String getDateException = "获取服务器数据失败！";// 获取数据异常提示
	final static String noDate = "没有找到符合条件的数据";// 服务器获取的数据为空的提示

	public int returnSuccessType = 0;// 用于区分调用的次数，以便在返回的时候分别给予应答（默认就一次）

	public ProgressDialog progressDialog;// 弹出框进度条
	public Handler handler;//
	public NetworkInfo networkInfo = null;
	public Context context;
	public BaseActivity activity;
	public boolean isShowDialog = true;

	public abstract void executeSuccess();// 执行成功的操作

	public String returnJson;// 返回的字符串
	public String returnJsonId;// 保存成功后返回的ID

	public int currentPage = 1;// 当前页
	public int pageSize = 10;// 每页的条数

	private DatePickerDialog dateDialog = null;
	private TimePickerDialog timeDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);// 将当前创建的Activity对象封装起来，用于退出的时候统一释放
		context = this.getApplicationContext();
		activity = this;
		networkInfo = NetworkCheck.check(BaseActivity.this);// 检测当前网络的状态
		updateUIProgressHandle();
	}

	/**
	 * 检测网络并给出相应的提示
	 */
	public boolean checkNetWork(boolean isShowDialog) {
		networkInfo = NetworkCheck.check(BaseActivity.this);// 检测当前网络的状态
		this.isShowDialog = isShowDialog;
		if (networkInfo != null) {
			if (isShowDialog) {
				try {
					progressDialog = ProgressDialog.show(BaseActivity.this,
							"请稍等...", "数据正在加载中......", false); // 打开进度条
					progressDialog.setCancelable(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		} else {
			showToastPromopt(0);// 如果为null提示网络不可用，则不进行更新页面
			return false;
		}
	}

	/**
	 * 从后台获得数据通过Handle更新UI界面
	 */
	public void updateUIProgressHandle() {
		handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case SUCCESS:
					if (isShowDialog) {
						progressDialog.dismiss();
					}
					if(returnSuccessType==9){//如果是权限判断请求
						List<KtQxpdData> list = new Gson().fromJson(returnJson, new TypeToken<List<KtQxpdData>>() {
						}.getType());
						if(list.size()>0){
							ShareUserInfo.setKey(context, "ll", list.get(0).getNExplore()+"");//
							ShareUserInfo.setKey(context, "xz", list.get(0).getNInsert()+"");//
							ShareUserInfo.setKey(context, "bj", list.get(0).getNUpdate()+"");//
							ShareUserInfo.setKey(context, "sc", list.get(0).getNDelete()+"");//
							UserPermissionsCallBack();
						}
					}else {
						executeSuccess();
					}
					break;
				case FIELD:
					if (isShowDialog) {
						progressDialog.dismiss();
					}
					onExecuteFailed();
					break;
				case EMPTY:
					if (isShowDialog) {
						progressDialog.dismiss();
					}
					onExecuteEmpty();
					break;
				}
			};
		};
	}

	/**
	 * 添加返回方法
	 */
	public void addFHMethod() {
		ImageButton imageButton = (ImageButton) findViewById(R.id.fh);
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onExecuteFh();
			}
		});
	}

	/**
	 * 用户权限回调
	 */
	protected void UserPermissionsCallBack(){

	}

	/**
	 * 添加返回的退出方法
	 */
	public void addZYMethod() {
		ImageButton imageButton = (ImageButton) findViewById(R.id.zy);
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(BaseActivity.this,
						IndexActivity.class);
				startActivity(intent);
			}
		});
	}


	public void CheckOperPriv(String menuid){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("menuid", menuid);
		findServiceData2(9, "checkoperpriv", parmMap, true);
	}

	// 调用网络查询出特定接口的数据
	public void findServiceData(int returnSuccessType, final String methodName,
			final Map<String, Object> params) {
		// Log.v("dddd",
		// SocThread.client.isConnected()+":"+SocThread.client.isClosed());
		if (ShareUserInfo.getKey(context, "networklock").equals("true")) {
			new AlertDialog.Builder(this)
					.setTitle("提示信息")
					.setMessage("与服务器断开连接,客户端程序将退出！")
					.setNegativeButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									MyApplication.getInstance().exit();
								}
							}).show();
			return;
		}
		this.isShowDialog = true;
		this.returnSuccessType = returnSuccessType;
		setInputManager(false);
		if (!checkNetWork(isShowDialog)) {
			return;
		}
		new Thread(new Runnable() {
			public void run() {
				try {
					returnJson = ServerRequest.webServicePost(methodName,
							params, context);
					if (returnJson.length() > 0
							&& Character.isDigit(returnJson.charAt(0))) {
						returnJsonId = returnJson;
						returnJson = "";
					}
					if (returnJson == null) {
						handler.sendEmptyMessage(FIELD);
					} else {
						handler.sendEmptyMessage(SUCCESS);
					}
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(FIELD);
				}
			}
		}).start();
	}

	// 调用网络查询出特定接口的数据
	public void findServiceData2(int returnSuccessType,
			final String methodName, final Map<String, Object> params,
			boolean isShowDialog) {
		// Log.v("dddd",
		// SocThread.client.isConnected()+":"+SocThread.client.isClosed());
		if (ShareUserInfo.getKey(context, "networklock").equals("true")) {
			new AlertDialog.Builder(this)
					.setTitle("提示信息")
					.setMessage("与服务器断开连接,客户端程序将退出！")
					.setNegativeButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									MyApplication.getInstance().exit();
								}
							}).show();
			return;
		}
		this.isShowDialog = isShowDialog;
		this.returnSuccessType = returnSuccessType;
		setInputManager(false);
		if (!checkNetWork(isShowDialog)) {
			return;
		}
		new Thread(new Runnable() {
			public void run() {
				try {
					returnJson = ServerRequest.webServicePost(methodName,
							params, context);
					if (returnJson.length() > 0
							&& Character.isDigit(returnJson.charAt(0))) {
						returnJsonId = returnJson;
						returnJson = "";
					}
					if (returnJson == null) {
						handler.sendEmptyMessage(FIELD);
					} else {
						handler.sendEmptyMessage(SUCCESS);
					}
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(FIELD);
				}
			}
		}).start();
	}

	// 调用网络查询出特定接口的数据
	public void findServiceData3(int returnSuccessType,
			final String methodName, final Map<String, Object> params) {
		this.isShowDialog = true;
		this.returnSuccessType = returnSuccessType;
		setInputManager(false);
		if (!checkNetWork(isShowDialog)) {
			return;
		}
		new Thread(new Runnable() {
			public void run() {
				try {
					returnJson = ServerRequest.webServicePost(methodName,
							params, context);

					if (returnJson.length() > 0
							&& Character.isDigit(returnJson.charAt(0))) {
						returnJsonId = returnJson;
						returnJson = "";
					}
					if (returnJson == null) {
						handler.sendEmptyMessage(FIELD);
					} else {
						handler.sendEmptyMessage(SUCCESS);
					}
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(FIELD);
				}
			}
		}).start();
	}

	/**
	 * 显示常用的几种提示信息
	 * 
	 * @param index
	 */
	public void showToastPromopt(int index) {
		switch (index) {
		case 0:
			Toast.makeText(BaseActivity.this, noNetWork, Toast.LENGTH_SHORT)
					.show();
			break;
		case 1:
			Toast.makeText(BaseActivity.this, getDateException,
					Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(BaseActivity.this, noDate, Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}
	}

	/**
	 * 默认的获取服务器失败执行的方法
	 */
	public void onExecuteFailed() {
		showToastPromopt(1);
	}

	/**
	 * 默认的获取服务器数据为空执行的方法
	 */
	public void onExecuteEmpty() {
		showToastPromopt(2);
	}

	/**
	 * 按返回键要执行的操作
	 */
	public void onExecuteFh() {
		finish();
		setInputManager(false);
	}

	/**
	 * 显示用户给定的提示信息
	 */
	public void showToastPromopt(String showText) {
		Toast.makeText(BaseActivity.this, showText, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示和隐藏键盘
	 */
	public void setInputManager(boolean b) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (getCurrentFocus() != null) {
			if (b) {
				imm.showSoftInput(getCurrentFocus(),
						InputMethodManager.SHOW_FORCED);
			} else {
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						0); // 强制隐藏键盘
			}
		}
	}

	/**
	 * 长按事件
	 */
	// OnItemLongClickListener longClickListener=new OnItemLongClickListener() {
	// @Override
	// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	// int arg2, long arg3) {
	// // longClickDialog("", arg2);
	// return true;
	// }
	// };
	// 保存成功后要做的操纵的弹出框
	// public void showAfterSaveMsg(){
	// new AlertDialog.Builder(context).setTitle("提示信息")
	// .setMessage("数据保存成功").setNegativeButton("返回", new
	// DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface arg0, int arg1) {
	// onExecuteFh();
	// }
	// }).setNeutralButton("取消", null).show();
	// }
	/**
	 * 退出系统的方法
	 */
	public void exitSystem() {

	}

	/**
	 * 添加播放声音
	 * 
	 * @param context
	 * @param calendar
	 *            type2,0个人日程
	 */
	public void setAlarmTime(Context context, Calendar calendar, int type,
			int type2) {
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(new Date());
		if (calendar2.getTimeInMillis() > calendar.getTimeInMillis()) {
			if (type == 0) {
				return;
			} else if (type == 1) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			} else if (type == 2) {
				calendar.add(Calendar.DAY_OF_MONTH, 7);
			} else if (type == 3) {
				calendar.add(Calendar.MONTH, 1);
			}
		}
		long millis = calendar.getTimeInMillis();
		AlarmManager am = (AlarmManager) context
				.getSystemService(ALARM_SERVICE);
		Intent intent = new Intent("android.alarm.demo.action");
		intent.putExtra("type", type2);
		PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		long interval = 0;// 闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
		// Log.v("dddd", type+"");
		if (type == 0) {// 一次性

		} else if (type == 1) {// 每天
			interval = 24 * 60 * 60 * 1000;
		} else if (type == 2) {// 每周
			interval = 7 * 24 * 60 * 60 * 1000;
		} else if (type == 3) {// 每月
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
			calendar.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
			int maxDate = calendar.get(Calendar.DATE);
			interval = maxDate * 24 * 60 * 60 * 1000;
		}
		// Log.v("dddd", interval+"");
		am.setRepeating(AlarmManager.RTC_WAKEUP, millis, interval, sender);
	}

	/* 初始化日期的弹出选择框 */
	public void date_init(final EditText editText) {
		DatePickerDialog.OnDateSetListener otsl = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int year, int month,
					int dayOfMonth) {
				month++;
				String myMonth = "";
				String myDay = "";
				if (month < 10) {
					myMonth = "0" + month;
				} else {
					myMonth = "" + month;
				}
				if (dayOfMonth < 10) {
					myDay = "0" + dayOfMonth;
				} else {
					myDay = "" + dayOfMonth;
				}
				editText.setText(year + "-" + myMonth + "-" + myDay);
				dateDialog.dismiss();
			}
		};
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		dateDialog = new DatePickerDialog(this, otsl, year, month, day);
		dateDialog.show();
	}

	/* 初始化日期的弹出选择框 */
	public void date_init2(final SelectValueChange selectValueChange) {
		DatePickerDialog.OnDateSetListener otsl = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int year, int month,
					int dayOfMonth) {
				month++;
				String myMonth = "";
				String myDay = "";
				if (month < 10) {
					myMonth = "0" + month;
				} else {
					myMonth = "" + month;
				}
				if (dayOfMonth < 10) {
					myDay = "0" + dayOfMonth;
				} else {
					myDay = "" + dayOfMonth;
				}
				// editText.setText(year + "-"+ myMonth + "-"+ myDay);
				selectValueChange.onValueChange(year + "-" + myMonth + "-"
						+ myDay);
				dateDialog.dismiss();
			}
		};
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		dateDialog = new DatePickerDialog(this, otsl, year, month, day);
		dateDialog.show();
	}

	/* 初始化时间的选择框 */
	public void time_init(final EditText editText) {
		TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
			int i = 0;

			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String hour = "";
				if (hourOfDay < 10) {
					hour = "0" + hourOfDay;
				} else {
					hour = hourOfDay + "";
				}
				String myMinute = "";
				if (minute < 10) {
					myMinute = "0" + minute;
				} else {
					myMinute = minute + "";
				}
				if (i == 0) {
					editText.setText(editText.getText().toString() + " " + hour
							+ ":" + myMinute + ":00");
					i++;
				}
				timeDialog.dismiss();
			}
		};
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		timeDialog = new TimePickerDialog(this, otsl, hourOfDay, minute, true);
		timeDialog.show();
	}
	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
	 * if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==
	 * KeyEvent.ACTION_DOWN){
	 * if(progressDialog!=null&&progressDialog.isShowing()){
	 * progressDialog.dismiss(); }else{ finish(); } return true; } return
	 * super.onKeyDown(keyCode, event); }
	 */
	
	/**
	 * 详情里面显示制单人信息
	 */
	public void showZdr(Map<String, Object> obj){
		EditText zdrEditText=(EditText) findViewById(R.id.zdr_edittext);
		zdrEditText.setText(obj.get("opname").toString());
	}
}