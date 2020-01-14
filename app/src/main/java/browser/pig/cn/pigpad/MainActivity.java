package browser.pig.cn.pigpad;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import cn.my.library.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
   private RecyclerView rv_data;
   private GoodsAdapter adapter;
   private List<GoodsBean> list ;


    JzvdStd video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //当sdk版本大于等于16更换播放引擎
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            Jzvd.setMediaInterface(new JZExoPlayer());
        }
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        for (int i =0;i < 8;i++){
            GoodsBean goodsBean = new GoodsBean();
            list.add(goodsBean);
        }
        adapter = new GoodsAdapter(this,list);
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

        video.setUp("https://media.w3.org/2010/05/sintel/trailer.mp4","",JzvdStd.SCREEN_WINDOW_NORMAL);
      //  video.startVideo();
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
        if(JzvdStd.backPress()){
            return;
        }
    }
}
