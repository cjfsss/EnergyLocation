package hos.nav.baidu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import hos.location.LatLngSource;
import hos.nav.NavCommon;


/**
 * <p>Title: BaiduMapNavigation </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 16:22
 */
public class BaiduMapNavigation {
    /**
     * 驾车导航
     *
     * @param query   目标
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapNavi(String query, Context context) {
        return openBaiduMapNavi(query, null, null, context);
    }

    /**
     * 驾车导航
     *
     * @param location 坐标点
     * @param context  上下文
     * @return
     */
    public static boolean openBaiduMapNavi(LatLngSource location, Context context) {
        return openBaiduMapNavi(null, location, null, context);
    }

    /**
     * 驾车导航
     *
     * @param location 坐标点
     * @param gpsType  坐标类型
     * @param context  上下文
     * @return
     */
    public static boolean openBaiduMapNavi(LatLngSource location, NavCommon.GPSType gpsType, Context context) {
        return openBaiduMapNavi(null, location, gpsType, context);
    }

    /**
     * 驾车导航
     *
     * @param query    目标
     * @param location 坐标点
     * @param gpsType  坐标类型
     * @param context  上下文
     * @return
     */
    public static boolean openBaiduMapNavi(String query, LatLngSource location, NavCommon.GPSType gpsType, Context context) {
        StringBuilder builder = new StringBuilder("baidumap://map/navi");
        builder.append("?src=").append(context.getPackageName());
        if (!TextUtils.isEmpty(query)) {
            builder.append("&query=").append(query);
        }
        if (location != null) {
            builder.append("&location=").append(location.getLatitude())
                    .append(",").append(location.getLongitude());
            if (gpsType != null) {
                builder.append("&coord_type=").append(gpsType.getName());
            }
        }
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(builder.toString()));
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 骑行导航
     *
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapBikeNavi(LatLngSource endPoint, Context context) {
        return openBaiduMapBikeNavi(null, endPoint, null, context);
    }

    /**
     * 骑行导航
     *
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapBikeNavi(LatLngSource startPoint, LatLngSource endPoint, Context context) {
        return openBaiduMapBikeNavi(startPoint, endPoint, null, context);
    }

    /**
     * 骑行导航
     *
     * @param gpsType 坐标类型
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapBikeNavi(LatLngSource startPoint, LatLngSource endPoint, NavCommon.GPSType gpsType, Context context) {
        StringBuilder builder = new StringBuilder("baidumap://map/bikenavi");
        builder.append("?src=").append(context.getPackageName());
        if (startPoint != null) {
            builder.append("&origin=").append(startPoint.getLatitude())
                    .append(",").append(startPoint.getLongitude());
        }
        if (endPoint != null) {
            builder.append("&destination=").append(endPoint.getLatitude())
                    .append(",").append(endPoint.getLongitude());
        }
        if (gpsType != null) {
            builder.append("&coord_type=").append(gpsType.getName());
        } else {
            builder.append("&coord_type=").append("bd09ll");
        }
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(builder.toString()));
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 步行导航
     *
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapWalkNaviAR(LatLngSource endPoint, Context context) {
        return openBaiduMapWalkNavi(null, endPoint, "walking_ar", null, context);
    }

    /**
     * 步行导航
     *
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapWalkNaviAR(LatLngSource startPoint, LatLngSource endPoint, Context context) {
        return openBaiduMapWalkNavi(startPoint, endPoint, "walking_ar", null, context);
    }

    /**
     * 步行导航
     *
     * @param gpsType 坐标类型
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapWalkNaviAR(LatLngSource startPoint, LatLngSource endPoint, NavCommon.GPSType gpsType, Context context) {
        return openBaiduMapWalkNavi(startPoint, endPoint, "walking_ar", gpsType, context);
    }

    /**
     * 步行导航
     *
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapWalkNavi(LatLngSource endPoint, Context context) {
        return openBaiduMapWalkNavi(null, endPoint, null, null, context);
    }

    /**
     * 步行导航
     *
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapWalkNavi(LatLngSource startPoint, LatLngSource endPoint, Context context) {
        return openBaiduMapWalkNavi(startPoint, endPoint, null, null, context);
    }

    /**
     * 步行导航
     *
     * @param gpsType 坐标类型
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapWalkNavi(LatLngSource startPoint, LatLngSource endPoint, NavCommon.GPSType gpsType, Context context) {
        return openBaiduMapWalkNavi(startPoint, endPoint, null, gpsType, context);
    }

    /**
     * 步行导航
     *
     * @param gpsType 坐标类型
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapWalkNavi(LatLngSource startPoint, LatLngSource endPoint, String mode, NavCommon.GPSType gpsType, Context context) {
        StringBuilder builder = new StringBuilder("baidumap://map/walknavi");
        builder.append("?src=").append(context.getPackageName());
        if (startPoint != null) {
            builder.append("&origin=").append(startPoint.getLatitude())
                    .append(",").append(startPoint.getLongitude());
        }
        if (endPoint != null) {
            builder.append("&destination=").append(endPoint.getLatitude())
                    .append(",").append(endPoint.getLongitude());
        }
        if (gpsType != null) {
            builder.append("&coord_type=").append(gpsType.getName());
        } else {
            builder.append("&coord_type=").append("bd09ll");
        }
        if (!TextUtils.isEmpty(mode)) {
            builder.append("&mode").append(mode);
        }
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(builder.toString()));
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
