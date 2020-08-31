package vn.chapp.vn24h.ui.chat;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.utils.AppConstants;


public class ChatRoomFrPresenter<V extends ChatRoomFrMvpView> extends BasePresenter<V> implements ChatRoomFrMvpPresent<V> {

    @Inject
    public ChatRoomFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getUserLinked() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getListShopLinked(getDataManager().getUserDefault().getId(),null, "1"),
                () -> { },
                () -> { }, new OnNetworkRequest<Response<List<Shop>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<Shop>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().parseUserLinked(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                }
        );
    }

    @Override
    public void getChatRoom() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getChatRoom(getDataManager().getUserDefault().getId(), AppConstants.APP_TYPE),
                () -> {},
                () -> {}, new OnNetworkRequest<Response<List<ChatRoom>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<ChatRoom>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().parseChatRoom(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                }
        );
    }
}
