package vn.chapp.vn24h.ui.services;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.base.OnItemClickListener;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.banner.BannerResponse;
import vn.chapp.vn24h.models.service.Service;
import vn.chapp.vn24h.ui.main.BackableActivity;

public class LinkServiceFragment extends BaseFragment implements LinkServiceFrMvpView, OnItemClickListener,
    RadioGroup.OnCheckedChangeListener{

    public static final String TAG = LinkServiceFragment.class.getCanonicalName();

    @Inject
    LinkServiceFrPresenter<LinkServiceFrMvpView> presenter;

    @Inject
    @Named("4Coloumn")
    GridLayoutManager gridLayoutManager;
    @Inject
    ServicesAdapter listServicedAdapter;

    @BindView(R.id.rcService)
    RecyclerView rcService;
    @BindView(R.id.tvAddCode)
    TextView tvAddCode;
    @BindView(R.id.sliderServices)
    SliderLayout sliderServices;
    @BindView(R.id.toggleViewShop)
    RadioGroup toggleViewShop;
    @BindView(R.id.rbShopQuen)
    RadioButton rbShopQuen;
    @BindView(R.id.rbShopAll)
    RadioButton rbShopAll;

    private ArrayList<Service> listService;
    private ArrayList<Service> listServiceLinked;
    private ArrayList<BannerResponse> bannerResponses;
    private ConfirmLinkingDialog confirmLinkingDialog;
    private int isOnLyShopLinked = 1;

    public static LinkServiceFragment newInstance() {
        LinkServiceFragment fragment = new LinkServiceFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_link_service;
    }

    @Override
    protected void init(View v) {

//        listService = new ArrayList<>();
        bannerResponses = new ArrayList<>();
        presenter.getBanners();
        rcService.setHasFixedSize(true);
        rcService.setLayoutManager(gridLayoutManager);
        listServicedAdapter.setType(isOnLyShopLinked);
        rcService.setAdapter(listServicedAdapter);
        listServicedAdapter.setOnItemClickListener(this);

        confirmLinkingDialog = new ConfirmLinkingDialog();

        toggleViewShop.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
            this.listService = new ArrayList<>();
            this.listServiceLinked = new ArrayList<>();
            if(MainApp.newInstance().getListService() != null) {
                this.listService.clear();
                this.listService.addAll(MainApp.newInstance().getListService());
                listServicedAdapter.replaceData(listService);
            } else {
                presenter.loadService(2);
                presenter.loadServiceLinked(1);
            }

            presenter.getWallet();
        }

    }

    @Override
    public void onClick(int position) {
//        AppUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(),UpdateInfoServiceFragment.newInstance(),R.id.frBackable,true,UpdateInfoServiceFragment.TAG);
//        startActivity(BackableActivity.newInstanceShopDetail(getContext(), BackableActivity.NAVIGATOR_FDT, this.listService.get(position).getId(), 0));
        // type: 1: đã liên kết, 2: tất cả
        int idService = 0;
        if(isOnLyShopLinked==1) {
            idService = this.listServiceLinked.get(position).getId();
        } else {
            idService = this.listService.get(position).getId();
        }

        startActivity(
                BackableActivity.newInstanceShopLinked(getContext(),
                        BackableActivity.NAVIGATOR_SHOP_LINKED,
                        idService,
                        isOnLyShopLinked)
        );
    }

    @OnClick(R.id.tvAddCode)
    public void onClickAddCode() {
//        AppUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), AddCodeFragment.newInstance(),R.id.frBackable,true,AddCodeFragment.TAG);
        startActivity(BackableActivity.newInstance(getContext(),BackableActivity.NAVIGATOR_FADD));
    }

    @OnClick(R.id.ivSearch)
    public void onSearchShopClick(View v) {
        startActivity(BackableActivity.newInstanceShopNearBy(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity)getActivity()).setToolbarState(true,getString(R.string.title_link_services));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity)getActivity()).restoreToolbar();
        }
    }

    @Override
    public void didGetListService(List<Service> listService) {
        this.listService.clear();
        this.listService.addAll(listService);
        MainApp.newInstance().setListService(listService);

        if(isOnLyShopLinked == 2) {
            listServicedAdapter.replaceData(listService);
        }
    }

    @Override
    public void gotBanners(List<BannerResponse> bannerResponses) {
        this.bannerResponses.clear();
        this.bannerResponses.addAll(bannerResponses);
        for(BannerResponse banner : bannerResponses){
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .image(banner.getImg())
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderServices.addSlider(textSliderView);
        }
    }

    @Override
    public void didGetListServiceLinked(List<Service> listService) {
        this.listServiceLinked.clear();
        this.listServiceLinked.addAll(listService);

        if(isOnLyShopLinked == 1) {
            listServicedAdapter.replaceData(listService);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId==R.id.rbShopAll) {
            isOnLyShopLinked = 2;
            rbShopQuen.setTextColor(ContextCompat.getColor(getContext(), R.color.color242937));
            rbShopAll.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFFFFFF));

            listServicedAdapter.setType(isOnLyShopLinked);
            listServicedAdapter.replaceData(listService);
        } else if (checkedId==R.id.rbShopQuen) {
            isOnLyShopLinked = 1;
            rbShopQuen.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFFFFFF));
            rbShopAll.setTextColor(ContextCompat.getColor(getContext(), R.color.color242937));

            // re render list
            listServicedAdapter.setType(isOnLyShopLinked);
            listServicedAdapter.replaceData(this.listServiceLinked);
        }
    }

    // type: 1: đã liên kết, 2: tất cả
    public void reloadData(int type) {
        if (type == 1) presenter.loadServiceLinked(1); else presenter.loadService(2);
    }

}
