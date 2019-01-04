package com.cr.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cr.activity.gzpt.dwzl.GzptDwzlActivity;
import com.cr.activity.gzpt.dwzl.GzptDwzlDwBjdwActivity;
import com.cr.activity.gzpt.dwzl.GzptDwzlDwLxfsActivity;
import com.cr.activity.gzpt.dwzl.GzptDwzlLxrBjlxrActivity;
import com.cr.activity.gzpt.dwzl.GzptDwzlLxrDetailActivity;
import com.cr.activity.gzpt.dwzl.xzdw.XzDwActivity;
import com.cr.adapter.GzptXzldAdapter;
import com.cr.dao.TelDao;
import com.cr.model.CustomerTel;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 新增来电
 *
 * @author Administrator
 */
public class GzptXzldActivity extends BaseActivity implements OnClickListener {
    BaseAdapter adapter;
    ListView xzldListView;
    List<CustomerTel> list = new ArrayList<CustomerTel>();
    EditText xzrqEditText;
    ImageButton cxButton;
    // List<GSLXRGroup>dataList=new ArrayList<GSLXRGroup>();
    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    int selectIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_xzld);// 设置成自己的页面文件
        checkNetWork(false);
        initActivity();// 该方法用于初始化Activity
        initListView();// 初始化ListView
        addFHMethod();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        xzrqEditText = (EditText) findViewById(R.id.xzrq_edit);
        xzrqEditText.setOnClickListener(this);
        xzldListView = (ListView) findViewById(R.id.xzld_listview);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        xzrqEditText.setText(sdf.format(new Date()));
        cxButton = (ImageButton) findViewById(R.id.search);
        cxButton.setOnClickListener(this);

        findServiceData(xzrqEditText.getText().toString());// 开启子线程获取特定接口的服务器数据用于更新页面
    }

    /**
     * 初始化ListView
     */
    private void initListView() {

        adapter = new GzptXzldAdapter(list, context, this);
        xzldListView.setAdapter(adapter);
        xzldListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // Intent intent=new
                // Intent(GzptXzldActivity.this,GzptHjzxKhzlActivity.class);
                // GSLXRGroup group=new GSLXRGroup();
                // group.setJhbid("");
                // intent.putExtra("object", group);
                // startActivity(intent);
                selectIndex = arg2;
                findServiceData2(list.get(arg2).getTel());
            }
        });
//		xzldListView.setOnItemLongClickListener(new OnItemLongClickListener() {
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				new AlertDialog.Builder(activity)
//				.setMultiChoiceItems(
//						new String[] { "新增单位", "保存至已有单位" }, null,
//						new OnMultiChoiceClickListener() {
//							@Override
//							public void onClick(DialogInterface arg0, int arg1,
//									boolean arg2) {
//								Intent intent=new Intent();
//								switch (arg1) {
//								case 0:
//									intent.putExtra("clientid", "0");
//									intent.setClass(activity, GzptDwzlDwBjdwActivity.class);
//									startActivity(intent);
//									arg0.dismiss();
//									break;
//								case 1:
//									intent.putExtra("dhhm", list.get(arg1).getTel());
//									intent.setClass(activity, GzptXzldBclxfsActivity.class);
//									startActivity(intent);
//									arg0.dismiss();
//									break;
//								default:
//									break;
//								}
//							}
//						}).show();
//				return false;
//			}
//		});
    }

    // 调用网络查询出特定接口的数据
    private void findServiceData(final String time) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    returnSuccessType = 1;
                    TelDao telDao = new TelDao(context);
                    list.clear();
                    list.addAll(telDao.findTel(time));
                    if (list.size() == 0) {
                        handler.sendEmptyMessage(EMPTY);
                    } else {
                        handler.sendEmptyMessage(SUCCESS);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 调用网络查询出特定接口的数据
    private void findServiceData2(final String phone) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("phone", phone);
        findServiceData(0, ServerURL.LXRINFOOFPHONE, parmMap);
    }

    /**
     * 创建日期及时间选择对话框
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        Calendar c = null;
        switch (id) {
            case 0:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                xzrqEditText.setText(year + "-" + (month + 1) + "-"
                                        + dayOfMonth);
                                // findServiceData(year + "-" + (month + 1) + "-"+
                                // dayOfMonth);
                            }
                        }, c.get(Calendar.YEAR),
                        // 传入年份
                        c.get(Calendar.MONTH),
                        // 传入月份
                        c.get(Calendar.DAY_OF_MONTH)
                        // 传入天数
                );
                break;
        }
        return dialog;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        // Log.v("dddd", returnSuccessType+"");
        switch (returnSuccessType) {
            case 0:
                if (returnJson.equals("")) {
//				Intent intent = new Intent(GzptXzldActivity.this,
//						GzptDwzlDwBjdwActivity.class);
//				intent.putExtra("tel", list.get(selectIndex).getTel());
//				intent.putExtra("lxrid", "0");// 单位ID为0，表示新增
//				intent.putExtra("clientid", "0");// 单位ID为0，表示新增
//				startActivity(intent);
                    new AlertDialog.Builder(activity)
                            .setTitle("无对应客户信息")
                            .setMultiChoiceItems(
                                    new String[]{"新增单位", "保存至已有单位"}, null,
                                    new OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1,
                                                            boolean arg2) {
                                            Intent intent = new Intent();
                                            switch (arg1) {
                                                case 0:
                                                    intent.putExtra("clientid", "0");
                                                    intent.putExtra("khlbid", "1");

                                                    intent.putExtra("tel", list.get(selectIndex).getTel());
                                                    intent.putExtra("dhmc", list.get(selectIndex).getName());
//                                                    intent.setClass(activity, XzDwActivity.class);
                                                    intent.setClass(activity, GzptDwzlDwBjdwActivity.class);
                                                    startActivity(intent);
                                                    arg0.dismiss();
                                                    break;
                                                case 1:
                                                    intent.putExtra("dhhm", list.get(selectIndex).getTel());
                                                    intent.setClass(activity, GzptXzldBclxfsActivity.class);
                                                    startActivity(intent);
                                                    arg0.dismiss();
                                                    break;

                                                default:
                                                    break;
                                            }
                                        }
                                    }).show();
                } else {
                    dataList.clear();
                    dataList.addAll((List<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson));
                    Intent intent = new Intent();
                    if (dataList.get(0).get("lxrid").toString().equals("0")) {
                        intent.setClass(GzptXzldActivity.this,
                                GzptDwzlActivity.class);
                    } else {
                        intent.setClass(GzptXzldActivity.this,
                                GzptDwzlLxrDetailActivity.class);
                        intent.putExtra("lxrid", dataList.get(0).get("lxrid")
                                .toString());
                        intent.putExtra("clientid", dataList.get(0).get("clientid")
                                .toString());
                        intent.putExtra("typesss", "1");
                    }
                    intent.putExtra("object", (Serializable) dataList.get(0));
                    startActivity(intent);
                }
                break;
            case 1:
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.search:
                findServiceData(xzrqEditText.getText().toString());
                break;
            case R.id.xzrq_edit:
                showDialog(0);
                break;
            default:
                break;
        }
    }
}