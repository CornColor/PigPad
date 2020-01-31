package browser.pig.cn.pigpad;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.liulishuo.filedownloader.FileDownloader;

import browser.pig.cn.pigpad.CustomMediaPlayer.CustomMediaPlayerAssertFolder;
import browser.pig.cn.pigpad.CustomMediaPlayer.JZExoPlayer;
import browser.pig.cn.pigpad.CustomMediaPlayer.JZMediaIjkplayer;
import browser.pig.cn.pigpad.db.DbHelper;
import cn.jzvd.JZMediaSystem;
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
        MultiDex.install(this);
        DbHelper.getInstance().init(this);
        FileDownloader.setup(this);
        FileDownloader.enableAvoidDropFrame();
        //当sdk版本大于等于16更换播放引擎.
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
//            Jzvd.setMediaInterface(new JZExoPlayer());
//        }
 //       Jzvd.setMediaInterface(new CustomMediaPlayerAssertFolder());
        Jzvd.setMediaInterface(new JZMediaIjkplayer());

    }
}
