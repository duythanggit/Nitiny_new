package vn.chapp.vn24h.models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRoom{

	@SerializedName("shop")
	@Expose
	private Shop shop;

	@SerializedName("last_message")
	@Expose
	private LastMessage lastMessage;

	@SerializedName("time")
	@Expose
	private String time;

	@SerializedName("user")
	@Expose
	private User user;

	@SerializedName("time_updated")
	@Expose
	private String timeUpdated;

	public void setShop(Shop shop){
		this.shop = shop;
	}

	public Shop getShop(){
		return shop;
	}

	public void setLastMessage(LastMessage lastMessage){
		this.lastMessage = lastMessage;
	}

	public LastMessage getLastMessage(){
		return lastMessage;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setTimeUpdated(String timeUpdated){
		this.timeUpdated = timeUpdated;
	}

	public String getTimeUpdated(){
		return timeUpdated;
	}

	@Override
 	public String toString(){
		return 
			"ChatRoom{" + 
			"shop = '" + shop + '\'' + 
			",last_message = '" + lastMessage + '\'' + 
			",time = '" + time + '\'' + 
			",user = '" + user + '\'' + 
			",time_updated = '" + timeUpdated + '\'' + 
			"}";
		}
}