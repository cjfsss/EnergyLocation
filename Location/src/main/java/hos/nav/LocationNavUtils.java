package hos.nav;

import android.content.Context;
import android.content.pm.PackageInfo;


import java.util.List;

/**
 * <p>Title: LocationNavUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 9:31
 */
public class LocationNavUtils {

    /**
     * 判断手机中是否有百度app
     */
    public static boolean isNavigationApkBaiDu(Context context) {
        return isNavigationApk(context,"com.baidu.BaiduMap");
    }

    /**
     * 判断手机中是否有高德app
     */
    public static boolean isNavigationApkAMap(Context context) {
        return isNavigationApk(context,"com.autonavi.minimap");
    }

    /**
     * 判断手机中是否有导航app
     *
     * @param packageName 包名
     */
    public static boolean isNavigationApk(Context context,String packageName) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}
