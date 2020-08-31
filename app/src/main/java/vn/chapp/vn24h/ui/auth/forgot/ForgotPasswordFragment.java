package vn.chapp.vn24h.ui.auth.forgot;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import vn.chapp.vn24h.ui.main.MainActivity;

public class ForgotPasswordFragment extends BaseFragment implements ForgotPasswordFrMvpView, TextWatcher {

    public static final String TAG = ForgotPasswordFragment.class.getCanonicalName();

    public static final String ARG_PHONE = "ARG_PHONE";
    public static final String ARG_OTP = "ARG_OTP";

    @Inject
    ForgotPasswordFrPresenter<ForgotPasswordFrMvpView> presenter;

    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtNewPassword)
    EditText edtNewPassword;
    @BindView(R.id.edtOTP)
    EditText edtOTP;
    @BindView(R.id.edtPhone)
    TextView edtPhone;
    @BindView(R.id.tvResendOTP)
    TextView tvResendOTP;
    @BindView(R.id.tvCountDownTime)
    TextView tvCountDownTime;

    private String phone;
    private int otp;
    private String otpUserInput;
    private CountDownTimer countDownTimer;

    public static ForgotPasswordFragment newInstance(String phone, int otp) {
        Bundle arg = new Bundle();
        arg.putString(ARG_PHONE, phone);
        arg.putInt(ARG_OTP, otp);
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    protected void init(View v) {
        phone = getArguments().getString(ARG_PHONE, "");
        otp = getArguments().getInt(ARG_OTP, 0);
        edtPhone.setText(phone);

        if (otp != 0) {
            this.startCountDown();
        }
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

    @OnClick(R.id.tvResendOTP)
    public void onReSendOTP(View v) {
        presenter.doGetOTP(phone);
    }

    @OnClick(R.id.btnStart)
    public void onChangePassword(View v) {
        otpUserInput = edtOTP.getText().toString();
        if (!otpUserInput.equals(String.valueOf(otp))) {
            showMessage(R.string.msg_error_invalid_otp);
        } else if (TextUtils.isEmpty(edtNewPassword.getText().toString()) || !edtNewPassword.getText().toString().equals(edtPassword.getText().toString())) {
            showMessage(R.string.msg_error_invalid_repassword);
        } else {
            presenter.doChangePassword(phone, edtNewPassword.getText().toString());
        }
    }

    @Override
    public void didGetOTP(int otp) {
        this.otp = otp;
        this.startCountDown();
    }

    public void startCountDown() {
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

    @Override
    public void didLogin(UserDefault userDefault) {
        getActivity().finish();
        startActivity(MainActivity.newInstance(getContext()));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        otpUserInput = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (countDownTimer != null) countDownTimer.cancel();
    }
}
