package browser.pig.cn.pigpad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;
import java.util.List;

import browser.pig.cn.pigpad.bean.GoodsListBean;
import browser.pig.cn.pigpad.net.CommonCallback;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import cn.my.library.ui.base.BaseActivity;
import cn.my.library.utils.util.SPUtils;

import static browser.pig.cn.pigpad.net.ApiSearvice.GOODS_LIST;

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

    RecyclerView rvBz;
    private RecyclerView rv_data;
    private GoodsAdapter adapter;
    private List<GoodsListBean.GoodsBean> list;

    private List<StepBean> list1;
    private StepAdapter stepAdapter;


    JzvdStd video;

    private GoodsListBean.GoodsBean mCurrGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        list1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            StepBean goodsBean = new StepBean();
            list1.add(goodsBean);
        }
        rvBz = findViewById(R.id.rv_bz);
        stepAdapter = new StepAdapter(this,list1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvBz.setLayoutManager(layoutManager);
        rvBz.setAdapter(stepAdapter);



        CircleIndicator4 indicator = findViewById(R.id.ci);
        indicator.attachToRecyclerView(rvBz);

// optional
        adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
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
    }

    @Override
    public void initPresenter() {



       // video.thumbImageView.setImageResource(R.drawable.applog);

        loadGoods();

    }


    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if (JzvdStd.backPress()) {
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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
                break;
            case R.id.tv_download:
                rV.setVisibility(View.GONE);
                tvVideo.setBackgroundColor(Color.parseColor("#EEEEEE"));
                tvVideo.setTextColor(Color.parseColor("#61000000"));
                rV01.setVisibility(View.VISIBLE);
                tvDownload.setBackgroundResource(R.drawable.shape_01);
                tvDownload.setTextColor(Color.WHITE);
                break;
        }
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
                                video.setUp(mCurrGoods.getProduct_video(), "", JzvdStd.SCREEN_WINDOW_NORMAL);

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
        video.changeUrl(mCurrGoods.getProduct_video(),"",1000);
    }
}
