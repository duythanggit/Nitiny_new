package vn.chapp.vn24h.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.di.component.DaggerActivityComponent;
import vn.chapp.vn24h.di.module.ActivityModule;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.KeyboardUtils;
import vn.chapp.vn24h.utils.NetworkUtils;
import vn.chapp.vn24h.utils.rx.Buser;
import vn.chapp.vn24h.utils.rx.RxBus;

public abstract class BaseActivity extends AppCompatActivity implements MvpView, BaseFragment.Callback {

    private DaggerActivityComponent.Builder builderActivityComponent;
    private ActivityComponent activityComponent;
    private Unbinder unbinder;
    private Dialog progressDialog;
    private AlertDialog.Builder alertDialogMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builderActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MainApp) getApplication()).getComponent());
        activityComponent = builderActivityComponent.build();
        onBeforeConfigView();
        setContentView(configView());
        init();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public DaggerActivityComponent.Builder getBuilderActivityComponent() {
        return builderActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            hideLoading();
            progressDialog = CommonUtils.showLoadingDialog(this);
        });

    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        });
    }

    @Override
    public void showMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showDialogMessage(int type, String title, String message, String positive, String negative, boolean cancelable, OnDialogMessageClick onDialogMessageClick) {
        if (alertDialogMessage == null) alertDialogMessage = new AlertDialog.Builder(this);
        alertDialogMessage.setCancelable(cancelable);
        if (!TextUtils.isEmpty(title)) alertDialogMessage.setTitle(title);
        if (!TextUtils.isEmpty(message)) alertDialogMessage.setMessage(message);
        if (!TextUtils.isEmpty(positive)) {
            alertDialogMessage.setPositiveButton(positive, (dialog, which) -> {
                if (onDialogMessageClick != null)
                    onDialogMessageClick.onPositiveDialogMessageClick(dialog, type);
            });
        }
        if (!TextUtils.isEmpty(negative)) {
            alertDialogMessage.setNegativeButton(negative, (dialog, which) -> {
                if (onDialogMessageClick != null)
                    onDialogMessageClick.onNegativeDialogMessageClick(dialog, type);
            });
        }
        alertDialogMessage.show();
    }

    @Override
    public void sendBuser(String key, Object values) {
        ((MainApp) getApplication()).getRxBus().send(new Buser(key, values));
    }

    @Override
    public void registerBuser(RxBus.OnMessageReceived onMessageReceived) {
        ((MainApp) getApplication()).getRxBus().registerBuser(onMessageReceived);
    }

    @Override
    public void unRegisterBuser() {
        ((MainApp) getApplication()).getRxBus().unRegisterBuser();
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void onFragmentAttached(String tag) {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        clearCurrentActivities();
        super.onDestroy();
    }

    // Release all resource from activity or fragment activity
    public synchronized void clearActivity(Activity activity,
                                           int rootLayout) {
        try {
            View view = activity.findViewById(rootLayout);
            if (view != null) {
                unbindViewReferences(view);
                if (view instanceof ViewGroup)
                    unbindViewGroupReferences((ViewGroup) view);
            }
            System.gc();
        } catch (Throwable e) {
        }
    }

    public synchronized void clearFragmentActivity(
            FragmentActivity activity, int rootLayout) {
        try {
            View view = activity.findViewById(rootLayout);
            if (view != null) {
                unbindViewReferences(view);
                if (view instanceof ViewGroup)
                    unbindViewGroupReferences((ViewGroup) view);
            }
            System.gc();
        } catch (Throwable e) {

        }
    }

    protected void unbindViewGroupReferences(ViewGroup viewGroup) {
        int nrOfChildren = viewGroup.getChildCount();
        for (int i = 0; i < nrOfChildren; i++) {
            View view = viewGroup.getChildAt(i);
            unbindViewReferences(view);
            if (view instanceof ViewGroup)
                unbindViewGroupReferences((ViewGroup) view);
        }
        try {
            viewGroup.removeAllViews();
        } catch (Throwable mayHappen) {
        }
    }

    protected void unbindViewReferences(View view) {
        try {
            view.setOnClickListener(null);
        } catch (Throwable mayHappen) {
        }

        try {
            view.setOnCreateContextMenuListener(null);
        } catch (Throwable mayHappen) {
        }

        try {
            view.setOnFocusChangeListener(null);
        } catch (Throwable mayHappen) {
        }

        try {
            view.setOnKeyListener(null);
        } catch (Throwable mayHappen) {
        }

        try {
            view.setOnLongClickListener(null);
        } catch (Throwable mayHappen) {
        }

        try {
            view.setOnClickListener(null);
        } catch (Throwable mayHappen) {
        }
        setNullView(view);
        if (view instanceof ImageView) {
            setNullImageView((ImageView) view);
        }

        if (view instanceof WebView) {
            ((WebView) view).destroy();
        }

        if (view instanceof Button) {
            Button bt = (Button) view;
            bt.setBackgroundResource(0);
        }
        if (view instanceof RelativeLayout) {
            RelativeLayout rl = (RelativeLayout) view;
            if (rl != null) {
                rl.destroyDrawingCache();
                rl.setBackgroundResource(0);
            }
        }
        if (view instanceof LinearLayout) {
            LinearLayout ln = (LinearLayout) view;
            if (ln != null) {
                ln.destroyDrawingCache();
                ln.setBackgroundResource(0);
            }
        }
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            if (view != null) {
                listView.destroyDrawingCache();
            }
        }

        if (view instanceof ConstraintLayout) {
            ConstraintLayout constraintLayout = (ConstraintLayout) view;
            if (constraintLayout != null) {
                constraintLayout.destroyDrawingCache();
                constraintLayout.setBackgroundResource(0);
            }
        }

    }

    @SuppressWarnings("deprecation")
    protected void setNullView(View view) {
        try {
            if (view != null) {
                view.setBackgroundDrawable(null);
                view.destroyDrawingCache();
            }
        } catch (Exception e) {
        }

    }

    @SuppressWarnings("deprecation")
    protected void setNullImageView(ImageView imgView) {
        try {
            if (imgView != null) {
                if (imgView.getDrawingCache() != null
                        && !imgView.getDrawingCache().isRecycled()) {
                    imgView.getDrawingCache().recycle();
                }
                imgView.destroyDrawingCache();
                imgView.setBackgroundDrawable(null);
                imgView.setImageBitmap(null);
                imgView.setImageDrawable(null);
                imgView.setBackgroundResource(0);
                imgView.setImageResource(0);
            }
        } catch (Exception e) {
        }
    }

    public void registerReceiverBusNotification() {
        ((MainApp) getApplication()).getRxBusNotification().registerBuser(buser -> {
        });
    }

    public void unRegisterReceiverBusNotification() {
        ((MainApp) getApplication()).getRxBusNotification().unRegisterBuser();
    }

    protected abstract void onBeforeConfigView();

    /**
     * @return layout của activity
     */
    protected abstract int configView();

    /**
     * Xử lý logic
     */
    protected abstract void init();

    public interface OnDatePickerListener {
        void onDateChoosed(DatePicker datePicker, int year, int month, int dayOfMonth, TextView view);
    }

    public interface OnFilterItemClickListener {
        void onFilterItemClickListener(int position, int type);
    }

    public interface OnLoadMoreFilterListener {
        void onLoadMoreFilter(int type);
    }

    public MainApp getMainApp() {
        return (MainApp) getApplication();
    }

    @Override
    protected void onResume() {
        ((MainApp) getApplication()).setCurrentActivity(this);
        super.onResume();
        registerReceiverBusNotification();
    }

    @Override
    protected void onPause() {
        clearCurrentActivities();
        super.onPause();
        unRegisterReceiverBusNotification();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void forceLogout() {
    }

    private void clearCurrentActivities() {
        Activity currActivity = ((MainApp) getApplication()).getCurrentActivity();
        if (this.equals(currActivity)) {
            ((MainApp) getApplication()).setCurrentActivity(null);
        }
    }

    public interface OnDialogMessageClick {
        void onPositiveDialogMessageClick(DialogInterface dialogInterface, int type);

        void onNegativeDialogMessageClick(DialogInterface dialogInterface, int type);
    }


}
