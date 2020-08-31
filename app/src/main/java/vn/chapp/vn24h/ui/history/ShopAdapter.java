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
import vn.chapp.vn24h.models.service.Shop;

public class ShopAdapter extends BaseAdapter {

    private List<Shop> listShopLinked;
    private LayoutInflater inflate;


    public ShopAdapter(List<Shop> listShopLinked) {
        if (listShopLinked != null) {
            this.listShopLinked = listShopLinked;
        } else {
            this.listShopLinked = new ArrayList<>();
        }
        inflate = (LayoutInflater.from(MainApp.newInstance().getApplicationContext()));
    }

    @Override
    public int getCount() {
        return this.listShopLinked.size();
    }

    @Override
    public Object getItem(int position) {
        return listShopLinked.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflate.inflate(R.layout.item_spinner_service, null);
        TextView tvName = convertView.findViewById(R.id.tvService);
        if (!TextUtils.isEmpty(listShopLinked.get(position).getName()))
            tvName.setText(listShopLinked.get(position).getName());
        return convertView;
    }
}
