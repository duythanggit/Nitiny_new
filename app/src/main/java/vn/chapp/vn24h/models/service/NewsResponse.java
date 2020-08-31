package vn.chapp.vn24h.models.service;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewsResponse implements Parcelable {

	@SerializedName("date")
	@Expose
	private String date;

	@SerializedName("shop_id")
	@Expose
	private String shopId;

	@SerializedName("img")
	@Expose
	private String img;

	@SerializedName("date_start")
	@Expose
	private String dateStart;

	@SerializedName("active")
	@Expose
	private String active;

	@SerializedName("date_end")
	@Expose
	private String dateEnd;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("content")
	@Expose
	private String content;

	@SerializedName("name")
	@Expose
	private String name;

	protected NewsResponse(Parcel in) {
		date = in.readString();
		shopId = in.readString();
		img = in.readString();
		dateStart = in.readString();
		active = in.readString();
		dateEnd = in.readString();
		id = in.readString();
		title = in.readString();
		type = in.readString();
		content = in.readString();
		name = in.readString();
	}

	public static final Creator<NewsResponse> CREATOR = new Creator<NewsResponse>() {
		@Override
		public NewsResponse createFromParcel(Parcel in) {
			return new NewsResponse(in);
		}

		@Override
		public NewsResponse[] newArray(int size) {
			return new NewsResponse[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setShopId(String shopId){
		this.shopId = shopId;
	}

	public String getShopId(){
		return shopId;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setDateStart(String dateStart){
		this.dateStart = dateStart;
	}

	public String getDateStart(){
		return dateStart;
	}

	public void setActive(String active){
		this.active = active;
	}

	public String getActive(){
		return active;
	}

	public void setDateEnd(String dateEnd){
		this.dateEnd = dateEnd;
	}

	public String getDateEnd(){
		return dateEnd;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
 	public String toString(){
		return 
			"NewsResponse{" + 
			"date = '" + date + '\'' + 
			",shop_id = '" + shopId + '\'' + 
			",img = '" + img + '\'' + 
			",date_start = '" + dateStart + '\'' + 
			",active = '" + active + '\'' + 
			",date_end = '" + dateEnd + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",content = '" + content + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(date);
		dest.writeString(shopId);
		dest.writeString(img);
		dest.writeString(dateStart);
		dest.writeString(active);
		dest.writeString(dateEnd);
		dest.writeString(id);
		dest.writeString(title);
		dest.writeString(type);
		dest.writeString(content);
		dest.writeString(name);
	}
}