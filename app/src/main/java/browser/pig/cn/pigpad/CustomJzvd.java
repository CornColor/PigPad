package browser.pig.cn.pigpad;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * created by dan
 */
public class CustomJzvd extends JzvdStd {
    Context mContext;

    public CustomJzvd(Context context) {
        super(context);
        mContext = context;
    }

    public CustomJzvd(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        //传入自定义布局
        return R.layout.my_play;
    }
    @Override
    public void setProgressAndText(int progress, long position, long duration) {
        super.setProgressAndText(progress, position, duration);
        change(progress);

    }
    public synchronized void change(int p){
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            if(p >=99){
               if(jzDataSource.currentUrlIndex<jzDataSource.urlsMap.size()){
                   changeUrl(jzDataSource.currentUrlIndex+1,1000);
               }else {
                   changeUrl(0,1000);
               }


            }

        }
    }
    @Override
    public void init(Context context) {
        super.init(context);
        //拿到自己添加的控件 设置listener
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //设置控件单击事件
    }
    @Override
    public void setUp(JZDataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        //这两行设置播放时屏幕状态
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        //播放下一集 在这里切换url
    }



    @Override
    public void playOnThisJzvd() {
        super.playOnThisJzvd();
        //退出全屏
        //Toast.makeText(mContext, "退出全屏", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                    //quit fullscreen

                    backPress();


                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
