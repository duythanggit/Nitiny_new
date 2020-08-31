package vn.chapp.vn24h.ui.web;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.guide.GuideResponse;


public class WebLoaderFrPresenter<V extends WebLoaderFrMvpView> extends BasePresenter<V> implements WebLoaderFrMvpPresent<V> {

    @Inject
    public WebLoaderFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public void getGuide() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getGuide(),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<GuideResponse>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<GuideResponse> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().parseContent(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }
}
