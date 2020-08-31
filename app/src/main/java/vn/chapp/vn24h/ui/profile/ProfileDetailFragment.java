package vn.chapp.vn24h.ui.profile;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.map.Address;
import vn.chapp.vn24h.models.map.PredictionsItem;
import vn.chapp.vn24h.ui.location.locationsearch.LocationSearchActivity;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.utils.AppUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

import static android.app.Activity.RESULT_OK;
import static vn.chapp.vn24h.ui.location.ChooseLocationActivity.ARG_ADDRESS;

public class ProfileDetailFragment extends BaseFragment implements ProfileDetailFrMvpView, TextWatcher, AdapterView.OnItemSelectedListener {

    public static final String TAG = ProfileDetailFragment.class.getCanonicalName();
    public static final int REQUEST_CODE_LOCATION = 20;

    @Inject
    ProfileDetailFrPresenter<ProfileDetailFrMvpView> presenter;

    @BindView(R.id.civAvatar)
    CircularImageView civAvatar;
    @BindView(R.id.tvName)
    EditText tvName;
    @BindView(R.id.tvUserCode)
    TextView tvUserCode;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvPhone)
    EditText tvPhone;
    @BindView(R.id.tvEmail)
    EditText tvEmail;
    @BindView(R.id.tvPassword)
    TextView tvPassword;

    private ArrayList<Image> avatarImage;
    private Address address;
    private ArrayAdapter adapterPlaces;
    private List<PredictionsItem> placeAddress;
    private Timer timerSearchPlace;
    private PredictionsItem placeAddressPicked;

    public static ProfileDetailFragment newInstance() {
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_profile_detail;
    }

    @Override
    protected void init(View v) {
        placeAddress = new ArrayList<>();
        adapterPlaces = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        //tvAddress.setAdapter(adapterPlaces);
        presenter.getUserDefault();
        presenter.getUserDetail();
        tvAddress.addTextChangedListener(this);
        //tvAddress.setOnItemSelectedListener(this);
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

    @OnClick(R.id.tvAddress)
    public void onAddressClick(View v) {
        startActivityForResult(LocationSearchActivity.newInstance(getContext(), tvAddress.getText().toString(), address), REQUEST_CODE_LOCATION);
    }

    @OnClick(R.id.imgEditAvatar)
    public void onCivAvatarClick(View v) {
        AppUtils.takeImageToFragment(this, avatarImage).start();
    }

    @OnClick(R.id.btnUpdate)
    public void onClickUpdate(View v) {
        String email = tvEmail.getText().toString();
        String name = tvName.getText().toString();
        String phone = tvPhone.getText().toString();

        if(address == null || TextUtils.isEmpty(email)){
            showMessage("Bạn chưa điền đủ thông tin email, địa chỉ");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showMessage(R.string.msg_error_empty_phone);
            return;
        }
        if (phone.length() != 10) {
            showMessage(R.string.msg_error_invalid_phone);
            return;
        }
        presenter.doUpdateProfile(address, email, name, phone);

//        if(address == null || TextUtils.isEmpty(email)) showMessage("Bạn chưa điền đủ thông tin email, địa chỉ");
//        if (placeAddressPicked != null) {
//            presenter.getDetailPlace(placeAddressPicked.getPlaceId());
//        } else {
//            presenter.doUpdateProfile(address, email);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data.getParcelableArrayListExtra(Config.EXTRA_IMAGES) != null) {
            avatarImage = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (avatarImage != null && avatarImage.size() > 0) {
                NetworkUtils.loadImage(getContext(), avatarImage.get(0).getPath(), civAvatar);
                presenter.doUploadAvatar(avatarImage.get(0).getPath());
            }
        } else if (requestCode == REQUEST_CODE_LOCATION && resultCode == RESULT_OK && data != null) {
            address = (Address) data.getSerializableExtra(ARG_ADDRESS);
            if (address != null) {
                if (!TextUtils.isEmpty(address.getAddr()))
                    tvAddress.setText(address.getAddr());
            }
        }
    }

    @Override
    public void parseUserDetail(UserDefault userDefault) {

        if (!TextUtils.isEmpty(userDefault.getAvatar()))
            NetworkUtils.loadImage(getContext(), userDefault.getAvatar(), civAvatar);
        if (!TextUtils.isEmpty(userDefault.getName())) {
            ((BackableActivity) getActivity()).setToolbarState(true, getString(R.string.title_profile) + " " + userDefault.getName());
            tvName.setText(userDefault.getName());
        }
        if (!TextUtils.isEmpty(userDefault.getName()))
            tvUserCode.setText(userDefault.getCode());
        if (!TextUtils.isEmpty(userDefault.getPhone()))
            tvPhone.setText(userDefault.getPhone());
        else tvPhone.setEnabled(true);

        if (!TextUtils.isEmpty(userDefault.getAddress())) {
            address = new Address(Double.valueOf(!TextUtils.isEmpty(userDefault.getLat()) ? userDefault.getLat() : "0"),Double.valueOf(!TextUtils.isEmpty(userDefault.getLng()) ? userDefault.getLng() : "0"),userDefault.getAddress(),null);
            tvAddress.setText(userDefault.getAddress());
        }
        if (!TextUtils.isEmpty(userDefault.getEmail()))
            tvEmail.setText(userDefault.getEmail());

    }

    @Override
    public void didUploadAvatar(String url) {
        NetworkUtils.loadImage(getContext(), url, civAvatar);
    }

    @Override
    public void didUpdateProfile(String address, String email, String name, String phone) {

    }

    @Override
    public void gotPlaces(List<PredictionsItem> predictionsItems, List<String> placeAddress) {
        this.placeAddress.clear();
        this.placeAddress.addAll(predictionsItems);
        adapterPlaces.clear();
        adapterPlaces.addAll(placeAddress);
        adapterPlaces.notifyDataSetChanged();
    }

    @Override
    public void gotPlaceDetail(Address address) {
        this.address = address;
        String email = tvEmail.getText().toString();
        String name = tvName.getText().toString();
        String phone = tvPhone.getText().toString();
        presenter.doUpdateProfile(address, email, name, phone);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (timerSearchPlace != null) timerSearchPlace.cancel();
    }

    @Override
    public void afterTextChanged(Editable s) {
        timerSearchPlace = new Timer();
        timerSearchPlace.schedule(new TimerTask() {
            @Override
            public void run() {
                if (s.toString().length() <= 1) {
                    adapterPlaces.clear();
                    adapterPlaces.notifyDataSetChanged();
                } else {
                    presenter.startSearchPlace(s.toString());
                }

            }
        },300);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        placeAddressPicked = placeAddress.get(position);
        address.setAddr(placeAddress.get(position).getDescription());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerSearchPlace != null) timerSearchPlace.cancel();
    }
}
