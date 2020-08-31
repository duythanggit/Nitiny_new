package vn.chapp.vn24h.ui.chat;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.models.chat.ContentItem;

public interface ChatMvpView extends MvpView {
    void parseChat(List<ContentItem> msgs);
    void parseChatRoom(ChatRoom chatRoom);
}
