package vn.chapp.vn24h.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.data.AppDataManager;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.AppApi;
import vn.chapp.vn24h.data.network.NetworkBuilder;
import vn.chapp.vn24h.data.prefs.Preference;
import vn.chapp.vn24h.data.prefs.PreferenceHelper;
import vn.chapp.vn24h.di.ApplicationContext;
import vn.chapp.vn24h.utils.rx.RxBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() { return application; }

    @Provides
    Application provideApplication() { return application; }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig
                .Builder()
                .setDefaultFontPath(application.getString(R.string.font_HelveticaNeue_Regular))
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
    @Provides
    @Singleton
    Gson provideGson() {
        return NetworkBuilder.provideGson();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }


    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(Preference appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    Preference providePreference(){
        return Preference.buildInstance(application);
    }

    @Provides
    @Singleton
    RxBus provideRxBus(){
        return new RxBus();
    }

    @Provides
    @Singleton
    @Named("networking")
    AppApi provideNetworkBuilder(){
        return NetworkBuilder.api(application);
    }

    @Provides
    @Singleton
    @Named("mappworking")
    AppApi provideMapNetworkBuilder(){
        return NetworkBuilder.mapApi(application);
    }

}
