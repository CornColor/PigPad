package cn.my.library.ui.base;

import android.annotation.SuppressLint;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.lzy.imagepicker.view.SystemBarTintManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import butterknife.ButterKnife;
import cn.my.library.R;
import cn.my.library.eventbus.MessageEvent;
import cn.my.library.other.AppManager;
import cn.my.library.utils.util.CommonUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportActivity;

import static android.view.View.GONE;

public abstract class BaseActivity extends SupportActivity implements BaseView {
    //当被观察者被订阅会出现Disposable，用于存储Disposable当页面销毁的时候取消订阅
    private CompositeDisposable mCompositeDisposable;
    private List<BasePresenter> basePresenters;
    private SweetAlertDialog dialog;
    private View rl_top;
    private View back;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSystemBarTint();
        // 添加Activity管理
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            AppManager.getInstance().addActivity(this);
        }
        //添加注解框架
   //       ButterKnife.bind(this);
        //注册事件接收
        EventBus.getDefault().register(this);
        mCompositeDisposable = new CompositeDisposable();


    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        try {
            rl_top = findViewById(R.id.rl_top);
            if(rl_top!= null){
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    rl_top.setVisibility(GONE);
                } else {
                    rl_top.getLayoutParams().height = CommonUtil.getStatusBarHeight(this);
                }
            }
        }catch (Exception e){

        }
        try {
            back = findViewById(R.id.back);
            if(back!= null){
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeKeyboard();
                        finish();
                    }
                });
            }

        }catch (Exception e){

        }

        initView();
        initData();
        initPresenter();
    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化Presenter
     */
    public abstract void initPresenter();

    /**
     * 添加Presenters
     * @param basePresenter
     */
    public void addPresenter(BasePresenter basePresenter){
        if(basePresenters == null){
            basePresenters = new ArrayList<>();
        }
        basePresenters.add(basePresenter);
    }

    /**
     * 关闭键盘
     */
    public void closeKeyboard(){
        InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    @Override
    public void showToast(String msg) {
        if(!isDeath()){
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelProgressDialog() {
        if(isDeath()){
            return;
        }
        if(dialog!= null){
            dialog.cancel();
        }
    }

    public void showConfirmDialog(String msg, SweetAlertDialog.OnSweetClickListener listener){
        SweetAlertDialog dialog = new SweetAlertDialog(this);
        dialog.setContentText(msg);
        dialog.setTitleText("");
        dialog.hidTtitle();
        dialog.setConfirmClickListener(listener);
        dialog.show();

    }

    @Override
    public void showProgressDialog(String msg) {
        if(isDeath()){
            return;
        }
        dialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)

                .setTitleText(msg);
        dialog.show();
//        if (msg != null || !"".equals(msg)) {
//            ProgressDialog dialog;
//            dialog = ProgressDialog.show(this,"",msg);
//            dialog.show();
//        }
    }

    /**
     * 判断页面是否死亡
     * @return
     */
    public boolean isDeath(){
        if (this == null && this.isFinishing()) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (this.isDestroyed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showDialog(String ...msg) {
        if(isDeath()){
            return;
        }
        String[]data = msg;
        int lenght = data.length;
        if(data!= null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            switch (lenght){
                case 1: {
                    String message = data[0];
                    builder.setMessage(message);
                }
                break;
                case 2: {
                    String title = data[0];
                    String message = data[1];
                    builder.setTitle(title);
                    builder.setMessage(message);
                }
                break;
            }
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        }

    }

    @Subscribe
    public void onBusEvent(MessageEvent messageEvent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 解除Presenter绑定
         */
        if(basePresenters!= null){
            for (BasePresenter b :
                    basePresenters) {
                b.removeAttach();
            }
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            AppManager.getInstance().finishActivity(this);
        }
        //解除绑定
         //    ButterKnife.unbind(this);
        //取消所有订阅
        clearDisposable();
        /**
         * 解除EventBus绑定
         */
        EventBus.getDefault().unregister(this);

    }
    /** 设置状态栏颜色 */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (true) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(Color.TRANSPARENT);

        }
    }

    //申请权限
    public void  requestPermission(String []...permission){
        AndPermission.with(this)
                .runtime()
                .permission(permission)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }
}
