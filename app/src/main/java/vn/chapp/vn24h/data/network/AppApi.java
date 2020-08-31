package vn.chapp.vn24h.data.network;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import vn.chapp.vn24h.BuildConfig;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.banner.BannerResponse;
import vn.chapp.vn24h.models.booking.Booking;
import vn.chapp.vn24h.models.booking.DetailBooking;
import vn.chapp.vn24h.models.cart.Cart;
import vn.chapp.vn24h.models.chat.ChatRoom;
import vn.chapp.vn24h.models.chat.ContentChatResponse;
import vn.chapp.vn24h.models.guide.GuideResponse;
import vn.chapp.vn24h.models.map.DetailPlaceResponse;
import vn.chapp.vn24h.models.map.PlaceResponse;
import vn.chapp.vn24h.models.point.PointDetailResponse;
import vn.chapp.vn24h.models.point.PointResponse;
import vn.chapp.vn24h.models.service.CategoryAdd;
import vn.chapp.vn24h.models.service.HistoryResponse;
import vn.chapp.vn24h.models.service.ListProducts;
import vn.chapp.vn24h.models.service.NewsResponse;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.models.service.Service;
import vn.chapp.vn24h.models.service.ServiceResponse;
import vn.chapp.vn24h.models.service.Shop;
import io.reactivex.Observable;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.wallet.WalletHistory;

import static vn.chapp.vn24h.data.network.NetworkEndPoint.GET_AUTOCOMPLETE_PLACE;
import static vn.chapp.vn24h.data.network.NetworkEndPoint.GET_DETAIL_PLACE;
import static vn.chapp.vn24h.data.network.NetworkEndPoint.GET_DETAIL_PLACE_COMPONENT;
import static vn.chapp.vn24h.data.network.NetworkEndPoint.GET_GUIDE;
import static vn.chapp.vn24h.data.network.NetworkEndPoint.GET_LIST_SERVICE;
import static vn.chapp.vn24h.data.network.NetworkEndPoint.GET_LIST_SHOP_LINKED;

public interface AppApi {

    String DOMAIN = BuildConfig.ROOT_DOMAIN;
    String MAP_API_DOMAIN = BuildConfig.MAP_API_DOMAIN;

    /*Tim kiem vi tri theo ten*/
    @GET(GET_AUTOCOMPLETE_PLACE)
    Observable<PlaceResponse> autoCompletePlace(@Query("input") String input, @Query("key") String key);

    /*Lay thong tin chi tiet cua vi tri*/
    @GET(GET_DETAIL_PLACE)
    Observable<DetailPlaceResponse> getDetailPlace(@Query("placeid") String input, @Query("key") String key);

    @GET(GET_DETAIL_PLACE_COMPONENT)
    Observable<DetailPlaceResponse> getDetailPlaceComponent(@Query("latlng") String input, @Query("key") String key);

    /*Get danh sach cac dich vu*/
    @GET(GET_LIST_SERVICE)
    Observable<Response<List<Service>>> getListService(@Query("user_id") String user_id, @Query("type") int type);

    /*Get cac shop da lien ket theo dich vu*/
    @GET(GET_LIST_SHOP_LINKED)
    Observable<Response<List<Shop>>> getListShopLinked(
            @Query("user_id") String userId,
            @Query("service_id") String serviceId,
            @Query("type") String type
    );

    @GET(NetworkEndPoint.GET_VERIFY_CODE)
    Observable<Response<Integer>> getVerifyCode(@Query("phone") String phone);

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_REGISTER)
    Observable<Response<UserDefault>> postRegister(
            @Field("phone") String phone,
            @Field("name") String name,
            @Field("password") String password,
            @Field("ref_code") String ref_code
    );

    @GET(NetworkEndPoint.GET_LOGIN)
    Observable<Response<UserDefault>> getLogin(@Query("phone") String phone, @Query("password") String password);

    @GET(NetworkEndPoint.GET_LOGIN_GOOGLE)
    Observable<Response<UserDefault>> getLoginGoogle(@Query("email") String email, @Query("ggid") String ggid, @Query("name") String name, @Query("ref_code") String ref_code);

    @POST(NetworkEndPoint.POST_UPLOAD_AVATAR)
    Observable<Response<String>> postUploadAvatar(@Header("Content-Type") String contentType, @Body MultipartBody body);

    @GET(NetworkEndPoint.GET_USER_DETAIL)
    Observable<Response<UserDefault>> getUserDetail(@Query("user_id") String user_id);

    @FormUrlEncoded
    @POST(NetworkEndPoint.GET_ADD_SHOP)
    Observable<Response<Integer>> getAddShop(
            @Field("user_id") String user_id,
            @Field("code") String code
    );

    @GET(NetworkEndPoint.GET_PRODUCTS)
    Observable<Response<ListProducts>> getProducts(@Query("user_id") String userId);

    @GET(NetworkEndPoint.GET_BANNERS)
    Observable<Response<List<BannerResponse>>> getBanners();

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_BOOKING)
    Observable<Response<Integer>> postBooking(
            @Field("user_id") String userId,
            @Field("shop_id") String shopId,
            @Field("list_product") String listProduct,
            @Field("date_booking") String dateBooking,
            @Field("time_booking") String timeBooking,
            @Field("number") String number,
            @Field("total_money") String totalMoney,
            @Field("note") String note,
            @Field("address") String address,
            @Field("is_receive") String isReceive,
            @Field("type") String type,
            @Field("payment") String payment,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_BOOKING_SERVICE)
    Observable<Response<Integer>> postBookingService(
            @Field("user_id") String userId,
            @Field("shop_id") String shopId,
            @Field("list_product") String listProduct,
            @Field("date_booking") String dateBooking,
            @Field("time_booking") String timeBooking,
            @Field("number") String number,
            @Field("total_money") String totalMoney,
            @Field("note") String note,
            @Field("address") String address,
            @Field("is_receive") String isReceive,
            @Field("type") String type,
            @Field("payment") String payment,
            @Field("service_id") String serviceId,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_RATING)
    Observable<Response<Object>> postRating(@Field("user_id") String userId, @Field("shop_id") String shopId, @Field("rating_shop") Float ratingShop, @Field("rating_staff") Float ratingStaff);

    @GET(NetworkEndPoint.GET_SERVICES)
    Observable<Response<List<ServiceResponse>>> getServices();

    @GET(NetworkEndPoint.GET_USER_HISTORY)
    Observable<Response<List<HistoryResponse>>> getUserHistory(@Query("user_id") String userId, @Query("shop_id") String shopId);

    @GET(NetworkEndPoint.GET_USER_HISTORY)
    Observable<Response<List<HistoryResponse>>> getUserHistory(@Query("user_id") String userId);

    @GET(NetworkEndPoint.GET_LIST_CHAT)
    Observable<Response<List<ChatRoom>>> getChatRoom(@Query("id") String id, @Query("type") int type);

    @GET(NetworkEndPoint.GET_CONTENT_CHAT)
    Observable<Response<ContentChatResponse>> getContentChat(@Query("user_id") String id, @Query("shop_id") String shopId);

    @GET(NetworkEndPoint.GET_LIST_BOOKING)
    Observable<Response<List<Booking>>> getListBooking(@Query("user_id") String userId, @Query("active") String active);

    @GET(NetworkEndPoint.GET_LIST_BOOKING)
    Observable<Response<List<Booking>>> getListBooking(@Query("user_id") String userId);

    @FormUrlEncoded
    @POST(NetworkEndPoint.UPDATE_STATUS_BOOKING)
    Observable<Response<Object>> updateStatusBooking(@Field("shop_id") String id, @Field("booking_id") String bookingId, @Field("active") int active);

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_CHAT)
    Observable<Response<Object>> postChat(@Field("shop_id") String shopId, @Field("user_id") String userId, @Field("content") String content, @Field("type") int type);

    @GET(NetworkEndPoint.GET_DETAIL_BOOKING)
    Observable<Response<DetailBooking>> getDetailBooking(@Query("booking_id") String bookingId);

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_FORGET_PASSWORD)
    Observable<Response<Integer>> forgetPassword(@Field("phone") String phone, @Field("type") int type);

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_UPDATE_PASSWORD)
    Observable<Response<Object>> updatePassword(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_CHANGE_PASSWORD)
    Observable<Response<Object>> changePassword(@Field("user_id") String userId, @Field("old_pass") String oldPass, @Field("new_pass") String newPass);

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_UPDATE_PROFILE)
    Observable<Response<Object>> updateProfile(
            @Field("user_id") String user_id,
            @Field("email") String email,
            @Field("address") String address,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("lat") String lat,
            @Field("lng") String lng
    );

    @GET(NetworkEndPoint.GET_DETAIL_NEWS)
    Observable<Response<NewsResponse>> getDetailNews(@Query("news_id") String newsId);

    @FormUrlEncoded
    @POST(NetworkEndPoint.POST_UPDATE_TOKEN_FCM)
    Observable<Response<Integer>> updateTokenFCM(
            @Field("user_id") String id,
            @Field("type") int type,
            @Field("token_fcm") String tokenFCM
    );

    @GET(GET_GUIDE)
    Observable<Response<GuideResponse>> getGuide();

    @GET(NetworkEndPoint.GET_WALLET)
    Observable<Response<WalletHistory>> getWallet(@Query("id") String id, @Query("type") int type);

    @GET(NetworkEndPoint.GET_SHOP_NEARBY)
    Observable<Response<List<Shop>>> getShopNearby(@QueryMap HashMap<String, Object> queries);

    @FormUrlEncoded
    @POST(NetworkEndPoint.UN_LINK_SHOP)
    Observable<Response<Object>> unLinkShop(
            @Field("user_id") String user_id,
            @Field("shop_id") String shop_id
    );

    @GET(NetworkEndPoint.GET_POINT)
    Observable<Response<List<PointResponse>>> getPoint(@Query("user_id") String id);

    @GET(NetworkEndPoint.GET_POINT_DETAIL)
    Observable<Response<List<PointDetailResponse>>> getPointDetail(
            @Query("user_id") String id, @Query("shop_id") String shopId,
            @Query("month") String month, @Query("year") String year
    );

    @GET(NetworkEndPoint.GET_CATEGORY)
    Observable<Response<List<CategoryAdd>>> getCategory(@Query("type") String type, @Query("is_check") int isCheck);

    @GET(NetworkEndPoint.GET_SEARCH_PRODUCT)
    Observable<Response<List<ProductResponse>>> getSearchProduct(@Query("shop_id") String id, @Query("category_id") String cateId, @Query("type") int type, @Query("user_id") String userId);

    @GET(NetworkEndPoint.GET_SEARCH_PRODUCT)
    Observable<Response<List<ProductResponse>>> getSearchProduct(@Query("category_id") String cateId, @Query("type") int type, @Query("user_id") String userId);

    @GET(NetworkEndPoint.GET_CART_INFOR)
    Observable<Response<List<Cart>>> getCart(@Query("user_id") String userId);

    @FormUrlEncoded
    @POST(NetworkEndPoint.ADD_PRODUCT_TO_CART)
    Observable<Response<Object>> addProtoCart(@Field("user_id") String user_id, @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST(NetworkEndPoint.DELETE_PRODUCT_TO_CART)
    Observable<Response<Object>> deleteProtoCart(@Field("user_id") String user_id, @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST(NetworkEndPoint.EDIT_PRODUCT_TO_CART)
    Observable<Response<Object>> editProtoCart(@Field("user_id") String user_id, @Field("product_id") String product_id, @Field("number") String number);

    @GET(NetworkEndPoint.GET_BANNER)
    Observable<Response<List<String>>> getBanner();
}
