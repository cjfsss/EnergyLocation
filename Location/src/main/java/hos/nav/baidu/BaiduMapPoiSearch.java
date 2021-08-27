package hos.nav.baidu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import hos.nav.NavCommon;


/**
 * <p>Title: BaiduMapPoiSearch </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 16:06
 */
public class BaiduMapPoiSearch {

    /**
     * 启动百度地图  打开附件页
     *
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapPoiNearby(Context context) {
        return openBaiduMapRoute(null, null, context);
    }

    /**
     * 启动百度地图  周边搜索
     *
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapPoiNearby(PoiParaOption option, Context context) {
        return openBaiduMapRoute(option, null, context);
    }

    /**
     * 启动百度地图 周边搜索
     *
     * @param option  路线参数
     * @param context 上下文
     * @return
     */
    public static boolean openBaiduMapRoute(PoiParaOption option, NavCommon.GPSType gpsType, Context context) {
        StringBuilder builder = new StringBuilder("baidumap://map/place/nearby");
        builder.append("?src=").append(context.getPackageName());
        if (option != null) {
            if (!TextUtils.isEmpty(option.getKey())) {
                builder.append("&query=").append(option.getKey());
            }
            // 可选 中心点
            if (option.getCenter() != null) {
                builder.append("&center=").append(option.getCenter().getLatitude())
                        .append(",").append(option.getCenter().getLongitude());
            }
            if (option.getRadius() != 0) {
                builder.append("&radius=").append(option.getRadius());
            }
        }
        if (gpsType != null) {
            builder.append("&coord_type=").append(gpsType.getName());
        }
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
