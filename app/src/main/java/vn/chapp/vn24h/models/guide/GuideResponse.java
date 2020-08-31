package vn.chapp.vn24h.models.guide;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuideResponse {

	@SerializedName("date")
	@Expose
	private String date;

	@SerializedName("active")
	@Expose
	private String active;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("content")
	@Expose
	private String content;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
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

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
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
			"GuideResponse{" + 
			"date = '" + date + '\'' + 
			",active = '" + active + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",content = '" + content + '\'' + 
			"}";
		}
}