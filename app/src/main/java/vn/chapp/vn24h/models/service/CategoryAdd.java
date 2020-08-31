package vn.chapp.vn24h.models.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryAdd {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("active")
    @Expose
    private String active;

    public CategoryAdd(String name) {
        this.name = name;
    }

    public CategoryAdd(String id, String serviceId, String name, String date, String active) {
        this.id = id;
        this.serviceId = serviceId;
        this.name = name;
        this.date = date;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
