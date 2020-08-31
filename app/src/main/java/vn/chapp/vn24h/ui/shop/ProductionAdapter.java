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
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;
import vn.chapp.vn24h.utils.AppConstants;

public class ProductionAdapter extends BaseAdapter<ProductResponse> {

    private OnClickItemShopDetail onClickItemShopDetail;

    public ProductionAdapter(List<ProductResponse> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case AppConstants.TYPE_PRODUCT:
                return new ProductionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_production,viewGroup,false));
            case AppConstants.TYPE_SERVICE:
                return new ServiceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_scheduled,viewGroup,false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        try {
            type = Integer.parseInt(getCollection().get(position).getType());
        } catch (NumberFormatException e) {

        } catch (NullPointerException n) {

        }
        return type;
    }

    class ProductionViewHolder extends BaseViewHolder {

        @BindView(R.id.txtOrder)
        TextView txtOrder;
        @BindView(R.id.ivProduct)
        ImageView ivProduct;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
      //  @BindView(R.id.tvPriceReal)
     //   TextView tvPriceReal;
        @BindView(R.id.tvPriceSale)
        TextView tvPriceSale;

        public ProductionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            txtOrder.setOnClickListener(v -> {
                if(onClickItemShopDetail!=null) onClickItemShopDetail.onClickOrder(position);
            });

            itemView.setOnClickListener( v -> {
                if(onClickItemShopDetail!=null) onClickItemShopDetail.onClickViewDetail(position, getCollection().get(position));
            });

            ProductResponse productResponse = getCollection().get(position);
            if (productResponse != null) {
                if (!TextUtils.isEmpty(productResponse.getName()))
                    tvTitle.setText(productResponse.getName());
                if (!TextUtils.isEmpty(productResponse.getPriceDiscount()))
                    tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPriceDiscount())));
              //  if (!TextUtils.isEmpty(productResponse.getPrice()))
                //    tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice())));
                if (!TextUtils.isEmpty(productResponse.getImg()))
                    NetworkUtils.loadImage(itemView.getContext(),productResponse.getImg(),ivProduct);
            }
        }
    }

    class ServiceViewHolder extends BaseViewHolder {

        @BindView(R.id.txtScheduled)
        TextView txtScheduled;
        @BindView(R.id.ivProduct)
        ImageView ivProduct;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
       // @BindView(R.id.tvPriceReal)
       // TextView tvPriceReal;
        @BindView(R.id.tvPriceSale)
        TextView tvPriceSale;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            txtScheduled.setOnClickListener(v -> {
                if(onClickItemShopDetail!=null) onClickItemShopDetail.onClickScheduled(position);
            });

            itemView.setOnClickListener( v -> {
                if(onClickItemShopDetail!=null) onClickItemShopDetail.onClickViewDetail(position, getCollection().get(position));
            });

            ProductResponse productResponse = getCollection().get(position);
            if (productResponse != null) {
                if (!TextUtils.isEmpty(productResponse.getName()))
                    tvTitle.setText(productResponse.getName());
                if (!TextUtils.isEmpty(productResponse.getPriceDiscount()))
                    tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPriceDiscount())));
//                if (!TextUtils.isEmpty(productResponse.getPrice()))
//                    tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice())));
                if (!TextUtils.isEmpty(productResponse.getImg()))
                    NetworkUtils.loadImage(itemView.getContext(),productResponse.getImg(),ivProduct);
            }
        }
    }

    public void setOnClickItemShopDetail(OnClickItemShopDetail onClickItemShopDetail) {
        this.onClickItemShopDetail = onClickItemShopDetail;
    }

}
