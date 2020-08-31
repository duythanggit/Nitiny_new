package vn.chapp.vn24h.ui.location;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.base.OnItemClickListener;
import vn.chapp.vn24h.models.map.PredictionsItem;

public class SearchPlaceAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<PredictionsItem> predictionsItems;
    private OnItemClickListener onItemClickListener;

    public SearchPlaceAdapter(List<PredictionsItem> predictionsItems) {
        if (predictionsItems == null) {
            this.predictionsItems = new ArrayList<>();
        } else {
            this.predictionsItems = predictionsItems;
        }
    }

    public void replacePredictionsItems(List<PredictionsItem> predictionsItems) {
        if (this.predictionsItems != null) {
            this.predictionsItems.clear();
            this.predictionsItems.addAll(predictionsItems);
        } else {
            this.predictionsItems = predictionsItems;
        }
        notifyDataSetChanged();
    }

    public void clearPredictionsItems() {
        if (this.predictionsItems != null) {
            this.predictionsItems.clear();
        } else {
            this.predictionsItems = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlaceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return predictionsItems.size();
    }

    public void clearPlace() {
        this.predictionsItems = new ArrayList<>();
        notifyDataSetChanged();
    }

    class PlaceViewHolder extends BaseViewHolder {

        @BindView(R.id.tvPlaceTitle)
        TextView tvPlaceTitle;

        public PlaceViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            PredictionsItem predictionsItem = predictionsItems.get(position);

            if (predictionsItem != null) {
                if (!TextUtils.isEmpty(predictionsItem.getDescription())) {
                    tvPlaceTitle.setText(predictionsItem.getDescription());
                }
            }

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) onItemClickListener.onClick(position);
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<PredictionsItem> getPredictionsItems() {
        return predictionsItems;
    }
}
