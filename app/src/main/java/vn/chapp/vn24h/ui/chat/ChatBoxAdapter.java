package vn.chapp.vn24h.ui.chat;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.adapter.SectionedRecyclerViewAdapter;
import vn.chapp.vn24h.base.adapter.SectionedViewHolder;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ChatBoxAdapter extends SectionedRecyclerViewAdapter<SectionedViewHolder> {

    private List<ChatRoom> chatRooms;
    private OnItemChatClickListener onItemChatClickListener;

    public ChatBoxAdapter(List<ChatRoom> chatRooms) {
        if (chatRooms != null) {
            this.chatRooms = chatRooms;
        } else {
            this.chatRooms = new ArrayList<>();
        }
    }

    public void replaceData(List<ChatRoom> chatRooms) {
        this.chatRooms.clear();
        this.chatRooms.addAll(chatRooms);
        notifyDataSetChanged();
    }


    @Override
    public int getSectionCount() {
        return 1;
    }

    @Override
    public int getItemCount(int section) {
        return chatRooms.size();
    }

    @Override
    public void onBindHeaderViewHolder(SectionedViewHolder holder, int section, boolean expanded) {

    }

    @Override
    public void onBindFooterViewHolder(SectionedViewHolder holder, int section) {

    }

    @Override
    public void onBindViewHolder(SectionedViewHolder holder, int section, int relativePosition, int absolutePosition) {
        ((ChatViewHolder) holder).onBind(section, relativePosition);
    }

    @NonNull
    @Override
    public SectionedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (i) {
            case VIEW_TYPE_HEADER:
                return new HeaderChatViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header_chat, viewGroup, false));
            case VIEW_TYPE_ITEM:
                return new ChatViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_box, viewGroup, false));
            default:
                return null;
        }
    }

    class HeaderChatViewHolder extends SectionedViewHolder {

        public HeaderChatViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ChatViewHolder extends SectionedViewHolder {

        @BindView(R.id.civAvatar)
        CircularImageView civAvatar;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvLastMsg)
        TextView tvLastMsg;

        public ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBind(int section, int relativePosition) {

            ChatRoom chatRoom = chatRooms.get(relativePosition);

            if (chatRoom.getUser() != null) {
                if (!TextUtils.isEmpty(chatRoom.getShop().getAvatar()))
                    NetworkUtils.loadImage(itemView.getContext(),chatRoom.getShop().getAvatar(),civAvatar,R.drawable.img_splash_logo,R.drawable.img_splash_logo);
                if (!TextUtils.isEmpty(chatRoom.getShop().getName()))
                    tvName.setText(chatRoom.getShop().getName());
            }
            if (chatRoom.getLastMessage() != null) {
                if (!TextUtils.isEmpty(chatRoom.getLastMessage().getContent()))
                    tvLastMsg.setText(chatRoom.getLastMessage().getContent());
                if (!TextUtils.isEmpty(chatRoom.getLastMessage().getTime()))
                    tvTime.setText(CommonUtils.formatCalendarToYYYYMMddHHmmSlash(CommonUtils.formatYYYYMMDDHHmmToCalendar(chatRoom.getLastMessage().getTime())));
            }


            itemView.setOnClickListener(v -> {
                if (onItemChatClickListener != null) onItemChatClickListener.onChatClick(relativePosition);
            });
        }
    }

    public interface OnItemChatClickListener{
        void onChatClick(int position);
    }

    public void setOnItemChatClickListener(OnItemChatClickListener onItemChatClickListener) {
        this.onItemChatClickListener = onItemChatClickListener;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }
}
