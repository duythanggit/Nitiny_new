package vn.chapp.vn24h.ui.shop;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.adapter.SectionedRecyclerViewAdapter;
import vn.chapp.vn24h.base.adapter.SectionedViewHolder;
import vn.chapp.vn24h.models.service.ServiceType;
import vn.chapp.vn24h.utils.AppConstants;

public class ShopDetailServiceAdapter extends SectionedRecyclerViewAdapter<SectionedViewHolder> {

    private OnClickItemShopDetail onClickItemShopDetail;

    private List<ServiceType> serviceTypes;

    public ShopDetailServiceAdapter(List<ServiceType> serviceTypes) {
        if (serviceTypes != null) {
            this.serviceTypes = serviceTypes;
        } else {
            this.serviceTypes = new ArrayList<>();
        }
    }

    public void replaceServices(List<ServiceType> serviceTypes) {
        this.serviceTypes.clear();
        this.serviceTypes.addAll(serviceTypes);
        notifyDataSetChanged();
    }

    @Override
    public int getSectionCount() {
        return serviceTypes.size();
    }

    @Override
    public int getItemCount(int section) {
        return 1;
    }

    @Override
    public void onBindHeaderViewHolder(SectionedViewHolder holder, int section, boolean expanded) {
        ((ShopDetailServiceHeaderViewHolder) holder).onBind(section);
    }

    @Override
    public void onBindFooterViewHolder(SectionedViewHolder holder, int section) {

    }

    @Override
    public void onBindViewHolder(SectionedViewHolder holder, int section, int relativePosition, int absolutePosition) {
        ((ShopDetailServiceViewHolder)holder).onBind(section,relativePosition);
    }

    @NonNull
    @Override
    public SectionedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case VIEW_TYPE_HEADER:
                return new ShopDetailServiceHeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_detail_service_header,viewGroup,false));
            case VIEW_TYPE_ITEM:
                return new ShopDetailServiceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_detail_service,viewGroup,false));
            default:
                return null;
        }
    }

    public void setOnClickItemShopDetail(OnClickItemShopDetail onClickItemShopDetail) {
        this.onClickItemShopDetail = onClickItemShopDetail;
    }

    class ShopDetailServiceHeaderViewHolder extends SectionedViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public ShopDetailServiceHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int section) {

            ServiceType serviceType = AppConstants.SERVICE_TYPES.get(section);
            tvTitle.setText(serviceType.getTitle());
        }

    }

    class ShopDetailServiceViewHolder extends SectionedViewHolder {

        @BindView(R.id.rcProduction)
        RecyclerView rcProduction;

        private LinearLayoutManager linearLayoutManager;
        private ProductionAdapter productionAdapter;

        public ShopDetailServiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            productionAdapter = new ProductionAdapter(null);
            linearLayoutManager = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false);
        }

        void onBind(int section, int position){
            rcProduction.setHasFixedSize(true);
            rcProduction.setLayoutManager(linearLayoutManager);
            productionAdapter.setOnClickItemShopDetail(onClickItemShopDetail);
            rcProduction.setAdapter(productionAdapter);
            productionAdapter.replaceData(serviceTypes.get(section).getProducts());
        }
    }
}
