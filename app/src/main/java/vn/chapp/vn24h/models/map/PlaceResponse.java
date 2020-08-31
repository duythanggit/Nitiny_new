package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceResponse{

	@SerializedName("predictions")
	@Expose
	private List<PredictionsItem> predictions;

	@SerializedName("status")
	@Expose
	private String status;

	public void setPredictions(List<PredictionsItem> predictions){
		this.predictions = predictions;
	}

	public List<PredictionsItem> getPredictions(){
		return predictions;
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
			"PlaceResponse{" + 
			"predictions = '" + predictions + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}