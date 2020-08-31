package vn.chapp.vn24h.ui.tichDiem;

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
import vn.chapp.vn24h.models.point.PointResponse;
import vn.chapp.vn24h.utils.NetworkUtils;

public class TichDiemAdapter extends BaseAdapter<PointResponse> {
    public TichDiemAdapter(List<PointResponse> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tich_diem, viewGroup, false));

    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.imgShopAvatar)
        ImageView imgShopAvatar;
        @BindView(R.id.txtShopName)
        TextView txtShopName;
        @BindView(R.id.txtRole)
        TextView txtRole;
        @BindView(R.id.txtType)
        TextView txtType;
        @BindView(R.id.txtPoint)
        TextView txtPoint;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            PointResponse item = getCollection().get(position);

            if (!TextUtils.isEmpty(item.getImg()))
                NetworkUtils.loadImage(itemView.getContext(), item.getImg(), imgShopAvatar);

            if (!TextUtils.isEmpty(item.getShopName()))
                txtShopName.setText(item.getShopName());

            txtRole.setText("Khách hàng");
            txtType.setText("Vip");

            if (!TextUtils.isEmpty(item.getPoint())) {
                txtPoint.setText(String.format("%s điểm", item.getPoint()));
            }
        }
    }
}
