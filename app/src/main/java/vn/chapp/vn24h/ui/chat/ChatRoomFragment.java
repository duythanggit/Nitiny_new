package vn.chapp.vn24h.ui.chat;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.base.OnItemClickListener;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.models.service.Shop;

public class ChatRoomFragment extends BaseFragment implements ChatRoomFrMvpView, OnItemClickListener, ChatBoxAdapter.OnItemChatClickListener {

    public static final String TAG = ChatRoomFragment.class.getCanonicalName();
    public static final int REQUEST_CODE_CHAT = 100;

    @Inject
    ChatRoomFrPresenter<ChatRoomFrMvpView> presenter;
    @Inject
    CustomerOnlineAdapter customerOnlineAdapter;
    @Inject
    @Named("horizontal")
    LinearLayoutManager linearLayoutManager;
    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManagerVertical;
    @Inject
    ChatBoxAdapter chatBoxAdapter;

    @BindView(R.id.rcCustomerOnline)
    RecyclerView rcCustomerOnline;
    @BindView(R.id.rcChat)
    RecyclerView rcChat;

    public static ChatRoomFragment newInstance() {
        ChatRoomFragment fragment = new ChatRoomFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_chat_room;
    }

    @Override
    protected void init(View v) {
        rcCustomerOnline.setHasFixedSize(true);
        rcCustomerOnline.setLayoutManager(linearLayoutManager);
        rcCustomerOnline.setAdapter(customerOnlineAdapter);
        customerOnlineAdapter.setOnItemClickListener(this);

        rcChat.setHasFixedSize(true);
        rcChat.setLayoutManager(linearLayoutManagerVertical);
        rcChat.setAdapter(chatBoxAdapter);
        chatBoxAdapter.setOnItemChatClickListener(this);

        presenter.getUserLinked();
        presenter.getChatRoom();
    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
        }

    }

    @Override
    public void onClick(int position) {
        startActivityForResult(ChatActivity.newInstance(getContext(),String.valueOf(customerOnlineAdapter.getCollection().get(position).getShopId()),customerOnlineAdapter.getCollection().get(position).getName(),customerOnlineAdapter.getCollection().get(position).getAvatar(), customerOnlineAdapter.getCollection().get(position).getPhone()),REQUEST_CODE_CHAT);
    }

    @Override
    public void parseUserLinked(List<Shop> userLinkeds) {
        customerOnlineAdapter.replaceData(userLinkeds);
    }

    @Override
    public void parseChatRoom(List<ChatRoom> chatRooms) {
        chatBoxAdapter.replaceData(chatRooms);
    }

    @Override
    public void onChatClick(int position) {
        if (chatBoxAdapter.getChatRooms().get(position).getShop() == null) return;
        startActivityForResult(
                ChatActivity.newInstance(getContext(),chatBoxAdapter.getChatRooms().get(position).getShop().getId(),chatBoxAdapter.getChatRooms().get(position).getShop().getName(),chatBoxAdapter.getChatRooms().get(position).getShop().getAvatar(), chatBoxAdapter.getChatRooms().get(position).getShop().getPhone())
                ,REQUEST_CODE_CHAT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CHAT:
                if (resultCode == Activity.RESULT_OK) {
                    presenter.getChatRoom();
                }
                break;
        }
    }
}