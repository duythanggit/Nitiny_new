package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailPlaceResponse{

	@SerializedName("result")
	@Expose
	private Result result;

	@SerializedName("results")
	@Expose
	private List<Result> resultsComponent;

	@SerializedName("html_attributions")
	@Expose
	private List<Object> htmlAttributions;

	@SerializedName("status")
	@Expose
	private String status;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public List<Result> getResultsComponent() {
		return resultsComponent;
	}

	public void setResultsComponent(List<Result> resultsComponent) {
		this.resultsComponent = resultsComponent;
	}

	public void setHtmlAttributions(List<Object> htmlAttributions){
		this.htmlAttributions = htmlAttributions;
	}

	public List<Object> getHtmlAttributions(){
		return htmlAttributions;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DetailPlaceResponse{" + 
			"result = '" + result + '\'' + 
			",html_attributions = '" + htmlAttributions + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}