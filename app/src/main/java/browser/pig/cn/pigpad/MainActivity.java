package browser.pig.cn.pigpad;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import browser.pig.cn.pigpad.bean.GoodsBean;
import browser.pig.cn.pigpad.bean.GoodsListBean;
import browser.pig.cn.pigpad.bean.GoodsPath;
import browser.pig.cn.pigpad.bean.Products;
import browser.pig.cn.pigpad.bean.StepABean;
import browser.pig.cn.pigpad.bean.StepBean;
import browser.pig.cn.pigpad.bean.VersionBean;
import browser.pig.cn.pigpad.bean.XGoodsListBean;
import browser.pig.cn.pigpad.db.DbHelper;
import browser.pig.cn.pigpad.db.StepABeanDao;
import browser.pig.cn.pigpad.net.CommonCallback;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserAction;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import cn.my.library.net.BaseBean;
import cn.my.library.ui.base.BaseActivity;
import cn.my.library.utils.util.AppUtils;
import cn.my.library.utils.util.DeviceUtils;
import cn.my.library.utils.util.FilePathUtil;
import cn.my.library.utils.util.FileUtils;
import cn.my.library.utils.util.NetworkUtils;
import cn.my.library.utils.util.StringUtils;
import me.relex.circleindicator.CircleIndicator;

import static browser.pig.cn.pigpad.net.ApiSearvice.CREATE_D;
import static browser.pig.cn.pigpad.net.ApiSearvice.GOODS_LIST;
import static browser.pig.cn.pigpad.net.ApiSearvice.GOODS_STEP;
import static browser.pig.cn.pigpad.net.ApiSearvice.UPDATA;
import static browser.pig.cn.pigpad.net.ApiSearvice.VERSION;
import static browser.pig.cn.pigpad.net.ApiSearvice.XINTIAO;
import static cn.jzvd.Jzvd.CURRENT_STATE_PAUSE;
import static cn.jzvd.Jzvd.CURRENT_STATE_PLAYING;
import static cn.jzvd.Jzvd.SCREEN_WINDOW_FULLSCREEN;

public class MainActivity extends BaseActivity implements GoodsAdapter.OnGoodsClickListener,View.OnClickListener {

    //   RecyclerView rvBz;
    private RecyclerView rv_data;
    private GoodsAdapter adapter;
    private List<Products.GoodsBean> list;
    private ConstraintLayout title;


    //   private List<StepBean> list1;
//    private StepAdapter stepAdapter;

    StepPageAdapter stepPageAdapter;
    CustomJzvd video;

    private Products.GoodsBean mCurrGoods;
    private ViewPager vp;
    private CircleIndicator indicator;
    final Handler handler = new Handler();

    private Timer timer;
    private TimerTask task;

    /**
     * 心跳间隔时间
     */
    private float update_Interval = 1.0f;


    private int fenzhong = 5;
    private boolean isUpDate = false;
    private int type = 0;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_video:
                type = 0;
                rV.setVisibility(View.VISIBLE);
                tvVideo.setBackgroundResource(R.drawable.shape_01);
                tvVideo.setTextColor(Color.WHITE);

                rV01.setVisibility(View.INVISIBLE);
                tvDownload.setBackgroundColor(Color.parseColor("#EEEEEE"));
                tvDownload.setTextColor(Color.parseColor("#61000000"));
                AudioPlay.getInstance().stop();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (video.currentState == CURRENT_STATE_PAUSE) {
                            video.onEvent(JZUserAction.ON_CLICK_RESUME);
                            JZMediaManager.start();
                            video.onStatePlaying();
                        }

                    }
                },1000);

                break;
            case R.id.tv_download:
                type = 1;
                rV.setVisibility(View.INVISIBLE);
                tvVideo.setBackgroundColor(Color.parseColor("#EEEEEE"));
                tvVideo.setTextColor(Color.parseColor("#61000000"));
                rV01.setVisibility(View.VISIBLE);
                tvDownload.setBackgroundResource(R.drawable.shape_01);
                tvDownload.setTextColor(Color.WHITE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (video.currentState == CURRENT_STATE_PLAYING) {
                            video.onEvent(JZUserAction.ON_CLICK_PAUSE);
                            JZMediaManager.pause();
                            video.onStatePause();
                        }

                    }
                },1000);

                break;
            case R.id.title:
                continuousClick(COUNTS, DURATION);
                break;
        }
    }


    class MyTask extends TimerTask {
        @Override
        public void run() {
            // 初始化计时器
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (video.currentState == CURRENT_STATE_PAUSE) {


                        if(mCurrGoods!= null&&"1".equals(mCurrGoods.getIffullscreen())){
                            //播放整体列表
                            rV.setVisibility(View.VISIBLE);
                            video.onEvent(JZUserAction.ON_CLICK_RESUME);
                            JZMediaManager.start();
                            video.onStatePlaying();

                            tVideoName.setVisibility(View.INVISIBLE);
                        }
                            new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if(mCurrGoods!= null&&"1".equals(mCurrGoods.getIffullscreen())){
                                    if (video.currentState == CURRENT_STATE_PLAYING&&!isUpDate) {
                                        video.startWindowFullscreen();
                                        video.onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
                                    }
                                }

                            }
                        },1000);
                    }else {
                        //播放整体列表
                        if(mCurrGoods!= null&&"1".equals(mCurrGoods.getIffullscreen())){
                            if (video.currentState == CURRENT_STATE_PLAYING&&!isUpDate) {
                                video.startWindowFullscreen();
                                video.onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
                            }
                        }
                    }



                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

    }

    private void initTimer() {
        // 初始化计时器
        task = new MyTask();
        timer = new Timer();
    }

    private void startTimer() {
        //启动计时器
        /**
         * java.util.Timer.schedule(TimerTask task, long delay, long period)：
         * 这个方法是说，delay/1000秒后执行task,然后进过period/1000秒再次执行task，
         * 这个用于循环任务，执行无数次，当然，你可以用timer.cancel();取消计时器的执行。
         */
        initTimer();
        try {
            timer.schedule(task, fenzhong*60 * 1000);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            initTimer();
            timer.schedule(task, fenzhong*60 * 1000);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
        AudioPlay.getInstance().stop();
        if(video!= null){
            video.release();
        }


    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }

    }


    @Override
    public void initData() {

        list = new ArrayList<>();

        adapter = new GoodsAdapter(this, list);
        adapter.setOnGoodsClickListener(this);
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        rv_data.setAdapter(adapter);

        create_d();
    }
    TextView tvVideo;
    TextView tvDownload;
    TextView tVideoName;
    RelativeLayout rV;
    RelativeLayout rV01;
    TextView tvCode;
    @Override
    public void initView() {
        rv_data = findViewById(R.id.rv_data);
        video = findViewById(R.id.video);


        indicator = findViewById(R.id.ci);
        vp = findViewById(R.id.vp);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                AudioPlay.getInstance().stop();
                EventBus.getDefault().post(new MessageBus(1));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        tvVideo = findViewById(R.id.tv_video);
        tvDownload = findViewById(R.id.tv_download);


        tVideoName = findViewById(R.id.t_video_name);
        rV = findViewById(R.id.r_v);
        rV01 = findViewById(R.id.r_v_01);
        tvCode = findViewById(R.id.tv_code);
        tvCode.setText("设备码:"+DeviceUtils.getAndroidID());
        tvVideo.setOnClickListener(this);
        tvDownload.setOnClickListener(this);

        title = findViewById(R.id.title);
        title.setOnClickListener(this);



    }

    public void onChangeX(int i) {
        if (i >= 0 && i < list.size()) {
            adapter.updata(i);
        }
        if(type == 1){
            rV.setVisibility(View.INVISIBLE);
            tVideoName.setVisibility(View.VISIBLE);
            tvVideo.setBackgroundColor(Color.parseColor("#EEEEEE"));
            tvVideo.setTextColor(Color.parseColor("#61000000"));
            rV01.setVisibility(View.VISIBLE);
            tvDownload.setBackgroundResource(R.drawable.shape_01);
            tvDownload.setTextColor(Color.WHITE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (video.currentState == CURRENT_STATE_PLAYING) {
                        video.onEvent(JZUserAction.ON_CLICK_PAUSE);
                        JZMediaManager.pause();
                        video.onStatePause();
                    }

                }
            },1000);
        }
    }
    public void onChangeZ() {
         startTimer();
    }


    @Override
    public void initPresenter() {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
// TODO Auto-generated method stub
// 在此处添加执行的代码
                loadXinTiao1();
                banben();
                handler.postDelayed(this, (long) (update_Interval * 60 * 1000));// 50是延时时长
            }
        };
        handler.postDelayed(runnable, (long) (update_Interval * 60 * 1000));// 打开定时器，执行操作

        // video.thumbImageView.setImageResource(R.drawable.applog);
        banben();


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopTimer();
                break;
            case MotionEvent.ACTION_UP:
                startTimer();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            Jzvd.clearSavedProgress(this, null);
            Jzvd.goOnPlayOnPause();
        } catch (Exception e) {

        }

    }


    @Override
    public void onBackPressed() {
      //  if (Jzvd.backPress()) {
        //    return;
        //}
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
        startTimer();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        try{
            handler.removeCallbacks((Runnable) this);// 关闭定时器处理
            if (AudioPlay.getInstance().isPlay()) {
                AudioPlay.getInstance().stop();
            }
            stopTimer();
            if(video!= null){
                video.release();
            }
        }catch (Exception e){

        }

    }




    private void banben() {
        if(!NetworkUtils.isConnected()){
            return;
        }
        OkGo.<VersionBean>post(VERSION)
                .execute(new CommonCallback<VersionBean>(VersionBean.class) {

                    @Override
                    public void onFailure(String code, String s) {
                        showToast(s);

                    }

                    @Override
                    public void onSuccess(VersionBean banBenBean) {
                        try {
                            String version = banBenBean.getData().getVersion();
                            String appVersionName = AppUtils.getAppVersionName();
                            if (!appVersionName.equals(version)) {
                                fileDownLoad(banBenBean.getData().getAddress());
                            }
                        } catch (Exception e) {

                        }


                    }
                });


    }

    private void create_d() {
        if(!NetworkUtils.isConnected()){
            loadXinTiao();
            return;
        }
        OkGo.<BaseBean>post(CREATE_D)
                .params("device_id",DeviceUtils.getAndroidID())
                .execute(new CommonCallback<BaseBean>(BaseBean.class) {

                    @Override
                    public void onFailure(String code, String s) {

                        if (code.equals("4003")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadXinTiao();
                                }
                            },1000);

                        } else {
                            showToast(s);
                            create_d();
                        }


                    }

                    @Override
                    public void onSuccess(BaseBean banBenBean) {
                        loadXinTiao();


                    }

                    @Override
                    public void onError(Response<BaseBean> response) {
                        super.onError(response);
                        create_d();
                    }
                });


    }

    private void updata(String product_id) {
        if(!NetworkUtils.isConnected()){
            return;
        }
        OkGo.<BaseBean>post(UPDATA)
                .params("device_id", DeviceUtils.getAndroidID())
                .params("product_id", product_id)
                .execute(new CommonCallback<BaseBean>(BaseBean.class) {

                    @Override
                    public void onFailure(String code, String s) {
                        showToast(s);
                    }

                    @Override
                    public void onSuccess(BaseBean banBenBean) {


                    }

                    @Override
                    public void onError(Response<BaseBean> response) {
                        super.onError(response);

                    }
                });


    }

    private void fileDownLoad(String path) {
        if(!NetworkUtils.isConnected()){
            return;
        }
        if (StringUtils.isEmpty(path)) {
            return;
        }
        FileDownloader.getImpl().create(path)
                .setPath(FilePathUtil.getFilePath(this, "apk") + File.separator + "pigadp" + System.currentTimeMillis() + ".apk")
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        try {
                            if (!StringUtils.isEmpty(task.getPath())) {
                                AppUtils.installApp(task.getPath());
                            }
                        } catch (Exception e) {

                        }


                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();

    }


    private void loadXinTiao1() {
        if(!NetworkUtils.isConnected()){
            return;
        }

        if(isDownload){
            return;
        }
        OkGo.<Products>post(XINTIAO)
                .params("device_id", DeviceUtils.getAndroidID())
                .execute(new CommonCallback<Products>(Products.class) {
                    @Override
                    public void onStart(Request<Products, ? extends Request> request) {
                        super.onStart(request);
                        isUpDate = true;
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        isUpDate = false;
                    }

                    @Override
                    public void onFailure(String code, String s) {
                        showToast(s);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (video.currentState == CURRENT_STATE_PAUSE) {
//                                    video.onEvent(JZUserAction.ON_CLICK_RESUME);
//                                    JZMediaManager.start();
//                                    video.onStatePlaying();
//                                }
//
//                            }
//                        },1000);

                    }

                    @Override
                    public void onError(Response<Products> response) {
                        super.onError(response);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (video.currentState == CURRENT_STATE_PAUSE) {
//                                    video.onEvent(JZUserAction.ON_CLICK_RESUME);
//                                    JZMediaManager.start();
//                                    video.onStatePlaying();
//                                }
//
//                            }
//                        },1000);
                    }

                    @Override
                    public void onSuccess(Products products) {


                        if(products!= null)
                            if(products.getData().getUpdatecode() == 0){
                                 cancelProgressDialog();
                                 return;
                            }

                            if(list!= null&&products.getData().getProducts()!= null){
                                for (int i = 0; i < list.size(); i++) {

                                    boolean isC =false;
                                    for (int j =0;j<products.getData().getProducts().size();j++){

                                        if(list.get(i).getProduct_id().equals(products.getData().getProducts().get(j).getProduct_id())){
                                            isC = true;
                                        }

                                    }
                                    if(!isC){
                                        if(FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                + File.separator + FileUtils.getFileNameByUrl(list.get(i).getProduct_video())  +
                                                FileUtils.getFileNameWithSuffix(list.get(i).getProduct_video())))){
                                            FileUtils.delete(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id()));
                                        }
                                    }


                                }

                            }
                        DbHelper.getInstance().goodsBeanLongDBManager().deleteAll();
                        DbHelper.getInstance().stepABeanLongDBManager().deleteAll();
                        list.clear();
                        List<Products.GoodsBean> goodsBeans = products.getData().getProducts();

                        List<GoodsBean> goodsList = new ArrayList<>();

                        if(goodsBeans!= null&&goodsBeans.size()>0){

                            for (int i =0;i<goodsBeans.size();i++){
                                Products.GoodsBean pro = goodsBeans.get(i);
                                GoodsBean goodsBean = new GoodsBean(pro.getProduct_id(),
                                        pro.getProduct_name(),
                                        pro.getProduct_video(),
                                        pro.getRemarks(),
                                        pro.getProduct_icon(),
                                        pro.getIffullscreen(),
                                        pro.getFullscreen_Interval(),
                                        pro.getCycleindex(),
                                        pro.getProduct_updatecode()
                                );
                                goodsList.add(goodsBean);

                                DbHelper.getInstance().goodsBeanLongDBManager().insert(goodsBean);
                                List<Products.StepBean> stepBeans = pro.getSteps();
                                if(stepBeans!= null){
                                    for (int j =0;j<stepBeans.size();j++){
                                        Products.StepBean sp = stepBeans.get(j);
                                        StepABean stepABean = new StepABean(sp.getStep_id(),
                                                pro.getProduct_id(),sp.getStep_num(),sp.getStep_text(),
                                                sp.getStep_img(),
                                                sp.getStep_voice(),
                                                sp.getProduct_name(),sp.getProduct_video(),
                                                sp.getRemarks());
                                        DbHelper.getInstance().stepABeanLongDBManager().insert(stepABean);
                                    }

                                }



                            }




                            mCurrGoods = goodsBeans.get(0);
                            if(mCurrGoods!= null&&StringUtils.isEmpty(mCurrGoods.getFullscreen_Interval())){
                                try{
                                    fenzhong = Integer.valueOf(mCurrGoods.getFullscreen_Interval());
                                }catch (Exception e){
                                    fenzhong = 5;
                                }

                            }
                            mCurrGoods.setSelect(true);
                            adapter.setLine(0);
                            list.addAll(goodsBeans);


                            tVideoName.setText("“" + mCurrGoods.getProduct_name() + "”" + "介绍视频");
                            int downCount = 0;

                            for (int i = 0; i < list.size(); i++) {
                                if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                        + File.separator + FileUtils.getFileNameByUrl(list.get(i).getProduct_video())  +
                                        FileUtils.getFileNameWithSuffix(list.get(i).getProduct_video())))){
                                    downCount++;
                                }

                                List<Products.StepBean> stepBeans = list.get(i).getSteps();
                                if(stepBeans!= null){
                                    for (int j =0;j<stepBeans.size();j++){
                                        Products.StepBean stepBean = stepBeans.get(j);
                                        stepBean.setProduct_id(list.get(i).getProduct_id());
                                        loadImg(stepBean.getStep_img());

                                        if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                + File.separator + FileUtils.getFileNameByUrl(stepBean.getStep_voice())  +
                                                FileUtils.getFileNameWithSuffix(stepBean.getStep_voice())))){
                                            downCount++;
                                        }
                                    }
                                }

                            }
                            if(downCount>0){
                                rV.setVisibility(View.VISIBLE);
                                tvVideo.setBackgroundResource(R.drawable.shape_01);
                                tvVideo.setTextColor(Color.WHITE);

                                rV01.setVisibility(View.INVISIBLE);
                                tvDownload.setBackgroundColor(Color.parseColor("#EEEEEE"));
                                tvDownload.setTextColor(Color.parseColor("#61000000"));
                                AudioPlay.getInstance().stop();


                                if (video.currentState == CURRENT_STATE_PLAYING) {
                                    video.onEvent(JZUserAction.ON_CLICK_PAUSE);
                                    JZMediaManager.pause();
                                    video.onStatePause();

                                }
                                Jzvd.backPress();


                                List<GoodsPath> goodsPaths = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {

                                    if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                            + File.separator + FileUtils.getFileNameByUrl(list.get(i).getProduct_video())  +
                                            FileUtils.getFileNameWithSuffix(list.get(i).getProduct_video())))){

                                        GoodsPath goodsPath = new GoodsPath();
                                        goodsPath.setUrl(list.get(i).getProduct_video());
                                        goodsPath.setLocationPath(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                + File.separator + FileUtils.getFileNameByUrl(list.get(i).getProduct_video())  +
                                                FileUtils.getFileNameWithSuffix(list.get(i).getProduct_video()));
                                        goodsPaths.add(goodsPath);


                                    }
                                    List<Products.StepBean> stepBeans = list.get(i).getSteps();
                                    if(stepBeans!= null){
                                        for (int j =0;j<stepBeans.size();j++){
                                            Products.StepBean stepBean = stepBeans.get(j);
                                            stepBean.setProduct_id(list.get(i).getProduct_id());

                                            if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                    + File.separator + FileUtils.getFileNameByUrl(stepBean.getStep_voice())  +
                                                    FileUtils.getFileNameWithSuffix(stepBean.getStep_voice())))){
                                                GoodsPath goodsPath = new GoodsPath();
                                                goodsPath.setUrl(stepBean.getStep_voice());
                                                goodsPath.setLocationPath(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                        + File.separator + FileUtils.getFileNameByUrl(stepBean.getStep_voice())  +
                                                        FileUtils.getFileNameWithSuffix(stepBean.getStep_voice()));
                                                goodsPaths.add(goodsPath);
                                            }
                                        }
                                    }

                                }

                                start_multi(goodsPaths);
                            }else {
                                cancelProgressDialog();
                                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                if (list != null && list.size() > 0) {
                                    for (int i = 0; i < list.size(); i++) {
                                        //插入数据库

                                        if(!downLoadGoods(list.get(i))){
                                            map.put(list.get(i).getProduct_id(),
                                                    getLoctionPath(list.get(i)));
                                        }else {
                                            map.put(list.get(i).getProduct_id(),
                                                    list.get(i).getProduct_video());
                                        }


                                    }

                                }
                                showToast("数据更新成功");

                                video.release();

                                //设置音乐
                                JZDataSource dataSource = new JZDataSource(map, "");
                                video.setUp(dataSource, JzvdStd.SCREEN_WINDOW_NORMAL);
                                video.startVideo();
                                video.setCount(Integer.valueOf(mCurrGoods.getCycleindex()==null?"0":mCurrGoods.getCycleindex()));
                                video.startVideo();
                                adapter.notifyDataSetChanged();



                                if (mCurrGoods != null) {
                                    List<Fragment> fragments = new ArrayList<>();
                                    for (int i = 0; i < mCurrGoods.getSteps().size(); i++) {

                                        StepFragment stepFragment = new StepFragment();
                                        Bundle bundle = new Bundle();
                                        if(!downLoadStep(mCurrGoods.getSteps().get(i))){
                                            bundle.putString("audio",getLoctionPath(mCurrGoods.getSteps().get(i)));
                                        }else {
                                            bundle.putString("audio", mCurrGoods.getSteps().get(i).getStep_voice());
                                        }
                                        bundle.putString("bg", mCurrGoods.getSteps().get(i).getStep_img());
                                        stepFragment.setArguments(bundle);
                                        fragments.add(stepFragment);
                                    }
                                    stepPageAdapter = new StepPageAdapter(getSupportFragmentManager(), fragments);
                                    vp.setAdapter(stepPageAdapter);
                                    vp.setCurrentItem(0);
                                    indicator.setViewPager(vp);


                                }

                            }

                        }



                        if(products.getData().getUpdatecode() == 1){//数据没有更新

                            for (int i = 0;i<list.size();i++){
                                if("1".equals(list.get(i).getProduct_updatecode())){
                                    updata(list.get(i).getProduct_id());

                                }
                            }


                        }
                        adapter.notifyDataSetChanged();

                    }
                });
    }


    private void loadXinTiao() {
        if(!NetworkUtils.isConnected()){
            List<GoodsBean> lgoods =  DbHelper.getInstance().goodsBeanLongDBManager().loadAll();
            if(lgoods!= null){

                List<Products.GoodsBean> goodsBeans = new ArrayList<>();


                for (int i =0;i<lgoods.size();i++){

                    List<Products.StepBean> stepBeans = new ArrayList<>();
                    List<StepABean> stepABeans = DbHelper.getInstance().stepABeanLongDBManager().queryBuilder().where(StepABeanDao.Properties.Product_id.eq(lgoods.get(i).getProduct_id())).build().list();
                    if(stepABeans!= null){
                        for (int j =0;j<stepABeans.size();j++){
                            Products.StepBean stepBean = new Products.StepBean(stepABeans.get(j).getStep_id(),
                                    stepABeans.get(j).getProduct_id(),
                                    stepABeans.get(j).getStep_num(),
                                    stepABeans.get(j).getStep_text(),
                                    stepABeans.get(j).getStep_img(),
                                    stepABeans.get(j).getStep_voice(),
                                    stepABeans.get(j).getProduct_name(),
                                    stepABeans.get(j).getProduct_video(),
                                    stepABeans.get(j).getRemarks()
                                    );

                            stepBeans.add(stepBean);
                        }
                    }

                    Products.GoodsBean goodsBean = new Products.GoodsBean(lgoods.get(i).getProduct_id(),
                            lgoods.get(i).getProduct_name(),
                            lgoods.get(i).getProduct_video(),
                            lgoods.get(i).getRemarks(),
                            lgoods.get(i).getProduct_icon(),
                            lgoods.get(i).getIsSelect(),
                            lgoods.get(i).getIsHidLine(),
                            lgoods.get(i).getIffullscreen(),
                            lgoods.get(i).getFullscreen_Interval(),
                            lgoods.get(i).getCycleindex(),
                            lgoods.get(i).getProduct_updatecode(),
                            stepBeans);
                    goodsBeans.add(goodsBean);

                }
                 list.clear();
                list.addAll(goodsBeans);
                if(list.size()>0){
                    mCurrGoods = list.get(0);
                    mCurrGoods.setSelect(true);
                    if(mCurrGoods!= null&&StringUtils.isEmpty(mCurrGoods.getFullscreen_Interval())){
                        try{
                            fenzhong = Integer.valueOf(mCurrGoods.getFullscreen_Interval());
                        }catch (Exception e){
                            fenzhong = 5;
                        }

                    }
                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            //插入数据库
                            if(!downLoadGoods(list.get(i))){
                                map.put(list.get(i).getProduct_id(),
                                        getLoctionPath(list.get(i)));
                            }else {
                                map.put(list.get(i).getProduct_id(),
                                        list.get(i).getProduct_video());
                            }


                        }

                    }
                    JZDataSource dataSource = new JZDataSource(map, "");
                    video.setUp(dataSource, JzvdStd.SCREEN_WINDOW_NORMAL);
                    if(!StringUtils.isEmpty(mCurrGoods.getCycleindex())){
                        try {
                            video.setCount(Integer.valueOf(mCurrGoods.getCycleindex()));
                        }catch (Exception e){

                        }
                    }
                    video.startVideo();



                    adapter.setLine(0);
                    adapter.notifyDataSetChanged();
                    if (mCurrGoods != null) {
                        List<Fragment> fragments = new ArrayList<>();
                        for (int i = 0; i < mCurrGoods.getSteps().size(); i++) {

                            StepFragment stepFragment = new StepFragment();
                            Bundle bundle = new Bundle();
                            if(!downLoadStep(mCurrGoods.getSteps().get(i))){
                                bundle.putString("audio",getLoctionPath(mCurrGoods.getSteps().get(i)));
                            }else {
                                bundle.putString("audio", mCurrGoods.getSteps().get(i).getStep_voice());
                            }
                            bundle.putString("bg", mCurrGoods.getSteps().get(i).getStep_img());
                            stepFragment.setArguments(bundle);
                            fragments.add(stepFragment);
                        }
                        stepPageAdapter = new StepPageAdapter(getSupportFragmentManager(), fragments);
                        vp.setAdapter(stepPageAdapter);
                        vp.setCurrentItem(0);
                        indicator.setViewPager(vp);


                    }
                }

            }

            return;
        }

                OkGo.<Products>post(XINTIAO)
                        .params("device_id", DeviceUtils.getAndroidID())
                        .execute(new CommonCallback<Products>(Products.class) {
                            @Override
                            public void onStart(Request<Products, ? extends Request> request) {
                                super.onStart(request);
                                showProgressDialog("加载...");
                            }

                            @Override
                            public void onFailure(String code, String s) {
                                cancelProgressDialog();
                                showToast(s);

                            }

                            @Override
                            public void onError(Response<Products> response) {
                                super.onError(response);
                                cancelProgressDialog();
                            }

                            @Override
                            public void onSuccess(Products products) {

                                if(products!= null)
                                    if(products.getData().getProducts() == null||products.getData().getProducts().size()<=0){
                                    cancelProgressDialog();
                                    showToast("暂无数据");
                                    return;
                                    }

                                if(list!= null&&products.getData().getProducts()!= null){
                                    for (int i = 0; i < list.size(); i++) {

                                        boolean isC =false;
                                        for (int j =0;j<products.getData().getProducts().size();j++){

                                            if(list.get(i).getProduct_id().equals(products.getData().getProducts().get(j).getProduct_id())){
                                                isC = true;
                                            }

                                        }
                                        if(!isC){
                                            if(FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                    + File.separator + FileUtils.getFileNameByUrl(list.get(i).getProduct_video())  +
                                                    FileUtils.getFileNameWithSuffix(list.get(i).getProduct_video())))){
                                                FileUtils.delete(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id()));
                                            }
                                        }


                                    }

                                }
                                DbHelper.getInstance().goodsBeanLongDBManager().deleteAll();
                                    DbHelper.getInstance().stepABeanLongDBManager().deleteAll();
                                    list.clear();
                                    List<Products.GoodsBean> goodsBeans = products.getData().getProducts();

                                    List<GoodsBean> goodsList = new ArrayList<>();

                                   if(goodsBeans!= null&&goodsBeans.size()>0){

                                       for (int i =0;i<goodsBeans.size();i++){
                                           Products.GoodsBean pro = goodsBeans.get(i);
                                           GoodsBean goodsBean = new GoodsBean(pro.getProduct_id(),
                                                   pro.getProduct_name(),
                                                   pro.getProduct_video(),
                                                   pro.getRemarks(),
                                                   pro.getProduct_icon(),
                                                   pro.getIffullscreen(),
                                                   pro.getFullscreen_Interval(),
                                                   pro.getCycleindex(),
                                                   pro.getProduct_updatecode()
                                                   );
                                           goodsList.add(goodsBean);

                                           DbHelper.getInstance().goodsBeanLongDBManager().insert(goodsBean);
                                           List<Products.StepBean> stepBeans = pro.getSteps();
                                           if(stepBeans!= null){
                                               for (int j =0;j<stepBeans.size();j++){
                                                   Products.StepBean sp = stepBeans.get(j);
                                                   StepABean stepABean = new StepABean(sp.getStep_id(),
                                                           pro.getProduct_id(),sp.getStep_num(),sp.getStep_text(),
                                                           sp.getStep_img(),
                                                           sp.getStep_voice(),
                                                           sp.getProduct_name(),sp.getProduct_video(),
                                                           sp.getRemarks());
                                                   DbHelper.getInstance().stepABeanLongDBManager().insert(stepABean);
                                               }

                                           }



                                       }




                                       mCurrGoods = goodsBeans.get(0);
                                       if(mCurrGoods!= null&&StringUtils.isEmpty(mCurrGoods.getFullscreen_Interval())){
                                           try{
                                               fenzhong = Integer.valueOf(mCurrGoods.getFullscreen_Interval());
                                           }catch (Exception e){
                                               fenzhong = 5;
                                           }

                                       }
                                       mCurrGoods.setSelect(true);
                                       adapter.setLine(0);
                                       list.addAll(goodsBeans);


                                       tVideoName.setText("“" + mCurrGoods.getProduct_name() + "”" + "介绍视频");
                                        int downCount = 0;

                                       for (int i = 0; i < list.size(); i++) {
                                           if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                   + File.separator + FileUtils.getFileNameByUrl(list.get(i).getProduct_video())  +
                                                   FileUtils.getFileNameWithSuffix(list.get(i).getProduct_video())))){
                                               downCount++;
                                           }

                                           List<Products.StepBean> stepBeans = list.get(i).getSteps();
                                           if(stepBeans!= null){
                                               for (int j =0;j<stepBeans.size();j++){
                                                   Products.StepBean stepBean = stepBeans.get(j);
                                                   stepBean.setProduct_id(list.get(i).getProduct_id());
                                                   loadImg(stepBean.getStep_img());

                                                   if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                           + File.separator + FileUtils.getFileNameByUrl(stepBean.getStep_voice())  +
                                                           FileUtils.getFileNameWithSuffix(stepBean.getStep_voice())))){
                                                       downCount++;
                                                   }
                                               }
                                           }

                                       }
                                       if(downCount>0){
                                           List<GoodsPath> goodsPaths = new ArrayList<>();
                                           for (int i = 0; i < list.size(); i++) {

                                               if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                       + File.separator + FileUtils.getFileNameByUrl(list.get(i).getProduct_video())  +
                                                       FileUtils.getFileNameWithSuffix(list.get(i).getProduct_video())))){

                                                    GoodsPath goodsPath = new GoodsPath();
                                                    goodsPath.setUrl(list.get(i).getProduct_video());
                                                    goodsPath.setLocationPath(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                            + File.separator + FileUtils.getFileNameByUrl(list.get(i).getProduct_video())  +
                                                            FileUtils.getFileNameWithSuffix(list.get(i).getProduct_video()));
                                                    goodsPaths.add(goodsPath);


                                               }
                                               List<Products.StepBean> stepBeans = list.get(i).getSteps();
                                               if(stepBeans!= null){
                                                   for (int j =0;j<stepBeans.size();j++){
                                                       Products.StepBean stepBean = stepBeans.get(j);
                                                       stepBean.setProduct_id(list.get(i).getProduct_id());

                                                       if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                               + File.separator + FileUtils.getFileNameByUrl(stepBean.getStep_voice())  +
                                                               FileUtils.getFileNameWithSuffix(stepBean.getStep_voice())))){
                                                           GoodsPath goodsPath = new GoodsPath();
                                                           goodsPath.setUrl(stepBean.getStep_voice());
                                                           goodsPath.setLocationPath(FilePathUtil.getFilePath(MainActivity.this, list.get(i).getProduct_id())
                                                                   + File.separator + FileUtils.getFileNameByUrl(stepBean.getStep_voice())  +
                                                                   FileUtils.getFileNameWithSuffix(stepBean.getStep_voice()));
                                                           goodsPaths.add(goodsPath);
                                                       }
                                                   }
                                               }

                                           }

                                           start_multi(goodsPaths);
                                       }else {
                                           cancelProgressDialog();
                                           LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                           if (list != null && list.size() > 0) {
                                               for (int i = 0; i < list.size(); i++) {
                                                   //插入数据库

                                                   if(!downLoadGoods(list.get(i))){
                                                       map.put(list.get(i).getProduct_id(),
                                                               getLoctionPath(list.get(i)));
                                                   }else {
                                                       map.put(list.get(i).getProduct_id(),
                                                               list.get(i).getProduct_video());
                                                   }


                                               }

                                           }


                                           //设置音乐
                                           JZDataSource dataSource = new JZDataSource(map, "");
                                           video.setUp(dataSource, JzvdStd.SCREEN_WINDOW_NORMAL);
                                           video.startVideo();
                                           video.setCount(Integer.valueOf(mCurrGoods.getCycleindex()==null?"0":mCurrGoods.getCycleindex()));
                                           video.startVideo();
                                           adapter.notifyDataSetChanged();



                                           if (mCurrGoods != null) {
                                               List<Fragment> fragments = new ArrayList<>();
                                               for (int i = 0; i < mCurrGoods.getSteps().size(); i++) {

                                                   StepFragment stepFragment = new StepFragment();
                                                   Bundle bundle = new Bundle();
                                                   if(!downLoadStep(mCurrGoods.getSteps().get(i))){
                                                       bundle.putString("audio",getLoctionPath(mCurrGoods.getSteps().get(i)));
                                                   }else {
                                                       bundle.putString("audio", mCurrGoods.getSteps().get(i).getStep_voice());
                                                   }
                                                   bundle.putString("bg", mCurrGoods.getSteps().get(i).getStep_img());
                                                   stepFragment.setArguments(bundle);
                                                   fragments.add(stepFragment);
                                               }
                                               stepPageAdapter = new StepPageAdapter(getSupportFragmentManager(), fragments);
                                               vp.setAdapter(stepPageAdapter);
                                               vp.setCurrentItem(0);
                                               indicator.setViewPager(vp);


                                           }

                                       }

                                   }



                                    if(products.getData().getUpdatecode() == 1){//数据没有更新

                                       for (int i = 0;i<list.size();i++){
                                           if("1".equals(list.get(i).getProduct_updatecode())){
                                               updata(list.get(i).getProduct_id());

                                           }
                                       }


                                    }
                                adapter.notifyDataSetChanged();

                            }
                        });
            }




    @Override
    public void onGoods(Products.GoodsBean goodsBean, int c) {
        try{
            AudioPlay.getInstance().stop();
            mCurrGoods = goodsBean;
            if(mCurrGoods!= null&&StringUtils.isEmpty(mCurrGoods.getFullscreen_Interval())){
                try{
                    fenzhong = Integer.valueOf(mCurrGoods.getFullscreen_Interval());
                }catch (Exception e){
                    fenzhong = 5;
                }

            }
            tVideoName.setText("“" + mCurrGoods.getProduct_name() + "”" + "介绍视频");
            video.changeUrl(c, 1000);


            if (mCurrGoods != null) {
                List<Fragment> fragments = new ArrayList<>();
                for (int i = 0; i < mCurrGoods.getSteps().size(); i++) {

                    StepFragment stepFragment = new StepFragment();
                    Bundle bundle = new Bundle();
                    if(!downLoadStep(mCurrGoods.getSteps().get(i))){
                        bundle.putString("audio",getLoctionPath(mCurrGoods.getSteps().get(i)));
                    }else {
                        bundle.putString("audio", mCurrGoods.getSteps().get(i).getStep_voice());
                    }
                    bundle.putString("bg", mCurrGoods.getSteps().get(i).getStep_img());
                    stepFragment.setArguments(bundle);
                    fragments.add(stepFragment);
                }
                stepPageAdapter = new StepPageAdapter(getSupportFragmentManager(), fragments);
                vp.setAdapter(stepPageAdapter);
                vp.setCurrentItem(0);
                indicator.setViewPager(vp);


            }


            rV.setVisibility(View.VISIBLE);
            tvVideo.setBackgroundResource(R.drawable.shape_01);
            tvVideo.setTextColor(Color.WHITE);

            rV01.setVisibility(View.INVISIBLE);
            tvDownload.setBackgroundColor(Color.parseColor("#EEEEEE"));
            tvDownload.setTextColor(Color.parseColor("#61000000"));
        }catch (Exception e){

        }

    }


    FileDownloadListener downloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void completed(BaseDownloadTask task) {
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {

        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };
    FileDownloadListener downloadListener1 = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void completed(BaseDownloadTask task) {
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {

        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };


    /**
     * 下载文件
     * @param path
     * @param id
     */
    private void zyFlieDownLoad(String path,String id,FileDownloadListener downloadListener){
        if(!NetworkUtils.isConnected()){
            return;
        }
        if (StringUtils.isEmpty(path)) {
            return;
        }
        String location = FilePathUtil.getFilePath(this, id) + File.separator +
                FileUtils.getFileNameByUrl(path)
                + FileUtils.getFileNameWithSuffix(path);
        if(!FileUtils.isFileExists(location)){
            FileDownloader.getImpl().create(path)
                    .setTag(path)
                    .setAutoRetryTimes(10)
                    .setPath(location)
                    .setListener(downloadListener)
                    .start();

        }
    }


    private boolean downLoadGoods(Products.GoodsBean goodsBean){
        if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(this, goodsBean.getProduct_id())
                + File.separator + FileUtils.getFileNameByUrl(goodsBean.getProduct_video())  + FileUtils.getFileNameWithSuffix(goodsBean.getProduct_video())))){
            zyFlieDownLoad(goodsBean.getProduct_video(),goodsBean.getProduct_id(),downloadListener1);
            return true;
        }
        return false;
    }


    private boolean downLoadStep(Products.StepBean goodsBean){
        if(!FileUtils.isFileExists(new File(FilePathUtil.getFilePath(this, goodsBean.getProduct_id())
                + File.separator + FileUtils.getFileNameByUrl(goodsBean.getStep_voice())  + FileUtils.getFileNameWithSuffix(goodsBean.getStep_voice())))){
            zyFlieDownLoad(goodsBean.getStep_voice(), goodsBean.getProduct_id(), downloadListener1);
            return true;
        }
        return false;
    }
    public String getLoctionPath(Products.GoodsBean goodsBean){
        return FilePathUtil.getFilePath(this, goodsBean.getProduct_id())
                + File.separator + FileUtils.getFileNameByUrl(goodsBean.getProduct_video())  + FileUtils.getFileNameWithSuffix(goodsBean.getProduct_video());
    }

    public String getLoctionPath(Products.StepBean goodsBean){
        return FilePathUtil.getFilePath(this, goodsBean.getProduct_id())
                + File.separator + FileUtils.getFileNameByUrl(goodsBean.getStep_voice())  + FileUtils.getFileNameWithSuffix(goodsBean.getStep_voice());
    }

    private int count = 0;
    private int sum = 0;
    private boolean isDownload =false;
    public void start_multi(List<GoodsPath> goodsPaths){
        if(!NetworkUtils.isConnected()){
            return;
        }
        if(goodsPaths == null || goodsPaths.size()<=0){
            return;
        }

         count = 0;
        sum = 0;
        count = goodsPaths.size();
        downloadListener = createLis();
        //(1) 创建 FileDownloadQueueSet
        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(downloadListener);

        //(2) 创建Task 队列
        final List<BaseDownloadTask> tasks = new ArrayList<>();
        for (int i = 0;i<goodsPaths.size();i++){
            BaseDownloadTask task1 = FileDownloader.getImpl().create(goodsPaths.get(i).getUrl()).
                    setPath(goodsPaths.get(i).getLocationPath());
            tasks.add(task1);
        }

        //(3) 设置参数

        // 每个任务的进度 无回调
        //queueSet.disableCallbackProgressTimes();
        // do not want each task's download progress's callback,we just consider which task will completed.

        queueSet.setCallbackProgressTimes(100);
        queueSet.setCallbackProgressMinInterval(100);
        //失败 重试次数
        queueSet.setAutoRetryTimes(10);

        //避免掉帧
        FileDownloader.enableAvoidDropFrame();

        //(4)串行下载
        queueSet.downloadSequentially(tasks);
        showProgressDialog("正在下载（"+sum+"/"+count+"）");
        //(5)任务启动
        isDownload = true;
        queueSet.start();
    }


    public FileDownloadListener createLis(){
        return new FileDownloadListener(){
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }

                Log.d("feifei","pending taskId:"+task.getId()+",fileName:"+task.getFilename()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }


                Log.d("feifei","progress taskId:"+task.getId()+",fileName:"+task.getFilename()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes+",speed:"+task.getSpeed());
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
                if(task.getListener() != downloadListener){
                    return;
                }
                sum++;
                Log.e("下载",sum+" "+count);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgressDialog("正在下载（"+sum+"/"+count+"）");
                        if(sum == count){
                            cancelProgressDialog();
                            isDownload = false;
                            showToast("下载完成");
                            cancelProgressDialog();
                            LinkedHashMap<String, String> map = new LinkedHashMap<>();
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    //插入数据库

                                    if (!downLoadGoods(list.get(i))) {
                                        map.put(list.get(i).getProduct_id(),
                                                getLoctionPath(list.get(i)));
                                    } else {
                                        map.put(list.get(i).getProduct_id(),
                                                list.get(i).getProduct_video());
                                    }


                                }

                            }
                            if(video.jzDataSource!= null&&(video.currentState == CURRENT_STATE_PAUSE||video.currentScreen == CURRENT_STATE_PLAYING)){
                                video.release();
                            }

                            //设置音乐
                            JZDataSource dataSource = new JZDataSource(map, "");
                            video.setUp(dataSource, JzvdStd.SCREEN_WINDOW_NORMAL);
                            video.startVideo();
                            video.setCount(Integer.valueOf(mCurrGoods.getCycleindex() == null ? "0" : mCurrGoods.getCycleindex()));
                            video.startVideo();
                            adapter.notifyDataSetChanged();


                            if (mCurrGoods != null) {
                                List<Fragment> fragments = new ArrayList<>();
                                for (int i = 0; i < mCurrGoods.getSteps().size(); i++) {

                                    StepFragment stepFragment = new StepFragment();
                                    Bundle bundle = new Bundle();
                                    if (!downLoadStep(mCurrGoods.getSteps().get(i))) {
                                        bundle.putString("audio", getLoctionPath(mCurrGoods.getSteps().get(i)));
                                    } else {
                                        bundle.putString("audio", mCurrGoods.getSteps().get(i).getStep_voice());
                                    }
                                    bundle.putString("bg", mCurrGoods.getSteps().get(i).getStep_img());
                                    stepFragment.setArguments(bundle);
                                    fragments.add(stepFragment);
                                }
                                stepPageAdapter = new StepPageAdapter(getSupportFragmentManager(), fragments);
                                vp.setAdapter(stepPageAdapter);
                                vp.setCurrentItem(0);
                                indicator.setViewPager(vp);

                            }
                        }
                    }
                });


//                if(sum==count){

//
//
//                    }
//                    isDownload = true;
//                }else {
//                   // showProgressDialog("正在下载（"+sum+"/"+count+"）");
//                }


                Log.d("x","blockComplete taskId:"+task.getId()+",filePath:"+task.getPath()+",fileName:"+task.getFilename()+",speed:"+task.getSpeed()+",isReuse:"+task.reuse());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if (task.getListener() != downloadListener) {
                    return;
                }

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","paused taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","error taskId:"+task.getId()+",e:"+e.getLocalizedMessage());
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","warn taskId:"+task.getId());
            }
        };
    }



    final static int COUNTS = 5;// 点击次数
    final static long DURATION = 5000;// 规定有效时间
    long[] mHits = new long[COUNTS];

    private void loadImg(final String img){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Glide.with(MainActivity.this)
                            .load(img)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void continuousClick(int count, long time) {

        if(!NetworkUtils.isConnected()){
            return;
        }
        //每次点击时，数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHits = new long[COUNTS];//重新初始化数组
            loadXinTiao1();
        }
    }
}
