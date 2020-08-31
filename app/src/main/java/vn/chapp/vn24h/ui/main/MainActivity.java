package vn.chapp.vn24h.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.models.FragmentController;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.ui.auth.AuthActivity;
import vn.chapp.vn24h.ui.booking.BookingFragment;
import vn.chapp.vn24h.ui.chat.ChatRoomFragment;
import vn.chapp.vn24h.ui.customer.CustomerFragment;
import vn.chapp.vn24h.ui.profile.ProfileFragment;
import vn.chapp.vn24h.ui.shop.ShopDetailFragment;
import vn.chapp.vn24h.ui.tichDiem.TichDiemFragment;
import vn.chapp.vn24h.ui.widget.UiToolbar;
import vn.chapp.vn24h.utils.AppUtils;

public class MainActivity extends BaseActivity implements MainMvpView, BottomNavigationView.OnNavigationItemSelectedListener, UiToolbar.OnToolbarWithBackClickListener {

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @Inject
    List<FragmentController> fragmentControllers;
    //    @Inject
//    LinkServiceFragment linkServiceFragment;
    @Inject
    ShopDetailFragment shopDetailFragment;

    @Inject
    TichDiemFragment tichDiemFragment;

    @Inject
    ChatRoomFragment chatfragment;
    @Inject
    BookingFragment bookingFragment;
    @Inject
    CustomerFragment customerFragment;
    @Inject
    ProfileFragment profileFragment;

    @BindView(R.id.mainBottomNavigation)
    BottomNavigationView mainBottomNavigation;
    @BindView(R.id.toolbarMainActivity)
    UiToolbar toolbarMainActivity;

    private Fragment activedFragment;
    private UserDefault userDefault;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private PermissionInterface permissionInterface;
    private boolean doubleBackToExitPressedOnce = false;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        userDefault = presenter.getUserDefault();

        mainBottomNavigation.setOnNavigationItemSelectedListener(this);

        fragmentControllers.add(new FragmentController(shopDetailFragment, ShopDetailFragment.TAG));
        fragmentControllers.add(new FragmentController(chatfragment, ChatRoomFragment.TAG));
        fragmentControllers.add(new FragmentController(bookingFragment, BookingFragment.TAG));
        fragmentControllers.add(new FragmentController(tichDiemFragment, TichDiemFragment.TAG));
        fragmentControllers.add(new FragmentController(profileFragment, ProfileFragment.TAG));
        activedFragment = AppUtils.addFragmentsToActivity(getSupportFragmentManager(), fragmentControllers, R.id.frMain, 0);
        toolbarMainActivity.setTitle(getResources().getString(R.string.str_home));
        toolbarMainActivity.setOnToolbarWithCloseClick(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getLocationPermission();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int maybeActive = -1;
        String title = "";
        boolean enableActionRight = false;
        int iconActionRight = -1;
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                maybeActive = 0;
                title = getResources().getString(R.string.str_home);
                break;
            case R.id.navigation_favourite:
                maybeActive = 1;
                title = getResources().getString(R.string.title_favourite);
                break;
            case R.id.navigation_history:
                maybeActive = 2;
                title = getResources().getString(R.string.title_booking_listing);
                break;
            case R.id.navigation_list_link:
                maybeActive = 3;
                title = getResources().getString(R.string.title_accumulate_points);
                break;
            case R.id.navigation_profile:
                maybeActive = 4;
                enableActionRight = true;
                iconActionRight = R.drawable.ic_logout;
                title = getResources().getString(R.string.title_profile) + " " + (!TextUtils.isEmpty(userDefault.getName()) ? userDefault.getName() : "");
                break;
        }
        if (maybeActive != -1) {
            toolbarMainActivity.setTitle(title);
            toolbarMainActivity.setActionRight(enableActionRight, iconActionRight);
            activedFragment = AppUtils.switchFragmentActivity(getSupportFragmentManager(), activedFragment, fragmentControllers.get(maybeActive).getFragment());
            return true;
        }
        return false;
    }

    @Override
    public void onToolbarBackClick() {
        if (isDetailShop)
            backToMain();
    }

    @Override
    public void onToolbarActionRightClick() {
        presenter.doLogout();
        finish();
        startActivity(AuthActivity.newInstance(this));
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            if (permissionInterface != null) permissionInterface.onLocationPermission(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        Log.d("TEST_123", requestCode + " " + grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    if (permissionInterface != null) permissionInterface.onLocationPermission(true);
                } else {
                    if (permissionInterface != null)
                        permissionInterface.onLocationPermission(false);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        clearActivity(this, R.id.rlMainActivity);
        super.onDestroy();
    }

    public void setOnPermissionListener(PermissionInterface permissionInterface) {
        this.permissionInterface = permissionInterface;
    }

    @Override
    public void onBackPressed() {
        if (isDetailShop) {
            backToMain();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.msg_back_again_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    private boolean isDetailShop = false;

    public void enableNavigationBottom(boolean isDetailShop) {
        mainBottomNavigation.setVisibility(isDetailShop ? View.GONE : View.VISIBLE);
        toolbarMainActivity.setEnableBack(isDetailShop);
        this.isDetailShop = isDetailShop;
    }

    public void setTitleToolBar(String title) {
        toolbarMainActivity.setTitle(title);
    }

    private void backToMain() {
        isDetailShop = !isDetailShop;
        enableNavigationBottom(false);
        this.getSupportFragmentManager().popBackStack();
        toolbarMainActivity.setTitle(getString(R.string.str_home));
        activedFragment = AppUtils.switchFragmentActivity(getSupportFragmentManager(), activedFragment, fragmentControllers.get(0).getFragment());
    }
}
