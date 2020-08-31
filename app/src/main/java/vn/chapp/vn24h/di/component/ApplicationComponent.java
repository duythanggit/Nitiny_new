package vn.chapp.vn24h.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.di.module.ApplicationModule;
import vn.chapp.vn24h.di.ApplicationContext;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
