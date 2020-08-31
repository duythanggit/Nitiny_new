package vn.chapp.vn24h.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements RecyclerEndlessScrollListener.OnLoadMoreListener {

    private RecyclerView recyclerView;
    private List<T> collection;
    private OnItemClickListener onItemClickListener;
    private RecyclerEndlessScrollListener recyclerEndlessScrollListener;
    private OnLoadMoreListener onLoadMoreListener;

    public BaseAdapter(List<T> collection) {
        if (collection == null) {
            this.collection = new ArrayList<>();
        } else {
            this.collection = collection;
        }
        recyclerEndlessScrollListener = new RecyclerEndlessScrollListener(this);
    }

    public void replaceData(List<T> collection) {
        if (this.collection != null) {
            this.collection.clear();
            this.collection.addAll(collection);
        } else {
            this.collection = collection;
        }
        notifyDataSetChanged();
    }

    public void addData(T data) {
        this.collection.add(data);
        notifyItemInserted(this.collection.size() - 1);
    }

    public void addAllData(List<T> collection) {
        int prev = this.collection.size();
        this.collection.addAll(collection);
        notifyItemRangeInserted(prev, collection.size());
    }

    public void clearData() {
        this.collection.clear();
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        this.collection.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onClick(i);
        });
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<T> getCollection() {
        return collection;
    }

    @Override
    public void onLoadMore() {
        if (onLoadMoreListener != null) onLoadMoreListener.onLoadMore();
    }

    public void setOnLoadMoreListener(RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        this.recyclerView = recyclerView;
        this.onLoadMoreListener = onLoadMoreListener;
        recyclerView.addOnScrollListener(recyclerEndlessScrollListener);
    }

    public void removeLoadMoreListener() {
        if (recyclerView != null) {
            recyclerView.removeOnScrollListener(recyclerEndlessScrollListener);
        }
    }

    public void setLoaded(boolean isLoaded) {
        recyclerEndlessScrollListener.setLoaded(isLoaded);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public RecyclerEndlessScrollListener getRecyclerEndlessScrollListener() {
        return recyclerEndlessScrollListener;
    }

    public void addDataToPosition(int position, T data) {
        this.collection.add(position, data);
        notifyItemInserted(position);
    }
}
