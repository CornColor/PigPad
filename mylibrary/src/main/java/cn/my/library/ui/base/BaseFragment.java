package cn.my.library.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import cn.my.library.eventbus.MessageEvent;
import cn.my.library.utils.util.ToastUtils;
import me.yokeyword.fragmentation.SupportFragment;



/**
 * created by dan
 */
public class BaseFragment extends SupportFragment implements BaseView{



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Subscribe
    public void onBusEvent(MessageEvent messageEvent) {

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showLong(msg);
    }

    @Override
    public void showProgressDialog(String msg) {

    }

    @Override
    public void cancelProgressDialog() {

    }

    @Override
    public void showDialog(String... msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
