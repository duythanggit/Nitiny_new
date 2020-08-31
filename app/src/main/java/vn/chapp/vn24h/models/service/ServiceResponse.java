package vn.chapp.vn24h.models.service;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponse implements Parcelable {

	@SerializedName("img")
	@Expose
	private String img;

	@SerializedName("code")
	@Expose
	private String code;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("id")
	@Expose
	private String id;

	protected ServiceResponse(Parcel in) {
		img = in.readString();
		code = in.readString();
		name = in.readString();
		id = in.readString();
	}

	public static final Creator<ServiceResponse> CREATOR = new Creator<ServiceResponse>() {
		@Override
		public ServiceResponse createFromParcel(Parcel in) {
			return new ServiceResponse(in);
		}

		@Override
		public ServiceResponse[] newArray(int size) {
			return new ServiceResponse[size];
		}
	};

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
			"ServiceResponse{" + 
			"img = '" + img + '\'' + 
			",code = '" + code + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(img);
		dest.writeString(code);
		dest.writeString(name);
		dest.writeString(id);
	}
}