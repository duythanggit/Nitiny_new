package vn.chapp.vn24h.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Unbinder;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.di.component.DaggerActivityComponent;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.rx.RxBus;

public abstract class BaseFragment extends Fragment implements MvpView {

    private View root;
    private BaseActivity activity;
    private Unbinder unbinder;
    private Dialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(configView(), container, false);
        initCreatedView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            this.activity = activity;
        }
    }

    @Override
    public void showLoading() {
        if (getBaseActivity() != null)
            getBaseActivity().runOnUiThread(() -> {
                hideLoading();
                if (this.getContext() != null)
                    progressDialog = CommonUtils.showLoadingDialog(this.getContext());
            });

    }

    @Override
    public void hideLoading() {
        if (getBaseActivity() != null)
            getBaseActivity().runOnUiThread(() -> {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
            });

    }

    @Override
    public void showMessage(String message) {

        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    @Override
    public void showDialogMessage(int type, String title, String message, String positive, String negative, boolean cancelable, BaseActivity.OnDialogMessageClick onDialogMessageClick) {
        if (activity != null) {
            activity.showDialogMessage(type,title, message, positive, negative, cancelable, onDialogMessageClick);
        }
    }

    @Override
    public void forceLogout() {
        if (activity != null) {
            activity.forceLogout();
        }
    }

    @Override
    public void sendBuser(String key, Object values) {
        if (activity != null) {
            activity.sendBuser(key, values);
        }
    }

    @Override
    public void registerBuser(RxBus.OnMessageReceived onMessageReceived) {
        if (activity != null) {
            activity.registerBuser(onMessageReceived);
        }
    }

    @Override
    public void unRegisterBuser() {
        if (activity != null) {
            activity.unRegisterBuser();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (activity != null) {
            return activity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public ActivityComponent getActivityComponent() {
        if (activity != null) {
            return activity.getActivityComponent();
        }
        return null;
    }

    private DaggerActivityComponent.Builder getBuilderActivityComponent() {
        if (activity != null) {
            return activity.getBuilderActivityComponent();
        }
        return null;
    }


    public BaseActivity getBaseActivity() {
        return activity;
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    /**
     * @return view của fragment
     */
    protected abstract int configView();

    /**
     * Xử lý logic
     */
    protected abstract void init(View v);

    protected abstract void initCreatedView(View v);

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached(String tag);

        void onFragmentDetached(String tag);
    }
}
