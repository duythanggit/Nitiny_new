package vn.chapp.vn24h.ui.history;

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
import vn.chapp.vn24h.models.service.HistoryResponse;
import vn.chapp.vn24h.utils.NetworkUtils;

public class HistoryAdapter extends BaseAdapter<HistoryResponse> {

    private OnClickHistoryListener onClickHistoryListener;

    public HistoryAdapter(List<HistoryResponse> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false));
    }


    class HistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.civAvatar)
        ImageView civAvatar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.imgCall)
        ImageView imgCall;
        @BindView(R.id.imgChat)
        ImageView imgChat;
        @BindView(R.id.imgLocation)
        ImageView imgLocation;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.tvTime)
        TextView tvTime;

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            HistoryResponse historyResponse = getCollection().get(position);
            if (!TextUtils.isEmpty(historyResponse.getShopAvatar()))
                NetworkUtils.loadImage(itemView.getContext(),historyResponse.getShopAvatar(),civAvatar);

            if (!TextUtils.isEmpty(historyResponse.getShopName()))
                tvName.setText(historyResponse.getShopName());
            else tvName.setText("Chưa có tên cụ thể");

            if (!TextUtils.isEmpty(historyResponse.getAddress()))
                tvAddress.setText(historyResponse.getAddress());
            else tvAddress.setText("Chưa có địa chỉ cụ thể");

            String time = "";
//            if (!TextUtils.isEmpty(booking.getTimeBooking()))
//                time += booking.getTimeBooking() + ", ";
            if (!TextUtils.isEmpty(historyResponse.getDateBooking()))
                time += historyResponse.getDateBooking();
            tvTime.setText(time);

            imgCall.setOnClickListener(v -> {
                if (onClickHistoryListener != null) onClickHistoryListener.onClickCall(position, historyResponse.getPhone());
            });
            imgChat.setOnClickListener(v -> {
                if (onClickHistoryListener != null) onClickHistoryListener.onClickChat(position, historyResponse.getShopId()+"", historyResponse.getShopName(), historyResponse.getShopAvatar(), historyResponse.getPhone());
            });
            imgLocation.setOnClickListener(v -> {
                if (onClickHistoryListener != null) onClickHistoryListener.onClickLocation(position);
            });

            String status = "";
            int colorResource = itemView.getContext().getResources().getColor(R.color.colorText);
            switch (historyResponse.getActive()) {
                case "1":
                    status = "Đã đặt";
                    colorResource = itemView.getContext().getResources().getColor(R.color.colorTextGreen);
                    break;
                case "2":
                    status = "Đã xác nhận";
                    colorResource = itemView.getContext().getResources().getColor(R.color.colorTextGreen);
                    break;
                case "3":
                case "4":
                    status = "Đã huỷ";
                    colorResource = itemView.getContext().getResources().getColor(R.color.colorText);
                    break;
                case "5":
                    status = "Hoàn thành";
                    colorResource = itemView.getContext().getResources().getColor(R.color.color1884EF);
                    break;
            }
            tvStatus.setText(status);
            tvStatus.setTextColor(colorResource);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickHistoryListener!=null) onClickHistoryListener.onClickViewDetail(position, historyResponse);
                }
            });

        }
    }

    public interface OnClickHistoryListener {
        void onClickViewDetail(int position, HistoryResponse historyResponse);

        void onClickCall(int position, String phone);

        void onClickChat(int position, String id, String name, String avatar, String phone);

        void onClickLocation(int position);
    }

    public void setOnClickHistoryListener(OnClickHistoryListener onClickHistoryListener) {
        this.onClickHistoryListener = onClickHistoryListener;
    }
}
