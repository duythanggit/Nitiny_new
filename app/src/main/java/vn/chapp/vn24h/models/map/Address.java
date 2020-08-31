package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {

    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("address")
    @Expose
    private String addr;

    @SerializedName("addressComponents")
    @Expose
    private List<AddressComponentsItem> addressComponents;

    public Address(double lat, double lng, String addr, List<AddressComponentsItem> addressComponents) {
        this.lat = lat;
        this.lng = lng;
        this.addr = addr;
        this.addressComponents = addressComponents;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<AddressComponentsItem> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponentsItem> addressComponents) {
        this.addressComponents = addressComponents;
    }
}
