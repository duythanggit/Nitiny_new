package vn.chapp.vn24h.ui.scheduled;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.sale.ProductSchedule;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ConfirmAdapter extends BaseAdapter<ProductSchedule> {

    public ConfirmAdapter(List<ProductSchedule> collection) {
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
        @BindView(R.id.imgProduct)
        ImageView imgProduct;
        @BindView(R.id.txtPriceDiscount)
        TextView txtPriceDiscount;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            txtPrice.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (!TextUtils.isEmpty(getCollection().get(position).getProductName())) {
                txtName.setText(getCollection().get(position).getProductName());
            }
            if (!TextUtils.isEmpty(getCollection().get(position).getImg()))
                NetworkUtils.loadImage(itemView.getContext(),getCollection().get(position).getImg(),imgProduct);
            else imgProduct.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.avb_logo));

            if (!TextUtils.isEmpty(getCollection().get(position).getPriceDiscount()))
                txtPriceDiscount.setText(String.format("%s đ", CommonUtils.parseMoney(getCollection().get(position).getPriceDiscount())));
            else txtPriceDiscount.setText("Chưa rõ");

            if (!TextUtils.isEmpty(String.valueOf(getCollection().get(position).getPrice())))
                txtPrice.setText(String.format("%s đ", CommonUtils.parseMoney(getCollection().get(position).getPrice())));
            else txtPrice.setText("Chưa rõ");

            txtCount.setText(String.format("- SL: %s", getCollection().get(position).getNumber()));
        }
    }
}
