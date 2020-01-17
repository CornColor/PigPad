package browser.pig.cn.pigpad;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import browser.pig.cn.pigpad.bean.GoodsListBean;
import cn.my.library.ui.adapter.recycleview.CommonAdapter;
import cn.my.library.ui.adapter.recycleview.base.ViewHolder;

/**
 * created by dan
 */
public class GoodsAdapter extends CommonAdapter<GoodsListBean.GoodsBean> {

    public GoodsAdapter(Context context, List<GoodsListBean.GoodsBean> datas) {
        super(context, R.layout.item_goods, datas);
    }
    OnGoodsClickListener onGoodsClickListener;

    public void setOnGoodsClickListener(OnGoodsClickListener onGoodsClickListener) {
        this.onGoodsClickListener = onGoodsClickListener;
    }

    @Override
    protected void convert(ViewHolder holder, GoodsListBean.GoodsBean goodsBean, final int position) {

        RelativeLayout bg  = holder.getView(R.id.bg);
        View v_01 = holder.getView(R.id.v_01);
        TextView tv_godds_name = holder.getView(R.id.tv_godds_name);
        tv_godds_name.setText(goodsBean.getProduct_name());
        View v_line = holder.getView(R.id.v_line);

        RoundedImageView rv_goods_img = holder.getView(R.id.rv_goods_img);
        Glide.with(mContext).load(goodsBean.getProduct_icon()).into(rv_goods_img);






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
                      onGoodsClickListener.onGoods(getDatas().get(position));
                    }else {
                        getDatas().get(i).setSelect(false);
                    }

                }
                notifyDataSetChanged();
            }
        });

    }

    public interface OnGoodsClickListener{
        void onGoods(GoodsListBean.GoodsBean goodsBean);
    }

    public void setLine(int position){
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
