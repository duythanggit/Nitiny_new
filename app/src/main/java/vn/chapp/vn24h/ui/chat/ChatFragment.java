package vn.chapp.vn24h.ui.chat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.models.chat.ContentItem;
import vn.chapp.vn24h.service.SocketService;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.CommonUtils;

public class ChatFragment extends BaseFragment implements ChatMvpView, SocketService.OnMessagerListener {
    public static final String TAG = ChatFragment.class.getCanonicalName();

    @Inject
    ChatMvpPresenter<ChatMvpView> presenter;

    @Inject
    ChatAdapter chatAdapter;

    @BindView(R.id.edtMessage)
    EditText edtMessage;
    @BindView(R.id.rcChatHistory)
    RecyclerView rcChat;
    private SocketService socketService;
    private boolean isSocketConnected = false;
    private String userId = "";
    private String name = "";
    private String avatar = "";


    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int configView() {
        return R.layout.fragment_chat;
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
    protected void init(View v) {
        presenter.onAttach(this);
        rcChat.setHasFixedSize(true);
        rcChat.setAdapter(chatAdapter);
        presenter.getChatRoom();
    }

    @OnClick(R.id.ivSentMsg)
    public void onSendMsgClick(View v) {
        if (!TextUtils.isEmpty(edtMessage.getText().toString())) {
            socketService.sendMessage(
                    presenter.getUserDefault().getName(),
                    presenter.getUserDefault().getId(),
                    userId,
                    Integer.valueOf(presenter.getUserDefault().getId()),
                    AppConstants.APP_TYPE,
                    CommonUtils.formatCurrentChatTime(),
                    edtMessage.getText().toString()
            );
            edtMessage.setText("");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(requireContext(), SocketService.class);
        if (getActivity() != null)
            getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isSocketConnected) {
            if (getActivity() != null)
                getActivity().unbindService(mConnection);
            if (socketService != null) socketService.stopSelf();
            isSocketConnected = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            SocketService.SocketBinder binder = (SocketService.SocketBinder) service;
            socketService = binder.getService();
            socketService.setOnMessagerListener(ChatFragment.this);
            isSocketConnected = true;
            socketService.connect();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isSocketConnected = false;
        }
    };

    @Override
    public void onSendMessager(ContentItem msg) {
        if (getActivity() != null) getActivity().setResult(Activity.RESULT_OK);
        presenter.postChat(msg);
        rcChat.post(() -> {
            chatAdapter.addDataToPosition(0, msg);
            if (chatAdapter.getCollection().size() > 1 && chatAdapter.getCollection().get(1).getType().equals("1")) {
                chatAdapter.notifyItemChanged(1);
            }
        });
        rcChat.post(() -> rcChat.smoothScrollToPosition(0));
    }

    @Override
    public void onReceiverMessager(ContentItem msg) {
        if (msg.getType().equals("2") && msg.getShopId().equals(userId)) {
            rcChat.post(() -> {
                chatAdapter.addDataToPosition(0, msg);
                if (chatAdapter.getCollection().size() > 1 && chatAdapter.getCollection().get(1).getType().equals("2")) {
                    chatAdapter.notifyItemChanged(1);
                }
            });
            rcChat.post(() -> rcChat.smoothScrollToPosition(0));

        }
    }

    @Override
    public void parseChat(List<ContentItem> msgs) {
        addFakeData(msgs);
        rcChat.post(() -> chatAdapter.replaceData(msgs));
        rcChat.post(() -> rcChat.smoothScrollToPosition(0));
    }

    private void addFakeData(List<ContentItem> msgsRoot) {
        List<ContentItem> msgs = new ArrayList<>();
        msgs.add(new ContentItem(null, null, null, "2", "Xin Chào Đây là Fake Data"));
        msgs.add(new ContentItem(null, null, null, "2", "Xin Chào 1"));
        msgs.add(new ContentItem(null, null, null, "2", "Xin Chào 2"));
        msgs.add(new ContentItem(null, null, null, "1", "Alo"));
        msgs.add(new ContentItem(null, null, null, "1", "Alo Alo"));
        msgs.addAll(msgsRoot);
        msgsRoot.clear();
        msgsRoot.addAll(msgs);
    }

    @Override
    public void parseChatRoom(ChatRoom chatRoom) {
        userId = chatRoom.getUser().getId();
        name = chatRoom.getUser().getName();
        avatar = chatRoom.getUser().getAvatar();
        chatAdapter.setUrlAvatar(avatar);
        presenter.getContentChat(userId);
    }
}
