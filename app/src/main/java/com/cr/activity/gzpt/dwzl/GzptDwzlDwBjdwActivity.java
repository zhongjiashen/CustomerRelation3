package com.cr.activity.gzpt.dwzl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.adapter.SlidePageAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlDwBjdwLxfsAdapter;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.mrwujay.cascade.model.CityModel;
import com.mrwujay.cascade.model.DistrictModel;
import com.mrwujay.cascade.model.ProvinceModel;
import com.mrwujay.cascade.service.XmlParserHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 工作平台-单位资料-单位-编辑单位
 * 
 * @author Administrator
 * 
 */
public class GzptDwzlDwBjdwActivity extends BaseActivity implements
		OnClickListener {
	private TextView dwxxTextView, lxfsTextView;
	private ViewPager viewPager;
	private LayoutInflater inflater;
	private View dwxxView, lxfsView;
	private ListView lxfsListView;
	private boolean isLxfs = false; // 是否是第一次加载
	private GzptDwzlDwBjdwLxfsAdapter lxfsAdapter;
	List<Map<String, Object>> lxfsList = new ArrayList<Map<String, Object>>();
	private EditText khbhEditText, khmcEditText, khdjEditText, khlxEditText,
			hylxEditText, khlbEditText, khlyEditText, dqEditText, frdbEditText,
			czEditText, dzEditText, wzEditText, bzEditText;
	private ImageView corsor1, corsor2;
	private String clientId = "", tel = ""; // 单位的ID
	private ImageView addImageView, delImageView, saveImageView;
	private String khdjId = "", khlxId = "", hylxId = "", khlyId = "",
			khlbId = "";
	private String dhmc;
	private String dq;
	
	// private WheelView mViewProvince;
	// private WheelView mViewCity;
	// private WheelView mViewDistrict;
	
	List<CityModel> cityList=null;
	
	List<ProvinceModel> provinceList = null;
	
	private List<DistrictModel> districtList=null;
	/**
	 * ����ʡ
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - ʡ value - ��
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - �� values - ��
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - �� values - �ʱ�
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * ��ǰʡ������
	 */
	protected String mCurrentProviceName;
	/**
	 * ��ǰ�е�����
	 */
	protected String mCurrentCityName;
	/**
	 * ��ǰ��������
	 */
	protected String mCurrentDistrictName = "";

	/**
	 * ��ǰ������������
	 */
	protected String mCurrentZipCode = "";
	
	String dhhm=" ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_dwzl_dw_bjdw);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (this.getIntent().hasExtra("clientid")) {
			clientId = this.getIntent().getExtras().getString("clientid");
			if(!clientId.equals("0")) {
				dhmc = this.getIntent().getExtras().getString("dhmc");
				dhhm = this.getIntent().getExtras().getString("dhhm");
			}
		}
		if (this.getIntent().hasExtra("tel")) {
			tel = this.getIntent().getExtras().getString("tel");
		}
		initActivity();
		initDwxxListView();
		initLxfsListView();
		addFHMethod();
		searchDateDwxx(0);
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {

		dwxxTextView = (TextView) findViewById(R.id.dwxx_textview);
		dwxxTextView.setOnClickListener(this);
		lxfsTextView = (TextView) findViewById(R.id.lxfs_textview);
		lxfsTextView.setOnClickListener(this);
		corsor1 = (ImageView) findViewById(R.id.corsor1);
		corsor2 = (ImageView) findViewById(R.id.corsor2);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		List<View> viewPage = new ArrayList<View>();
		dwxxView = inflater.inflate(R.layout.activity_gzpt_dwzl_dw_bjdw_dwxx,
				null);
		lxfsView = inflater.inflate(R.layout.activity_gzpt_dwzl_dw_bjdw_lxfs,
				null);
		viewPage.add(dwxxView);
		viewPage.add(lxfsView);
		SlidePageAdapter myAdapter = new SlidePageAdapter(viewPage, this);
		viewPager.setAdapter(myAdapter);
		// 下面的点图
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
					viewPager.setCurrentItem(0);
					corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
					corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
				} else if (arg0 == 1) {
					if (clientId.equals("0")) {
						viewPager.setCurrentItem(0);
						showToastPromopt("请先保存单位信息!");
						return;
					}
					viewPager.setCurrentItem(1);
					corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
					corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
					if (!isLxfs) {
						lxfsList.clear();
						searchDateLxfs(1);
						isLxfs = true;
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// Log.v("dddd", "onPageScrollStateChanged");
			}
		});
	}

	@Override
	public void onExecuteFh() {
		setResult(RESULT_OK);
		super.onExecuteFh();
	}

	protected void initProvinceDatas() {
		
		AssetManager asset = getAssets();
		try {
			InputStream input = asset.open("province_data.xml");
			// ����һ������xml�Ĺ�������
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// ����xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// ��ȡ��������������
			provinceList = handler.getDataList();
			// */ ��ʼ��Ĭ��ѡ�е�ʡ���С���
			if (provinceList != null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList != null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0)
							.getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			// */
			mProvinceDatas = new String[provinceList.size()];
			for (int i = 0; i < provinceList.size(); i++) {
				// ��������ʡ������
				mProvinceDatas[i] = provinceList.get(i).getName();
				List<CityModel> cityList = provinceList.get(i).getCityList();
				String[] cityNames = new String[cityList.size()];
				for (int j = 0; j < cityList.size(); j++) {
					// ����ʡ����������е�����
					cityNames[j] = cityList.get(j).getName();
					List<DistrictModel> districtList = cityList.get(j)
							.getDistrictList();
					String[] distrinctNameArray = new String[districtList
							.size()];
					DistrictModel[] distrinctArray = new DistrictModel[districtList
							.size()];
					for (int k = 0; k < districtList.size(); k++) {
						// ����������������/�ص�����
						DistrictModel districtModel = new DistrictModel(
								districtList.get(k).getName(), districtList
										.get(k).getZipcode());
						// ��/�ض��ڵ��ʱ࣬���浽mZipcodeDatasMap
						mZipcodeDatasMap.put(districtList.get(k).getName(),
								districtList.get(k).getZipcode());
						distrinctArray[k] = districtModel;
						distrinctNameArray[k] = districtModel.getName();
					}
					// ��-��/�ص����ݣ����浽mDistrictDatasMap
					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
				}
				// ʡ-�е����ݣ����浽mCitisDatasMap
				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * ���ݵ�ǰ���У�������WheelView����Ϣ
	 */
	// private void updateAreas() {
	// int pCurrent = mViewCity.getCurrentItem();
	// mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
	// String[] areas = mDistrictDatasMap.get(mCurrentCityName);
	//
	// if (areas == null) {
	// areas = new String[] { "" };
	// }
	// mViewDistrict
	// .setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
	// mViewDistrict.setCurrentItem(0);
	// }

	/**
	 * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
	 */
	// @Override
	// public void onChanged(WheelView wheel, int oldValue, int newValue) {
	// // TODO Auto-generated method stub
	// if (wheel == mViewProvince) {
	// updateCities();
	// } else if (wheel == mViewCity) {
	// updateAreas();
	// } else if (wheel == mViewDistrict) {
	// mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
	// mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
	// }
	// }

	/**
	 * ���ݵ�ǰ���У�������WheelView����Ϣ
	 */

	/**
	 * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
	 */
	private void updateCities() {
		// int pCurrent = mViewProvince.getCurrentItem();
		// mCurrentProviceName = mProvinceDatas[pCurrent];
		// String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		// if (cities == null) {
		// cities = new String[] { "" };
		// }
		// mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this,
		// cities));
		// mViewCity.setCurrentItem(0);
		// updateAreas();
	}

	/**
	 * 初始化”单位“页面
	 */
	private void initDwxxListView() {
		// mViewProvince = (WheelView) dwxxView.findViewById(R.id.id_province);
		// mViewCity = (WheelView) dwxxView.findViewById(R.id.id_city);
		// mViewDistrict = (WheelView) dwxxView.findViewById(R.id.id_district);
		// mViewProvince.addChangingListener(this);
		// mViewCity.addChangingListener(this);
		// mViewDistrict.addChangingListener(this);
		 initProvinceDatas();
		// mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this,
		// mProvinceDatas));
		// // ���ÿɼ���Ŀ����
		// mViewProvince.setVisibleItems(7);
		// mViewCity.setVisibleItems(7);
		// mViewDistrict.setVisibleItems(7);
		// updateCities();
		// updateAreas();
		khbhEditText = (EditText) dwxxView.findViewById(R.id.khbh_edittext);
		khmcEditText = (EditText) dwxxView.findViewById(R.id.khmc_edittext);
		khmcEditText.setText(dhmc);
		khdjEditText = (EditText) dwxxView.findViewById(R.id.khdj_edittext);
		khdjEditText.setOnClickListener(this);
		khlxEditText = (EditText) dwxxView.findViewById(R.id.khlx_edittext);
		khlxEditText.setOnClickListener(this);

		khlbEditText = (EditText) dwxxView.findViewById(R.id.khlb_edittext);
		khlbEditText.setOnClickListener(this);
		hylxEditText = (EditText) dwxxView.findViewById(R.id.hylx_edittext);
		hylxEditText.setOnClickListener(this);
		khlyEditText = (EditText) dwxxView.findViewById(R.id.khly_edittext);
		khlyEditText.setOnClickListener(this);
		dqEditText = (EditText) dwxxView.findViewById(R.id.dq_edittext);
		dqEditText.setOnClickListener(this);
		frdbEditText = (EditText) dwxxView.findViewById(R.id.frdb_edittext);
		czEditText = (EditText) dwxxView.findViewById(R.id.cz_edittext);
		dzEditText = (EditText) dwxxView.findViewById(R.id.dz_edittext);
		wzEditText = (EditText) dwxxView.findViewById(R.id.wz_edittext);
		bzEditText = (EditText) dwxxView.findViewById(R.id.bz_edittext);
		if (clientId.equals("0")) {
			dwxxView.findViewById(R.id.khbh_linearlayout).setVisibility(
					View.GONE);
			dwxxView.findViewById(R.id.khbh_view).setVisibility(View.GONE);
		} else {
			dwxxView.findViewById(R.id.khbh_linearlayout).setVisibility(
					View.VISIBLE);
			dwxxView.findViewById(R.id.khbh_view).setVisibility(View.VISIBLE);
		}
		if (this.getIntent().hasExtra("khlbid")) {
			// khdjId=this.getIntent().getExtras().getString("khdjid");
			// khdjEditText.setText(this.getIntent().getExtras().getString("khdjname"));
			khlbId = this.getIntent().getExtras().getString("khlbid");
			if (khlbId.equals("1")) {
				khlbEditText.setText("客户");
			} else if (khlbId.equals("2")) {
				khlbEditText.setText("供应商");
			} else if (khlbId.equals("3")) {
				khlbEditText.setText("竞争对手");
			} else if (khlbId.equals("4")) {
				khlbEditText.setText("渠道");
			}
		}
		if (khlbId.equals("")) {
			khlbId = "1";
			khlbEditText.setText("客户");
		}
	}

	/**
	 * 初始化”联系方式“页面
	 */
	private void initLxfsListView() {
		lxfsListView = (ListView) lxfsView.findViewById(R.id.listview);
		lxfsAdapter = new GzptDwzlDwBjdwLxfsAdapter(lxfsList, activity);
		lxfsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(GzptDwzlDwBjdwActivity.this,
						GzptDwzlDwBjdwLxfsAddActivity.class);
				intent.putExtra("id", lxfsList.get(arg2).get("id").toString());
				intent.putExtra("itemno", lxfsList.get(arg2).get("itemno")
						.toString());
				intent.putExtra("lb", lxfsList.get(arg2).get("lb").toString());
				intent.putExtra("clientid", clientId);
				startActivityForResult(intent, 0);
			}
		});
		lxfsListView.setAdapter(lxfsAdapter);
		addImageView = (ImageView) lxfsView.findViewById(R.id.add_imageview);
		addImageView.setOnClickListener(this);
		delImageView = (ImageView) lxfsView.findViewById(R.id.del_imageview);
		delImageView.setOnClickListener(this);
		saveImageView = (ImageView) findViewById(R.id.save);
		saveImageView.setOnClickListener(this);
	}

	/**
	 * 连接网络的操作(查询单位信息)
	 */
	private void searchDateDwxx(int type) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("clientid", clientId);
		findServiceData(type, ServerURL.CLIENTINFO, parmMap);
	}

	/**
	 * 连接网络的操作(查询联系方式)
	 */
	private void searchDateLxfs(int type) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		// parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("clientid", clientId);
		parmMap.put("havelxr", "0");
		findServiceData(type, ServerURL.CLIENTLXFSLIST, parmMap);
	}

	/**
	 * 连接网络的操作(保存单位信息)
	 */
	private void searchDateSaveDw(int type) {
		if (khmcEditText.getText().toString().equals("")) {
			showToastPromopt("单位不能为空！");
			return;
		} else if (khlbEditText.getText().toString().equals("")) {
			showToastPromopt("请选择客户类别！");
			return;
		} else if (khdjEditText.getText().toString().equals("")) {
			showToastPromopt("请选择客户等级！");
			return;
		}
		// else if (khlxEditText.getText().toString().equals("")) {
		// showToastPromopt("请选择客户类型！");
		// return;
		// }else if (hylxEditText.getText().toString().equals("")) {
		// showToastPromopt("请选择行业类型！");
		// return;
		// }else if (khlyEditText.getText().toString().equals("")) {
		// showToastPromopt("请选择客户来源！");
		// return;
		// }
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("clientid", clientId);
		parmMap.put("types", khlbId);
		parmMap.put("cname", khmcEditText.getText().toString());
		String dq = mCurrentProviceName + "/" + mCurrentCityName + "/"
				+ mCurrentDistrictName;
		parmMap.put("areafullname", dqEditText.getText().toString());
		parmMap.put("areatypeid", "");
		parmMap.put("typeid", khdjId);
		parmMap.put("hytypeid", hylxId);
		parmMap.put("csourceid", khlyId);
		parmMap.put("khtypeid", khlxId);
		parmMap.put("frdb", frdbEditText.getText().toString());
		parmMap.put("cnet", wzEditText.getText().toString());
		parmMap.put("fax", czEditText.getText().toString());
		parmMap.put("address", dzEditText.getText().toString());
		findServiceData(type, ServerURL.CLIENTSAVE, parmMap);
	}

	/**
	 * 连接网络的操作(删除联系方式)
	 */
	private void searchDateDelLxfs(int type, String ids) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		// parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("ids", ids);
		parmMap.put("itemtype", "LXFS");
		findServiceData(type, ServerURL.DELDATA, parmMap);
	}

	/**
	 * 连接网络的操作(单位联系方式保存)
	 */
	private void searchDateSave() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("id", "0");
		parmMap.put("clientid", clientId);
		parmMap.put("lxrid", "0");
		parmMap.put("lb", tel.length() == 11 ? "2" : "1");
		parmMap.put("itemno", tel);
		findServiceData(4, ServerURL.LXFSSAVE, parmMap);
	}

	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.dwxx_textview:
			viewPager.setCurrentItem(0);
			corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
			corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
			break;
		case R.id.lxfs_textview:
			if (clientId.equals("0")) {
				showToastPromopt("请先保存单位信息");
				return;
			}
			viewPager.setCurrentItem(1);
			corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
			corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
			if (!isLxfs) {
				lxfsList.clear();
				searchDateLxfs(1);
				isLxfs = true;
			}
			break;
		case R.id.add_imageview:
			intent.setClass(this, GzptDwzlDwBjdwLxfsAddActivity.class);
			intent.putExtra("dhhm", "");
			intent.putExtra("clientid", clientId);
			startActivityForResult(intent, 0);
			break;
		case R.id.del_imageview:
			String ids = "";
			List<Map<String, Object>> maps = lxfsAdapter.getList();
			for (int i = 0; i < maps.size(); i++) {
				Map<String, Object> map = maps.get(i);
				if (map.get("select") != null
						&& map.get("select").toString().equals("true")) {
					ids += map.get("id") + ",";
				}
			}
			if (ids.equals("")) {
				showToastPromopt("请选择要删除的项！");
				return;
			}
			searchDateDelLxfs(2, ids.substring(0, ids.length() - 1));
			break;
		case R.id.khdj_edittext:
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "KHDJ");
			startActivityForResult(intent, 1);
			break;
		case R.id.khlx_edittext:
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "KHLX");
			startActivityForResult(intent, 2);
			break;
		case R.id.hylx_edittext:
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "HYLX");
			startActivityForResult(intent, 3);
			break;
		case R.id.khly_edittext:
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "KHLY");
			startActivityForResult(intent, 4);
			break;
		case R.id.khlb_edittext:
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "KHLB");
			startActivityForResult(intent, 5);
			break;
		case R.id.save:
//			Log.v("dddd", "0000000000000000");
			searchDateSaveDw(3);
			break;
		case R.id.dq_edittext:
			setSheng();
			break;
		default:
			break;
		}
	}
	/**
	 * 选择省
	 */
	private void setSheng(){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("选择省");



		// 设置一个下拉的列表选择项
		builder.setItems(mProvinceDatas, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(activity, "选择的城市为：" + mProvinceDatas[which],
//						Toast.LENGTH_SHORT).show();
				dq=mProvinceDatas[which];
				setShi(which);
			}
		});
		builder.show();
	}
	/**
	 * 选择市
	 */
	private void setShi(int index){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("选择市");
		cityList=provinceList.get(index).getCityList();
		final String city[]=new String[cityList.size()];
		for(int i=0;i<cityList.size();i++){
			city[i]=cityList.get(i).getName();
		}
		// 设置一个下拉的列表选择项
		builder.setItems(city, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(activity, "选择的城市为：" + mProvinceDatas[which],
//						Toast.LENGTH_SHORT).show();
				dq+="/"+city[which];
				setQu(which);
			}
		});
		builder.show();
	}
	/**
	 * 选择区
	 */
	private void setQu(int index){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("选择区");
		districtList=cityList.get(index).getDistrictList();
		final String qu[]=new String[districtList.size()];
		for(int i=0;i<districtList.size();i++){
			qu[i]=districtList.get(i).getName();
		}
		// 设置一个下拉的列表选择项
		builder.setItems(qu, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(activity, "选择的城市为：" + mProvinceDatas[which],
//						Toast.LENGTH_SHORT).show();
				dq+="/"+qu[which];
				dqEditText.setText(dq);
			}
		});
		builder.show();
	}
	
	
	/**
     * 连接网络的操作(单位联系方式保存)
     */
    private void SaveLxrSave(String cid) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("id", "0");
        parmMap.put("clientid", cid);
        parmMap.put("lxrid", "0");
        parmMap.put("lb", dhhm.length()==11?"2":"1");
        parmMap.put("itemno",dhhm);
        findServiceData2(10, ServerURL.LXFSSAVE, parmMap,false);
    }

	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
		case 0:
			if (returnJson.equals("")) {
				return;
			}
			Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
					.paseJsonToObject(returnJson)).get(0);
			khbhEditText.setText(object.get("code").toString());
			khmcEditText.setText(object.get("cname").toString());
			khdjEditText.setText(object.get("typename").toString());
			khlxEditText.setText(object.get("khtypename").toString());
			hylxEditText.setText(object.get("hylxname").toString());
			khlyEditText.setText(object.get("khlyname").toString());
			 dqEditText.setText(object.get("areafullname").toString());
			String[] dq = object.get("areafullname").toString().split("/");
			if (dq.length >= 3) {
				mCurrentProviceName = dq[dq.length - 3];
				mCurrentCityName = dq[dq.length - 2];
				mCurrentDistrictName = dq[dq.length - 1];
			}
			frdbEditText.setText(object.get("frdb").toString());
			czEditText.setText(object.get("fax").toString());
			dzEditText.setText(object.get("address").toString());
			wzEditText.setText(object.get("cnet").toString());
			bzEditText.setText(object.get("memo").toString());
			khdjId = object.get("typeid").toString();
			khlxId = object.get("khtypeid").toString();
			hylxId = object.get("hytypeid").toString();
			khlyId = object.get("csourceid").toString();
			khlbId = object.get("types").toString();
			if (khlbId.equals("1")) {
				khlbEditText.setText("客户");
			} else if (khlbId.equals("2")) {
				khlbEditText.setText("供应商");
			} else if (khlbId.equals("3")) {
				khlbEditText.setText("竞争对手");
			} else if (khlbId.equals("4")) {
				khlbEditText.setText("渠道");
			} else if (khlbId.equals("5")) {
				khlbEditText.setText("员工");
			}
			break;
		case 1:
			if (returnJson.equals("")) {
				showToastPromopt(2);
			} else {
				lxfsList.addAll((List<Map<String, Object>>) PaseJson
						.paseJsonToObject(returnJson));
			}
			lxfsAdapter.notifyDataSetChanged();
			break;
		case 2:
			if (returnJson.equals("")) {
				showToastPromopt("操作成功");
				lxfsList.clear();
				searchDateLxfs(1);
			} else {
				showToastPromopt("操作失败" + returnJson.substring(5));
			}
			break;
		case 3:
			if (returnJson.equals("")) {
				showToastPromopt("保存成功");
				clientId = returnJsonId;
				// setResult(RESULT_OK);
				// finish();
				SaveLxrSave(clientId);
				Log.v("dddd", ":;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
				if (this.getIntent().hasExtra("tel")) {// 如果是从新增来动中过来的话就保存联系方式进去
					searchDateSave();
				}
			} else {
				showToastPromopt("保存失败" + returnJson.substring(6));
			}
			break;
		case 4:// 保存从新增来电中带过来的电话（此处不做操作）

			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {// 编辑联系人
				// viewPager.setCurrentItem(0);
				lxfsList.clear();
				dhhm="";
				searchDateLxfs(1);
			} else if (requestCode == 1) {
				khdjId = data.getExtras().getString("id");
				khdjEditText.setText(data.getExtras().getString("dictmc"));
			} else if (requestCode == 2) {
				khlxId = data.getExtras().getString("id");
				khlxEditText.setText(data.getExtras().getString("dictmc"));
			} else if (requestCode == 3) {
				hylxId = data.getExtras().getString("id");
				hylxEditText.setText(data.getExtras().getString("dictmc"));
			} else if (requestCode == 4) {
				khlyId = data.getExtras().getString("id");
				khlyEditText.setText(data.getExtras().getString("dictmc"));
			} else if (requestCode == 5) {
				khlbId = data.getExtras().getString("id");
				khlbEditText.setText(data.getExtras().getString("dictmc"));
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			setResult(RESULT_OK);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
