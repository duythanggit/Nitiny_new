package vn.chapp.vn24h.ui.scheduledProduct;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.sale.Scheduled;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.ui.main.MainActivity;
import vn.chapp.vn24h.utils.CommonUtils;

public class ConfirmProductFragment extends BaseFragment implements ConfirmProductFrMvpView {

    public static final String TAG = ConfirmProductFragment.class.getCanonicalName();

    public static final String ARG_SCHEDULED = "ARG_SCHEDULED";
    public static final String ARG_SHOP_NAME = "ARG_SHOP_NAME";
    public static final String ARG_SHOP_PHONE_CF = "ARG_SHOP_PHONE_CF";
    public static final String ARG_SHOP_ADDRESS_PRO = "ARG_SHOP_ADDRESS_PRO";

    private Scheduled scheduled;
    private String shopName;
    private String shopPhoneCF;
    private String shopAddress;

    @Inject
    ConfirmProductFrPresenter<ConfirmProductFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    ConfirmProductAdapter confirmAdapter;

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
    @BindView(R.id.txtNote)
    TextView txtNote;
    @BindView(R.id.txtPayment)
    TextView txtPayment;
    @BindView(R.id.imgAvatarHost)
    ImageView imgAvatarHost;
    @BindView(R.id.txtAddressReceive)
    TextView txtAddressReceive;
    @BindView(R.id.txtTotalCount)
    TextView txtTotalCount;
    @BindView(R.id.txtPhoneReceive)
    TextView txtPhoneReceive;

    public static ConfirmProductFragment newInstance(Scheduled scheduled, String shopName, String shopPhoneCF, String shopAddress) {
        ConfirmProductFragment fragment = new ConfirmProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEDULED, scheduled);
        args.putString(ARG_SHOP_NAME, shopName);
        args.putString(ARG_SHOP_PHONE_CF, shopPhoneCF);
        args.putString(ARG_SHOP_ADDRESS_PRO, shopAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_confirm;
    }

    @Override
    protected void init(View v) {
        scheduled = getArguments().getParcelable(ARG_SCHEDULED);
        shopName = getArguments().getString(ARG_SHOP_NAME);
        shopPhoneCF = getArguments().getString(ARG_SHOP_PHONE_CF);
        shopAddress = getArguments().getString(ARG_SHOP_ADDRESS_PRO);

        rcConfirm.setLayoutManager(linearLayoutManager);
        rcConfirm.setNestedScrollingEnabled(false);
        confirmAdapter.replaceData(scheduled.getListProduct());
        rcConfirm.setAdapter(confirmAdapter);

        Date date = CommonUtils.formatStringToDateforScheduled(scheduled.getDateBooking());
        txtDateTime.setText("Thời gian: "+CommonUtils.formatDateToDayddHHyyyyHHmm(date));

//        txtName.setText(scheduled.getProductResponse().getShopName());
//        txtAddress.setText(scheduled.getProductResponse().getShopAddress());
//        txtPhone.setText(scheduled.getProductResponse().getShopPhone());
//        NetworkUtils.loadImage(getContext(),scheduled.getProductResponse().getShopAvatar(),imgAvatarHost);
        txtName.setText(shopName);
        txtAddress.setText(shopAddress);
        txtPhone.setText(shopPhoneCF);
//        NetworkUtils.loadImage(getContext(),scheduled.getProductResponse().getShopAvatar(),imgAvatarHost);

        if (!TextUtils.isEmpty(scheduled.getNote())) txtNote.setText(scheduled.getNote());
        else txtNote.setText("Không có ghi chú");

        if(scheduled.getPayment()==2) txtPayment.setText("Bằng ví điện tử"); else txtPayment.setText("Tiền mặt");

        if (scheduled.getIsReceive()==0) {
            if (!TextUtils.isEmpty(scheduled.getAddress())) txtAddressReceive.setText("Địa điểm nhận hàng: "+scheduled.getAddress());
            else txtAddressReceive.setText("Địa điểm nhận hàng: chưa có thông tin ");
        } else {
            txtAddressReceive.setText("Địa điểm nhận hàng: nhận tại shop");
        }

        txtTotalCount.setText(String.valueOf(scheduled.getNumber()));
        txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(scheduled.getTotalMoney())));
        txtPhoneReceive.setText("SĐT người nhận: "+scheduled.getPhone());

    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if(component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity)getActivity()).setToolbarState(true, getString(R.string.title_scheduled_confirm));
        }
    }

    @Override
    public void didBooking() {
        showMessage("Bạn đã đặt hàng thành công");
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.btnBooking)
    public void OnClickBooking() {
        String date = CommonUtils.formatDateSendServer(scheduled.getDateBooking());
        String time = CommonUtils.formatTimeSendServer(scheduled.getDateBooking());
        scheduled.setDateBooking(date);
        scheduled.setTimeBooking(time);
        presenter.doBooking(scheduled);
    }

    @OnClick({R.id.txtPhone})
    public void onClickCall(View v) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + MainApp.newInstance().getPreference().getUserDefault().getPhone()));
        startActivity(callIntent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity)getActivity()).restoreToolbar();
        }
    }
}
