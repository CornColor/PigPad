package browser.pig.cn.pigpad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.my.library.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
   private RecyclerView rv_data;
   private GoodsAdapter adapter;
   private List<GoodsBean> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    @Override
    public void initPresenter() {

    }
}
