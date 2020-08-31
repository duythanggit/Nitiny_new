package vn.chapp.vn24h.ui.sample;

import android.view.View;

import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SampleFragment extends BaseFragment implements SampleFrMvpView {

    public static final String TAG = SampleFragment.class.getCanonicalName();

    @Inject
    SampleFrPresenter<SampleFrMvpView> presenter;

    public static SampleFragment newInstance() {
        SampleFragment fragment = new SampleFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_sample;
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
}
