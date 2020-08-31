package vn.chapp.vn24h.models.sale;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.chapp.vn24h.models.service.ProductResponse;

public class Scheduled implements Parcelable {

    @SerializedName("list_product")
    @Expose
    private List<ProductSchedule> listProduct;
    @SerializedName("date_booking")
    @Expose
    private String dateBooking;
    @SerializedName("time_booking")
    @Expose
    private String timeBooking;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("total_money")
    @Expose
    private float totalMoney;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("shop_id")
    @Expose
    private int shopId;
    @SerializedName("is_receive")
    @Expose
    private int isReceive;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("payment")
    @Expose
    private int payment;

    private ProductResponse productResponse;

    @SerializedName("service_id")
    @Expose
    private String serviceId;

    @SerializedName("phone")
    @Expose
    private String phone;

    public Scheduled() {
    }

    protected Scheduled(Parcel in) {
        listProduct = in.createTypedArrayList(ProductSchedule.CREATOR);
        dateBooking = in.readString();
        timeBooking = in.readString();
        number = in.readInt();
        totalMoney = in.readFloat();
        note = in.readString();
        address = in.readString();
        shopId = in.readInt();
        isReceive = in.readInt();
        type = in.readInt();
        payment = in.readInt();
        productResponse = in.readParcelable(ProductResponse.class.getClassLoader());
        serviceId = in.readString();
        phone = in.readString();
    }

    public static final Creator<Scheduled> CREATOR = new Creator<Scheduled>() {
        @Override
        public Scheduled createFromParcel(Parcel in) {
            return new Scheduled(in);
        }

        @Override
        public Scheduled[] newArray(int size) {
            return new Scheduled[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(listProduct);
        dest.writeString(dateBooking);
        dest.writeString(timeBooking);
        dest.writeInt(number);
        dest.writeFloat(totalMoney);
        dest.writeString(note);
        dest.writeString(address);
        dest.writeInt(shopId);
        dest.writeInt(isReceive);
        dest.writeInt(type);
        dest.writeInt(payment);
        dest.writeParcelable(productResponse, flags);
        dest.writeString(serviceId);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<ProductSchedule> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<ProductSchedule> listProduct) {
        this.listProduct = listProduct;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public void setTimeBooking(String timeBooking) {
        this.timeBooking = timeBooking;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(int isReceive) {
        this.isReceive = isReceive;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
