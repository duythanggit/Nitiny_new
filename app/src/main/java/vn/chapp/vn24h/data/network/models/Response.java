package vn.chapp.vn24h.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response<T>{

	@SerializedName("errorId")
	@Expose
	private int error;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("data")
	@Expose
	private T data;

	@SerializedName("dataPage")
	@Expose
	private DataPage dataPage;

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public DataPage getDataPage() {
		return dataPage;
	}

	public void setDataPage(DataPage dataPage) {
		this.dataPage = dataPage;
	}

	public boolean isSuccess(){
		return error == 200;
	}

	public boolean isSuccessNonNull(){
		return isSuccess() && data != null;
	}

	public boolean canLoadMore() {
		return dataPage != null && dataPage.isNextPage();
	}

	@Override
	public String toString(){
		return
				"Response{" +
						"error = '" + error + '\'' +
						",message = '" + message + '\'' +
						"}";
	}

}