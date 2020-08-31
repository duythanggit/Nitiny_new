package vn.chapp.vn24h.models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentChatResponse{

	@SerializedName("name_shop")
	@Expose
	private String nameShop;

	@SerializedName("shop_id")
	@Expose
	private String shopId;

	@SerializedName("avatar_shop")
	@Expose
	private String avatarShop;

	@SerializedName("name_user")
	@Expose
	private String nameUser;

	@SerializedName("user_id")
	@Expose
	private String userId;

	@SerializedName("active")
	@Expose
	private String active;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("content")
	@Expose
	private List<ContentItem> content;

	@SerializedName("time_updated")
	@Expose
	private String timeUpdated;

	@SerializedName("avatar_user")
	@Expose
	private String avatarUser;

	public void setNameShop(String nameShop){
		this.nameShop = nameShop;
	}

	public String getNameShop(){
		return nameShop;
	}

	public void setShopId(String shopId){
		this.shopId = shopId;
	}

	public String getShopId(){
		return shopId;
	}

	public void setAvatarShop(String avatarShop){
		this.avatarShop = avatarShop;
	}

	public String getAvatarShop(){
		return avatarShop;
	}

	public void setNameUser(String nameUser){
		this.nameUser = nameUser;
	}

	public String getNameUser(){
		return nameUser;
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

	public void setContent(List<ContentItem> content){
		this.content = content;
	}

	public List<ContentItem> getContent(){
		return content;
	}

	public void setTimeUpdated(String timeUpdated){
		this.timeUpdated = timeUpdated;
	}

	public String getTimeUpdated(){
		return timeUpdated;
	}

	public void setAvatarUser(String avatarUser){
		this.avatarUser = avatarUser;
	}

	public String getAvatarUser(){
		return avatarUser;
	}

	@Override
 	public String toString(){
		return 
			"ContentChatResponse{" + 
			"name_shop = '" + nameShop + '\'' + 
			",shop_id = '" + shopId + '\'' + 
			",avatar_shop = '" + avatarShop + '\'' + 
			",name_user = '" + nameUser + '\'' + 
			",user_id = '" + userId + '\'' + 
			",active = '" + active + '\'' + 
			",id = '" + id + '\'' + 
			",content = '" + content + '\'' + 
			",time_updated = '" + timeUpdated + '\'' + 
			",avatar_user = '" + avatarUser + '\'' + 
			"}";
		}
}