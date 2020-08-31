package vn.chapp.vn24h.ui.customer;

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
import vn.chapp.vn24h.utils.NetworkUtils;

public class CustomerLinkAdapter extends BaseAdapter<Shop> {

    public CustomerLinkAdapter(List<Shop> collection) {
        super(collection);
    }

    private OnCustomerItemClickListener onCustomerItemClickListener;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer_linked,viewGroup,false));
    }

    class HistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.civAvatar)
        ImageView civAvatar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvTypeCustomer)
        TextView tvTypeCustomer;
        @BindView(R.id.tvPoint)
        TextView tvPoint;

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Shop shop = getCollection().get(position);
            if(shop != null) {
                if (!TextUtils.isEmpty(shop.getAvatar()))
                    NetworkUtils.loadImage(itemView.getContext(),shop.getAvatar(),civAvatar);
                else
                    civAvatar.setImageResource(R.drawable.ic_shop_default);
                if (!TextUtils.isEmpty(shop.getCompanyName()))
                    tvName.setText(shop.getCompanyName());

//                if (!TextUtils.isEmpty(shop.getCode()))
//                    tvType.setText(shop.getCode());
//                if (!TextUtils.isEmpty(shop.getAddress()))
//                    tvPoint.setText(shop.getAddress());

                tvType.setText("Khách hàng");
                tvTypeCustomer.setText("Khách hàng");
                tvPoint.setText("Chưa có điểm");

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCustomerItemClickListener != null) onCustomerItemClickListener.onItemClick(getCurrentPosition());
                    }
                });

            }
        }
    }

    public interface OnCustomerItemClickListener {
        void onItemClick(int position);
    }

    public void setOnCustomerItemClickListener(OnCustomerItemClickListener onCustomerItemClickListener) {
        this.onCustomerItemClickListener = onCustomerItemClickListener;
    }

}