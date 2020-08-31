package vn.chapp.vn24h.ui.detailBooking;

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

public class DetailBookingAdapter extends BaseAdapter<ProductSchedule> {

    public DetailBookingAdapter(List<ProductSchedule> collection) {
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

            String price = this.getPrice(getCollection().get(position));
            String realPrice = this.getRealPrice(getCollection().get(position));

            if (!TextUtils.isEmpty(price))
                txtPriceDiscount.setText(String.format("%s đ", CommonUtils.parseMoney(price)));
            else txtPriceDiscount.setText("Chưa rõ");

            if (!TextUtils.isEmpty(realPrice))
                txtPrice.setText(String.format("%s đ", CommonUtils.parseMoney(realPrice)));
            else txtPrice.setText("Chưa rõ");

            txtCount.setText(String.format("- SL: %s", getCollection().get(position).getNumber()));
        }

        private String getPrice(ProductSchedule productSchedule) {
            if (productSchedule.getTypeUser() == null) return productSchedule.getPriceDiscount();

            String price = "0";
            switch (productSchedule.getTypeUser()) {
                case "1":
                    price = productSchedule.getPrice1();
                    break;
                case "2":
                    price = productSchedule.getPrice2();
                    break;
                case "4":
                    price = productSchedule.getPrice3();
                    break;
                default:
                    price = productSchedule.getPriceDiscount();
                    break;

            }
            return price;
        }

        private String getRealPrice(ProductSchedule productSchedule) {
            if (productSchedule.getTypeUser() == null) return productSchedule.getPrice()+"";

            String price = "0";
            switch (productSchedule.getTypeUser()) {
                case "1":
                    price = productSchedule.getPrice1();
                    break;
                case "2":
                    price = productSchedule.getPrice2();
                    break;
                case "4":
                    price = productSchedule.getPrice3();
                    break;
                default:
                    price = productSchedule.getPrice()+"";
                    break;
            }
            return price;
        }

    }
}
