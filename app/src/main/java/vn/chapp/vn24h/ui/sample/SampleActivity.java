package vn.chapp.vn24h.ui.sample;

import android.content.Context;
import android.content.Intent;

import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import javax.inject.Inject;

import butterknife.ButterKnife;

public class SampleActivity extends BaseActivity implements SampleMvpView {

    @Inject
    SampleMvpPresenter<SampleMvpView> presenter;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, SampleActivity.class);
        return intent;
    }

    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_sample;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);

    }
}
