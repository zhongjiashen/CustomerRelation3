package com.update.actiity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.SurfaceView;

import com.crcxj.activity.R;
import com.google.zxing.Result;
import com.google.zxing.client.android.AutoScannerView;
import com.google.zxing.client.android.BaseCaptureActivity;
import com.update.utils.LogUtils;


/**
 * 模仿微信的扫描界面
 */
public class WeChatCaptureActivity extends BaseCaptureActivity {

    private static final String TAG = WeChatCaptureActivity.class.getSimpleName();

    private SurfaceView surfaceView;
    private AutoScannerView autoScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_capture);
        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        autoScannerView = (AutoScannerView) findViewById(R.id.autoscanner_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoScannerView.setCameraManager(cameraManager);
    }



    @Override
    public SurfaceView getSurfaceView() {
        return (surfaceView == null) ? (SurfaceView) findViewById(R.id.preview_view) : surfaceView;
    }

    @Override
    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        LogUtils.i( "dealDecode ~~~~~ " + rawResult.getText() + " " + barcode + " " + scaleFactor);
        playBeepSoundAndVibrate(true, false);
        setResult(RESULT_OK,new Intent().putExtra("qr",rawResult.getText()));
        finish();
//        对此次扫描结果不满意可以调用
//        reScan();
    }
}
