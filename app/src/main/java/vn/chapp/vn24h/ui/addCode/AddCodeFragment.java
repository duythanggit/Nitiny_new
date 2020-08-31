package vn.chapp.vn24h.ui.addCode;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.base.BaseRecyclerView;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.ui.shop.ShopDetailFragment;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.utils.AppUtils;

public class AddCodeFragment extends BaseFragment implements AddCodeFrMvpView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = AddCodeFragment.class.getCanonicalName();

    @Inject
    AddCodeFrPresenter<AddCodeFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    ShopLinkedAdapter shopLinkedAdapter;

    @BindView(R.id.rcShopLinked)
    BaseRecyclerView rcShopLinked;
    @BindView(R.id.btnAddCode)
    Button btnAddCode;
    @BindView(R.id.edtInsertCode)
    EditText edtInsertCode;

    public static AddCodeFragment newInstance() {
        AddCodeFragment fragment = new AddCodeFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_add_code;
    }

    @Override
    protected void init(View v) {
        rcShopLinked.setLayoutManager(linearLayoutManager);
        rcShopLinked.setAdapter(shopLinkedAdapter);
        rcShopLinked.setOnRefreshListener(this);
        presenter.doGetShopLinked();
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

    @OnClick(R.id.btnAddCode)
    public void onClickAddCode() {
        String code = edtInsertCode.getText().toString();
        presenter.doAddService(code);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity)getActivity()).setToolbarState(true,getString(R.string.title_add_code));
        }
    }

    @Override
    public void didAddShop(Integer idService) {
        AppUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), ShopDetailFragment.newInstance(idService, 0, null, 1),R.id.frBackable,true, ShopDetailFragment.TAG);
    }

    @Override
    public void didGetShopLinked(List<Shop> listShopLinked) {
        shopLinkedAdapter.replaceData(listShopLinked);
    }

    @Override
    public void setRefresh(boolean refresh) {
       getActivity().runOnUiThread(() -> rcShopLinked.setRefresh(refresh));
    }

    @Override
    public void onRefresh() {
        presenter.doGetShopLinked();
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        if (getActivity() != null && getActivity() instanceof BackableActivity) {
//            ((BackableActivity)getActivity()).restoreToolbar();
//        }
//    }

}
