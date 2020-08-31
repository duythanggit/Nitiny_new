package vn.chapp.vn24h.models.chat;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop{

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("avatar")
	@Expose
	private String avatar;

	@SerializedName("phone")
	@Expose
	private String phone;

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

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
 	public String toString(){
		return 
			"Shop{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",avatar = '" + avatar + '\'' + 
			"}";
		}
}