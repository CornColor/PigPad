package cn.my.library.other;

import android.os.Handler;
import android.os.Message;



import java.lang.ref.WeakReference;

/**
 * created by dan
 * 弱引用Handle 用于解决内存泄露的问题
 */
public class WeakReferenceHandle extends Handler {
    WeakReference<Callback> callbackWeakReference;
    public WeakReferenceHandle(Callback callback){
        callbackWeakReference = new WeakReference<>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        if(callbackWeakReference!= null){
            Callback callback = callbackWeakReference.get();
            if(callback!= null){
                callback.handleMessage(msg);
            }
        }
    }
}
