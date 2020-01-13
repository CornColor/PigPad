package cn.my.library.BottomBarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.my.library.R;


public class BottomBarTab extends FrameLayout {
    private ImageView mIcon;
    private TextView mTvTitle;
    private Context mContext;
    private int mTabPosition = -1;

    private TextView mTvUnreadCount;

    private int mSelectIcon, mNorIcon;

    public BottomBarTab(Context context, @DrawableRes int icon, int icon2, CharSequence title) {
        this(context, null, icon, icon2, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int icon, int icon2, CharSequence title) {
        this(context, attrs, 0, icon, icon2, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, int icon2, CharSequence title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, icon2, title);
    }

    private void init(Context context, int icon, int icon2, CharSequence title) {
        mContext = context;
        mSelectIcon = icon;
        mNorIcon = icon2;
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        LinearLayout lLContainer = new LinearLayout(context);
        lLContainer.setOrientation(LinearLayout.VERTICAL);
        lLContainer.setGravity(Gravity.CENTER);
        LayoutParams paramsContainer = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsContainer.gravity = Gravity.CENTER;
        lLContainer.setLayoutParams(paramsContainer);


        mIcon = new ImageView(context);
        int size = TextUtils.isEmpty(title) ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()) :
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        mIcon.setImageResource(icon2);
        mIcon.setLayoutParams(params);
//        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        lLContainer.addView(mIcon);

        if (!TextUtils.isEmpty(title)) {
            mTvTitle = new TextView(context);
            mTvTitle.setText(title);
            LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsTv.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            mTvTitle.setTextSize(10);
            mTvTitle.setTextColor(ContextCompat.getColor(context, R.color.nav_tgay));
            mTvTitle.setLayoutParams(paramsTv);
            lLContainer.addView(mTvTitle);
        }

        addView(lLContainer);

        int min = Math.round(getResources().getDimension(R.dimen.content_text_s14));
        mTvUnreadCount = new TextView(context);
        mTvUnreadCount.setMinWidth(min);
        mTvUnreadCount.setTextSize(getResources().getDimension(R.dimen.content_text_s3));
        mTvUnreadCount.setTextColor(Color.WHITE);
//        mTvUnreadCount.setPadding(padding, 0, padding, 0);
        mTvUnreadCount.setGravity(Gravity.CENTER);
        LayoutParams tvUnReadParams = new LayoutParams(min, min);
        tvUnReadParams.gravity = Gravity.CENTER;
        tvUnReadParams.leftMargin = dip2px(context, 10);
        tvUnReadParams.bottomMargin = dip2px(context, 18);
        mTvUnreadCount.setLayoutParams(tvUnReadParams);
        mTvUnreadCount.setVisibility(GONE);

        addView(mTvUnreadCount);
    }

//    private int[] SELECT_ICON = new int[]{
//            R.mipmap.ic_launcher,
//            R.mipmap.ic_launcher,
//            R.mipmap.ic_launcher,
//            R.mipmap.ic_launcher,
//            R.mipmap.ic_launcher,
//    };
//
//    private int[] UNSELECT_ICON = new int[]{
//            R.mipmap.ic_launcher,
//            R.mipmap.ic_launcher,
//            R.mipmap.ic_launcher,
//            R.mipmap.ic_launcher,
//            R.mipmap.ic_launcher,
//    };


    private int mSelectTextColot = getResources().getColor(R.color.them);

    public BottomBarTab setSelectTextColor(int color) {
        mSelectTextColot = color;
        return this;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setImageResource(mSelectIcon);
            if (mTvTitle != null)
                mTvTitle.setTextColor(mSelectTextColot);
        } else {
            if (mTvTitle != null)
                mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.nav_tgay));
            mIcon.setImageResource(mNorIcon);
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    /**
     * 设置未读数量
     */
    public BottomBarTab setUnreadCount(int num) {
        if (num <= 0) {
            mTvUnreadCount.setText(String.valueOf(0));
            mTvUnreadCount.setVisibility(GONE);
        } else {
            mTvUnreadCount.setVisibility(VISIBLE);
            if (num > 99) {
                mTvUnreadCount.setText("99+");
            } else {
                mTvUnreadCount.setText(String.valueOf(num));
            }
        }
        return this;
    }

    /**
     * 获取当前未读数量
     */
    public int getUnreadCount() {
        int count = 0;
        if (TextUtils.isEmpty(mTvUnreadCount.getText())) {
            return count;
        }
        if (mTvUnreadCount.getText().toString().equals("99+")) {
            return 99;
        }
        try {
            count = Integer.valueOf(mTvUnreadCount.getText().toString());
        } catch (Exception ignored) {
        }
        return count;
    }

    private int dip2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
