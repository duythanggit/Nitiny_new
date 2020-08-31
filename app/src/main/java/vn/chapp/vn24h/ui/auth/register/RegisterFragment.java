package vn.chapp.vn24h.ui.auth.register;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.ui.auth.AuthActivity;
import vn.chapp.vn24h.ui.main.MainActivity;

public class RegisterFragment extends BaseFragment implements RegisterFrMvpView {

    public static final String TAG = RegisterFragment.class.getCanonicalName();

    @Inject
    RegisterFrPresenter<RegisterFrMvpView> presenter;

    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtRePassword)
    EditText edtRePassword;
    @BindView(R.id.edtOTP)
    EditText edtOTP;
    @BindView(R.id.tvResendOTP)
    TextView tvResendOTP;
    @BindView(R.id.edtRefCode)
    TextView edtRefCode;
    @BindView(R.id.tvCountDownTime)
    TextView tvCountDownTime;

    private int verifyOTP;

    private CountDownTimer countDownTimer;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.frangment_register;
    }

    @Override
    protected void init(View v) {

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

    @Override
    public void didVerifyOTP(int code) {
        verifyOTP = code;
    }

    @Override
    public void didRegister(UserDefault userDefault) {
        getActivity().finish();
        startActivity(MainActivity.newInstance(getContext()));
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClickListener(View v) {
        presenter.doRegisterInfo(
                verifyOTP,
                edtOTP.getText().toString(),
                edtName.getText().toString(),
                edtPhone.getText().toString(),
                edtPassword.getText().toString(),
                edtRePassword.getText().toString(),
                edtRefCode.getText().toString()
        );
    }

    @OnClick(R.id.tvResendOTP)
    public void onViewClicked() {
        presenter.doSendOTP(
                edtName.getText().toString(),
                edtPhone.getText().toString(),
                edtPassword.getText().toString(),
                edtRePassword.getText().toString()
        );

        countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set text to edittext
                tvResendOTP.setVisibility(View.INVISIBLE);
                tvCountDownTime.setVisibility(View.VISIBLE);
                tvCountDownTime.setText(String.format("Mã xác thực còn %d", millisUntilFinished / 1000));
            }

            public void onFinish() {
                tvResendOTP.setVisibility(View.VISIBLE);
                tvCountDownTime.setVisibility(View.INVISIBLE);
            }

        }.start();
    }

    @OnClick(R.id.tvLogin)
    public void onLoginClicked() {
        ((AuthActivity) getActivity()).onTitleLoginClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (countDownTimer!=null) countDownTimer.cancel();
    }
}
