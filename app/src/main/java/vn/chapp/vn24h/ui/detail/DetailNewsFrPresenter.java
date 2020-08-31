package vn.chapp.vn24h.ui.detail;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.service.NewsResponse;


public class DetailNewsFrPresenter<V extends DetailNewsFrMvpView> extends BasePresenter<V> implements DetailNewsFrMvpPresent<V> {

    @Inject
    public DetailNewsFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }


    @Override
    public void getNewsDetail(String id) {
        doNetworkRequest(MainApp.newInstance().getNetworking().getDetailNews(id),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<NewsResponse>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<NewsResponse> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().parseNewsResponse(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }
}
