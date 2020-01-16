package browser.pig.cn.pigpad;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.View;

import me.relex.circleindicator.BaseCircleIndicator;


/**
 * CircleIndicator2 work with RecyclerView and SnapHelper
 */
public class CircleIndicator4 extends BaseCircleIndicator {


    private RecyclerView mRecyclerView;


    public CircleIndicator4(Context context) {
        super(context);
    }

    public CircleIndicator4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleIndicator4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicator4(Context context, AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void attachToRecyclerView(@NonNull RecyclerView recyclerView
                                     ) {
        mRecyclerView = recyclerView;

        mLastPosition = -1;
        createIndicators();
        recyclerView.removeOnScrollListener(mInternalOnScrollListener);
        recyclerView.addOnScrollListener(mInternalOnScrollListener);
    }

    private void createIndicators() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        int count;
        if (adapter == null) {
            count = 0;
        } else {
            count = adapter.getItemCount();
        }
        createIndicators(count, getSnapPosition(mRecyclerView.getLayoutManager()));
    }

    public int getSnapPosition(@Nullable RecyclerView.LayoutManager layoutManager) {
        if (layoutManager == null) {
            return RecyclerView.NO_POSITION;
        }

        return ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
    }

    private final RecyclerView.OnScrollListener mInternalOnScrollListener =
            new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int position = getSnapPosition(recyclerView.getLayoutManager());
                    if (position == RecyclerView.NO_POSITION) {
                        return;
                    }
                    animatePageSelected(position);
                }
            };

    private final RecyclerView.AdapterDataObserver mAdapterDataObserver =
            new RecyclerView.AdapterDataObserver() {
                @Override public void onChanged() {
                    super.onChanged();
                    if (mRecyclerView == null) {
                        return;
                    }
                    RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                    int newCount = adapter != null ? adapter.getItemCount() : 0;
                    int currentCount = getChildCount();
                    if (newCount == currentCount) {
                        // No change
                        return;
                    } else if (mLastPosition < newCount) {
                        mLastPosition = getSnapPosition(mRecyclerView.getLayoutManager());
                    } else {
                        mLastPosition = RecyclerView.NO_POSITION;
                    }
                    createIndicators();
                }

                @Override public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    onChanged();
                }

                @Override public void onItemRangeChanged(int positionStart, int itemCount,
                                                         @Nullable Object payload) {
                    super.onItemRangeChanged(positionStart, itemCount, payload);
                    onChanged();
                }

                @Override public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    onChanged();
                }

                @Override public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    onChanged();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    onChanged();
                }
            };

    public RecyclerView.AdapterDataObserver getAdapterDataObserver() {
        return mAdapterDataObserver;
    }
}
