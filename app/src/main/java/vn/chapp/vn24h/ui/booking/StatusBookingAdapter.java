package vn.chapp.vn24h.ui.booking;

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
import vn.chapp.vn24h.models.booking.BookingStatus;

public class StatusBookingAdapter extends BaseAdapter {

    private List<BookingStatus> bookingStatusList;
    private LayoutInflater inflate;


    public StatusBookingAdapter(List<BookingStatus> listShopLinked) {
        if (listShopLinked != null) {
            this.bookingStatusList = listShopLinked;
        } else {
            this.bookingStatusList = new ArrayList<>();
        }
        inflate = (LayoutInflater.from(MainApp.newInstance().getApplicationContext()));
    }

    @Override
    public int getCount() {
        return this.bookingStatusList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingStatusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflate.inflate(R.layout.item_spinner_service, null);
        TextView tvName = convertView.findViewById(R.id.tvService);
        if (!TextUtils.isEmpty(bookingStatusList.get(position).getStatus()))
            tvName.setText(bookingStatusList.get(position).getStatus());
        return convertView;
    }
}
