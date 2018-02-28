package com.update.base;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;


import com.cr.tools.ServerRequest;
import com.update.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/9/18 0018 下午 5:39
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/9/18 0018         申中佳               V1.0
 */
public class BaseP {
    protected BaseV view;
    protected Activity mActivity;
    static boolean run = false;


    public BaseP(BaseV view, Activity activity) {
        this.view = view;
        mActivity = activity;

    }

    /**
     * @param requestCode 请求码
     * @param url         请求路径
     * @param params         请求参数map
     */
    public void post(final int requestCode, final String url, final Map<String, Object> params) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
               String returnJson = ServerRequest.webServicePost(url,
                        params, mActivity);
                subscriber.onNext(returnJson);
                subscriber.onCompleted();
            }


        })
                .subscribeOn(Schedulers.computation()) // 指定 subscribe() 发生在 运算 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>() {

                    @Override
                    public void onNext(String s) {//主线程执行的方法
                        LogUtils.e(s);


                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }


    protected void returnData(int requestCode, Object object) {
        view.returnData(requestCode, object);
    }


}
