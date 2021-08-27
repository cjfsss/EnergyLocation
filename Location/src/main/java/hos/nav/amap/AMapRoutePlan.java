package hos.nav.amap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import hos.nav.NavCommon;


/**
 * <p>Title: AMapNavigation </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 17:58
 */
public class AMapRoutePlan {

    /**
     * 启动高德地图 路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openAMapRoute(NaviParaOption option, Context context) {
        return openAMapRoute(option, null, context);
    }

    /**
     * 启动高德地图 路线规划
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openAMapRoute(NaviParaOption option, NavCommon.NavMode navMode, Context context) {
        StringBuilder builder = new StringBuilder("amapuri://route/plan/?sourceApplication=" + context.getPackageName());
        if (option.startPoint != null && !TextUtils.isEmpty(option.startName)) {
            builder.append("&sname=").append(option.startName)
                    .append("&slat=")
                    .append(option.startPoint.getLatitude())
                    .append("&slon=")
                    .append(option.startPoint.getLongitude());
        } else if (option.startPoint != null) {
            builder.append("&slat=")
                    .append(option.startPoint.getLatitude())
                    .append("&slon=")
                    .append(option.startPoint.getLongitude());
        } else if (!TextUtils.isEmpty(option.startName)) {
            builder.append("&sname=").append(option.startName);
        }
        if (option.endPoint != null && !TextUtils.isEmpty(option.endName)) {
            builder.append("&dname=").append(option.startName)
                    .append("&dlat").append(option.endPoint.getLatitude())
                    .append("&dlon").append(option.endPoint.getLongitude());
        } else if (option.endPoint != null) {
            builder.append("&dlat").append(option.endPoint.getLatitude())
                    .append("&dlon").append(option.endPoint.getLongitude());
        } else if (!TextUtils.isEmpty(option.endName)) {
            builder.append("&dname=").append(option.startName);
        }
        // 可选 规划类型
        if (navMode != null && !TextUtils.isEmpty(navMode.getName())) {
            builder.append("&t=").append(navMode.getType());
        } else {
            builder.append("&t=").append("0");
        }
        builder.append("&dev=0");
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
