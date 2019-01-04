package com.update.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.mrwujay.cascade.model.CityModel;
import com.mrwujay.cascade.model.DistrictModel;
import com.mrwujay.cascade.model.ProvinceModel;
import com.mrwujay.cascade.service.XmlParserHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class AreaSelectionDialog {
    private Activity mActivity;
    List<CityModel> cityList = null;

    List<ProvinceModel> provinceList = null;

    private List<DistrictModel> districtList = null;
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


    public enum Type {
        ALL, PROVINCE, PROVINCE_CITY
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    private OnDialogClickInterface mDialogClickInterface;
    private Type type;
    private Map<String, String> mMap;

    public AreaSelectionDialog(Activity activity, Type type,OnDialogClickInterface mDialogClickInterface) {
        mActivity = activity;
        this.type = type;
        this.mDialogClickInterface=mDialogClickInterface;
        initProvinceDatas();
        mMap = new HashMap<>();
    }

    private void initProvinceDatas() {

        AssetManager asset = mActivity.getAssets();
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

    public void showDialog() {
        setSheng();
    }

    /**
     * 选择省
     */
    private void setSheng() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择省");
        // 设置一个下拉的列表选择项
        builder.setItems(mProvinceDatas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(activity, "选择的城市为：" + mProvinceDatas[which],
//						Toast.LENGTH_SHORT).show();
                mMap.put("province", mProvinceDatas[which]);
                switch (type) {
                    case ALL:
                    case PROVINCE_CITY:
                        setShi(which);
                        break;
                    default:
                        mDialogClickInterface.OnClick(0, mMap);
                        break;
                }

            }
        });
        builder.show();
    }

    /**
     * 选择市
     */
    private void setShi(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择市");
        cityList = provinceList.get(index).getCityList();
        final String city[] = new String[cityList.size()];
        for (int i = 0; i < cityList.size(); i++) {
            city[i] = cityList.get(i).getName();
        }
        // 设置一个下拉的列表选择项
        builder.setItems(city, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(activity, "选择的城市为：" + mProvinceDatas[which],
//						Toast.LENGTH_SHORT).show();
                mMap.put("city", city[which]);
                switch (type) {
                    case ALL:
                        setQu(which);
                        break;
                    default:
                        mDialogClickInterface.OnClick(0, mMap);
                        break;
                }
            }
        });
        builder.show();
    }

    /**
     * 选择区
     */
    private void setQu(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择区");
        districtList = cityList.get(index).getDistrictList();
        final String qu[] = new String[districtList.size()];
        for (int i = 0; i < districtList.size(); i++) {
            qu[i] = districtList.get(i).getName();
        }
        // 设置一个下拉的列表选择项
        builder.setItems(qu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(activity, "选择的城市为：" + mProvinceDatas[which],
//						Toast.LENGTH_SHORT).show();
                mMap.put("area", qu[which]);
                mDialogClickInterface.OnClick(0, mMap);
            }
        });
        builder.show();
    }


}
