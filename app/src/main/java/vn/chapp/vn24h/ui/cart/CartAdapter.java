package vn.chapp.vn24h.ui.cart;

import android.support.annotation.NonNull;
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
import vn.chapp.vn24h.models.cart.CartProduct;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class CartAdapter extends BaseAdapter<CartProduct> {

    public CartAdapter(List<CartProduct> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CartViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart,viewGroup,false));
    }

    class CartViewHolder extends BaseViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvProductName)
        TextView tvProductName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvPriceDiscount)
        TextView tvPriceDiscount;
        @BindView(R.id.tvQuantity)
        TextView tvQuantity;

        public CartViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {

            CartProduct cartProduct = getCollection().get(position);

            if (!TextUtils.isEmpty(cartProduct.getImg()))
                NetworkUtils.loadImage(itemView.getContext(), cartProduct.getImg(), ivImage);

            if (!TextUtils.isEmpty(cartProduct.getProductName()))
                tvProductName.setText(cartProduct.getProductName());

//            if (!TextUtils.isEmpty(cartProduct.getPrice()))
//                tvPrice.setText(String.format("%sđ", CommonUtils.parseMoney(cartProduct.getPrice())));
//
//            if (!TextUtils.isEmpty(cartProduct.getPriceDiscount()))
//                tvPriceDiscount.setText(String.format("%sđ", CommonUtils.parseMoney(cartProduct.getPriceDiscount())));

            if (getCollection().get(position).getTypeUser()==null) {
                tvPriceDiscount.setVisibility(View.VISIBLE);
            } else if (getCollection().get(position).getTypeUser().equals("3")) {
                tvPriceDiscount.setVisibility(View.VISIBLE);
            } else {
                tvPriceDiscount.setVisibility(View.GONE);
            }
            String price = getPrice(getCollection().get(position));
            String realPrice = getRealPrice(getCollection().get(position));

            if (!TextUtils.isEmpty(price))
                tvPriceDiscount.setText(String.format("%s đ", CommonUtils.parseMoney(price)));
            else tvPriceDiscount.setText("Chưa rõ");

            if (!TextUtils.isEmpty(realPrice))
                tvPrice.setText(String.format("%s đ", CommonUtils.parseMoney(realPrice)));
            else tvPrice.setText("Chưa rõ");

            if (!TextUtils.isEmpty(cartProduct.getNumber()))
                tvQuantity.setText(cartProduct.getNumber());

        }

        private String getPrice(CartProduct cartProduct) {
            if (cartProduct.getTypeUser()==null) return cartProduct.getPriceDiscount();

            String price = "0";
            switch (cartProduct.getTypeUser()) {
                case "1":
                    price = cartProduct.getPrice1();
                    break;
                case "2":
                    price = cartProduct.getPrice2();
                    break;
                case "3":
                    price = cartProduct.getPriceDiscount();
                    break;
                case "4":
                    price = cartProduct.getPrice3();
                    break;
                default:
                    price = cartProduct.getPriceDiscount();
                    break;
            }
            return price;
        }

        private String getRealPrice(CartProduct cartProduct) {
            if (cartProduct.getTypeUser()==null) return cartProduct.getPrice();

            String price = "0";
            switch (cartProduct.getTypeUser()) {
                case "1":
                    price = cartProduct.getPrice1();
                    break;
                case "2":
                    price = cartProduct.getPrice2();
                    break;
                case "3":
                    price = cartProduct.getPrice();
                    break;
                case "4":
                    price = cartProduct.getPrice3();
                    break;
                default:
                    price = cartProduct.getPrice();
                    break;
            }
            return price;
        }
    }

}
