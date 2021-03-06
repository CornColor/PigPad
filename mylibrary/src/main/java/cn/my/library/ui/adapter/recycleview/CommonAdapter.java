package cn.my.library.ui.adapter.recycleview;

import android.content.Context;
import android.view.LayoutInflater;




import java.util.List;

import cn.my.library.ui.adapter.recycleview.base.ItemViewDelegate;
import cn.my.library.ui.adapter.recycleview.base.ViewHolder;


/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected boolean mEditMode = false;
    protected boolean mAllSelectStatus = false;
    public void setSelectStatus(boolean selectStatus){
        mAllSelectStatus = selectStatus;
    }
    public void setEditMode(boolean editMode){
        mEditMode = editMode;
        notifyDataSetChanged();
    }
    public CommonAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }

            @Override
            public void convert(ViewHolder holder, T t, int position, List<Object> payloads) {
                CommonAdapter.this.convert(holder, t, position,payloads);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);
    protected void convert(ViewHolder holder, T t, int position,List<Object> payloads){

    }
}
