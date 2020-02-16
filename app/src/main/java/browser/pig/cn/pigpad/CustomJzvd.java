package browser.pig.cn.pigpad;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import browser.pig.cn.pigpad.bean.Products;
import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * created by dan
 */
public class CustomJzvd extends JzvdStd {
    Context mContext;
    private List<Products.GoodsBean> list;



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
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        Log.e("完结","onStateAutoComplete");
    }

    @Override
    public void onCompletion() {
        super.onCompletion();
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        //播放下一集 在这里切换url
//                if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
//                    if(count <= 0){
//                        if(jzDataSource.currentUrlIndex<jzDataSource.urlsMap.size()-1){
//                            int index = jzDataSource.currentUrlIndex+1;
//                            changeUrl(index,0);
//                        }else {
//                            changeUrl(0,0);
//                        }
//                    }else {
//                        count--;
//                        int index = jzDataSource.currentUrlIndex;
//                        changeUrl(index,0);
//                    }
//
//
//                }else {
//                        int index = jzDataSource.currentUrlIndex;
//                        changeUrl(index,0);
//                }
        list =  ((MainActivity)mContext).getGoods();
       int count = ((MainActivity)mContext).getXunhuan();
        ((MainActivity)mContext).setXunhuan(count-1);
        count = ((MainActivity)mContext).getXunhuan();
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            if(list!= null){
                int index = jzDataSource.currentUrlIndex;
                   if(count <= 0){

                           index = jzDataSource.currentUrlIndex;
                           if(index == list.size()-1){
                               index = -1;
                           }

                       for (int i = index+1;i<list.size();i++){
                           Products.GoodsBean goodsBean = list.get(i);
                           if(!"0".equals(goodsBean.getIffullscreen())){
                               try{
                                   ((MainActivity)mContext).setXunhuan(Integer.valueOf(goodsBean.getCycleindex()));
                               }catch (Exception e){
                                   ((MainActivity)mContext).setXunhuan(1);
                               }
                               changeUrl(i,0);

                               break;

                           }
                           if(i == list.size()-1){
                               i =-1;
                           }
                       }
                    }else {
                       changeUrl(index,0);
                    }

            }

        }else {
                       int index = jzDataSource.currentUrlIndex;
                        changeUrl(index,0);
        }

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
