package vn.chapp.vn24h.ui.chat;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.models.chat.ContentChatResponse;
import vn.chapp.vn24h.models.chat.ContentItem;
import vn.chapp.vn24h.utils.AppConstants;

public class ChatPresenter<V extends ChatMvpView> extends BasePresenter<V> implements ChatMvpPresenter<V> {

    @Inject
    public ChatPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public UserDefault getUserDefault() {
        return getDataManager().getUserDefault();
    }

    @Override
    public void getContentChat(String userId) {
        doNetworkRequest(MainApp.newInstance().getNetworking().getContentChat(getDataManager().getUserDefault().getId(), userId),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<ContentChatResponse>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<ContentChatResponse> response) {

                        if (response.isSuccessNonNull() && response.getData().getContent() != null) {
                            Collections.reverse(response.getData().getContent());
                            getMvpView().parseChat(response.getData().getContent());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }

    @Override
    public void postChat(ContentItem contentItem) {
        doNetworkRequest(MainApp.newInstance().getNetworking().postChat(contentItem.getShopId(), contentItem.getUserId(), contentItem.getContent(), AppConstants.APP_TYPE),
                () -> {
                },
                () -> {
                }, new OnNetworkRequest<Response<Object>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Object> response) {

                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }

    @Override
    public void getChatRoom() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getChatRoom(getDataManager().getUserDefault().getId(), AppConstants.APP_TYPE),
                () -> {
                },
                () -> {
                }, new OnNetworkRequest<Response<List<ChatRoom>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<ChatRoom>> response) {
                        if (response.isSuccessNonNull() && response.getData() != null && !response.getData().isEmpty()) {
                            getMvpView().parseChatRoom(response.getData().get(0));
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                }
        );
    }
}
