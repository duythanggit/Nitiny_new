package vn.chapp.vn24h.ui.tichDiem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.point.PointResponse;

public class TichDiemPresenter<V extends TichDiemMvpView> extends BasePresenter<V> implements TichDiemMvpPresent<V> {

    @Inject
    public TichDiemPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getPointHistory() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getPoint(getDataManager().getUserDefault().getId()),
                () -> { },
                () -> { }, new OnNetworkRequest<Response<List<PointResponse>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<PointResponse>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().parsePointHistory(response.getData());
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