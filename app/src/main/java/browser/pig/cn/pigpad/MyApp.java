package browser.pig.cn.pigpad;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import cn.jzvd.Jzvd;

/**
 * created by dan
 */
public class MyApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //当sdk版本大于等于16更换播放引擎
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            Jzvd.setMediaInterface(new JZExoPlayer());
        }
    }
}
