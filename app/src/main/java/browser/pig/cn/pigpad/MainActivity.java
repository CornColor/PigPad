package browser.pig.cn.pigpad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import browser.pig.cn.pigpad.bean.GoodsListBean;
import browser.pig.cn.pigpad.bean.StepBean;
import browser.pig.cn.pigpad.bean.VersionBean;
import browser.pig.cn.pigpad.bean.XGoodsListBean;
import browser.pig.cn.pigpad.net.CommonCallback;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaInterface;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JzvdStd;
import cn.my.library.ui.base.BaseActivity;
import cn.my.library.utils.util.AppUtils;
import cn.my.library.utils.util.DeviceUtils;
import cn.my.library.utils.util.FilePathUtil;
import cn.my.library.utils.util.SPUtils;
import cn.my.library.utils.util.StringUtils;
import me.relex.circleindicator.CircleIndicator;

import static browser.pig.cn.pigpad.net.ApiSearvice.GOODS_LIST;
import static browser.pig.cn.pigpad.net.ApiSearvice.GOODS_STEP;
import static browser.pig.cn.pigpad.net.ApiSearvice.VERSION;
import static browser.pig.cn.pigpad.net.ApiSearvice.XINTIAO;

public class MainActivity extends BaseActivity implements GoodsAdapter.OnGoodsClickListener {
    @Bind(R.id.tv_video)
    TextView tvVideo;
    @Bind(R.id.tv_download)
    TextView tvDownload;
    @Bind(R.id.t_video_name)
    TextView tVideoName;
    @Bind(R.id.r_v)
    RelativeLayout rV;
    @Bind(R.id.r_v_01)
    RelativeLayout rV01;

 //   RecyclerView rvBz;
    private RecyclerView rv_data;
    private GoodsAdapter adapter;
    private List<GoodsListBean.GoodsBean> list;

 //   private List<StepBean> list1;
//    private StepAdapter stepAdapter;

    StepPageAdapter stepPageAdapter;
    CustomJzvd video;

    private GoodsListBean.GoodsBean mCurrGoods;
    private ViewPager vp;
    private  CircleIndicator indicator;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        list1 = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            StepBean goodsBean = new StepBean();
//            list1.add(goodsBean);
//        }
     //   rvBz = findViewById(R.id.rv_bz);
//        stepAdapter = new StepAdapter(this,list1);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rvBz.setLayoutManager(layoutManager);
//        rvBz.setAdapter(stepAdapter);






// optional
       // adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        adapter = new GoodsAdapter(this, list);
        adapter.setOnGoodsClickListener(this);
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        rv_data.setAdapter(adapter);







    }

    @Override
    public void initView() {
        rv_data = findViewById(R.id.rv_data);
        video = findViewById(R.id.video);


        indicator = findViewById(R.id.ci);
        vp = findViewById(R.id.vp);


    }

    @Override
    public void initPresenter() {


        Runnable runnable = new Runnable(){
            @Override
            public void run() {
// TODO Auto-generated method stub
// 在此处添加执行的代码
                loadXinTiao();
                banben();
                handler.postDelayed(this, 1*60*1000);// 50是延时时长
            }
        };
        handler.postDelayed(runnable, 1*60*1000);// 打开定时器，执行操作


       // video.thumbImageView.setImageResource(R.drawable.applog);

        loadGoods();
        banben();

    }


    @Override
    protected void onPause() {
        super.onPause();
        JZMediaManager.pause();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if (JzvdStd.backPress()) {
            return;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(JZMediaManager.getDataSource()!= null){
            JZMediaManager.start();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        handler.removeCallbacks((Runnable) this);// 关闭定时器处理
        if(AudioPlay.getInstance().isPlay()){
            AudioPlay.getInstance().stop();
        }
        JzvdStd.releaseAllVideos();
    }

    @OnClick({R.id.tv_video, R.id.tv_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_video:
                rV.setVisibility(View.VISIBLE);
                tvVideo.setBackgroundResource(R.drawable.shape_01);
                tvVideo.setTextColor(Color.WHITE);

                rV01.setVisibility(View.GONE);
                tvDownload.setBackgroundColor(Color.parseColor("#EEEEEE"));
                tvDownload.setTextColor(Color.parseColor("#61000000"));
                JZMediaManager.start();
                break;
            case R.id.tv_download:
                rV.setVisibility(View.GONE);
                tvVideo.setBackgroundColor(Color.parseColor("#EEEEEE"));
                tvVideo.setTextColor(Color.parseColor("#61000000"));
                rV01.setVisibility(View.VISIBLE);
                tvDownload.setBackgroundResource(R.drawable.shape_01);
                tvDownload.setTextColor(Color.WHITE);
                JZMediaManager.pause();
                break;
        }
    }
    private void banben(){

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
                            if(!appVersionName.equals(version)){
                                fileDownLoad(banBenBean.getData().getAddress());
                            }
                        }catch (Exception e){

                        }


                    }
                });


    }

    private void fileDownLoad(String path) {
        if(StringUtils.isEmpty(path)){
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
                        try{
                            if(!StringUtils.isEmpty(task.getPath())){
                                AppUtils.installApp(task.getPath());
                            }
                        }catch (Exception e){

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
    private void loadGoods() {
        OkGo.<GoodsListBean>post(GOODS_LIST)
                .execute(new CommonCallback<GoodsListBean>(GoodsListBean.class) {

                    @Override
                    public void onFailure(String code, String s) {

                    }

                    @Override
                    public void onSuccess(GoodsListBean goodsListBean) {
                        if(goodsListBean!= null){
                            list.clear();
                            if(goodsListBean.getData().getList()!= null && goodsListBean.getData().getList().size()>0){
                                mCurrGoods = goodsListBean.getData().getList().get(0);
                                mCurrGoods.setSelect(true);
                                adapter.setLine(0);
                                list.addAll(goodsListBean.getData().getList());
                                tVideoName.setText("“"+mCurrGoods.getProduct_name()+"”"+"介绍视频");
                                JZDataSource jzDataSource = new JZDataSource(mCurrGoods.getProduct_video(), "");
                                jzDataSource.looping = true;
                                video.setUp(jzDataSource, JzvdStd.SCREEN_WINDOW_NORMAL);
                                loadStep(mCurrGoods.getProduct_id());
                                video.startVideo();

                            }

                            adapter.notifyDataSetChanged();


                        }

                    }
                });
    }
    private void loadXinTiao() {
        OkGo.<XGoodsListBean>post(XINTIAO)
                .params("device_id", DeviceUtils.getAndroidID())
                .execute(new CommonCallback<XGoodsListBean>(XGoodsListBean.class) {

                    @Override
                    public void onFailure(String code, String s) {

                    }

                    @Override
                    public void onSuccess(XGoodsListBean goodsListBean) {
                        if(goodsListBean!= null&&goodsListBean.getData().getUpdatecode() == 1){
                            list.clear();
                            if(goodsListBean.getData().getProducts()!= null && goodsListBean.getData().getProducts().size()>0){
                                mCurrGoods = goodsListBean.getData().getProducts().get(0);
                                list.addAll(goodsListBean.getData().getProducts());

                            }

                            adapter.notifyDataSetChanged();


                        }

                    }
                });
    }
    @Override
    public void onGoods(GoodsListBean.GoodsBean goodsBean) {

        mCurrGoods = goodsBean;
        tVideoName.setText("“"+mCurrGoods.getProduct_name()+"”"+"介绍视频");
        JZDataSource jzDataSource = new JZDataSource(mCurrGoods.getProduct_video(), "");
        jzDataSource.looping = true;
        video.changeUrl(jzDataSource,1000);
        loadStep(mCurrGoods.getProduct_id());


        rV.setVisibility(View.VISIBLE);
        tvVideo.setBackgroundResource(R.drawable.shape_01);
        tvVideo.setTextColor(Color.WHITE);

        rV01.setVisibility(View.GONE);
        tvDownload.setBackgroundColor(Color.parseColor("#EEEEEE"));
        tvDownload.setTextColor(Color.parseColor("#61000000"));

        if(AudioPlay.getInstance().isPlay()){
            AudioPlay.getInstance().stop();
        }
    }

    /**
     * 加载步骤
     * @param id
     */
    private void loadStep(String id){
        OkGo.<StepBean>post(GOODS_STEP)
                .params("product_id",id)
                .execute(new CommonCallback<StepBean>(StepBean.class) {

                    @Override
                    public void onFailure(String code, String s) {

                    }

                    @Override
                    public void onSuccess(StepBean goodsListBean) {
                        if(goodsListBean!= null){
                            List<Fragment> fragments = new ArrayList<>();
                          for (int i = 0;i < goodsListBean.getData().getList().size();i++){
                              StepFragment stepFragment = new StepFragment();
                              Bundle bundle = new Bundle();
                              bundle.putString("audio",goodsListBean.getData().getList().get(i).getStep_voice());
                              bundle.putString("bg",goodsListBean.getData().getList().get(i).getStep_img());
                              stepFragment.setArguments(bundle);
                              fragments.add(stepFragment);
                          }
                          stepPageAdapter = new StepPageAdapter(getSupportFragmentManager(),fragments);
                          vp.setAdapter(stepPageAdapter);
                          vp.setCurrentItem(0);
                          indicator.setViewPager(vp);


                        }

                    }
                });
    }


}
