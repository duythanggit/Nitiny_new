package vn.chapp.vn24h.service;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.ui.main.MainActivity;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.AppLogger;
import vn.chapp.vn24h.utils.rx.RxUtil;

public class CustomerFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        Log.d("TOKEN_FIREBASE", s);
        MainApp.newInstance().getPreference().setTokenFirebase(s);
        this.updateToken(s);
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
//        Log.d("TOKEN_FIREBASE", message+"");
        if(message.getNotification()!=null && message.getNotification().getBody()!=null) sendMyNotification(message.getNotification().getBody());
    }

    private void sendMyNotification(String message) {

        //On click of notification it redirect to this Activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Shop")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(103, notificationBuilder.build());
    }

    @SuppressLint("CheckResult")
    public void updateToken(String token) {
        if(!MainApp.newInstance().getPreference().isLogin()) return;
        RxUtil.appleHandlerStartFinish(
                MainApp.newInstance().getNetworking().updateTokenFCM(MainApp.newInstance().getPreference().getUserDefault().getId(), AppConstants.APP_TYPE, token),
                () -> {},
                () -> {})
                .compose(RxUtil.applySchedulers())
                .subscribe(
                        tResponse -> {
                            if (tResponse.isSuccess()) {
                                AppLogger.d("FCM", "update token fcm success");
                            } else {
                                switch (tResponse.getError()) {
                                    default:
                                        AppLogger.d("FCM", "update token fcm faile");
                                        break;

                                }
                            }

                        },
                        throwable -> {
                            AppLogger.d("FCM", "update token fcm faile");
                        }
            );
    }
}
