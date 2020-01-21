package browser.pig.cn.pigpad;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaManager;
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
    public void onStateError() {
        super.onStateError();
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
        post(new Runnable() {
            @Override
            public void run() {
                if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                    if(jzDataSource.currentUrlIndex<jzDataSource.urlsMap.size()){
                        int index = jzDataSource.currentUrlIndex+1;
                        changeUrl(index,0);
                    }else {
                        changeUrl(0,0);
                    }

                }else {
                    changeUrl(jzDataSource.currentUrlIndex,0);
                }
            }
        });

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
                    try{
                        backPress();
                        ((MainActivity)mContext).onChangeX(jzDataSource.currentUrlIndex);
                    }catch (Exception e){

                    }
                    //quit fullscreen




                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }



}
