package vn.chapp.vn24h.models.cart;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartProduct implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("ag_id")
    @Expose
    private String agId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("price_discount")
    @Expose
    private String priceDiscount;
    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("type_user")
    @Expose
    private String typeUser;

    @SerializedName("price_1")
    @Expose
    private String price1;

    @SerializedName("price_2")
    @Expose
    private String price2;

    @SerializedName("price_3")
    @Expose
    private String price3;

    protected CartProduct(Parcel in) {
        id = in.readString();
        userId = in.readString();
        shopId = in.readString();
        agId = in.readString();
        productId = in.readString();
        price = in.readString();
        number = in.readString();
        date = in.readString();
        active = in.readString();
        productName = in.readString();
        priceDiscount = in.readString();
        img = in.readString();
        typeUser = in.readString();
        price1 = in.readString();
        price2 = in.readString();
        price3 = in.readString();
    }

    public static final Creator<CartProduct> CREATOR = new Creator<CartProduct>() {
        @Override
        public CartProduct createFromParcel(Parcel in) {
            return new CartProduct(in);
        }

        @Override
        public CartProduct[] newArray(int size) {
            return new CartProduct[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(shopId);
        dest.writeString(agId);
        dest.writeString(productId);
        dest.writeString(price);
        dest.writeString(number);
        dest.writeString(date);
        dest.writeString(active);
        dest.writeString(productName);
        dest.writeString(priceDiscount);
        dest.writeString(img);
        dest.writeString(typeUser);
        dest.writeString(price1);
        dest.writeString(price2);
        dest.writeString(price3);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }
}