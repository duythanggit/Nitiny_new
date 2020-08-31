

package vn.chapp.vn24h.ui.services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseDialog;
import vn.chapp.vn24h.di.component.ActivityComponent;


public class ConfirmLinkingDialog extends BaseDialog implements ConfirmLinkingDialogMvpView {

    public static final String TAG = ConfirmLinkingDialog.class.getSimpleName();
    private static final String ARG_CAN_TOUCH_OUT_SIDE = "ARG_CAN_TOUCH_OUT_SIDE";
    public static final String ARG_LESSON = "ARG_LESSON";


    @Inject
    ConfirmLinkingDialogMvpPresenter<ConfirmLinkingDialogMvpView> mPresenter;


    public static ConfirmLinkingDialog newInstance(Boolean canTouchOutSide) {
        ConfirmLinkingDialog fragment = new ConfirmLinkingDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_CAN_TOUCH_OUT_SIDE, canTouchOutSide);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_confirm_linking, container, false);

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
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

}