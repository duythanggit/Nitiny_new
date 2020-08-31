package vn.chapp.vn24h.data.network.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DataPage {

	@SerializedName("total")
	@Expose
	private int total;

	@SerializedName("perPage")
	@Expose
	private int perPage;

	@SerializedName("lastPage")
	@Expose
	private int lastPage;

	@SerializedName("nextPage")
	@Expose
	private boolean nextPage;

	@SerializedName("currentPage")
	@Expose
	private int currentPage;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setPerPage(int perPage){
		this.perPage = perPage;
	}

	public int getPerPage(){
		return perPage;
	}

	public void setLastPage(int lastPage){
		this.lastPage = lastPage;
	}

	public int getLastPage(){
		return lastPage;
	}

	public void setNextPage(boolean nextPage){
		this.nextPage = nextPage;
	}

	public boolean isNextPage(){
		return nextPage;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	@Override
 	public String toString(){
		return 
			"DataPage{" + 
			"total = '" + total + '\'' + 
			",perPage = '" + perPage + '\'' + 
			",lastPage = '" + lastPage + '\'' + 
			",nextPage = '" + nextPage + '\'' + 
			",currentPage = '" + currentPage + '\'' + 
			"}";
		}
}