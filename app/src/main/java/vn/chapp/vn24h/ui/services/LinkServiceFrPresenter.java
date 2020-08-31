package vn.chapp.vn24h.ui.services;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.banner.BannerResponse;
import vn.chapp.vn24h.models.service.Service;
import vn.chapp.vn24h.models.wallet.WalletHistory;
import vn.chapp.vn24h.utils.AppConstants;


public class LinkServiceFrPresenter<V extends LinkServiceFrMvpView> extends BasePresenter<V> implements LinkServiceFrMvpPresent<V> {

    @Inject
    public LinkServiceFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public void loadService(int type) {
        getMvpView().hideKeyboard();
        doNetworkRequest(MainApp.newInstance().getNetworking().getListService(getDataManager().getUserDefault().getId(), type),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(),
                new OnNetworkRequest<Response<List<Service>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<Service>> response) {
                        if(response.isSuccessNonNull()) {
                            getMvpView().didGetListService(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                        getMvpView().showMessage(R.string.message_unknow_error);
                    }
                });
    }

    @Override
    public void loadServiceLinked(int type) {
        getMvpView().hideKeyboard();
        doNetworkRequest(MainApp.newInstance().getNetworking().getListService(getDataManager().getUserDefault().getId(), type),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(),
                new OnNetworkRequest<Response<List<Service>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<Service>> response) {
                        if(response.isSuccessNonNull()) {
                            getMvpView().didGetListServiceLinked(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                        getMvpView().showMessage(R.string.message_unknow_error);
                    }
                });
    }

    @Override
    public void getBanners() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getBanners(),
                () -> {},
                () -> {}, new OnNetworkRequest<Response<List<BannerResponse>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<BannerResponse>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().gotBanners(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }

    @Override
    public void getWallet() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getWallet(getDataManager().getUserDefault().getId(), AppConstants.APP_TYPE),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<WalletHistory>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<WalletHistory> response) {
                        if (response.isSuccessNonNull()) {
                            MainApp.newInstance().setBalance(response.getData().getAccount());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }


}
