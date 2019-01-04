package com.update.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public abstract class BaseFragment<T extends BaseP> extends Fragment implements BaseV{
    /**
     * ButterKnife
     */
    Unbinder unbinder;
    protected BaseRecycleAdapter mAdapter;
    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    private boolean onFragmentFirstVisible;//该方法是否已经执行
    protected Activity mActivity;
    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    private View rootView;

    protected T presenter;
    protected long classID;

    public long getClassID() {
        return classID;
    }

    public void setClassID(long classID) {
        this.classID = classID;
    }


    protected Gson mGson;

    protected Map<String, Object> mMap;
    private String mClassName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        mActivity = getActivity();
        mClassName = getClass().getSimpleName();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (rootView == null) {
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
//                    RefreshManagement.map.put(mClassName, false);
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        mGson = new Gson();
        mMap = new ArrayMap<>();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
//        RefreshManagement.map.remove(mClassName);
    }

    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    //如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
//        if (!isFirstVisible && isVisible && RefreshManagement.map.get(mClassName) != null && RefreshManagement.map.get(mClassName)) {
//            refersh();
//            RefreshManagement.map.put(mClassName, false);
//        }

    }

    protected void refersh() {

    }


    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {

    }


    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    protected abstract int getLayout();

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 传递数据
     *
     * @param object
     */
    public void setData(Object object) {

    }

    /**
     * 显示显示吐司
     *
     * @param text 吐司显示文本
     */
    @Override
    public void showShortToast(String text) {
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * 网络请求成功返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {

    }

    /**
     * 网络请求失败
     *
     * @param requestCode 请求码
     */
    @Override
    public void httpfaile(int requestCode) {

    }


    @Override
    public void httpFinish(int requestCode) {

    }

    protected void Login_prompt() {
//        ButtonDialog buttonDialog = new ButtonDialog(getActivity(), new DialogInterface() {
//            @Override
//            public void OkListener() {
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivityForResult(intent,99);
//            }
//
//
//        });
//        buttonDialog.setMsgText("账号未登录，请先登录账号！");
//        buttonDialog.setOkButton("登录");
//        buttonDialog.show();
    }

    public void login() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (RefreshManagement.map.get(mClassName) != null && RefreshManagement.map.get(mClassName)) {
//            refersh();
//            RefreshManagement.map.put(mClassName, false);
//        }
        if (resultCode == RESULT_OK) {
            if (requestCode == 99) {
                //判断结果状态吗
                login();
            } else {
                onMyActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
    }

}