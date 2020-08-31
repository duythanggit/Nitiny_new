package vn.chapp.vn24h.ui.services;

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
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class SearchShopAdapter extends BaseAdapter<Shop> {

    private OnClickSearchShop onClickSearchShop;

    public SearchShopAdapter(List<Shop> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SearchShopViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_shop, viewGroup, false));
    }

    class SearchShopViewHolder extends BaseViewHolder {

        @BindView(R.id.ivAvatarShop)
        ImageView ivAvatarShop;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvCode)
        TextView tvCode;
        @BindView(R.id.tvDistance)
        TextView tvDistance;
        @BindView(R.id.tvAddr)
        TextView tvAddr;
        @BindView(R.id.tvView)
        TextView tvView;
        @BindView(R.id.tvLink)
        TextView tvLink;

        public SearchShopViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Shop shop = getCollection().get(position);
            NetworkUtils.loadImage(itemView.getContext(),shop.getAvatar(),ivAvatarShop,R.drawable.ic_logo_mini,R.drawable.ic_logo_mini);
            if (!TextUtils.isEmpty(shop.getCompanyName()))
                tvName.setText(shop.getCompanyName());
            if (!TextUtils.isEmpty(shop.getCode()))
                tvCode.setText(String.format("(Mã: %s)", shop.getCode()));
            if (!TextUtils.isEmpty(shop.getAddress())) {
                tvAddr.setText(shop.getAddress());
                tvAddr.setSelected(true);
            }
            if (shop.getDistance() != 0) {
                tvDistance.setText(String.format("%skm", CommonUtils.round(shop.getDistance(),2)));
            }

            if(shop.getIsConnect()==1) {
                tvLink.setText("Đã liên kết");
            } else {
                tvLink.setText("Liên kết");
                tvLink.setOnClickListener(v -> {
                    if(onClickSearchShop!=null) onClickSearchShop.onClickAddShop(shop.getCode(), shop.getServiceId(), shop.getId());
                });
            }

            tvView.setOnClickListener(v -> {
                if(onClickSearchShop!=null) onClickSearchShop.onClickView(shop.getServiceId(), shop.getId(), shop);
            });

        }
    }

    public void setOnClickSearchShop(OnClickSearchShop onClickSearchShop) {
        this.onClickSearchShop = onClickSearchShop;
    }

    interface OnClickSearchShop {
        void onClickView(int serviceId, int shopId, Shop shop);
        void onClickAddShop(String code, int serviceId, int shopId);
    }
}
