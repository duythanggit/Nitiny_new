package vn.chapp.vn24h.ui.services;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.chapp.vn24h.R;
import vn.chapp.vn24h.models.service.Service;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ServiceSpinnerAdapter extends BaseAdapter {

    private List<Service> services;
    private LayoutInflater inflater;

    public ServiceSpinnerAdapter(Context context, List<Service> services) {
        this.services = services;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return services.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_service_spinner,parent,false);
        ImageView ivService = view.findViewById(R.id.ivAvatarService);
        TextView tvService  = view.findViewById(R.id.tvServiceName);
        if (position == 0) {
            ivService.setVisibility(View.GONE);
            tvService.setText("Chọn dịch vụ");
        } else {
            if (services.get(position).getImg() != null)
                NetworkUtils.loadImage(parent.getContext(),services.get(position).getImg(),ivService);
            if (!TextUtils.isEmpty(services.get(position).getName()))
                tvService.setText(services.get(position).getName());
        }
        return view;
    }
}
