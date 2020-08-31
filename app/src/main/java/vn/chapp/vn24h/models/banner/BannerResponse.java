package vn.chapp.vn24h.models.banner;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerResponse implements Parcelable {

	@SerializedName("date")
	@Expose
	private String date;

	@SerializedName("img")
	@Expose
	private String img;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("active")
	@Expose
	private String active;

	@SerializedName("id")
	@Expose
	private String id;

	public BannerResponse(String date, String img, String name, String active, String id) {
		this.date = date;
		this.img = img;
		this.name = name;
		this.active = active;
		this.id = id;
	}

	protected BannerResponse(Parcel in) {
		date = in.readString();
		img = in.readString();
		name = in.readString();
		active = in.readString();
		id = in.readString();
	}

	public static final Creator<BannerResponse> CREATOR = new Creator<BannerResponse>() {
		@Override
		public BannerResponse createFromParcel(Parcel in) {
			return new BannerResponse(in);
		}

		@Override
		public BannerResponse[] newArray(int size) {
			return new BannerResponse[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setActive(String active){
		this.active = active;
	}

	public String getActive(){
		return active;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"BannerResponse{" + 
			"date = '" + date + '\'' + 
			",img = '" + img + '\'' + 
			",name = '" + name + '\'' + 
			",active = '" + active + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(date);
		dest.writeString(img);
		dest.writeString(name);
		dest.writeString(active);
		dest.writeString(id);
	}
}