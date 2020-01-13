package cn.my.library.utils.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import android.text.TextUtils;

import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.Setting;

import java.util.List;

/**
 * created by dan
 */
public class PermissionUtil {
    public void requestPermission(final Activity activity,final String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Build.VERSION_CODES.M
            AndPermission.with(activity)
                    .runtime()
                    .permission(permissions)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {

                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            AndPermission.with(activity)
                                    .runtime()
                                    .setting()
                                    .onComeback(new Setting.Action() {
                                        @Override
                                        public void onAction() {

                                        }
                                    })
                                    .start();

                        }
                        }
                    });
        }

    }
}
