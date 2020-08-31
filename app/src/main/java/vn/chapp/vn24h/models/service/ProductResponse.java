package vn.chapp.vn24h.models.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse implements Parcelable {

	@SerializedName("date")
	@Expose
	private String date;

	@SerializedName("shop_id")
	@Expose
	private String shopId;

	@SerializedName("price_discount")
	@Expose
	private String priceDiscount;

	@SerializedName("note")
	@Expose
	private String note;

	@SerializedName("img")
	@Expose
	private String img;

	@SerializedName("product_type")
	@Expose
	private String productType;

	@SerializedName("price")
	@Expose
	private String price;

	@SerializedName("service_id")
	@Expose
	private String serviceId;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("active")
	@Expose
	private String active;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("imgs")
	@Expose
	private List<String> imgs;

	@SerializedName("address")
	@Expose
	private String address;

	@SerializedName("shop_name")
	@Expose
	private String shopName;
	@SerializedName("shop_address")
	@Expose
	private String shopAddress;
	@SerializedName("category_name")
	@Expose
	private String categoryName;
	@SerializedName("shop_phone")
	@Expose
	private String shopPhone;
	@SerializedName("shop_avatar")
	@Expose
	private String shopAvatar;

	@SerializedName("price_1")
	@Expose
	private String price1;

	@SerializedName("price_2")
	@Expose
	private String price2;

	@SerializedName("price_3")
	@Expose
	private String price3;

	@SerializedName("type_user")
	@Expose
	private String typeUser;

	@SerializedName("number")
	@Expose
	private String number;

	@SerializedName("made_from")
	@Expose
	private String madeFrom;


	private int indexSpinner;


	protected ProductResponse(Parcel in) {
		date = in.readString();
		shopId = in.readString();
		priceDiscount = in.readString();
		note = in.readString();
		img = in.readString();
		productType = in.readString();
		price = in.readString();
		serviceId = in.readString();
		name = in.readString();
		madeFrom = in.readString();
		active = in.readString();
		id = in.readString();
		type = in.readString();
		imgs = in.createStringArrayList();
		address = in.readString();
		shopName = in.readString();
		shopAddress = in.readString();
		categoryName = in.readString();
		shopPhone = in.readString();
		shopAvatar = in.readString();
		price1 = in.readString();
		price2 = in.readString();
		price3 = in.readString();
		typeUser = in.readString();
		number = in.readString();
		indexSpinner = in.readInt();
	}

	public static final Creator<ProductResponse> CREATOR = new Creator<ProductResponse>() {
		@Override
		public ProductResponse createFromParcel(Parcel in) {
			return new ProductResponse(in);
		}

		@Override
		public ProductResponse[] newArray(int size) {
			return new ProductResponse[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(date);
		dest.writeString(shopId);
		dest.writeString(priceDiscount);
		dest.writeString(note);
		dest.writeString(img);
		dest.writeString(productType);
		dest.writeString(price);
		dest.writeString(serviceId);
		dest.writeString(name);
		dest.writeString(madeFrom);
		dest.writeString(active);
		dest.writeString(id);
		dest.writeString(type);
		dest.writeStringList(imgs);
		dest.writeString(address);
		dest.writeString(shopName);
		dest.writeString(shopAddress);
		dest.writeString(categoryName);
		dest.writeString(shopPhone);
		dest.writeString(shopAvatar);
		dest.writeString(price1);
		dest.writeString(price2);
		dest.writeString(price3);
		dest.writeString(typeUser);
		dest.writeString(number);
		dest.writeInt(indexSpinner);
	}

	@Override
	public int describeContents() {
		return 0;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getPriceDiscount() {
		return priceDiscount;
	}

	public void setPriceDiscount(String priceDiscount) {
		this.priceDiscount = priceDiscount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getMadeFrom() {
		return madeFrom;
	}

	public void setMadeFrom(String madeFrom) {
		this.madeFrom = madeFrom;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public int getIndexSpinner() {
		return indexSpinner;
	}

	public void setIndexSpinner(int indexSpinner) {
		this.indexSpinner = indexSpinner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public String getShopAvatar() {
		return shopAvatar;
	}

	public void setShopAvatar(String shopAvatar) {
		this.shopAvatar = shopAvatar;
	}

	public String getPrice1() {
		return price1;
	}

	public void setPrice1(String price1) {
		this.price1 = price1;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

	public String getPrice3() {
		return price3;
	}

	public void setPrice3(String price3) {
		this.price3 = price3;
	}

	public String getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(String typeUser) {
		this.typeUser = typeUser;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}