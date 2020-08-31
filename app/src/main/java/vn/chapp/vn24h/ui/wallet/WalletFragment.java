package vn.chapp.vn24h.ui.wallet;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.base.BaseRecyclerView;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.wallet.Wallet;
import vn.chapp.vn24h.models.wallet.WalletHistory;
import vn.chapp.vn24h.utils.CommonUtils;

public class WalletFragment extends BaseFragment implements WalletFrMvpView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = WalletFragment.class.getCanonicalName();

    @Inject
    WalletFrPresent<WalletFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    WalletAdapter walletAdapter;

    @BindView(R.id.rcWallet)
    BaseRecyclerView rcWalletHistory;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.tvPrice)
    TextView tvPrice;

    private List<Wallet> walletHistoryList = new ArrayList<Wallet>();

    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void init(View v) {
        rcWalletHistory.setLayoutManager(linearLayoutManager);
        rcWalletHistory.setAdapter(walletAdapter);
        rcWalletHistory.setOnRefreshListener(this);
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
    public void onResume() {
        super.onResume();
        presenter.getWallet();
    }

    @Override
    public void gotWallet(WalletHistory walletHistory) {
        if(walletHistory.getWallet()!=null) {
            if(walletHistory.getWallet().size()>0) {
                rcWalletHistory.setVisibility(View.VISIBLE);
                txtNoData.setVisibility(View.GONE);
            } else {
                rcWalletHistory.setVisibility(View.GONE);
                txtNoData.setVisibility(View.VISIBLE);
            }
            walletAdapter.replaceData(walletHistory.getWallet());
            walletHistoryList = walletHistory.getWallet();
        }

        if (!TextUtils.isEmpty(walletHistory.getAccount()))
            tvPrice.setText(String.format("%s đ", CommonUtils.parseMoney(walletHistory.getAccount())));
        else tvPrice.setText("");
    }


    @Override
    public void setRefresh(boolean refreshed) {
        getActivity().runOnUiThread(() -> rcWalletHistory.setRefresh(refreshed));
    }


    @Override
    public void onRefresh() {
        if (walletHistoryList.size() > 0) {
            presenter.getWallet();
        } else {
            getActivity().runOnUiThread(() -> rcWalletHistory.setRefresh(false));
        }
    }

    @OnClick(R.id.txtRecharge)
    public void onClicktxtRecharge(View v) {
        Toast.makeText(getContext(), "Chức năng này đang được phát triển", Toast.LENGTH_SHORT).show();
    }

}