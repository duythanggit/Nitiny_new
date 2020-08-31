package vn.chapp.vn24h.ui.detail;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.ui.widget.UiToolbar;

public class DetailActivity extends BaseActivity implements DetailMvpView, UiToolbar.OnToolbarWithBackClickListener {

    @Inject
    DetailMvpPresenter<DetailMvpView> presenter;

    @BindView(R.id.toolbarDetailActivity)
    UiToolbar toolbarDetailActivity;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        return intent;
    }

    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_detail;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);

        toolbarDetailActivity.setTitle(getResources().getString(R.string.toolbar_detail));
    }

    @Override
    protected void onDestroy() {
        clearActivity(this,R.id.detailContainer);
        super.onDestroy();
    }

    @Override
    public void onToolbarBackClick() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    public void onToolbarActionRightClick() {

    }
}
