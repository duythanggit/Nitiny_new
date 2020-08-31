package vn.chapp.vn24h.ui.booking;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.base.BaseRecyclerView;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.booking.Booking;
import vn.chapp.vn24h.models.booking.BookingStatus;
import vn.chapp.vn24h.ui.chat.ChatActivity;
import vn.chapp.vn24h.ui.main.BackableActivity;

public class BookingFragment extends BaseFragment implements BookingFrMvpView, AdapterView.OnItemSelectedListener,
    BookingAdapter.OnActiveActionClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = BookingFragment.class.getCanonicalName();

    @Inject
    BookingFrPresenter<BookingFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    BookingAdapter bookingAdapter;

    private StatusBookingAdapter statusBookingAdapter;

    @BindView(R.id.rcListBooking)
    BaseRecyclerView rcListBooking;
    @BindView(R.id.spinnerStatus)
    Spinner spinnerStatus;

    private List<BookingStatus> listBookingStatus;
    private List<Booking> bookingList;
    private int idStatus;

    public static BookingFragment newInstance() {
        BookingFragment fragment = new BookingFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_booking;
    }

    @Override
    protected void init(View v) {
        rcListBooking.setLayoutManager(linearLayoutManager);
        bookingAdapter.setOnActiveActionClickListener(this);
        rcListBooking.setAdapter(bookingAdapter);
        rcListBooking.setOnRefreshListener(this);
        rcListBooking.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        /*listBookingStatus = new ArrayList<BookingStatus>();
        listBookingStatus.add(new BookingStatus(1, "Đã đặt"));
        listBookingStatus.add(new BookingStatus(2, "Đã xác nhận"));
        listBookingStatus.add(new BookingStatus(3, "Shop huỷ"));
        listBookingStatus.add(new BookingStatus(4, "Khách huỷ"));
        listBookingStatus.add(new BookingStatus(5, "Hoàn thành"));
        statusBookingAdapter = new StatusBookingAdapter(listBookingStatus);
        spinnerStatus.setAdapter(statusBookingAdapter);
        spinnerStatus.setOnItemSelectedListener(this);*/

        presenter.getListBooking(null);
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
    public void didGetListBooking(List<Booking> bookingList) {
        getBaseActivity().runOnUiThread(new Runnable() {
            public void run() {
                rcListBooking.setRefresh(false);
            }
        });

        this.bookingList = bookingList;
        bookingAdapter.replaceData(bookingList);
    }

    @Override
    public void didUpdatedBooking(int position, int active) {
        bookingAdapter.updateStatus(position, active);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idStatus = listBookingStatus.get(position).getId();
//        presenter.getListBooking(listBookingStatus.get(position).getId()+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onUpdateStatusClick(int position, int active) {
        presenter.updateStatusBooking(position, bookingList.get(position), active);
    }

    @Override
    public void onClickViewDetail(int position, int bookingId) {
        startActivity(BackableActivity.newInstanceBookingDetail(getContext(), BackableActivity.NAVIGATOR_FBD, bookingId));
    }

    @Override
    public void onClickCall(int position, String phone) {
        if(phone==null || TextUtils.isEmpty(phone)) {
            showMessage("Số điện thoại không hợp lệ");
            return;
        }
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }

    @Override
    public void onClickChat(int position, String id, String name, String avatar, String phone) {
        startActivity(ChatActivity.newInstance(getContext(), id, name, avatar, phone));
    }

    @Override
    public void onClickLocation(int position) {

    }

    @Override
    public void onRefresh() {
//        presenter.getListBooking(String.valueOf(idStatus));
        presenter.getListBooking(null);
    }
}