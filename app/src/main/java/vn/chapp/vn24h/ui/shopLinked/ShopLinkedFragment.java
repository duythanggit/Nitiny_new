package vn.chapp.vn24h.ui.shopLinked;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.base.BaseRecyclerView;
import vn.chapp.vn24h.base.OnItemClickListener;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.ui.services.LinkServiceFragment;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.utils.CommonUtils;

public class ShopLinkedFragment extends BaseFragment implements ShopLinkedFrMvpView,
        SwipeRefreshLayout.OnRefreshListener, OnItemClickListener,
        ListShopLinkedAdapter.OnClickItemLinkedListener {

    public static final String TAG = ShopLinkedFragment.class.getCanonicalName();

    public static final String ARG_ID_SERVICE_SHOP_LINKED = "ARG_ID_SERVICE_SHOP_LINKED";
    public static final String ARG_IS_ONLY_SHOPLINKED_SERVICE_SHOP_LINKED = "ARG_IS_ONLY_SHOPLINKED_SERVICE_SHOP_LINKED";

    @Inject
    ShopLinkedFrPresenter<ShopLinkedFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    ListShopLinkedAdapter shopLinkedAdapter;

    @BindView(R.id.rcShopLinked)
    BaseRecyclerView rcShopLinked;

    private int idService;
    private int isOnlyShopLinked;
    private List<Shop> shopListLinked;

    public static ShopLinkedFragment newInstance() {
        ShopLinkedFragment fragment = new ShopLinkedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_SERVICE_SHOP_LINKED, 10);
        args.putInt(ARG_IS_ONLY_SHOPLINKED_SERVICE_SHOP_LINKED, 1);
        return fragment;
    }

    public static ShopLinkedFragment newInstance(Integer idService, int isOnlyShopLinked) {
        ShopLinkedFragment fragment = new ShopLinkedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_SERVICE_SHOP_LINKED, idService);
        args.putInt(ARG_IS_ONLY_SHOPLINKED_SERVICE_SHOP_LINKED, isOnlyShopLinked);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_shop_linked;
    }

    @Override
    protected void init(View v) {
        idService = getArguments().getInt(ARG_ID_SERVICE_SHOP_LINKED, 0);
        isOnlyShopLinked = getArguments().getInt(ARG_IS_ONLY_SHOPLINKED_SERVICE_SHOP_LINKED, 0);

        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            String title = getString(R.string.title_shop_linked);
            title = title + " " + CommonUtils.getServiceName(idService);
            ((BackableActivity) getActivity()).setToolbarState(true, title);
        }

        rcShopLinked.setLayoutManager(linearLayoutManager);
        shopLinkedAdapter.setOnItemClickListener(this);
        shopLinkedAdapter.setOnClickItemLinkedListener(this);
        rcShopLinked.setAdapter(shopLinkedAdapter);
        rcShopLinked.setOnRefreshListener(this);
        presenter.doGetShopLinked(idService, isOnlyShopLinked);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).setToolbarState(true, getString(R.string.title_shop_linked));
        }
    }

    @Override
    public void didGetShopLinked(List<Shop> listShopLinked) {
        shopListLinked = listShopLinked;
        shopLinkedAdapter.replaceData(listShopLinked);
    }

    @Override
    public void setRefresh(boolean refresh) {
        getActivity().runOnUiThread(() -> rcShopLinked.setRefresh(refresh));
    }

    @Override
    public void didUnLink() {
        presenter.doGetShopLinked(idService, isOnlyShopLinked);

        //reload
        LinkServiceFragment linkServiceFragment = (LinkServiceFragment) (requireActivity()).getSupportFragmentManager().findFragmentByTag(LinkServiceFragment.TAG);
        if (linkServiceFragment != null) linkServiceFragment.reloadData(1);
    }

    @Override
    public void onRefresh() {
        presenter.doGetShopLinked(idService, isOnlyShopLinked);
    }

    @Override
    public void onClick(int position) {
        if (shopListLinked != null) startActivity(
                BackableActivity.newInstanceShopDetail(getContext(),
                        BackableActivity.NAVIGATOR_FDT,
                        shopListLinked.get(position).getServiceId(),
                        shopListLinked.get(position).getShopId())
        );
    }

    @Override
    public void onUnLink(int position) {
        presenter.doUnLink(String.valueOf(shopListLinked.get(position).getShopId()));
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        if (getActivity() != null && getActivity() instanceof BackableActivity) {
//            ((BackableActivity)getActivity()).restoreToolbar();
//        }
//    }

}
