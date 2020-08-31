package vn.chapp.vn24h.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.NewsResponse;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class DetailNewsFragment extends BaseFragment implements DetailNewsFrMvpView {

    public static final String TAG = DetailNewsFragment.class.getCanonicalName();
    public static final String ARG_NEWS = "ARG_NEWS";
    public static final String ARG_POS = "ARG_POS";

    @Inject
    DetailNewsFrPresenter<DetailNewsFrMvpView> presenter;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.ivNews)
    RoundedImageView ivNews;

    private NewsResponse newsResponse;
    private int position;


    public static DetailNewsFragment newInstance(int position, NewsResponse newsResponse) {
        DetailNewsFragment fragment = new DetailNewsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWS, newsResponse);
        args.putInt(ARG_POS,position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_detail_news;
    }


    @Override
    protected void init(View v) {
        newsResponse = getArguments().getParcelable(ARG_NEWS);
        position = getArguments().getInt(ARG_POS);
        parseNewsResponse(newsResponse);
        presenter.getNewsDetail(newsResponse.getId());

    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).restoreToolbar();
        }
    }

    @Override
    public void parseNewsResponse(NewsResponse news) {
        if (!TextUtils.isEmpty(newsResponse.getTitle()))
            tvTitle.setText(newsResponse.getTitle());
        if (!TextUtils.isEmpty(newsResponse.getContent()))
            tvContent.setText(newsResponse.getContent());
        if (newsResponse.getType().equals("2")) {
            tvTime.setText(String.format("Áp dụng từ %s đến %s", CommonUtils.formatCalendarToDDMMYYYY(CommonUtils.formatYYYYMMDDToCalendar(newsResponse.getDateStart())), CommonUtils.formatCalendarToDDMMYYYY(CommonUtils.formatYYYYMMDDToCalendar(newsResponse.getDateStart()))));
        } else if (newsResponse.getType().equals("1")) {
            tvTime.setText(CommonUtils.formatCalendarToYYYYMMddHHmmSlash(CommonUtils.formatYYYYMMDDHHmmssToCalendar(newsResponse.getDate())));
        }
        if (!TextUtils.isEmpty(newsResponse.getImg()))
            NetworkUtils.loadImage(getContext(), newsResponse.getImg(), ivNews);
    }

    public void getDetailNews(NewsResponse newsResponse){
        presenter.getNewsDetail(newsResponse.getId());
    }
}
