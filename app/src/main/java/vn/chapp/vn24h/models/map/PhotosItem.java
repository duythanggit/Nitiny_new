package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotosItem{

	@SerializedName("photo_reference")
	@Expose
	private String photoReference;

	@SerializedName("width")
	@Expose
	private int width;

	@SerializedName("html_attributions")
	@Expose
	private List<String> htmlAttributions;

	@SerializedName("height")
	@Expose
	private int height;

	public void setPhotoReference(String photoReference){
		this.photoReference = photoReference;
	}

	public String getPhotoReference(){
		return photoReference;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setHtmlAttributions(List<String> htmlAttributions){
		this.htmlAttributions = htmlAttributions;
	}

	public List<String> getHtmlAttributions(){
		return htmlAttributions;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"PhotosItem{" + 
			"photo_reference = '" + photoReference + '\'' + 
			",width = '" + width + '\'' + 
			",html_attributions = '" + htmlAttributions + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}