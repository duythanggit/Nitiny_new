package vn.chapp.vn24h.ui.detailProduct;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.sale.ProductSchedule;

public class DetailProductAdapter extends BaseAdapter<ProductSchedule> {

    public DetailProductAdapter(List<ProductSchedule> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_confirm_schedule,viewGroup,false));
    }


    class HistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtCount)
        TextView txtCount;
        @BindView(R.id.txtPrice)
        TextView txtPrice;

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            txtName.setText(" "+getCollection().get(position).getProductName());
            txtCount.setText(" "+getCollection().get(position).getNumber()+"");
            txtPrice.setText(" "+getCollection().get(position).getPrice()+"");
        }
    }
}
