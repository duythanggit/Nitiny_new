package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class PredictionsItem implements Serializable {

	@SerializedName("reference")
	@Expose
	private String reference;

	@SerializedName("types")
	@Expose
	private List<String> types;

	@SerializedName("matched_substrings")
	@Expose
	private List<MatchedSubstringsItem> matchedSubstrings;

	@SerializedName("terms")
	@Expose
	private List<TermsItem> terms;

	@SerializedName("description")
	@Expose
	private String description;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("place_id")
	@Expose
	private String placeId;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setMatchedSubstrings(List<MatchedSubstringsItem> matchedSubstrings){
		this.matchedSubstrings = matchedSubstrings;
	}

	public List<MatchedSubstringsItem> getMatchedSubstrings(){
		return matchedSubstrings;
	}

	public void setTerms(List<TermsItem> terms){
		this.terms = terms;
	}

	public List<TermsItem> getTerms(){
		return terms;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
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
			"PredictionsItem{" + 
			"reference = '" + reference + '\'' + 
			",types = '" + types + '\'' + 
			",matched_substrings = '" + matchedSubstrings + '\'' + 
			",terms = '" + terms + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}