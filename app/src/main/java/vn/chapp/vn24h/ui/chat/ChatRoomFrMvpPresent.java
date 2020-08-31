package vn.chapp.vn24h.ui.chat;


import vn.chapp.vn24h.base.MvpPresenter;

public interface ChatRoomFrMvpPresent<V extends ChatRoomFrMvpView> extends MvpPresenter<V> {
    void getUserLinked();
    void getChatRoom();
}
