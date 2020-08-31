package vn.chapp.vn24h.models.service;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ServiceType implements Parcelable {
    private int type;
    private String title;
    private String adder;
    private List<ProductResponse> products;

    public ServiceType(int type, String title, String adder) {
        this.type = type;
        this.title = title;
        this.adder = adder;
    }

    protected ServiceType(Parcel in) {
        type = in.readInt();
        title = in.readString();
        adder = in.readString();
        products = in.createTypedArrayList(ProductResponse.CREATOR);
    }

    public static final Creator<ServiceType> CREATOR = new Creator<ServiceType>() {
        @Override
        public ServiceType createFromParcel(Parcel in) {
            return new ServiceType(in);
        }

        @Override
        public ServiceType[] newArray(int size) {
            return new ServiceType[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(adder);
        dest.writeTypedList(products);
    }
}
