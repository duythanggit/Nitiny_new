package vn.chapp.vn24h.models.map;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationResponse{

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("error")
	@Expose
	private boolean error;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}
}