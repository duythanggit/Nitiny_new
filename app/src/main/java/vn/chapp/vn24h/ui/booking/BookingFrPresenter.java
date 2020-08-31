package vn.chapp.vn24h.ui.booking;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.booking.Booking;

public class BookingFrPresenter<V extends BookingFrMvpView> extends BasePresenter<V> implements BookingFrMvpPresent<V> {

    @Inject
    public BookingFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getListBooking(String active) {
        if(active!=null && !active.isEmpty() && active!="0" && active!="null") {
            doNetworkRequest(MainApp.newInstance().getNetworking().getListBooking(getDataManager().getUserDefault().getId(),active),
                    () -> getMvpView().showLoading(),
                    () -> {
                        getMvpView().hideLoading();
                    }, new OnNetworkRequest<Response<List<Booking>>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<List<Booking>> response) {
                            if (response.isSuccessNonNull()) {
                                getMvpView().didGetListBooking(response.getData());
                            } else {
                                getMvpView().showMessage(response.getMessage());
                            }
                        }

                        @Override
                        public void onNetworkRequestError(Throwable throwable) {

                        }
                    });
        } else {
            doNetworkRequest(MainApp.newInstance().getNetworking().getListBooking(getDataManager().getUserDefault().getId()),
                    () -> getMvpView().showLoading(),
                    () -> {
                        getMvpView().hideLoading();
                    }, new OnNetworkRequest<Response<List<Booking>>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<List<Booking>> response) {
                            if (response.isSuccessNonNull()) {
                                getMvpView().didGetListBooking(response.getData());
                            } else {
                                getMvpView().showMessage(response.getMessage());
                            }
                        }

                        @Override
                        public void onNetworkRequestError(Throwable throwable) {

                        }
                    });
        }
    }

    @Override
    public void updateStatusBooking(int position, Booking booking, int active) {
        doNetworkRequest(MainApp.newInstance().getNetworking().updateStatusBooking(getDataManager().getUserDefault().getId(),String.valueOf(booking.getId()),active),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Object>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Object> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().didUpdatedBooking(position, active);
                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }

}
