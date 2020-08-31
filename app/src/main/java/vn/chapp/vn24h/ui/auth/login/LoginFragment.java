package vn.chapp.vn24h.ui.auth.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.ui.auth.AuthActivity;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.ui.main.MainActivity;

public class LoginFragment extends BaseFragment implements LoginFrMvpView, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = LoginFragment.class.getCanonicalName();

    private static final int RC_SIGN_IN = 1009;

    @Inject
    LoginFrPresenter<LoginFrMvpView> presenter;

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @BindView(R.id.tvPolicy)
    TextView tvPolicy;

    private GoogleSignInClient mGoogleSignInClient;

    private RefCodeDialog refCodeDialog;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void init(View v) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tvRegister.setText(Html.fromHtml(getString(R.string.title_register_login), Html.FROM_HTML_MODE_COMPACT));
//            tvPolicy.setText(Html.fromHtml(getString(R.string.title_policy_and_tearms), Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            tvRegister.setText(Html.fromHtml(getString(R.string.title_register_login)));
//            tvPolicy.setText(Html.fromHtml(getString(R.string.title_policy_and_tearms)));
//        }

    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
        }

    }

    @OnClick(R.id.tvForgot)
    public void onForgotPasswordClick(View v) {
        String phone = edtPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showMessage(R.string.msg_empty_phone);
            return;
        }
        if (phone.length() != 10) {
            showMessage(R.string.msg_error_invalid_phone);
            return;
        }
        presenter.doForgetPassword(phone);
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClick(View v) {
        presenter.doLogin(
                edtPhone.getText().toString(),
                edtPassword.getText().toString()
        );
    }

    @OnClick(R.id.tvRegister)
    public void onRegisterClick(View v) {
        ((AuthActivity) getActivity()).onTitleRegisterClick();
    }

    @Override
    public void didLogin(UserDefault userDefault) {
        Log.d("TOKEN_FIREBASE", userDefault.getId().toString());
        getActivity().finish();
        startActivity(MainActivity.newInstance(getContext()));
    }

    @Override
    public void didForgetPassword(String phone, int otp) {
        startActivity(BackableActivity.newInstanceForgotPassword(getContext(), phone, otp));
    }

    @OnClick(R.id.imgGoogle)
    public void onClickLoginGoogle(View v) {
        signIn();
    }

    @OnClick({R.id.imgFacebook})
    public void onClickLoginFacebook(View v) {
        showMessage("Chức năng này đang được phát triển");
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            doRegisterRefCode(account);
            presenter.doLoginGoogle(account.getEmail(), account.getId(), account.getDisplayName(), "");
        } catch (ApiException e) {
            showMessage(R.string.msg_error_login_google);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void doRegisterRefCode(GoogleSignInAccount account) {
        if (refCodeDialog != null) refCodeDialog.dismiss();
        refCodeDialog = RefCodeDialog.newInstance(false);
        refCodeDialog.setOnSubmitRefCode(refCode -> {
           refCodeDialog.dismiss();
           presenter.doLoginGoogle(account.getEmail(), account.getId(), account.getDisplayName(),refCode);
        }).show(getActivity().getSupportFragmentManager(),RefCodeDialog.TAG);
    }
}
