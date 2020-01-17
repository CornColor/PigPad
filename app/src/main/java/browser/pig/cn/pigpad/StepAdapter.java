package browser.pig.cn.pigpad;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import browser.pig.cn.pigpad.bean.StepBean;
import cn.my.library.ui.adapter.recycleview.CommonAdapter;
import cn.my.library.ui.adapter.recycleview.base.ViewHolder;

/**
 * created by dan
 */
public class StepAdapter extends CommonAdapter<StepBean> {
    public StepAdapter(Context context,  List<StepBean> datas) {
        super(context, R.layout.item_step, datas);
    }

    @Override
    protected void convert(ViewHolder holder, StepBean stepBean, int position) {
        RelativeLayout rv_01 = holder.getView(R.id.rv_01);
        RelativeLayout rv_02 = holder.getView(R.id.rv_02);

        if(position == 0){
            rv_01.setVisibility(View.VISIBLE);
            rv_02.setVisibility(View.GONE);
        }else {
            rv_01.setVisibility(View.GONE);
            rv_02.setVisibility(View.VISIBLE);
        }



    }
}
