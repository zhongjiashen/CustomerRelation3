package com.update.viewbar;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crcxj.activity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/22 0022 上午 10:19
 * Description: 自定义标题头
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/22 0022         申中佳               V1.0
 */
public class TitleBar extends RelativeLayout {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_right_two)
    ImageView ivRightTwo;


    private Activity mActivity;
    private TitleOnlicListener mTitleOnlicListener;

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewbar_title, this);
        ButterKnife.bind(view);

    }

    /**
     * 设置标题文本
     * @param activity
     * @param text
     */
    public void setTitleText(Activity activity, String text) {
        mActivity = activity;
        tvTitle.setText(text);
    }

    /**
     * 设置
     * @param resId
     */
    public void setIvRightTwoImageResource(int resId) {
        ivRightTwo.setVisibility(VISIBLE);
        ivRightTwo.setImageResource(resId);
    }

    /**
     * 设置标题头点击事件
     * @param titleOnlicListener
     */
    public void setTitleOnlicListener(TitleOnlicListener titleOnlicListener) {
        mTitleOnlicListener = titleOnlicListener;
    }

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.iv_right_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (mActivity != null) {
                    mActivity.finish();
                } else {

                }
                break;
            case R.id.iv_right:
                mTitleOnlicListener.onClick(0);
                break;
            case R.id.iv_right_two:
                mTitleOnlicListener.onClick(1);
                break;
        }
    }

    public interface TitleOnlicListener {
        /**
         * 自定义标题头点击事件
         *
         * @param i
         */
        void onClick(int i);
    }
}
