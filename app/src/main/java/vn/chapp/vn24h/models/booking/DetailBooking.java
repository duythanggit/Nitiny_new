package vn.chapp.vn24h.models.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.chapp.vn24h.models.sale.ProductSchedule;

public class DetailBooking {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("shop_id")
    @Expose
    private int shopId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("total_money")
    @Expose
    private float totalMoney;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("date_booking")
    @Expose
    private String dateBooking;
    @SerializedName("time_booking")
    @Expose
    private String timeBooking;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("is_receive")
    @Expose
    private int isReceive;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("active")
    @Expose
    private int active;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_phone")
    @Expose
    private String shopPhone;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;
    @SerializedName("shop_avatar")
    @Expose
    private String shopAvatar;
    @SerializedName("list_product")
    @Expose
    private List<ProductSchedule> listProduct;
    @SerializedName("payment")
    @Expose
    private String payment;
    @SerializedName("service_name")
    @Expose
    private String service_name;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAvatar() {
        return shopAvatar;
    }

    public void setShopAvatar(String shopAvatar) {
        this.shopAvatar = shopAvatar;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public List<ProductSchedule> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<ProductSchedule> listProduct) {
        this.listProduct = listProduct;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
}
