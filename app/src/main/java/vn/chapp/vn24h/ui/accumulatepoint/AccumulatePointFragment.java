package vn.chapp.vn24h.ui.accumulatepoint;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.point.PointDetailResponse;
import vn.chapp.vn24h.models.point.PointResponse;
import vn.chapp.vn24h.utils.NetworkUtils;

public class AccumulatePointFragment extends BaseFragment implements AccumulatePointMvpView,
        DatePickerDialog.OnDateSetListener {

    public static final String TAG = AccumulatePointFragment.class.getCanonicalName();
    public static final String ARG_POINT_RESPONSE = "ARG_POINT_RESPONSE";

    @Inject
    AccumulatePointPresenter<AccumulatePointMvpView> presenter;

    @BindView(R.id.imgShopAvatar)
    CircularImageView imgShopAvatar;
    @BindView(R.id.rcvPointHistory)
    RecyclerView rcvPointHistory;
    @BindView(R.id.txtShopName)
    TextView txtShopName;
    @BindView(R.id.layoutChooseTimes)
    ConstraintLayout layoutChooseTimes;
    @BindView(R.id.txtTime)
    TextView txtTime;

    @Inject
    PointHistoryAdapter pointHistoryAdapter;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private PointResponse pointResponse;

    public static AccumulatePointFragment newInstance(PointResponse pointResponse) {
        AccumulatePointFragment fragment = new AccumulatePointFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_POINT_RESPONSE, pointResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_accumulate_points;
    }

    @Override
    protected void init(View v) {
        rcvPointHistory.setAdapter(pointHistoryAdapter);
        UserDefault userDefault = presenter.getUserDefault();
        if (!TextUtils.isEmpty(userDefault.getAvatar()))
            NetworkUtils.loadImage(getContext(),userDefault.getAvatar(),imgShopAvatar);
        if (!TextUtils.isEmpty(userDefault.getName()))
            txtShopName.setText(userDefault.getName());

        calendar = Calendar.getInstance();
        this.createDialogWithoutDateField(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        txtTime.setText("Tháng "+(calendar.get(Calendar.MONTH)+1)+" - "+calendar.get(Calendar.YEAR));

        pointResponse = getArguments().getParcelable(ARG_POINT_RESPONSE);
        if (pointResponse!=null) {
            presenter.getPointHistory(pointResponse.getShopId(), String.valueOf(calendar.get(Calendar.MONTH)+1), String.valueOf(calendar.get(Calendar.YEAR)));
        }
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
    public void parsePointHistory(List<PointDetailResponse> pointResponses) {
        pointHistoryAdapter.replaceData(pointResponses);
    }

    @OnClick(R.id.layoutChooseTimes)
    public void onCLickChooseMonth() {
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        datePickerDialog.updateDate(year, month, dayOfMonth);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        txtTime.setText("Tháng "+(month+1)+" - "+year);

        if (pointResponse!=null) {
            presenter.getPointHistory(pointResponse.getShopId(), String.valueOf(calendar.get(Calendar.MONTH)+1), String.valueOf(calendar.get(Calendar.YEAR)));
        }

    }

    private void createDialogWithoutDateField(int year, int month, int day) {
        datePickerDialog = new DatePickerDialog(getContext(),
                AlertDialog.THEME_HOLO_LIGHT, this, year, month, day){
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);
            }
        };
        datePickerDialog.setTitle("Chọn tháng");
    }

}
