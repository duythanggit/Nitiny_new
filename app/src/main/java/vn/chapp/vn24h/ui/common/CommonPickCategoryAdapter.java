package vn.chapp.vn24h.ui.common;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.service.CategoryFilter;

public class CommonPickCategoryAdapter extends BaseAdapter<CategoryFilter> {

    private OnPickCategoryListener onPickCategoryListener;
    public static final int TYPE_UN_PICK = 1;
    public static final int TYPE_PICK_ACTIVE = 2;

    public CommonPickCategoryAdapter(List<CategoryFilter> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_UN_PICK:
                return new UnPickedViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choose_category,viewGroup,false));
            case TYPE_PICK_ACTIVE:
                return new PickActiveViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choose_category_active,viewGroup,false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getCollection().get(position).isCheck() ? TYPE_PICK_ACTIVE : TYPE_UN_PICK;
    }

    class UnPickedViewHolder extends BaseViewHolder {
        @BindView(R.id.txtName)
        TextView txtName;

        public UnPickedViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (!TextUtils.isEmpty(getCollection().get(position).getName()))
                txtName.setText(getCollection().get(position).getName());

            itemView.setOnClickListener(v -> {
                if (onPickCategoryListener != null) onPickCategoryListener.onPickCategory(position, getCollection().get(position));
            });
        }
    }

    class PickActiveViewHolder extends BaseViewHolder {
        @BindView(R.id.txtName)
        TextView txtName;

        public PickActiveViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (!TextUtils.isEmpty(getCollection().get(position).getName()))
                txtName.setText(getCollection().get(position).getName());

            itemView.setOnClickListener(v -> {
                if (onPickCategoryListener != null) onPickCategoryListener.onPickCategory(position, getCollection().get(position));
            });
        }
    }

    public interface OnPickCategoryListener {
        void onPickCategory(int position, CategoryFilter categoryFilter);
    }

    public void setOnPickCategoryListener(OnPickCategoryListener onPickCategoryListener) {
        this.onPickCategoryListener = onPickCategoryListener;
    }
}