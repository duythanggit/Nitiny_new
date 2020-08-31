package vn.chapp.vn24h.models.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListProducts implements Parcelable {

    @SerializedName("product")
    @Expose
    private List<ProductResponse> product;

    @SerializedName("service")
    @Expose
    private List<ProductResponse> service;

    @SerializedName("news")
    @Expose
    private List<NewsResponse> news;

    @SerializedName("promotion")
    @Expose
    private List<NewsResponse> promotion;


    protected ListProducts(Parcel in) {
        product = in.createTypedArrayList(ProductResponse.CREATOR);
        service = in.createTypedArrayList(ProductResponse.CREATOR);
        news = in.createTypedArrayList(NewsResponse.CREATOR);
        promotion = in.createTypedArrayList(NewsResponse.CREATOR);
    }

    public static final Creator<ListProducts> CREATOR = new Creator<ListProducts>() {
        @Override
        public ListProducts createFromParcel(Parcel in) {
            return new ListProducts(in);
        }

        @Override
        public ListProducts[] newArray(int size) {
            return new ListProducts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(product);
        dest.writeTypedList(service);
        dest.writeTypedList(news);
        dest.writeTypedList(promotion);
    }

    public List<ProductResponse> getProduct() {
        return product;
    }

    public void setProduct(List<ProductResponse> product) {
        this.product = product;
    }

    public List<ProductResponse> getService() {
        return service;
    }

    public void setService(List<ProductResponse> service) {
        this.service = service;
    }

    public List<NewsResponse> getNews() {
        return news;
    }

    public void setNews(List<NewsResponse> news) {
        this.news = news;
    }

    public List<NewsResponse> getPromotion() {
        return promotion;
    }

    public void setPromotion(List<NewsResponse> promotion) {
        this.promotion = promotion;
    }
}
