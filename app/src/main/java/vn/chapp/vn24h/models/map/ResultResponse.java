package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultResponse{

	@SerializedName("formatted_address")
	@Expose
	private String formattedAddress;

	@SerializedName("types")
	@Expose
	private List<String> types;

	@SerializedName("geometry")
	@Expose
	private Geometry geometry;

	@SerializedName("address_components")
	@Expose
	private List<AddressComponentsItem> addressComponents;

	@SerializedName("place_id")
	@Expose
	private String placeId;

	public void setFormattedAddress(String formattedAddress){
		this.formattedAddress = formattedAddress;
	}

	public String getFormattedAddress(){
		return formattedAddress;
	}

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public void setAddressComponents(List<AddressComponentsItem> addressComponents){
		this.addressComponents = addressComponents;
	}

	public List<AddressComponentsItem> getAddressComponents(){
		return addressComponents;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	@Override
 	public String toString(){
		return 
			"ResultResponse{" + 
			"formatted_address = '" + formattedAddress + '\'' + 
			",types = '" + types + '\'' + 
			",geometry = '" + geometry + '\'' + 
			",address_components = '" + addressComponents + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}