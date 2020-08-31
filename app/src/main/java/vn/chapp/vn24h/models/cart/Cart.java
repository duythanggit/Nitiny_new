package vn.chapp.vn24h.models.cart;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart implements Parcelable {

    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("product")
    @Expose
    private List<CartProduct> product;

    private boolean isChoose;

    protected Cart(Parcel in) {
        shopId = in.readString();
        shopName = in.readString();
        product = in.createTypedArrayList(CartProduct.CREATOR);
        isChoose = in.readByte() != 0;
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<CartProduct> getProduct() {
        return product;
    }

    public void setProduct(List<CartProduct> product) {
        this.product = product;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopId);
        dest.writeString(shopName);
        dest.writeTypedList(product);
        dest.writeByte((byte) (isChoose ? 1 : 0));
    }
}
