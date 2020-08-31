package vn.chapp.vn24h.models.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryResponse implements Parcelable {

	@SerializedName("date")
	@Expose
	private String date;

	@SerializedName("note")
	@Expose
	private String note;

	@SerializedName("address")
	@Expose
	private String address;

	@SerializedName("shop_avatar")
	@Expose
	private String shopAvatar;

	@SerializedName("active")
	@Expose
	private String active;

	@SerializedName("shop_name")
	@Expose
	private String shopName;

	@SerializedName("product_name")
	@Expose
	private String productName;

	@SerializedName("shop_id")
	@Expose
	private String shopId;

	@SerializedName("number")
	@Expose
	private String number;

	@SerializedName("user_id")
	@Expose
	private String userId;

	@SerializedName("date_booking")
	@Expose
	private String dateBooking;

	@SerializedName("service_id")
	@Expose
	private String serviceId;

	@SerializedName("product_id")
	@Expose
	private String productId;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("total_money")
	@Expose
	private String totalMoney;

	@SerializedName("time_booking")
	@Expose
	private String timeBooking;

	@SerializedName("is_receive")
	@Expose
	private String isReceive;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("phone")
	@Expose
	private String phone;


	protected HistoryResponse(Parcel in) {
		date = in.readString();
		note = in.readString();
		address = in.readString();
		shopAvatar = in.readString();
		active = in.readString();
		shopName = in.readString();
		productName = in.readString();
		shopId = in.readString();
		number = in.readString();
		userId = in.readString();
		dateBooking = in.readString();
		serviceId = in.readString();
		productId = in.readString();
		id = in.readString();
		totalMoney = in.readString();
		timeBooking = in.readString();
		isReceive = in.readString();
		type = in.readString();
		phone = in.readString();
	}

	public static final Creator<HistoryResponse> CREATOR = new Creator<HistoryResponse>() {
		@Override
		public HistoryResponse createFromParcel(Parcel in) {
			return new HistoryResponse(in);
		}

		@Override
		public HistoryResponse[] newArray(int size) {
			return new HistoryResponse[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setNote(String note){
		this.note = note;
	}

	public String getNote(){
		return note;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setShopAvatar(String shopAvatar){
		this.shopAvatar = shopAvatar;
	}

	public String getShopAvatar(){
		return shopAvatar;
	}

	public void setActive(String active){
		this.active = active;
	}

	public String getActive(){
		return active;
	}

	public void setShopName(String shopName){
		this.shopName = shopName;
	}

	public String getShopName(){
		return shopName;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setShopId(String shopId){
		this.shopId = shopId;
	}

	public String getShopId(){
		return shopId;
	}

	public void setNumber(String number){
		this.number = number;
	}

	public String getNumber(){
		return number;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setDateBooking(String dateBooking){
		this.dateBooking = dateBooking;
	}

	public String getDateBooking(){
		return dateBooking;
	}

	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}

	public String getServiceId(){
		return serviceId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTotalMoney(String totalMoney){
		this.totalMoney = totalMoney;
	}

	public String getTotalMoney(){
		return totalMoney;
	}

	public void setTimeBooking(String timeBooking){
		this.timeBooking = timeBooking;
	}

	public String getTimeBooking(){
		return timeBooking;
	}

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "HistoryResponse{" +
				"date='" + date + '\'' +
				", note='" + note + '\'' +
				", address='" + address + '\'' +
				", shopAvatar='" + shopAvatar + '\'' +
				", active='" + active + '\'' +
				", shopName='" + shopName + '\'' +
				", productName='" + productName + '\'' +
				", shopId='" + shopId + '\'' +
				", number='" + number + '\'' +
				", userId='" + userId + '\'' +
				", dateBooking='" + dateBooking + '\'' +
				", serviceId='" + serviceId + '\'' +
				", productId='" + productId + '\'' +
				", id='" + id + '\'' +
				", totalMoney='" + totalMoney + '\'' +
				", timeBooking='" + timeBooking + '\'' +
				", isReceive='" + isReceive + '\'' +
				", type='" + type + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(date);
		dest.writeString(note);
		dest.writeString(address);
		dest.writeString(shopAvatar);
		dest.writeString(active);
		dest.writeString(shopName);
		dest.writeString(productName);
		dest.writeString(shopId);
		dest.writeString(number);
		dest.writeString(userId);
		dest.writeString(dateBooking);
		dest.writeString(serviceId);
		dest.writeString(productId);
		dest.writeString(id);
		dest.writeString(totalMoney);
		dest.writeString(timeBooking);
		dest.writeString(isReceive);
		dest.writeString(type);
		dest.writeString(phone);
	}
}