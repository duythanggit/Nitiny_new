package vn.chapp.vn24h.ui.profile;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MultipartBody;
import vn.chapp.vn24h.BuildConfig;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.NetworkParams;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.map.Address;
import vn.chapp.vn24h.models.map.PredictionsItem;
import vn.chapp.vn24h.utils.AppLogger;
import vn.chapp.vn24h.utils.rx.RxUtil;


public class ProfileDetailFrPresenter<V extends ProfileDetailFrMvpView> extends BasePresenter<V> implements ProfileDetailFrMvpPresent<V> {

    @Inject
    public ProfileDetailFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public void getUserDefault() {
        getMvpView().parseUserDetail(getDataManager().getUserDefault());
    }

    @Override
    public void doUploadAvatar(String path) {
        MultipartBody multipartBodyUpload = NetworkParams.bodyUploadAvatar(path);
        doNetworkRequest(MainApp.newInstance().getNetworking().postUploadAvatar(NetworkParams.contentTypeUpload(multipartBodyUpload), multipartBodyUpload),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<String>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<String> response) {
                        if (response.isSuccessNonNull()) {
                            UserDefault userDefault = getDataManager().getUserDefault();
                            userDefault.setAvatar(response.getData());
                            getDataManager().setUserDefault(userDefault);
                            getMvpView().didUploadAvatar(response.getData());
                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                        getMvpView().showMessage(R.string.message_unknow_error);
                    }
                });
    }

    @Override
    public void getUserDetail() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getUserDetail(getDataManager().getUserDefault().getId()),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<UserDefault>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<UserDefault> response) {
                        if (response.isSuccessNonNull()) {
                            getDataManager().setUserDefault(response.getData());
                            getMvpView().parseUserDetail(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }

    @Override
    public void doUpdateProfile(Address address, String email, String name, String phone) {
        UserDefault user = getDataManager().getUserDefault();
        doNetworkRequest(MainApp.newInstance().getNetworking().updateProfile(
                user.getId(), email, address.getAddr(), name, phone,String.valueOf(address.getLat()),String.valueOf(address.getLng())),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Object>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Object> response) {
                        if (response.isSuccess()) {
                            user.setAddress(address.getAddr());
                            user.setLat(String.valueOf(address.getLat()));
                            user.setLng(String.valueOf(address.getLng()));
                            user.setEmail(email);
                            user.setName(name);
                            user.setPhone(phone);
                            getDataManager().setUserDefault(user);
                            getMvpView().showMessage("Bạn đã cập nhật thành công!");
                            getMvpView().didUpdateProfile(address.getAddr(), email, name, phone);
                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                        getMvpView().showMessage(R.string.message_unknow_error);
                    }
                });
    }

    @Override
    public void startSearchPlace(String s) {
        getCompositeDisposable().add(RxUtil.appleHandlerStartFinish(MainApp.newInstance().getMapNetworking().autoCompletePlace(s, BuildConfig.PLACE_SEARCH_KEY),
                () -> { },
                () -> { })
                .compose(RxUtil.applySchedulers())
                .subscribe(
                        placeResponse -> {
                            if (placeResponse.getStatus().equals("OK")) {
                                List<String> placeAddress = new ArrayList<>();
                                for (PredictionsItem predictionsItem : placeResponse.getPredictions()) {
                                    placeAddress.add(predictionsItem.getDescription());
                                }
                                getMvpView().gotPlaces(placeResponse.getPredictions(),placeAddress);
                            } else {
                                AppLogger.e(placeResponse.getStatus());
                            }
                        },
                        throwable -> AppLogger.e(throwable.getLocalizedMessage())
                ));
    }

    @Override
    public void getDetailPlace(String placeId) {
        getCompositeDisposable().add(RxUtil.appleHandlerStartFinish(
                MainApp.newInstance().getMapNetworking().getDetailPlace(placeId, BuildConfig.PLACE_SEARCH_KEY),
                () -> { getMvpView().showLoading();},
                () -> { getMvpView().hideLoading();})
                .compose(RxUtil.applySchedulers())
                .subscribe(
                        detailPlaceResponse -> {
                            if (detailPlaceResponse.getStatus().equals("OK")) {
                                Address address = new Address(
                                        detailPlaceResponse.getResult().getGeometry().getLocation().getLat(),
                                        detailPlaceResponse.getResult().getGeometry().getLocation().getLng(),
                                        detailPlaceResponse.getResult().getFormattedAddress(),
                                        detailPlaceResponse.getResult().getAddressComponents()
                                );
                                getMvpView().gotPlaceDetail(address);
                            } else {
                                getMvpView().showMessage(R.string.message_unknow_error);
                            }
                        },
                        throwable -> {
                            AppLogger.e(throwable.getLocalizedMessage());
                            getMvpView().showMessage(R.string.message_unknow_error);
                            throwable.printStackTrace();
                        }
                )
        );
    }
}
