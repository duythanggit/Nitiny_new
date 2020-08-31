package vn.chapp.vn24h.models.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("product_id")
    @Expose
    private String productId;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("active")
    @Expose
    private String active;


    protected ImageResponse(Parcel in) {
        id = in.readString();
        productId = in.readString();
        img = in.readString();
        date = in.readString();
        active = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(productId);
        dest.writeString(img);
        dest.writeString(date);
        dest.writeString(active);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageResponse> CREATOR = new Creator<ImageResponse>() {
        @Override
        public ImageResponse createFromParcel(Parcel in) {
            return new ImageResponse(in);
        }

        @Override
        public ImageResponse[] newArray(int size) {
            return new ImageResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
