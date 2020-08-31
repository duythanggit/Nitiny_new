

package vn.chapp.vn24h.ui.auth.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseDialog;
import vn.chapp.vn24h.di.component.ActivityComponent;


public class RefCodeDialog extends BaseDialog implements RefCodeDialogMvpView {

    public static final String TAG = RefCodeDialog.class.getSimpleName();
    private static final String ARG_CAN_TOUCH_OUT_SIDE = "ARG_CAN_TOUCH_OUT_SIDE";


    @Inject
    RefCodeDialogMvpPresenter<RefCodeDialogMvpView> mPresenter;
    private OnSubmitRefCode onSubmitRefCode;

    @BindView(R.id.edtRefCode)
    EditText edtRefCode;


    public static RefCodeDialog newInstance(Boolean canTouchOutSide) {
        RefCodeDialog fragment = new RefCodeDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_CAN_TOUCH_OUT_SIDE, canTouchOutSide);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_ref_code, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        boolean canTouchOutSide = getArguments().getBoolean(ARG_CAN_TOUCH_OUT_SIDE, true);
        setCancelable(canTouchOutSide);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClick() {
        if (onSubmitRefCode != null) onSubmitRefCode.onSubmitRefCode(edtRefCode.getText().toString());
    }

    public interface OnSubmitRefCode {
        void onSubmitRefCode(String refCode);
    }

    public RefCodeDialog setOnSubmitRefCode(OnSubmitRefCode onSubmitRefCode) {
        this.onSubmitRefCode = onSubmitRefCode;
        return this;
    }
}