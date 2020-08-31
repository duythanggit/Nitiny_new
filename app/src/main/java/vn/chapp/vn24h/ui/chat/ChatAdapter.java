package vn.chapp.vn24h.ui.chat;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.chat.ContentItem;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ChatAdapter extends BaseAdapter<ContentItem> {

    public static final int TYPE_SENDER = 1;
    public static final int TYPE_RECEIVER = 2;
    private String urlAvatar;

    public ChatAdapter(List<ContentItem> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_RECEIVER:
                return new ChatReceiverViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_receiver,viewGroup,false));
            case TYPE_SENDER:
                return new ChatSenderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_sender,viewGroup,false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getCollection().get(position).getType().equals("1") ? TYPE_SENDER : TYPE_RECEIVER;
    }

    class ChatReceiverViewHolder extends BaseViewHolder {
        @BindView(R.id.tvMsg)
        TextView tvMsg;
        @BindView(R.id.civAvatar)
        CircularImageView civAvatar;

        public ChatReceiverViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            ContentItem contentItem = getCollection().get(position);
            if (!TextUtils.isEmpty(contentItem.getContent()))
                tvMsg.setText(contentItem.getContent());
            civAvatar.setVisibility(position == 0 || getCollection().get(position - 1).getType().equals("1") ? View.VISIBLE : View.INVISIBLE);
            if (!TextUtils.isEmpty(urlAvatar))
                NetworkUtils.loadImage(itemView.getContext(),urlAvatar,civAvatar,R.drawable.img_splash_logo,R.drawable.img_splash_logo);
        }
    }

    class ChatSenderViewHolder extends BaseViewHolder {
        @BindView(R.id.tvMsg)
        TextView tvMsg;

        public ChatSenderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            ContentItem contentItem = getCollection().get(position);
            if (!TextUtils.isEmpty(contentItem.getContent()))
                tvMsg.setText(contentItem.getContent());
        }
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
}
