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
import vn.chapp.vn24h.models.service.Service;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ServicesAdapter extends BaseAdapter<Service> {

    private int type = 1;

    public ServicesAdapter(List<Service> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ServicesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_services,viewGroup,false));
    }

    class ServicesViewHolder extends BaseViewHolder {

        @BindView(R.id.imgServiceAvatar)
        ImageView imgServiceAvatar;
        @BindView(R.id.txtServiceName)
        TextView txtServiceName;
        @BindView(R.id.imgFavorite)
        ImageView imgFavorite;

        public ServicesViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            //Su ly du lieu tai day

            Service service = getCollection().get(position);
            if (service != null){
                if (!TextUtils.isEmpty(service.getImg()))
                    NetworkUtils.loadImage(itemView.getContext(), service.getImg(),imgServiceAvatar);
                if (!TextUtils.isEmpty(service.getName()))
                    txtServiceName.setText(service.getName());
                if (type==1) {
                    imgFavorite.setVisibility(View.VISIBLE);
                } else {
                    imgFavorite.setVisibility(View.GONE);
                }
            }

        }
    }

    public void setType (int type) {
        this.type = type;
    }

}
