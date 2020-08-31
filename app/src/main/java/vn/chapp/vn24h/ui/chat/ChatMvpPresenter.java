package vn.chapp.vn24h.ui.chat;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.di.PerActivity;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.chat.ContentItem;

@PerActivity
public interface ChatMvpPresenter<V extends ChatMvpView> extends MvpPresenter<V> {

    UserDefault getUserDefault();
    void getContentChat(String userId);
    void postChat(ContentItem contentItem);
    void getChatRoom();
}
