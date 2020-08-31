package vn.chapp.vn24h.ui.history;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.models.service.ServiceResponse;

public class ServiceAdapter extends BaseAdapter {

    private List<ServiceResponse> serviceResponses;
    private LayoutInflater inflate;


    public ServiceAdapter(List<ServiceResponse> serviceResponses) {
        if (serviceResponses != null) {
            this.serviceResponses = serviceResponses;
        } else {
            this.serviceResponses = new ArrayList<>();
        }
        inflate = (LayoutInflater.from(MainApp.newInstance().getApplicationContext()));
    }

    @Override
    public int getCount() {
        return this.serviceResponses.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceResponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflate.inflate(R.layout.item_spinner_service, null);
        TextView tvName = convertView.findViewById(R.id.tvService);
        if (!TextUtils.isEmpty(serviceResponses.get(position).getName()))
            tvName.setText(serviceResponses.get(position).getName());
        return convertView;
    }
}
