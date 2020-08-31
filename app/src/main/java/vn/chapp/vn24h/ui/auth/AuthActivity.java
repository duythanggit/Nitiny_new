package vn.chapp.vn24h.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.models.FragmentController;
import vn.chapp.vn24h.ui.auth.login.LoginFragment;
import vn.chapp.vn24h.ui.auth.register.RegisterFragment;
import vn.chapp.vn24h.utils.AppUtils;

public class AuthActivity extends BaseActivity implements AuthMvpView {

    @Inject
    AuthMvpPresenter<AuthMvpView> presenter;
    @Inject
    LoginFragment loginFragment;
    @Inject
    RegisterFragment registerFragment;
    @Inject
    List<FragmentController> fragmentControllers;


    private Fragment activedFragment;


    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        return intent;
    }

    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_auth;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        fragmentControllers.add(new FragmentController(loginFragment, LoginFragment.TAG));
        fragmentControllers.add(new FragmentController(registerFragment, RegisterFragment.TAG));
        activedFragment = AppUtils.addFragmentsToActivity(getSupportFragmentManager(), fragmentControllers, R.id.frAuth, 0);
    }

    public void onTitleRegisterClick() {
        if (activedFragment != null && activedFragment instanceof RegisterFragment) return;
        activedFragment = AppUtils.switchFragmentActivity(getSupportFragmentManager(), activedFragment, registerFragment);
    }

    public void onTitleLoginClick() {
        if (activedFragment != null && activedFragment instanceof LoginFragment) return;
        activedFragment = AppUtils.switchFragmentActivity(getSupportFragmentManager(), activedFragment, loginFragment);
    }
}
