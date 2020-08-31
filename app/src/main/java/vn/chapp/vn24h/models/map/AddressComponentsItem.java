package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddressComponentsItem implements Serializable {

	@SerializedName("types")
	@Expose
	private List<String> types;

	@SerializedName("short_name")
	@Expose
	private String shortName;

	@SerializedName("long_name")
	@Expose
	private String longName;

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	public String getShortName(){
		return shortName;
	}

	public void setLongName(String longName){
		this.longName = longName;
	}

	public String getLongName(){
		return longName;
	}

	@Override
 	public String toString(){
		return 
			"AddressComponentsItem{" + 
			"types = '" + types + '\'' + 
			",short_name = '" + shortName + '\'' + 
			",long_name = '" + longName + '\'' + 
			"}";
		}
}