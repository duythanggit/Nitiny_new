package vn.chapp.vn24h.models.sale;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductSchedule implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("booking_id")
    @Expose
    private int bookingId;
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("active")
    @Expose
    private int active;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("price_discount")
    @Expose
    private String priceDiscount;
    @SerializedName("price_1")
    @Expose
    private String price1;
    @SerializedName("price_2")
    @Expose
    private String price2;
    @SerializedName("price_3")
    @Expose
    private String price3;
    @SerializedName("type_user")
    @Expose
    private String typeUser;

    private int indexSpinner;

    public ProductSchedule(int productId, int number, float priceDiscount, int indexSpinner, String productName, float price, String img) {
        this.productId = productId;
        this.number = number;
        this.priceDiscount = String.valueOf(priceDiscount);
        this.indexSpinner = indexSpinner;
        this.productName = productName;
        this.price = price;
        this.img = img;
    }

    protected ProductSchedule(Parcel in) {
        id = in.readInt();
        bookingId = in.readInt();
        productId = in.readInt();
        number = in.readInt();
        price = in.readFloat();
        date = in.readString();
        active = in.readInt();
        productName = in.readString();
        img = in.readString();
        priceDiscount = in.readString();
        price1 = in.readString();
        price2 = in.readString();
        price3 = in.readString();
        typeUser = in.readString();
        indexSpinner = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(bookingId);
        dest.writeInt(productId);
        dest.writeInt(number);
        dest.writeFloat(price);
        dest.writeString(date);
        dest.writeInt(active);
        dest.writeString(productName);
        dest.writeString(img);
        dest.writeString(priceDiscount);
        dest.writeString(price1);
        dest.writeString(price2);
        dest.writeString(price3);
        dest.writeString(typeUser);
        dest.writeInt(indexSpinner);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductSchedule> CREATOR = new Creator<ProductSchedule>() {
        @Override
        public ProductSchedule createFromParcel(Parcel in) {
            return new ProductSchedule(in);
        }

        @Override
        public ProductSchedule[] newArray(int size) {
            return new ProductSchedule[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIndexSpinner() {
        return indexSpinner;
    }

    public void setIndexSpinner(int indexSpinner) {
        this.indexSpinner = indexSpinner;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
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

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    @NonNull
    @Override
    public String toString() {
        return productName +" "+number+" "+price+" "+price*number;
    }
}
