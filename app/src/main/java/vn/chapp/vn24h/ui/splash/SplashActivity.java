package vn.chapp.vn24h.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import javax.inject.Inject;

import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.ui.auth.AuthActivity;
import vn.chapp.vn24h.ui.main.MainActivity;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashMvpPresenter<SplashMvpView> presenter;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        new Handler().postDelayed(runnableDirect, 1000);
    }

    private Runnable runnableDirect = new Runnable() {
        @Override
        public void run() {
            finish();
            if (!presenter.isLoggedIn()) {
                startActivity(AuthActivity.newInstance(SplashActivity.this));
            } else {
                startActivity(MainActivity.newInstance(SplashActivity.this));
            }
        }
    };

    @Override
    protected void onDestroy() {
        clearActivity(this, R.id.parentView);
        super.onDestroy();
    }
}
