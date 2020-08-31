package vn.chapp.vn24h.data.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.chapp.vn24h.utils.CommonUtils;

import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.schedulers.Schedulers;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkBuilder {

    public static final Charset UTF8 = Charset.forName("UTF-8");
    private static NetworkBuilder instance;
    private final AppApi suApi;

    private NetworkBuilder(Context context, String domain) {

        final Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl                (domain)
                                .addConverterFactory    (GsonConverterFactory.create(provideGson()))
                                .addCallAdapterFactory  (RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                                .client(provideOkhttpClientBasic())
                                .build();

        suApi                   = retrofit.create(AppApi.class);
    }

    public static NetworkBuilder build(Context context) {

        instance = new NetworkBuilder(context, AppApi.DOMAIN);

        return instance;
    }

    public static AppApi api(Context context) {
        return build(context).suApi;
    }

    public static NetworkBuilder buildMapApi(Context context) {
        instance = new NetworkBuilder(context,AppApi.MAP_API_DOMAIN);
        return instance;
    }

    public static AppApi mapApi(Context context) {
        return buildMapApi(context).suApi;
    }


    public static Gson provideGson() {

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }


    private OkHttpClient provideOkhttpClient(Context context)  {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        final Interceptor paramtersInterceptor = chain -> {
            final Request original = chain.request();
            final HttpUrl originalHttpUrl = original.url();
            HttpUrl url = originalHttpUrl.newBuilder()
                    .build();
            final Request request = original.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        };
        return new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(paramtersInterceptor)
                .build();
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient(Context context) {
        try {
            // Create a trust customer that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust customer
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting customer
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            final Interceptor paramtersInterceptor = chain -> {
                final Request original = chain.request();
                final HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("uuid", CommonUtils.getDeviceId(context))
                        .build();
                final Request request = original.newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(request);
            };

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.connectTimeout(3, TimeUnit.MINUTES);
            builder.readTimeout(3, TimeUnit.MINUTES);
            builder.writeTimeout(3, TimeUnit.MINUTES);
            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(paramtersInterceptor);

            builder.hostnameVerifier((hostname, session) -> true);
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private OkHttpClient provideOkhttpClientBasic()  {
        return new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .build();
    }

}
