package vn.chapp.vn24h.models.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindStockBody {
    @SerializedName("departure")
    @Expose
    private String departure;

    @SerializedName("destination")
    @Expose
    private String destination;

    @SerializedName("page")
    @Expose
    private int page;

    public FindStockBody(String departure, String destination, int page) {
        this.departure = departure;
        this.destination = destination;
        this.page = page;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "FindStockBody{" +
                "departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", page=" + page +
                '}';
    }
}
