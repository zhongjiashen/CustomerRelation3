package com.cr.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.cr.dao.ClientIPDao;
import com.cr.model.ClientIP;
import com.cr.service.SocketService;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.permission.PermissionsActivity;
import com.permission.PermissionsChecker;

import java.util.List;

/**
 * 起始页
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	private EditText ipEditText;
	/**
	 * 端口输入框
	 */
	private EditText dkEditText;
	private ImageButton ljButton;
	private ImageButton tcButton;
	public static Intent intent;
	private Spinner ipSpinner;
	private ArrayAdapter<String> ipAdapter;
	public static Intent serviceIntent;
	private List<ClientIP> ipList;

	private ImageButton szButton;




	private static final int REQUEST_CODE = 0; // 请求码

	// 所需的全部权限
	static final String[] PERMISSIONS = new String[]{
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.CAMERA,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.CHANGE_WIFI_STATE


	};



	private PermissionsChecker mPermissionsChecker; // 权限检测器
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPermissionsChecker = new PermissionsChecker(this);
		initActivity();
		searchDBFroIp();
	}
	@Override protected void onResume() {
		super.onResume();

		// 缺少权限时, 进入权限配置页面
		if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
			startPermissionsActivity();
		}
	}

	private void startPermissionsActivity() {
		PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
	}

	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 拒绝时, 关闭页面, 缺少主要权限, 无法运行
		if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
			finish();
		}
	}
	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		szButton = (ImageButton) findViewById(R.id.sz_imagebutton);
		szButton.setOnClickListener(this);
		ipEditText = (EditText) findViewById(R.id.main_ip);
		ipSpinner = (Spinner) findViewById(R.id.ip_spinner);
		ipSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				ipEditText.setText(ipList.get(arg2).getIp());
				dkEditText.setText(ipList.get(arg2).getDk());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				ipEditText.setText(ShareUserInfo.getIP(context));
				dkEditText.setText(ShareUserInfo.getDK(context));
			}
		});
		// String ip = ShareUserInfo.getIP(context);
		// ip = ip.replace("http://", "");
		// ipEditText.setText(ip);
		dkEditText = (EditText) findViewById(R.id.main_dk);
		// dkEditText.setText(ShareUserInfo.getDK(context));
		ljButton = (ImageButton) findViewById(R.id.main_lj);
		tcButton = (ImageButton) findViewById(R.id.main_tc);
		ljButton.setOnClickListener(this);
		tcButton.setOnClickListener(this);

		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("LoginSuccess");
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);

	}

	/**
	 * 调用网络查询出特定接口的数据
	 */
	private void saveDbData() {
		new Thread(new Runnable() {
			public void run() {
				try {
					ClientIPDao cipdao = new ClientIPDao(context);
					ClientIP ip = new ClientIP();
					String i = ipEditText.getText().toString();
					i = i.replace("http://", "");
					i = "http://" + i;
					ip.setIp(i);
					// Log.v("dddd", i);
					if(ShareUserInfo.getKey(context, "socketPort").equals("")){
						ShareUserInfo.setKey(context, "socketPort", "3010");
					}
					ip.setDk(dkEditText.getText().toString());
					ShareUserInfo.setIP(context, i);
					ShareUserInfo.setDK(context, ip.getDk());
					ip.setIp(i.replace("http://", ""));
					cipdao.add(ip);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.main_lj://链接按钮点击事件
			// if(serviceIntent!=null){
			// Log.v("dddd", "停止serice");
			// stopService(serviceIntent);
			// }
			if (ipEditText.getText().toString().equals("")) {
				showToastPromopt("IP地址不能为空！");
				return;
			} else if (dkEditText.getText().toString().equals("")) {
				showToastPromopt("端口号不能为空！");
				return;
			}
			saveDbData();// 保存信息到服务器当中
			/*serviceIntent = new Intent(this, SocketService.class);
			String i = ipEditText.getText().toString();
			i = i.replace("http://", "");
			i = "http://" + i;
			serviceIntent.putExtra("ip", i);
			startService(serviceIntent);
			progressDialog = ProgressDialog.show(MainActivity.this, "请稍等...",
					"连接服务器中......", false); // 打开进度条
			progressDialog.setCancelable(true);*/

			Intent intent2 = new Intent(this, LoginActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent2);
			break;
		case R.id.main_tc://退出按钮点击事件
			finish();
			break;
		case R.id.sz_imagebutton://设置按钮点击事件
			final EditText editText=new EditText(this);
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			if(ShareUserInfo.getKey(context, "socketPort").equals("")){
				ShareUserInfo.setKey(context, "socketPort", "3010");
			}
			editText.setText(ShareUserInfo.getKey(context, "socketPort"));
			new AlertDialog.Builder(activity)
				.setTitle("设置通讯端口")
				.setView(editText)
				.setPositiveButton("取消",null)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if(editText.getText().toString().equals("")){
							showToastPromopt("请输入端口号!");
							return;
						}
						ShareUserInfo.setKey(context, "socketPort", editText.getText().toString());
						showToastPromopt("保存成功!");
					}
				})
				.show();
			break;
		default:
			break;
		}
	}

	@Override
	public void executeSuccess() {
		// TODO Auto-generated method stub
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("LoginSuccess")) {
				progressDialog.dismiss();
			}
		}
	};

	/**
	 * 查询数据库中的IP信息
	 */
	private void searchDBFroIp() {
		String msg = ShareUserInfo.getIP(context).replace("http://", "") + ":"
				+ ShareUserInfo.getDK(context);
		int index = 0;
		ClientIPDao clientIPDao = new ClientIPDao(context);
		ipList = clientIPDao.findAllIP();
		if (ipList.size() == 0) {
			for (int j = 0; j < 2; j++) {
				ClientIP ip = new ClientIP();
				ip.setDk("8031");
				if (j == 0) {
					ip.setIp("hengshicrm.eicp.net");
				} else if (j == 1) {
					ip.setIp("hengvideocrm.oicp.net");
				}
				ClientIPDao cipdao = new ClientIPDao(context);
				cipdao.add(ip);
				ipList.add(ip);
			}
		}
		String[] bffsString = new String[ipList.size()];
		for (int i = 0; i < ipList.size(); i++) {
			bffsString[i] = ipList.get(i).getIp() + ":" + ipList.get(i).getDk();
			if (bffsString[i].equals(msg)) {
				index = i;
			}
		}
		ipAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, bffsString);
		ipAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ipSpinner.setAdapter(ipAdapter);
		ipSpinner.setSelection(index);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}
}