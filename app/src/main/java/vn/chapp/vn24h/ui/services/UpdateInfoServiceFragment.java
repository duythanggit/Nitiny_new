package vn.chapp.vn24h.ui.services;

import android.content.Context;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.ui.main.BackableActivity;

public class UpdateInfoServiceFragment extends BaseFragment implements UpdateInfoServiceFrMvpView {

    public static final String TAG = UpdateInfoServiceFragment.class.getCanonicalName();

    @Inject
    UpdateInfoServiceFrPresenter<UpdateInfoServiceFrMvpView> presenter;

    private ConfirmLinkingDialog confirmLinkingDialog;

    public static UpdateInfoServiceFragment newInstance() {
        UpdateInfoServiceFragment fragment = new UpdateInfoServiceFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_update_info_service;
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

    @OnClick(R.id.btnSaveService)
    public void onSaveServiceClick(View v) {
        if (confirmLinkingDialog != null) confirmLinkingDialog.dismiss();
        confirmLinkingDialog = ConfirmLinkingDialog.newInstance(true);
        confirmLinkingDialog.show(getActivity().getSupportFragmentManager(),ConfirmLinkingDialog.TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity)getActivity()).setToolbarState(true,getString(R.string.title_toolbar_update_service));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity)getActivity()).restoreToolbar();
        }
    }
}
