package vn.chapp.vn24h.ui.chat;

import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.models.service.Shop;

public interface ChatRoomFrMvpView extends MvpView {
    void parseUserLinked(List<Shop> userLinkeds);
    void parseChatRoom(List<ChatRoom> chatRooms);
}
