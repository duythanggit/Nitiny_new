package vn.chapp.vn24h.ui.wallet;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.wallet.WalletHistory;
import vn.chapp.vn24h.utils.AppConstants;

public class WalletFrPresent <V extends WalletFrMvpView> extends BasePresenter<V> implements WalletFrMvpPresent<V> {

    @Inject
    public WalletFrPresent(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getWallet() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getWallet(getDataManager().getUserDefault().getId(), AppConstants.APP_TYPE),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<WalletHistory>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<WalletHistory> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().gotWallet(response.getData());
                            getMvpView().setRefresh(false);
                            MainApp.newInstance().setBalance(response.getData().getAccount());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                        getMvpView().showMessage("Có lỗi xảy ra");
                    }
                });
    }

}
