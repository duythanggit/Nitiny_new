

package vn.chapp.vn24h.utils;

import java.util.Arrays;
import java.util.List;

import vn.chapp.vn24h.models.service.Category;
import vn.chapp.vn24h.models.service.ServiceType;

public class AppConstants {


    private AppConstants() {
        // This utility class is not publicly instantiable
    }

    public static final int APP_TYPE = 1;

    public static final String[] LOCATION_PERMISSION = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static final List<ServiceType> SERVICE_TYPES = Arrays.asList(
            new ServiceType(AppConstants.TYPE_PRODUCT,"Danh mục sản phẩm","Thêm sản phẩm"),
            new ServiceType(AppConstants.TYPE_SERVICE,"Danh sách dịch vụ","Thêm dịch vụ"),
            new ServiceType(AppConstants.TYPE_VOUCHER,"Danh sách khuyến mãi","Thêm khuyến mãi"),
            new ServiceType(AppConstants.TYPE_NEWS,"Danh sách tin tức","Thêm tin tức")
    );

    public static final int TYPE_PRODUCT = 1;
    public static final int TYPE_SERVICE = 2;
    public static final int TYPE_VOUCHER = 3;
    public static final int TYPE_NEWS = 4;

//    public static final Map<Integer, Service> SERVICE_NAME;
//    static {
//        Map<Integer, Service> aMap = new HashMap<Integer, Service>();
//        aMap.put(1, new Service(1, "Spa", "SB", "http://chapp.vn/shop/uploads/service/sp.png",  "2019-10-07 10:10:54", 1));
//        aMap.put(2, new Service(2, "Cắt tóc", "CT", "http://chapp.vn/shop/uploads/service/ct.jpeg",  "2019-10-07 10:10:54", 1));
//        aMap.put(3, new Service(3, "Sân bóng", "SP", "http://chapp.vn/shop/uploads/service/sb.png",  "2019-10-07 10:11:05", 1));
//        SERVICE_NAME = Collections.unmodifiableMap(aMap);
//    }

    public static final int TYPE_SCHEDULE_BOOKING = 1;
    public static final int TYPE_BUY_BOOKING = 2;

    public static final int IS_TRUE = 1;
    public static final int IS_FALSE = 0;

    public static final List<Category> categoryService = Arrays.asList(
            new Category("Sản phẩm"),
            new Category("Dịch vụ"),
            new Category("Khuyến mãi"),
            new Category("Tin tức")
    );

}