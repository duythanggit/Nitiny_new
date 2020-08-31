package vn.chapp.vn24h.ui.shop;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.utils.AppUtils;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ListProductAdapter extends BaseAdapter<ProductResponse> {

    private int typePosition = -1;
    private OnOrderClickListener onOrderClickListener;

    public static final int TYPE_PRODUCT = 1;
    public static final int TYPE_SERVICE = 2;

    public ListProductAdapter(List<ProductResponse> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case TYPE_PRODUCT:
                return new ListProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_update, viewGroup, false));
            case TYPE_SERVICE:
                return new ListProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service_update, viewGroup, false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getCollection().get(position).getType().equals("1") ? TYPE_PRODUCT : TYPE_SERVICE;
    }

    class ListProductViewHolder extends BaseViewHolder {

        @BindView(R.id.ivProduct)
        ImageView ivProduct;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDesc)
        TextView tvDesc;
        //  @BindView(R.id.tvPriceReal)
        //  TextView tvPriceReal;
        @BindView(R.id.tvPriceSale)
        TextView tvPriceSale;
        @BindView(R.id.txtScheduled)
        TextView txtScheduled;

        public ListProductViewHolder(View itemView) {
            super(itemView);

            //    tvPriceReal.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            ProductResponse productResponse = getCollection().get(position);
            if (productResponse != null) {
                if (!TextUtils.isEmpty(productResponse.getName()))
                    tvTitle.setText(productResponse.getName());

                // sản phẩm mới filter theo giá đại lý
                if (productResponse.getType().equals("2")) {
                    if (!TextUtils.isEmpty(productResponse.getPriceDiscount()))
                        tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPriceDiscount())));

                    //  if (!TextUtils.isEmpty(productResponse.getPrice()))
                    //  tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice())));
                } else {
                    if (productResponse.getTypeUser() == null) {
                        //  tvPriceReal.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(productResponse.getPriceDiscount()))
                            tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPriceDiscount())));
                        if (!TextUtils.isEmpty(productResponse.getPrice())) {
                            // tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice())));
                        }
                    } else if (productResponse.getTypeUser().equals("3")) {
                        // tvPriceReal.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(productResponse.getPriceDiscount()))
                            tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPriceDiscount())));
                        if (!TextUtils.isEmpty(productResponse.getPrice())) {
                            //    tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice())));
                        }
                    } else {
                        // tvPriceReal.setVisibility(View.INVISIBLE);
                        switch (productResponse.getTypeUser()) {
                            case "1":
                                if (!TextUtils.isEmpty(productResponse.getPrice1())) {
                                    tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice1())));
                                }
                                break;
                            case "2":
                                if (!TextUtils.isEmpty(productResponse.getPrice2())) {
                                    tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice2())));
                                }
                                break;
                            case "4":
                                if (!TextUtils.isEmpty(productResponse.getPrice3())) {
                                    tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice3())));
                                }
                                break;
                        }
                    }
                }

                if (!TextUtils.isEmpty(productResponse.getImg()))
                    NetworkUtils.loadImage(itemView.getContext(), productResponse.getImg(), ivProduct);

                if (typePosition == 0) {
                    if (!TextUtils.isEmpty(productResponse.getNumber()))
                        tvDesc.setText("Số lượng: " + productResponse.getNumber());
                } else {
                    if (!TextUtils.isEmpty(productResponse.getNote()))
                        tvDesc.setText(AppUtils.loadHtml(productResponse.getNote()));
                }

                txtScheduled.setText(typePosition == 0 ? "Đặt mua" : "Đặt quà");
//                if (typePosition==0) txtScheduled.setVisibility(View.INVISIBLE); else txtScheduled.setVisibility(View.VISIBLE);
                if (typePosition == 0) txtScheduled.setVisibility(View.GONE);
                else txtScheduled.setVisibility(View.VISIBLE);

                ImageView ivAddProductToCart = itemView.findViewById(R.id.ivAddProductToCart);
                if (ivAddProductToCart != null) {
                    ivAddProductToCart.setOnClickListener(v -> {
                        if (onOrderClickListener != null)
                            onOrderClickListener.onAddProductToCart(getCollection().get(position));
                    });
                }
            }
        }

        @OnClick(R.id.txtScheduled)
        public void onOrderClick(View v) {
            if (onOrderClickListener != null)
                onOrderClickListener.onOrderClick(getCurrentPosition());
        }

    }

    public void setTypePosition(int typePosition) {
        this.typePosition = typePosition;
    }

    public interface OnOrderClickListener {
        void onOrderClick(int position);

        void onAddProductToCart(ProductResponse productResponse);
    }

    public void setOnOrderClickListener(OnOrderClickListener onOrderClickListener) {
        this.onOrderClickListener = onOrderClickListener;
    }
}
