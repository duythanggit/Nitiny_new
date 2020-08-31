package vn.chapp.vn24h.ui.accumulatepoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.point.PointDetailResponse;

public class AccumulatePointPresenter<V extends AccumulatePointMvpView> extends BasePresenter<V> implements AccumulatePointMvpPresent<V> {

    @Inject
    public AccumulatePointPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getPointHistory(String shopId, String month, String year) {
        doNetworkRequest(MainApp.newInstance().getNetworking().getPointDetail(getDataManager().getUserDefault().getId(), shopId, month, year),
                () -> { },
                () -> { }, new OnNetworkRequest<Response<List<PointDetailResponse>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<PointDetailResponse>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().parsePointHistory(response.getData());
                        } else if (response.isSuccess()) {
                            getMvpView().parsePointHistory(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }

    @Override
    public UserDefault getUserDefault() {
        return getDataManager().getUserDefault();
    }
}