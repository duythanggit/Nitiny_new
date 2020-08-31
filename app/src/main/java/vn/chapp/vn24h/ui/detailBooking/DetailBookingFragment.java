package vn.chapp.vn24h.ui.detailBooking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.booking.DetailBooking;
import vn.chapp.vn24h.models.sale.ProductSchedule;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.utils.CommonUtils;

public class DetailBookingFragment extends BaseFragment implements DetailBookingFrMvpView {

    public static final String TAG = DetailBookingFragment.class.getCanonicalName();

    public static final String ARG_ID_BOOKING = "ARG_ID_BOOKING";

    private int idBooking;

    @Inject
    DetailBookingFrPresenter<DetailBookingFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    DetailBookingAdapter confirmAdapter;

    @BindView(R.id.rcConfirm)
    RecyclerView rcConfirm;
    @BindView(R.id.txtDateTime)
    TextView txtDateTime;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtTotalPrice)
    TextView txtTotalPrice;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.txtTotalProd)
    TextView txtTotalProd;
    @BindView(R.id.txtPaymentType)
    TextView txtPaymentType;
    @BindView(R.id.txtNote)
    TextView txtNote;
    @BindView(R.id.txtAddressReceive)
    TextView txtAddressReceive;
    @BindView(R.id.tvLabelProduct)
    TextView tvLabelProduct;
    @BindView(R.id.lnWrapTotalProduct)
    LinearLayout lnWrapTotalProduct;
    @BindView(R.id.txtServiceInfor)
    TextView txtServiceInfor;


    public static DetailBookingFragment newInstance(int idBooking) {
        DetailBookingFragment fragment = new DetailBookingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_BOOKING, idBooking);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_detail_booking;
    }

    @Override
    protected void init(View v) {
        idBooking = getArguments().getInt(ARG_ID_BOOKING);

        rcConfirm.setLayoutManager(linearLayoutManager);
        rcConfirm.setNestedScrollingEnabled(false);
        rcConfirm.setAdapter(confirmAdapter);
        presenter.doGetDetailBooking(idBooking);
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
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).setToolbarState(true, getString(R.string.title_detail_booking));
        }
    }


    @Override
    public void didGetDetailBooking(DetailBooking detailBooking) {
        confirmAdapter.replaceData(detailBooking.getListProduct());
        int total = 0;
        if (detailBooking.getListProduct() != null) {
            for (ProductSchedule prod :  detailBooking.getListProduct()) {
                total += prod.getNumber();
            }
        }
        txtTotalProd.setText(String.valueOf(total));
        Date date = CommonUtils.formatServerToDate(detailBooking.getDateBooking(), detailBooking.getTimeBooking());
        txtDateTime.setText("Thời gian: " + CommonUtils.formatDateToDayddHHyyyyHHmm(date));
        txtName.setText(detailBooking.getShopName());

        if (!TextUtils.isEmpty(detailBooking.getShopAddress())) txtAddress.setText(detailBooking.getShopAddress());
        else txtAddress.setText("Chưa rõ");

        txtPhone.setText(String.valueOf(detailBooking.getShopPhone()));
        txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(detailBooking.getTotalMoney())));
        txtPaymentType.setText("2".equals(detailBooking.getPayment()) ? "Ví điện tử" : "Tiền mặt");

        if (!TextUtils.isEmpty(detailBooking.getNote()))
            txtNote.setText(detailBooking.getNote());
        else txtNote.setText("Không");

        if (detailBooking.getIsReceive()==0) {
            if (!TextUtils.isEmpty(detailBooking.getAddress())) txtAddressReceive.setText("Địa điểm nhận hàng: "+detailBooking.getAddress());
            else txtAddressReceive.setText("Địa điểm nhận hàng: chưa có thông tin ");
        } else {
            txtAddressReceive.setText("Địa điểm nhận hàng: nhận tại shop");
        }

        String status = "";
        int colorResource = getResources().getColor(R.color.colorText);
        switch (detailBooking.getActive()) {
            case 1:
                status = "Đã đặt";
                colorResource = getResources().getColor(R.color.colorTextGreen);
                break;
            case 2:
                status = "Đã xác nhận";
                colorResource = getResources().getColor(R.color.colorTextGreen);
                break;
            case 3:
                status = "Shop đã huỷ";
                colorResource = getResources().getColor(R.color.colorText);
                break;
            case 4:
                status = "Đã huỷ";
                colorResource = getResources().getColor(R.color.colorText);
                break;
            case 5:
                status = "Đã hoàn thành";
                colorResource = getResources().getColor(R.color.color1884EF);
                break;
        }
        txtStatus.setText(status);
        txtStatus.setTextColor(colorResource);

        txtPhone.setOnClickListener(v-> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + detailBooking.getShopPhone()));
            startActivity(callIntent);
        });

        if(detailBooking.getType()==0) {
            // dich vu
            tvLabelProduct.setVisibility(View.GONE);
            lnWrapTotalProduct.setVisibility(View.GONE);
            txtAddressReceive.setVisibility(View.VISIBLE);
            txtServiceInfor.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(detailBooking.getService_name()))
                txtServiceInfor.setText("Dịch vụ đặt: "+detailBooking.getService_name());
            else txtServiceInfor.setText("Dịch vụ đặt: Chưa rõ");
        } else {
            txtAddressReceive.setVisibility(View.VISIBLE);
            txtServiceInfor.setVisibility(View.GONE);
        }
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        if(getActivity() != null && getActivity() instanceof BackableActivity) {
//            ((BackableActivity)getActivity()).restoreToolbar();
//        }
//    }
}
