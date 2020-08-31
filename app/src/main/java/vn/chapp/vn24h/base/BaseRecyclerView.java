package vn.chapp.vn24h.base;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import vn.chapp.vn24h.R;

public class BaseRecyclerView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    public BaseRecyclerView(Context context) {
        super(context);
        onCreateView(context, null);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreateView(context, attrs);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreateView(context, attrs);
    }

    private void onCreateView(Context context,AttributeSet attrs ){
        swipeRefreshLayout = new SwipeRefreshLayout(context,attrs);
        swipeRefreshLayout.setColorSchemeColors(ResourcesCompat.getColor(getResources(), R.color.colorFF8800,null));
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = new RecyclerView(context,attrs);
        swipeRefreshLayout.addView(recyclerView);
        addView(swipeRefreshLayout);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRefresh(boolean refreshing){
        swipeRefreshLayout.setRefreshing(refreshing);
    }
    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        recyclerView.setHasFixedSize(hasFixedSize);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.addItemDecoration(decor);
    }


    @Override
    public void onRefresh() {
        if (onRefreshListener != null) onRefreshListener.onRefresh();
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
