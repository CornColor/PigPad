package browser.pig.cn.pigpad;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import cn.my.library.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
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
    private List<GoodsBean> list;

    private List<StepBean> list1;
    private StepAdapter stepAdapter;


    JzvdStd video;

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
        for (int i = 0; i < 8; i++) {
            GoodsBean goodsBean = new GoodsBean();
            list.add(goodsBean);
        }
        adapter = new GoodsAdapter(this, list);
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

        video.setUp("https://media.w3.org/2010/05/sintel/trailer.mp4", "", JzvdStd.SCREEN_WINDOW_NORMAL);
        video.startVideo();
        video.thumbImageView.setImageResource(R.drawable.applog);

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
}
