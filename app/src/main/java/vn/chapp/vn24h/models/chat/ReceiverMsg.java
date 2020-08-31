package vn.chapp.vn24h.models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ReceiverMsg{

	@SerializedName("idUser")
	@Expose
	private String idUser;

	@SerializedName("idHost")
	@Expose
	private String idHost;

	@SerializedName("sendFrom")
	@Expose
	private String sendFrom;

	@SerializedName("time")
	@Expose
	private String time;

	@SerializedName("type")
	@Expose
	private int type;

	@SerializedName("content")
	@Expose
	private String content;

	@SerializedName("idPost")
	@Expose
	private int idPost;

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setIdHost(String idHost){
		this.idHost = idHost;
	}

	public String getIdHost(){
		return idHost;
	}

	public void setSendFrom(String sendFrom){
		this.sendFrom = sendFrom;
	}

	public String getSendFrom(){
		return sendFrom;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setIdPost(int idPost){
		this.idPost = idPost;
	}

	public int getIdPost(){
		return idPost;
	}

	@Override
 	public String toString(){
		return 
			"ReceiverMsg{" + 
			"idUser = '" + idUser + '\'' + 
			",idHost = '" + idHost + '\'' + 
			",sendFrom = '" + sendFrom + '\'' + 
			",time = '" + time + '\'' + 
			",type = '" + type + '\'' + 
			",content = '" + content + '\'' + 
			",idPost = '" + idPost + '\'' + 
			"}";
		}
}