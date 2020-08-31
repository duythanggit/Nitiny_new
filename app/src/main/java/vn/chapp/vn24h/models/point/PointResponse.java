package vn.chapp.vn24h.models.point;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointResponse implements Parcelable {

	@SerializedName("date")
	@Expose
	private String date;

	@SerializedName("shop_id")
	@Expose
	private String shopId;

	@SerializedName("img")
	@Expose
	private String img;

	@SerializedName("user_id")
	@Expose
	private String userId;

	@SerializedName("active")
	@Expose
	private String active;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("shop_name")
	@Expose
	private String shopName;

	@SerializedName("point")
	@Expose
	private String point;

	protected PointResponse(Parcel in) {
		date = in.readString();
		shopId = in.readString();
		img = in.readString();
		userId = in.readString();
		active = in.readString();
		id = in.readString();
		shopName = in.readString();
		point = in.readString();
	}

	public static final Creator<PointResponse> CREATOR = new Creator<PointResponse>() {
		@Override
		public PointResponse createFromParcel(Parcel in) {
			return new PointResponse(in);
		}

		@Override
		public PointResponse[] newArray(int size) {
			return new PointResponse[size];
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

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
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

	public void setShopName(String shopName){
		this.shopName = shopName;
	}

	public String getShopName(){
		return shopName;
	}

	public void setPoint(String point){
		this.point = point;
	}

	public String getPoint(){
		return point;
	}

	@Override
 	public String toString(){
		return 
			"PointResponse{" + 
			"date = '" + date + '\'' + 
			",shop_id = '" + shopId + '\'' + 
			",img = '" + img + '\'' + 
			",user_id = '" + userId + '\'' + 
			",active = '" + active + '\'' + 
			",id = '" + id + '\'' + 
			",shop_name = '" + shopName + '\'' + 
			",point = '" + point + '\'' + 
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
		dest.writeString(userId);
		dest.writeString(active);
		dest.writeString(id);
		dest.writeString(shopName);
		dest.writeString(point);
	}
}