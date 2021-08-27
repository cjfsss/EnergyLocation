package hos.nav.baidu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import hos.nav.NavCommon;


/**
 * <p>Title: BaiduMapNavigation </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 15:13
 */
public class BaiduMapRoutePlan {
    /**
     * 启动百度地图 步行路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRouteWalking(RouteParaOption option,
                                                   Context context) {
        return openBaiduMapRoute(option, null, NavCommon.NavMode.driving, context);
    }

    /**
     * 启动百度地图 步行路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRouteWalking(RouteParaOption option, NavCommon.GPSType gpsType,
                                                   Context context) {
        return openBaiduMapRoute(option, gpsType, NavCommon.NavMode.walking, context);
    }

    /**
     * 启动百度地图 驾驶路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRouteDriving(RouteParaOption option,
                                                   Context context) {
        return openBaiduMapRoute(option, null, NavCommon.NavMode.driving, context);
    }

    /**
     * 启动百度地图 驾驶路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRouteDriving(RouteParaOption option, NavCommon.GPSType gpsType,
                                                   Context context) {
        return openBaiduMapRoute(option, gpsType, NavCommon.NavMode.driving, context);
    }

    /**
     * 启动百度地图 骑行路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRouteRiding(RouteParaOption option,
                                                  Context context) {
        return openBaiduMapRoute(option, null, NavCommon.NavMode.riding, context);
    }

    /**
     * 启动百度地图 骑行路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRouteRiding(RouteParaOption option, NavCommon.GPSType gpsType,
                                                  Context context) {
        return openBaiduMapRoute(option, gpsType, NavCommon.NavMode.riding, context);
    }

    /**
     * 启动百度地图 公交路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRouteTransit(RouteParaOption option,
                                                   Context context) {
        return openBaiduMapRoute(option, null, NavCommon.NavMode.transit, context);
    }

    /**
     * 启动百度地图 公交路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRouteTransit(RouteParaOption option, NavCommon.GPSType gpsType,
                                                   Context context) {
        return openBaiduMapRoute(option, gpsType, NavCommon.NavMode.transit, context);
    }

    /**
     * 启动百度地图 路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRoute(RouteParaOption option, NavCommon.GPSType gpsType,
                                            NavCommon.NavMode navMode, Context context) {
        StringBuilder builder = new StringBuilder("baidumap://map/direction?origin=");
        if (option.startPoint != null && !TextUtils.isEmpty(option.startName)) {
            builder.append("name:").append(option.startName).append("|").append("latlng:")
                    .append(option.startPoint.getLatitude())
                    .append(",").append(option.startPoint.getLongitude());
        } else if (option.startPoint != null) {
            builder.append(option.startPoint.getLatitude())
                    .append(",").append(option.startPoint.getLongitude());
        } else if (!TextUtils.isEmpty(option.startName)) {
            builder.append("name:").append(option.startName);
        }
        builder.append("&destination=");
        if (option.endPoint != null && !TextUtils.isEmpty(option.endName)) {
            builder.append("name:").append(option.endName)
                    .append("|latlng:").append(option.endPoint.getLatitude())
                    .append(",").append(option.endPoint.getLongitude());
        } else if (option.endPoint != null) {
            builder.append(option.endPoint.getLatitude())
                    .append(",").append(option.endPoint.getLongitude());
        } else if (!TextUtils.isEmpty(option.endName)) {
            builder.append("name:").append(option.endName);
        }
        if (gpsType != null) {
            builder.append("&coord_type=").append(gpsType.getName());
        } else {
            builder.append("&coord_type=").append("bd09ll");
        }
        // 可选 规划类型
        if (!TextUtils.isEmpty(navMode.getName())) {
            builder.append("&mode=").append(navMode.getName());
        }
        if (!TextUtils.isEmpty(option.cityName)) {
            builder.append("&region=").append(option.getCityName());
        }
        builder.append("&sy=0&index=0&target=1&src=").append(context.getPackageName());
        // 公交路线规划
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
