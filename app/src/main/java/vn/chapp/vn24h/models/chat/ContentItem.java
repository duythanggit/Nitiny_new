package vn.chapp.vn24h.models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentItem{

	@SerializedName("shop_id")
	@Expose
	private String shopId;

	@SerializedName("user_id")
	@Expose
	private String userId;

	@SerializedName("time")
	@Expose
	private String time;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("content")
	@Expose
	private String content;

	public ContentItem(String shopId, String userId, String time, String type, String content) {
		this.shopId = shopId;
		this.userId = userId;
		this.time = time;
		this.type = type;
		this.content = content;
	}

	public void setShopId(String shopId){
		this.shopId = shopId;
	}

	public String getShopId(){
		return shopId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
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

	@Override
 	public String toString(){
		return 
			"ContentItem{" + 
			"shop_id = '" + shopId + '\'' + 
			",user_id = '" + userId + '\'' + 
			",time = '" + time + '\'' + 
			",type = '" + type + '\'' + 
			",content = '" + content + '\'' + 
			"}";
		}
}