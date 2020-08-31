

package vn.chapp.vn24h.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import vn.chapp.vn24h.base.GlideApp;
import vn.chapp.vn24h.data.network.NetworkBuilder;

import java.lang.reflect.Type;


public final class NetworkUtils {

    private NetworkUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static Object parseObjectFromJson(String json, Type type){
        try {
            return NetworkBuilder.provideGson().fromJson(json, type);
        } catch (Exception e) {
            return null;
        }

    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .into(imageView);
    }
    public static void loadImage(Context context, String url, ImageView imageView,int placeHolder,int error) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeHolder)
                .error(error)
                .into(imageView);
    }

    
}
