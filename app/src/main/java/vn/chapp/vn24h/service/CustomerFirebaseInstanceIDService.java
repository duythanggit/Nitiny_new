package vn.chapp.vn24h.service;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import vn.chapp.vn24h.MainApp;

public class CustomerFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN_FIREBASE", token);
        MainApp.newInstance().getPreference().setTokenFirebase(token);

    }
}
