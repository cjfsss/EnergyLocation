package hos.nav.amap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import hos.location.LatLngSource;


/**
 * <p>Title: AMapNavigation </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 18:17
 */
public class AMapNavigation {

    /**
     * 启动高德地图 导航规划
     *
     * @param context 上下文
     * @return
     */
    public static boolean openAMapNav(LatLngSource latLngSource, Context context) {
        return openAMapNav(null, latLngSource, context);
    }

    /**
     * 启动高德地图 导航规划
     *
     * @param context 上下文
     * @return
     */
    public static boolean openAMapNav(String poiName, LatLngSource latLngSource, Context context) {
        StringBuilder builder = new StringBuilder("androidamap://navi?sourceApplication=" + context.getPackageName());
        if (!TextUtils.isEmpty(poiName)) {
            builder.append("&poiname=").append(poiName);
        }
        if (latLngSource != null) {
            builder.append("&lat=").append(latLngSource.getLatitude())
                    .append("&lon=").append(latLngSource.getLongitude());
        }
        builder.append("&dev=0&style=2");
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
