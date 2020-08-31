package vn.chapp.vn24h.ui.accumulatepoint;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.point.PointDetailResponse;
import vn.chapp.vn24h.utils.CommonUtils;

public class PointHistoryAdapter extends BaseAdapter<PointDetailResponse> {
    public PointHistoryAdapter(List<PointDetailResponse> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_point, viewGroup, false));

    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtDateTime)
        TextView txtDateTime;
        @BindView(R.id.txtPoint)
        TextView txtPoint;
        @BindView(R.id.txtStatus)
        TextView txtStatus;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            PointDetailResponse item = getCollection().get(position);

            if (!TextUtils.isEmpty(item.getContent()))
                txtTitle.setText(item.getContent());

            if (!TextUtils.isEmpty(item.getDate()))
                txtDateTime.setText(CommonUtils.formatTimeServer("yyyy-MM-dd HH:mm:ss","dd/MM/yyyy - HH:mm",item.getDate()));

            if (!TextUtils.isEmpty(item.getPoint())) {
                txtPoint.setText(String.format("+%s điểm", item.getPoint()));
            }
        }
    }
}
