package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location{

	@SerializedName("lng")
	@Expose
	private double lng;

	@SerializedName("lat")
	@Expose
	private double lat;

	public void setLng(double lng){
		this.lng = lng;
	}

	public double getLng(){
		return lng;
	}

	public void setLat(double lat){
		this.lat = lat;
	}

	public double getLat(){
		return lat;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"lng = '" + lng + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}