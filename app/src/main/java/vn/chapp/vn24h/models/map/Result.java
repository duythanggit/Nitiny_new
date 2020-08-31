package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result{

		@SerializedName("utc_offset")
		@Expose
		private int utcOffset;

		@SerializedName("formatted_address")
		@Expose
		private String formattedAddress;

		@SerializedName("types")
		@Expose
		private List<String> types;

		@SerializedName("icon")
		@Expose
		private String icon;

		@SerializedName("geometry")
		@Expose
		private Geometry geometry;

		@SerializedName("address_components")
		@Expose
		private List<AddressComponentsItem> addressComponents;

		@SerializedName("photos")
		@Expose
		private List<PhotosItem> photos;

		@SerializedName("url")
		@Expose
		private String url;

		@SerializedName("reference")
		@Expose
		private String reference;

		@SerializedName("scope")
		@Expose
		private String scope;

		@SerializedName("name")
		@Expose
		private String name;

		@SerializedName("id")
		@Expose
		private String id;

		@SerializedName("adr_address")
		@Expose
		private String adrAddress;

		@SerializedName("place_id")
		@Expose
		private String placeId;

		@SerializedName("plus_code")
		@Expose
		private PlusCode plusCode;

	public void setUtcOffset(int utcOffset){
		this.utcOffset = utcOffset;
	}

	public int getUtcOffset(){
		return utcOffset;
	}

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

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
	}

	public void setAddressComponents(List<AddressComponentsItem> addressComponents){
		this.addressComponents = addressComponents;
	}

	public List<AddressComponentsItem> getAddressComponents(){
		return addressComponents;
	}

	public void setPhotos(List<PhotosItem> photos){
		this.photos = photos;
	}

	public List<PhotosItem> getPhotos(){
		return photos;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setScope(String scope){
		this.scope = scope;
	}

	public String getScope(){
		return scope;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAdrAddress(String adrAddress){
		this.adrAddress = adrAddress;
	}

	public String getAdrAddress(){
		return adrAddress;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	public void setPlusCode(PlusCode plusCode){
		this.plusCode = plusCode;
	}

	public PlusCode getPlusCode(){
		return plusCode;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"utc_offset = '" + utcOffset + '\'' + 
			",formatted_address = '" + formattedAddress + '\'' + 
			",types = '" + types + '\'' + 
			",icon = '" + icon + '\'' + 
			",address_components = '" + addressComponents + '\'' + 
			",photos = '" + photos + '\'' + 
			",url = '" + url + '\'' + 
			",reference = '" + reference + '\'' + 
			",scope = '" + scope + '\'' + 
			",name = '" + name + '\'' + 
			",geometry = '" + geometry + '\'' + 
			",id = '" + id + '\'' + 
			",adr_address = '" + adrAddress + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}