package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class TermsItem implements Serializable {

	@SerializedName("offset")
	@Expose
	private int offset;

	@SerializedName("value")
	@Expose
	private String value;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"TermsItem{" + 
			"offset = '" + offset + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}