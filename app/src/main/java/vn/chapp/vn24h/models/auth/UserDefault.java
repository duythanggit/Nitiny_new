package vn.chapp.vn24h.models.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDefault {

	@SerializedName("date")
	@Expose
	private String date;

	@SerializedName("note")
	@Expose
	private String note;

	@SerializedName("code")
	@Expose
	private String code;

	@SerializedName("address")
	@Expose
	private String address;

	@SerializedName("lng")
	@Expose
	private String lng;

	@SerializedName("star")
	@Expose
	private String star;

	@SerializedName("active")
	@Expose
	private String active;

	@SerializedName("avatar")
	@Expose
	private String avatar;

	@SerializedName("password")
	@Expose
	private String password;

	@SerializedName("phone")
	@Expose
	private String phone;

	@SerializedName("number_rating")
	@Expose
	private String numberRating;

	@SerializedName("service_id")
	@Expose
	private String serviceId;

	@SerializedName("company_name")
	@Expose
	private String companyName;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("total_rating")
	@Expose
	private String totalRating;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("lat")
	@Expose
	private String lat;

	@SerializedName("email")
	@Expose
	private String email;

	@SerializedName("service_name")
	@Expose
	private String serviceName;

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

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setStar(String star){
		this.star = star;
	}

	public String getStar(){
		return star;
	}

	public void setActive(String active){
		this.active = active;
	}

	public String getActive(){
		return active;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setNumberRating(String numberRating){
		this.numberRating = numberRating;
	}

	public String getNumberRating(){
		return numberRating;
	}

	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}

	public String getServiceId(){
		return serviceId;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTotalRating(String totalRating){
		this.totalRating = totalRating;
	}

	public String getTotalRating(){
		return totalRating;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
 	public String toString(){
		return 
			"UserDefault{" +
			"date = '" + date + '\'' + 
			",note = '" + note + '\'' + 
			",code = '" + code + '\'' + 
			",address = '" + address + '\'' + 
			",lng = '" + lng + '\'' + 
			",star = '" + star + '\'' + 
			",active = '" + active + '\'' + 
			",avatar = '" + avatar + '\'' + 
			",password = '" + password + '\'' + 
			",phone = '" + phone + '\'' + 
			",number_rating = '" + numberRating + '\'' + 
			",service_id = '" + serviceId + '\'' + 
			",company_name = '" + companyName + '\'' + 
			",name = '" + name + '\'' + 
			",total_rating = '" + totalRating + '\'' + 
			",id = '" + id + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}