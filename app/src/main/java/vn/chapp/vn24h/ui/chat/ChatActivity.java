package vn.chapp.vn24h.ui.chat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.models.chat.ContentItem;
import vn.chapp.vn24h.service.SocketService;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ChatActivity extends BaseActivity implements ChatMvpView, SocketService.OnMessagerListener {

    public static final String ARG_USER_ID = "ARG_USER_ID";
    public static final String ARG_USER_NAME = "ARG_USER_NAME";
    public static final String ARG_USER_AVATAR = "ARG_USER_AVATAR";
    public static final String ARG_USER_PHONE= "ARG_USER_PHONE";

    @Inject
    ChatMvpPresenter<ChatMvpView> presenter;

    @Inject
    ChatAdapter chatAdapter;
    @Inject
    @Named("vertReverse")
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.edtMessage)
    EditText edtMessage;
    @BindView(R.id.rcChatHistory)
    RecyclerView rcChat;
    @BindView(R.id.civAvatar)
    CircularImageView civAvatar;
    @BindView(R.id.tvName)
    TextView tvName;

    private SocketService socketService;
    private boolean isSocketConnected = false;
    private String userId;
    private String name;
    private String avatar;
    private String phone;

    public static Intent newInstance(Context context, String userId, String name, String avatar, String phone) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(ARG_USER_ID,userId);
        intent.putExtra(ARG_USER_NAME,name);
        intent.putExtra(ARG_USER_AVATAR,avatar);
        intent.putExtra(ARG_USER_PHONE,phone);
        return intent;
    }

    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        userId = getIntent().getExtras().getString(ARG_USER_ID,"");
        name = getIntent().getExtras().getString(ARG_USER_NAME,"");
        avatar = getIntent().getExtras().getString(ARG_USER_AVATAR,"");
        phone = getIntent().getExtras().getString(ARG_USER_PHONE,"");

        if (!TextUtils.isEmpty(name))
            tvName.setText(name);
        if (!TextUtils.isEmpty(avatar))
            NetworkUtils.loadImage(this,avatar,civAvatar,R.drawable.img_splash_logo,R.drawable.img_splash_logo);

        chatAdapter.setUrlAvatar(avatar);
        rcChat.setHasFixedSize(true);
        rcChat.setLayoutManager(linearLayoutManager);
        rcChat.setAdapter(chatAdapter);
        presenter.getContentChat(userId);

    }

    @OnClick(R.id.ivBack)
    public void onBackClickListener(View v){
        finish();
//        socketService.sendMessage("Thuy", "1", "6", 0, 1, "2019-10-29 16:05:30");
    }

    @OnClick(R.id.ivPhone)
    public void onPhoneClick(View v){
        if (phone == null || TextUtils.isEmpty(phone)) {
            showMessage("Số điện thoại không hợp lệ");
            return;
        }
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
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
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, SocketService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isSocketConnected) {
            unbindService(mConnection);
            if (socketService != null) socketService.stopSelf();
            isSocketConnected = false;
        }
    }

    @Override
    protected void onDestroy() {
        clearActivity(this, R.id.parentChat);
        super.onDestroy();
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            SocketService.SocketBinder binder = (SocketService.SocketBinder) service;
            socketService = binder.getService();
            socketService.setOnMessagerListener(ChatActivity.this);
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
        setResult(RESULT_OK);
        presenter.postChat(msg);
        rcChat.post(() -> {
            chatAdapter.addDataToPosition(0,msg);
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
                chatAdapter.addDataToPosition(0,msg);
                if (chatAdapter.getCollection().size() > 1 && chatAdapter.getCollection().get(1).getType().equals("2")) {
                    chatAdapter.notifyItemChanged(1);
                }
            });
            rcChat.post(() -> rcChat.smoothScrollToPosition(0));

        }
    }

    @Override
    public void parseChat(List<ContentItem> msgs) {
        rcChat.post(() -> chatAdapter.replaceData(msgs));
        rcChat.post(() -> rcChat.smoothScrollToPosition(0));
    }

    @Override
    public void parseChatRoom(ChatRoom chatRoom) {
        // do nothing
    }
}
