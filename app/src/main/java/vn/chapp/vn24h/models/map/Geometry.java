package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry{

	@SerializedName("viewport")
	@Expose
	private Viewport viewport;

	@SerializedName("bounds")
	@Expose
	private Bounds bounds;

	@SerializedName("location")
	@Expose
	private Location location;

	@SerializedName("location_type")
	@Expose
	private String locationType;

	public void setViewport(Viewport viewport){
		this.viewport = viewport;
	}

	public Viewport getViewport(){
		return viewport;
	}

	public void setBounds(Bounds bounds){
		this.bounds = bounds;
	}

	public Bounds getBounds(){
		return bounds;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setLocationType(String locationType){
		this.locationType = locationType;
	}

	public String getLocationType(){
		return locationType;
	}

	@Override
 	public String toString(){
		return 
			"Geometry{" + 
			"viewport = '" + viewport + '\'' + 
			",bounds = '" + bounds + '\'' + 
			",location = '" + location + '\'' + 
			",location_type = '" + locationType + '\'' + 
			"}";
		}
}