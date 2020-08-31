package vn.chapp.vn24h.ui.history;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import vn.chapp.vn24h.models.service.HistoryResponse;
import vn.chapp.vn24h.models.service.HistoryWrapper;
import vn.chapp.vn24h.models.service.ServiceResponse;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.ui.chat.ChatActivity;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.utils.NetworkUtils;

public class HistoryFragment extends BaseFragment implements HistoryFrMvpView, AdapterView.OnItemSelectedListener,
        HistoryAdapter.OnClickHistoryListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = HistoryFragment.class.getCanonicalName();

    @Inject
    HistoryFrPresenter<HistoryFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    HistoryAdapter historyAdapter;

    private ServiceAdapter serviceAdapter;
    private ShopAdapter shopAdapter;

    @BindView(R.id.rcCustomerHistory)
    BaseRecyclerView rcCustomerHistory;
    @BindView(R.id.spinnerService)
    Spinner spinnerService;
    @BindView(R.id.ivService)
    ImageView ivService;

    private List<ServiceResponse> serviceResponses;
    private List<HistoryWrapper> historyWrappers;
    private List<Shop> listShopLinked;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_history;
    }

    @Override
    protected void init(View v) {
        rcCustomerHistory.setLayoutManager(linearLayoutManager);
        historyAdapter.setOnClickHistoryListener(this);
        rcCustomerHistory.setAdapter(historyAdapter);
        rcCustomerHistory.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rcCustomerHistory.setOnRefreshListener(this);

//        presenter.doGetShopLinked();
        //ko filter theo shop
        presenter.getUserHistory(null);


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
    public void didGetShopLinked(List<Shop> listShopLinked) {
        this.listShopLinked = listShopLinked;
        shopAdapter = new ShopAdapter(listShopLinked);
        spinnerService.setAdapter(shopAdapter);
        if (listShopLinked.size() > 0 && !TextUtils.isEmpty(listShopLinked.get(0).getAvatar()))
            NetworkUtils.loadImage(getContext(), listShopLinked.get(0).getAvatar(), ivService);
        else ivService.setImageResource(R.drawable.ic_shop_default);
        spinnerService.setOnItemSelectedListener(this);
        if (listShopLinked.size() > 0) {
            presenter.getUserHistory(listShopLinked.get(0).getShopId() + "");
        }
    }

    @Override
    public void gotServices(List<ServiceResponse> serviceResponses) {
        this.serviceResponses = serviceResponses;
    }

    @Override
    public void gotHistory(List<HistoryResponse> historyResponses) {
        getBaseActivity().runOnUiThread(new Runnable() {
            public void run() {
                rcCustomerHistory.setRefresh(false);
            }
        });
        historyAdapter.replaceData(historyResponses);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!TextUtils.isEmpty(listShopLinked.get(position).getAvatar()))
            NetworkUtils.loadImage(getContext(), listShopLinked.get(position).getAvatar(), ivService);
        else
            ivService.setImageResource(R.drawable.ic_shop_default);
        presenter.getUserHistory(listShopLinked.get(position).getShopId() + "");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClickViewDetail(int position, HistoryResponse historyResponse) {
        startActivity(BackableActivity.newInstanceHistoryDetail(getContext(), BackableActivity.NAVIGATOR_HSDT, historyResponse));
    }

    @Override
    public void onClickCall(int position, String phone) {
        if (phone == null || TextUtils.isEmpty(phone)) {
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
        presenter.getUserHistory(null);
    }
}