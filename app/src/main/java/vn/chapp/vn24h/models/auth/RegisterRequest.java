package vn.chapp.vn24h.models.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.chapp.vn24h.utils.AppConstants;

public class RegisterRequest {

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("type")
    @Expose
    private int type;

    public RegisterRequest(String phone, String name, String password) {
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.type = AppConstants.APP_TYPE;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
