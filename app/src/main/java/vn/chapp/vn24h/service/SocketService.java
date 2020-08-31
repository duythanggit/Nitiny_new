package vn.chapp.vn24h.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.models.chat.ContentItem;
import vn.chapp.vn24h.models.chat.ReceiverMsg;
import vn.chapp.vn24h.utils.AppLogger;

public class SocketService extends Service {

    private final IBinder socketBinder = new SocketBinder();
    private Socket socket;
    private OnMessagerListener onMessagerListener;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            socket = IO.socket("http://45.119.82.138:4001");
        } catch (URISyntaxException e) {}
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return socketBinder;
    }

    public class SocketBinder extends Binder {
        public SocketService getService() {
            return SocketService.this;
        }
    }

    public void sendMessage(String sendFrom, String idUser, String idHost, int idPost, int type, String time, String content) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sendFrom", sendFrom);
            jsonObject.put("idUser", idUser);
            jsonObject.put("idHost", idHost);
            jsonObject.put("idPost", idPost);
            jsonObject.put("type", type);
            jsonObject.put("time", time);
            jsonObject.put("content",content);
            socket.emit("shop_chat", jsonObject);
            if (onMessagerListener != null) onMessagerListener.onSendMessager(
                    new ContentItem(idHost,idUser,time,String.valueOf(type),content)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(){
        socket.connect();
        AppLogger.d("Do connect");
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                AppLogger.d("Connected");
                socket.off("shop_send_chat");
                onSendChat();

            }
        });
    }

    public void onSendChat(){
        socket.on("shop_send_chat", args -> {
            if (args == null || args.length == 0) return;
            ReceiverMsg receiverMsg = MainApp.newInstance().getGson().fromJson(args[0].toString(),ReceiverMsg.class);
            if (onMessagerListener != null) onMessagerListener.onReceiverMessager(
                  new ContentItem(
                          receiverMsg.getIdHost(),
                          receiverMsg.getIdUser(),
                          receiverMsg.getTime(),
                          String.valueOf(receiverMsg.getType()),
                          receiverMsg.getContent()
                  )

            );
            AppLogger.d(args[0].toString());
        });
    }

    public interface OnMessagerListener {
        void onSendMessager(ContentItem msg);
        void onReceiverMessager(ContentItem msg);
    }

    public void setOnMessagerListener(OnMessagerListener onMessagerListener) {
        this.onMessagerListener = onMessagerListener;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (socket != null) {
            socket.disconnect();
            socket.off("shop_send_chat");
            socket.off(Socket.EVENT_CONNECT);
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (socket != null) {
            socket.disconnect();
            socket.off("shop_send_chat");
            socket.off(Socket.EVENT_CONNECT);
        }
        super.onDestroy();
    }
}
