package com.cr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.tools.PicUtil;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import org.kobjects.base64.Base64;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到-新增签到
 *
 * @author Administrator
 */
public class QdXzqdActivity extends BaseActivity implements OnClickListener, TakePhoto.TakeResultListener, InvokeListener {
    private ImageButton qdImageButton;
    private String lng = "39.963175", lat = "116.400244"; // 经度，纬度
    private EditText ddEditText;// 地点
    private EditText khEditText;// 客户
    private EditText pzEditText;// 拍照
    private ImageView showPzImageView;// 现在拍照的照片
    private EditText descEditText;// 说点什么吧
    private ImageView scImageView;//删除
    private ImageView sckhImageView;//删除客户

    private LinearLayout zpLayout;//照片

    private String khId = "";// 客户id
    private String pzUrl = "";// 拍照的URl地址

    private String address = "";// 定位的地点
    private String qdType = "1";//签到的类型 1：外出办公；2：上班签到；3下班签到
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private ImageButton wcbgButton, sbqdButton, xbqdButton;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_qd_xzqd);
        addFHMethod();
        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        InitLocation();
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        initActivity();
        mLocationClient.start();// 开启定位功能
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        qdImageButton = (ImageButton) findViewById(R.id.qd);
        qdImageButton.setOnClickListener(this);
        ddEditText = (EditText) findViewById(R.id.dd);
        khEditText = (EditText) findViewById(R.id.kh);
        khEditText.setOnClickListener(this);
        pzEditText = (EditText) findViewById(R.id.pz);
        pzEditText.setOnClickListener(this);
        descEditText = (EditText) findViewById(R.id.desc);
        showPzImageView = (ImageView) findViewById(R.id.show_zp);
        showPzImageView.setOnClickListener(this);
        wcbgButton = (ImageButton) findViewById(R.id.wcbg);
        wcbgButton.setOnClickListener(this);
        sbqdButton = (ImageButton) findViewById(R.id.sbqd);
        sbqdButton.setOnClickListener(this);
        xbqdButton = (ImageButton) findViewById(R.id.xbqd);
        xbqdButton.setOnClickListener(this);
        scImageView = (ImageView) findViewById(R.id.sc);
        scImageView.setOnClickListener(this);
        sckhImageView = (ImageView) findViewById(R.id.sckh);
        sckhImageView.setOnClickListener(this);
        zpLayout = (LinearLayout) findViewById(R.id.zp_layout);
        zpLayout.setVisibility(View.GONE);
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation) {

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }

            Log.i("BaiduLocationApiDem", sb.toString());

            lng = location.getLongitude() + "";
            lat = location.getLatitude() + "";
            address = location.getAddrStr();
//            saveQdMsg();//保存签到信息
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //此时已在主线程中，可以更新UI了
                    ddEditText.setText(address);
                    mLocationClient.stop();
                }
            });

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 连接网络的操作（保存）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("jd", lng);
        parmMap.put("wd", lat);
        parmMap.put("types", qdType);
        parmMap.put("address", ddEditText.getText().toString());
        parmMap.put("memo", descEditText.getText().toString());
        parmMap.put("clientid", khId);
        String[] ss = pzUrl.split("\\.");
        parmMap.put("ext", ss.length > 1 ? ss[1] : "");
        parmMap.put("fs", getImageByte());

        findServiceData2(0, ServerURL.REGISTERWRITE, parmMap, true);
    }

    //	private String getImageByte(){
//		 File file = new File(pzUrl);
//         if (file.exists()) {
//             FileInputStream fis = null;
//             try {
//                 fis = new FileInputStream(file);
//                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                 byte[] buffer = new byte[1024];
//                 int count = 0;
//                 while ((count = fis.read(buffer)) >= 0) {
//                     baos.write(buffer, 0, count);
//                 }
////                 return baos.toByteArray().toString();
//                 //进行Base64编码 
//                 String uploadBuffer = new String(Base64.encode(baos.toByteArray()));
////                 rpc.addProperty("file", uploadBuffer);
////                 rpc.addProperty("fileName", ele);
//                 return uploadBuffer;
//             } catch (Exception e) {
//                 e.printStackTrace();
//             } finally {
//                 try {
//                     fis.close();
//                 } catch (IOException e) {
//                	 e.printStackTrace();
//                 }
//             }
//         }
//         return "";
//	}
    private String getImageByte() {
        if (pzUrl.equals("")) {
            return "";
        }
        Bitmap bitmap = PicUtil.getLoacalBitmap(pzUrl);
        Bitmap bitmap2 = PicUtil.comp(bitmap);
        //进行Base64编码
        String uploadBuffer = new String(Base64.encode(PicUtil.compressImage(bitmap2)));
        return uploadBuffer;
    }

    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson.equals("")) {
                showToastPromopt("签到成功！");
                mLocationClient.stop();// 关闭定位功能
                finish();
            } else {
                showToastPromopt("签到失败！" + returnJson.substring(5));
            }
        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.sc://删除
                pzUrl = "";
                zpLayout.setVisibility(View.GONE);
                break;
            case R.id.sckh://删除
                khEditText.setText("");
                khId = "";
                break;
            case R.id.qd:// 签到
                searchDate2();
                break;
            case R.id.kh:
                intent.setClass(activity, CommonXzkhActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.pz:
                // 拍照
//                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                String name = String.valueOf(System.currentTimeMillis()).concat(".jpg");
//                String zpPath = com.cr.service.FileUtil.getRFileSaveDir(DirType.ZFWS, name);
//                File imageFile = new File(zpPath);//通过路径创建保存文件
//                Uri imageFileUri = Uri.fromFile(imageFile);//获取文件的Uri
//                i.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
//                pzUrl = zpPath;
//                startActivityForResult(i, 1);
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                takePhoto.onPickFromCapture(imageUri);
                break;
            case R.id.show_zp:
                Intent intent2 = new Intent();
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("picPath", "file:///" + pzUrl);
                list.add(map);
                intent2.putExtra("object", (Serializable) list);
                intent2.setClass(activity, ShowImgActivity.class);
                startActivity(intent2);
                break;
            case R.id.wcbg:
                qdType = "1";
                wcbgButton.setBackgroundResource(R.drawable.wcqd);
                sbqdButton.setBackgroundResource(R.drawable.sbqd2);
                xbqdButton.setBackgroundResource(R.drawable.xbqd2);
                break;
            case R.id.sbqd:
                qdType = "2";
                wcbgButton.setBackgroundResource(R.drawable.wcqd2);
                sbqdButton.setBackgroundResource(R.drawable.sbqd);
                xbqdButton.setBackgroundResource(R.drawable.xbqd2);
                break;
            case R.id.xbqd:
                qdType = "3";
                wcbgButton.setBackgroundResource(R.drawable.wcqd2);
                sbqdButton.setBackgroundResource(R.drawable.sbqd2);
                xbqdButton.setBackgroundResource(R.drawable.xbqd);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                khEditText.setText(data.getExtras().getString("name"));
                khId = data.getExtras().getString("id");
            } else if (requestCode == 1) {
//				pzUrl = PicUtil.savePz(data);
//				pzUrl=data.getExtras().getString("picPath");
//				Log.v("dddd", pzUrl+"@@@@@");
                showPzImageView.setImageBitmap(PicUtil.getLoacalBitmap(pzUrl));
                zpLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    /*图片选择和拍照*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(QdXzqdActivity.this).bind(new TakePhotoImpl(QdXzqdActivity.this, QdXzqdActivity.this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        pzUrl = result.getImage().getOriginalPath();
        showPzImageView.setImageBitmap(PicUtil.comp(PicUtil.getLoacalBitmap(pzUrl)));
        zpLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {
        Toast.makeText(this, "取消当前操作！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

}
