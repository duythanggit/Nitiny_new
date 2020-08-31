package vn.chapp.vn24h.models.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("active")
    @Expose
    private int active;

    public Service() {
    }

    public Service(int id) {
        this.id = id;
    }

    public Service(int id, String name, String code, String img, String date, int active) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.img = img;
        this.date = date;
        this.active = active;
    }

    protected Service(Parcel in) {
        id = in.readInt();
        name = in.readString();
        code = in.readString();
        img = in.readString();
        date = in.readString();
        active = in.readInt();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(img);
        dest.writeString(date);
        dest.writeInt(active);
    }
}
