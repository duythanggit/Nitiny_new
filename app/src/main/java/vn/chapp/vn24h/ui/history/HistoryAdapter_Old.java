package vn.chapp.vn24h.ui.history;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.adapter.SectionedRecyclerViewAdapter;
import vn.chapp.vn24h.base.adapter.SectionedViewHolder;
import vn.chapp.vn24h.models.service.HistoryResponse;
import vn.chapp.vn24h.models.service.HistoryWrapper;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.utils.NetworkUtils;

public class HistoryAdapter_Old extends SectionedRecyclerViewAdapter<SectionedViewHolder> {

    private List<HistoryWrapper> historyWrappers;

    public HistoryAdapter_Old(List<HistoryWrapper> historyWrappers) {
        if (historyWrappers != null) {
            this.historyWrappers = historyWrappers;
        } else {
            this.historyWrappers = new ArrayList<>();
        }
    }

    public void notifyShopLinked(List<Shop> shops) {
        historyWrappers.get(0).setShops(shops);
        notifySectionChanged(0);
    }

    public void notifyHistory(List<HistoryResponse> historyResponses) {
        historyWrappers.get(1).setHistoryResponses(historyResponses);
        notifySectionChanged(1);
    }

    public void replaceData(List<HistoryWrapper> historyWrappers) {
        this.historyWrappers = historyWrappers;
        notifyDataSetChanged();
    }

    @Override
    public int getSectionCount() {
        return historyWrappers.size();
    }

    @Override
    public int getHeaderViewType(int section) {
        return historyWrappers.get(section).getType();
    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        return historyWrappers.get(section).getType() == 1 ? 3 : 4;
    }

    @Override
    public int getItemCount(int section) {
        switch (historyWrappers.get(section).getType()) {
            case 1:
                return historyWrappers.get(section).getShops().size();
            case 2:
                return historyWrappers.get(section).getHistoryResponses().size();
            default:
                return 0;
        }
    }

    @Override
    public void onBindHeaderViewHolder(SectionedViewHolder holder, int section, boolean expanded) {

    }

    @Override
    public void onBindFooterViewHolder(SectionedViewHolder holder, int section) {

    }

    @Override
    public void onBindViewHolder(SectionedViewHolder holder, int section, int relativePosition, int absolutePosition) {
        switch (getItemViewType(section, relativePosition, absolutePosition)) {
            case 3:
                ((ItemShopViewHolder) holder).onBind(relativePosition);
                break;
            case 4:
                ((ItemHistoryViewHolder) holder).onBind(relativePosition);
                break;
        }
    }

    @NonNull
    @Override
    public SectionedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 1:
                return new HeaderShopViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header_shop_linked, viewGroup, false));
            case 2:
                return new HeaderHistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header_history, viewGroup, false));
            case 3:
                return new ItemShopViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_linked, viewGroup, false));
            case 4:
                return new ItemHistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false));
            default:
                return null;
        }
    }

    class HeaderShopViewHolder extends SectionedViewHolder {

        public HeaderShopViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderHistoryViewHolder extends SectionedViewHolder {

        public HeaderHistoryViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ItemShopViewHolder extends SectionedViewHolder {

        @BindView(R.id.imgAvatarShop)
        ImageView imgAvatarShop;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtCode)
        TextView txtCode;
        @BindView(R.id.txtAddress)
        TextView txtAddress;

        public ItemShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {
            Shop shop = historyWrappers.get(0).getShops().get(position);
            if (!TextUtils.isEmpty(shop.getAvatar()))
                NetworkUtils.loadImage(itemView.getContext(), shop.getAvatar(), imgAvatarShop);
            else
                imgAvatarShop.setImageResource(R.drawable.ic_shop_default);
            if (!TextUtils.isEmpty(shop.getName()))
                txtName.setText(shop.getName());
            if (!TextUtils.isEmpty(shop.getCode()))
                txtCode.setText(String.format("(Mã:%s)", shop.getCode()));
            if (!TextUtils.isEmpty(shop.getAddress()))
                txtAddress.setText(shop.getAddress());
        }

    }

    class ItemHistoryViewHolder extends SectionedViewHolder {

        @BindView(R.id.civAvatar)
        CircularImageView civAvatar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvStatus)
        TextView tvStatus;


        public ItemHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {
            HistoryResponse historyResponse = historyWrappers.get(1).getHistoryResponses().get(position);
            if (!TextUtils.isEmpty(historyResponse.getShopAvatar()))
                NetworkUtils.loadImage(itemView.getContext(),historyResponse.getShopAvatar(),civAvatar);
            else
                civAvatar.setImageResource(R.drawable.ic_avatar_default);
            if (!TextUtils.isEmpty(historyResponse.getProductName()))
                tvName.setText(historyResponse.getProductName());
            if (!TextUtils.isEmpty(historyResponse.getAddress()))
                tvAddress.setText(historyResponse.getAddress());

            String status = "";
            switch (historyResponse.getActive()) {
                case "1":
                    status = "Đã đặt";
                    break;
                case "2":
                    status = "Đã xác nhận";
                    break;
                case "3":
                    status = "Đã huỷ";
                    break;
                case "4":
                    status = "Đã huỷ";
                    break;
                case "5":
                    status = "Hoàn thành";
                    break;
            }
            tvStatus.setText(status);

        }
    }
}
