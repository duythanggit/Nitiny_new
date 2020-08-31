package vn.chapp.vn24h.data.network;

import android.location.Location;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.chapp.vn24h.MainApp;

public class NetworkParams {

    public NetworkParams() {
    }

    public static final HashMap<String, String> authHeader(String token) {
        HashMap<String, String> data = new HashMap<>();
        data.put("Authorization", token);
        return data;
    }

    public static String contentTypeUpload(MultipartBody body) {
        return "multipart/form-data; boundary=" + body.boundary();
    }

    public static MultipartBody bodyUploadAvatar(String patch) {
        File file = new File(patch);
        MultipartBody body;
        Compressor compressor = new Compressor(MainApp.newInstance());
        try {
            file = compressor.setQuality(80).compressToFile(file);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
            body = new MultipartBody.Builder()
                    .addFormDataPart("user_id",MainApp.newInstance().getPreference().getUserDefault().getId())
                    .addFormDataPart("img", file.getName(), fileBody)
                    .build();
            return body;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final HashMap<String,Object> paramsShopNearBy(Location location, String serviceId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("user_id", MainApp.newInstance().getPreference().getUserDefault().getId());
        if (location != null){
            data.put("lat",location.getLatitude());
            data.put("lng",location.getLongitude());
        } else {
            data.put("lat", 0);
            data.put("lng", 0);
        }
        if (!TextUtils.isEmpty(serviceId))
            data.put("service_id",serviceId);
        return data;
    }

}
