package vn.chapp.vn24h.ui.addCode;

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

public class ShopLinkedAdapter extends BaseAdapter<Shop> {

    public ShopLinkedAdapter(List<Shop> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_linked,viewGroup,false));
    }

    class HistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.imgAvatarShop)
        ImageView imgAvatarShop;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtCode)
        TextView txtCode;
        @BindView(R.id.txtAddress)
        TextView txtAddress;

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Shop shop = getCollection().get(position);
            if(shop != null) {
                if (!TextUtils.isEmpty(shop.getAvatar()))
                    NetworkUtils.loadImage(itemView.getContext(),shop.getAvatar(),imgAvatarShop);
                else
                    imgAvatarShop.setImageResource(R.drawable.ic_shop_default);
                if (!TextUtils.isEmpty(shop.getCompanyName()))
                    txtName.setText(shop.getCompanyName());
                if (!TextUtils.isEmpty(shop.getCode()))
                    txtCode.setText(shop.getCode());
                if (!TextUtils.isEmpty(shop.getAddress()))
                    txtAddress.setText(shop.getAddress());
            }
        }
    }
}
