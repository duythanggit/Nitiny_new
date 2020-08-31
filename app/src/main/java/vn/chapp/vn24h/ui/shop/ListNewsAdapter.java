package vn.chapp.vn24h.ui.shop;

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
import vn.chapp.vn24h.models.service.NewsResponse;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;


public class ListNewsAdapter extends BaseAdapter<NewsResponse> {

    public ListNewsAdapter(List<NewsResponse> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ListProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false));
    }

    class ListProductViewHolder extends BaseViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.ivThumb)
        ImageView ivThumb;

        public ListProductViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            NewsResponse newsResponse = getCollection().get(position);
            if (!TextUtils.isEmpty(newsResponse.getTitle()))
                tvTitle.setText(newsResponse.getTitle());
            if (!TextUtils.isEmpty(newsResponse.getContent()))
                tvContent.setText(newsResponse.getContent());
            if (newsResponse.getType().equals("2")) {
                tvTime.setText(String.format("Áp dụng từ %s đến %s", CommonUtils.formatCalendarToDDMMYYYY(CommonUtils.formatYYYYMMDDToCalendar(newsResponse.getDateStart())), CommonUtils.formatCalendarToDDMMYYYY(CommonUtils.formatYYYYMMDDToCalendar(newsResponse.getDateEnd()))));
            } else if (newsResponse.getType().equals("1")) {
                tvTime.setText(CommonUtils.formatCalendarToYYYYMMddHHmmSlash(CommonUtils.formatYYYYMMDDHHmmssToCalendar(newsResponse.getDate())));
            }
            if (!TextUtils.isEmpty(newsResponse.getImg()))
                NetworkUtils.loadImage(itemView.getContext(),newsResponse.getImg(),ivThumb);


        }
    }
}
