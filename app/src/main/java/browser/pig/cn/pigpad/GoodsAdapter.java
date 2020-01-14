package browser.pig.cn.pigpad;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.my.library.ui.adapter.recycleview.CommonAdapter;
import cn.my.library.ui.adapter.recycleview.base.ViewHolder;

/**
 * created by dan
 */
public class GoodsAdapter extends CommonAdapter<GoodsBean> {

    public GoodsAdapter(Context context, List<GoodsBean> datas) {
        super(context, R.layout.item_goods, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsBean goodsBean, final int position) {

        RelativeLayout bg  = holder.getView(R.id.bg);
        View v_01 = holder.getView(R.id.v_01);
        TextView tv_godds_name = holder.getView(R.id.tv_godds_name);
        View v_line = holder.getView(R.id.v_line);


        if(goodsBean.isSelect()){
            bg.setBackgroundColor(Color.parseColor("#fff8f8f8"));
            v_01.setVisibility(View.VISIBLE);
            tv_godds_name.setTextColor(Color.parseColor("#fffe9c2d"));
        }else {
            bg.setBackgroundColor(Color.parseColor("#FFEEDA"));
            v_01.setVisibility(View.GONE);
            tv_godds_name.setTextColor(Color.parseColor("#8a000000"));
        }

        if(goodsBean.isHidLine()){
            v_line.setVisibility(View.GONE);
        }else {
            v_line.setVisibility(View.VISIBLE);
        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i =0;i < getItemCount();i++){
                    if(position == i){
                      getDatas().get(position).setSelect(true);
                      setLine(position);
                    }else {
                        getDatas().get(i).setSelect(false);
                    }

                }
                notifyDataSetChanged();
            }
        });

    }

    private void setLine(int position){
        if(position >=0&&position<getItemCount()){
            for (int i =0;i < getItemCount();i++){
                   if(position == i){
                       getDatas().get(position).setHidLine(true);
                       if(position - 1>=0&&position-1<getItemCount()){
                           getDatas().get(position - 1).setHidLine(true);
                       }
                   }else if(position != i&&i!= position- 1) {

                       getDatas().get(i).setHidLine(false);
                   }


            }
        }
    }


}
