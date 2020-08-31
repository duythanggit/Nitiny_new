package vn.chapp.vn24h.ui.booking;

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
import vn.chapp.vn24h.models.booking.Booking;
import vn.chapp.vn24h.utils.NetworkUtils;

public class BookingAdapter extends BaseAdapter<Booking> {
    private OnActiveActionClickListener onActiveActionClickListener;

    public BookingAdapter(List<Booking> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BookingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_booking, viewGroup, false));
    }

    void updateStatus(int position, int active){
        getCollection().get(position).setActive(active);
        notifyItemChanged(position);

    }

    class BookingViewHolder extends BaseViewHolder {

        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.imgCall)
        ImageView imgCall;
        @BindView(R.id.imgChat)
        ImageView imgChat;
        @BindView(R.id.imgLocation)
        ImageView imgLocation;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.txtViewDetail)
        TextView txtViewDetail;

        public BookingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Booking booking = getCollection().get(position);
            if (!TextUtils.isEmpty(booking.getShopAvatar()))
                NetworkUtils.loadImage(itemView.getContext(), booking.getShopAvatar(), imgAvatar);

            if (!TextUtils.isEmpty(booking.getShopName()))
                tvName.setText(booking.getShopName());
            else tvName.setText("Chưa có tên cụ thể");

            if (!TextUtils.isEmpty(booking.getAddress()))
                tvAddress.setText(booking.getAddress());
            else tvAddress.setText("Chưa có địa chỉ cụ thể");

            String time = "";
//            if (!TextUtils.isEmpty(booking.getTimeBooking()))
//                time += booking.getTimeBooking() + ", ";
            if (!TextUtils.isEmpty(booking.getDateBooking()))
                time += booking.getDateBooking();
            tvTime.setText(time);

//            tvStatus.setVisibility(String.valueOf(booking.getActive()).equals("1") ? View.GONE : View.VISIBLE);
            String status = "";
            int colorResource = itemView.getContext().getResources().getColor(R.color.colorText);
            switch (booking.getActive()) {
                case 1:
                    status = "Đã đặt";
                    colorResource = itemView.getContext().getResources().getColor(R.color.colorTextGreen);
                    break;
                case 2:
                    status = "Đã xác nhận";
                    colorResource = itemView.getContext().getResources().getColor(R.color.colorTextGreen);
                    break;
                case 3:
                    status = "Shop đã huỷ";
                    colorResource = itemView.getContext().getResources().getColor(R.color.colorText);
                    break;
                case 4:
                    status = "Đã huỷ";
                    colorResource = itemView.getContext().getResources().getColor(R.color.colorText);
                    break;
                case 5:
                    status = "Hoàn thành";
                    colorResource = itemView.getContext().getResources().getColor(R.color.color1884EF);
                    break;
            }
            tvStatus.setText(status);
            tvStatus.setTextColor(colorResource);

            itemView.setOnClickListener(new View.OnClickListener() { //xem chi tiet
                @Override
                public void onClick(View v) {
                    if (onActiveActionClickListener != null) onActiveActionClickListener.onClickViewDetail(getCurrentPosition(), getCollection().get(getCurrentPosition()).getId());
                }
            });
        }

    }

    public interface OnActiveActionClickListener {
        void onUpdateStatusClick(int position, int active);
        void onClickViewDetail(int position, int bookingId);
        void onClickCall(int position, String phone);
        void onClickChat(int position, String id, String name, String avatar, String phone);
        void onClickLocation(int position);
    }

    public void setOnActiveActionClickListener(OnActiveActionClickListener onActiveActionClickListener) {
        this.onActiveActionClickListener = onActiveActionClickListener;
    }
}
