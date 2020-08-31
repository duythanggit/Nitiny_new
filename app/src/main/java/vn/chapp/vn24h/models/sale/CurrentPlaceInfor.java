package vn.chapp.vn24h.models.sale;

import com.google.android.gms.maps.model.LatLng;

public class CurrentPlaceInfor {
    private String address;
    private LatLng latLng;

    public CurrentPlaceInfor(String address, LatLng latLng) {
        this.address = address;
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
