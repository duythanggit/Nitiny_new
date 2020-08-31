package vn.chapp.vn24h;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.chapp.vn24h.data.network.AppApi;
import vn.chapp.vn24h.data.prefs.Preference;
import vn.chapp.vn24h.di.component.ApplicationComponent;
import vn.chapp.vn24h.di.component.DaggerApplicationComponent;
import vn.chapp.vn24h.di.module.ApplicationModule;
import vn.chapp.vn24h.models.sale.CurrentPlaceInfor;
import vn.chapp.vn24h.models.service.Service;
import vn.chapp.vn24h.utils.AppLogger;
import vn.chapp.vn24h.utils.rx.RxBus;

public class MainApp extends Application {

    private static MainApp instance;

    @Inject
    CalligraphyConfig calligraphyConfig;
    @Inject
    Gson gson;
    @Inject
    RxBus rxBus;
    @Inject
    RxBus rxBusNotification;
    @Inject
    Preference preference;

    @Inject
    @Named("networking")
    AppApi networking;

    @Inject
    @Named("mappworking")
    AppApi mapNetworking;

    private ApplicationComponent applicationComponent;
    private Activity mCurrentActivity = null;
    private List<Service> listService;
    private CurrentPlaceInfor currentPlaceInfor;
    private String balance = "0";

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        applicationComponent =  DaggerApplicationComponent
                                .builder()
                                .applicationModule(new ApplicationModule(this))
                                .build();

        applicationComponent.inject(this);

        AppLogger.init();

        CalligraphyConfig.initDefault(calligraphyConfig);

    }

    public static MainApp newInstance() {
        return instance;
    }

    public Gson getGson() {
        return gson;
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public RxBus getRxBus() {
        return rxBus;
    }

    public RxBus getRxBusNotification() {
        return rxBusNotification;
    }

    public Preference getPreference() {
        return preference;
    }

    public AppApi getNetworking() {
        return networking;
    }

    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    public AppApi getMapNetworking() {
        return mapNetworking;
    }

    public void setListService(List<Service> listService) {
        this.listService = listService;
    }

    public List<Service> getListService() {
        return this.listService;
    }

    public CurrentPlaceInfor getCurrentPlaceInfor() {
        return currentPlaceInfor;
    }

    public void setCurrentPlaceInfor(CurrentPlaceInfor currentPlaceInfor) {
        this.currentPlaceInfor = currentPlaceInfor;
    }

    public void setBalance(String balance) {
        if(TextUtils.isEmpty(balance)) return;
        if(balance==null) return;
        this.balance = balance;
    }

    public String getBalance() {
        return this.balance;
    }
}
