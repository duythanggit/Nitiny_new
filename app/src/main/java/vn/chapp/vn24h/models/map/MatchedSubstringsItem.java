package vn.chapp.vn24h.models.map;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MatchedSubstringsItem implements Serializable {

	@SerializedName("offset")
	@Expose
	private int offset;

	@SerializedName("length")
	@Expose
	private int length;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setLength(int length){
		this.length = length;
	}

	public int getLength(){
		return length;
	}

	@Override
 	public String toString(){
		return 
			"MatchedSubstringsItem{" + 
			"offset = '" + offset + '\'' + 
			",length = '" + length + '\'' + 
			"}";
		}
}