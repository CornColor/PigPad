package browser.pig.cn.pigpad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

/**
 * created by dan
 */
public class StepPageAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction;
    private Context mContext;
    private List<Fragment> mFragmentArrayList;


    public StepPageAdapter(FragmentManager fm,Context ctx, List<Fragment> fragments) {
        super(fm);
        mFragmentManager = fm;
        mContext = ctx;
        mFragmentArrayList = fragments;

    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentArrayList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentArrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    /**
     * 清除缓存fragment
     * @param container ViewPager
     */
    public void clear(ViewGroup container){
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }

        for(int i=0;i<mFragmentArrayList.size();i++){
            long itemId = this.getItemId(i);
            String name = makeFragmentName(container.getId(), itemId);
            Fragment fragment = this.mFragmentManager.findFragmentByTag(name);
            if(fragment != null){//根据对应的ID，找到fragment，删除
                mCurTransaction.remove(fragment);
            }
        }
        mCurTransaction.commitNowAllowingStateLoss();
    }

    /**
     * 等同于FragmentPagerAdapter的makeFragmentName方法，
     * 由于父类的该方法是私有的，所以在此重新定义
     * @param viewId
     * @param id
     * @return
     */
    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }



}
