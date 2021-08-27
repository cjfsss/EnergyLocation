package hos.location;

import android.text.TextUtils;

import androidx.annotation.NonNull;


/**
 * <p>Title: LatLogUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/3/6 14:57
 */
public class LatLngUtils {

    /***
     * 将点位截取小数点前6位
     * @param point 经度或者纬度
     * @return 经度或者纬度 只有前6位
     */
    public static double pointIntercept(double point) {
        return Double.parseDouble(pointIntercept(String.valueOf(point)));
    }

    /***
     * 将点位截取小数点前6位
     * @param pointStr 经度或者纬度
     * @return 经度或者纬度 只有前6位
     */
    @NonNull
    public static String pointIntercept(String pointStr) {
        if (TextUtils.isEmpty(pointStr) || pointStr.contains("NaN")) {
            return "";
        }
        if (pointStr.contains(".")) {
            String[] pointArr = pointStr.split("\\.");
            if (pointArr[1].length() <= 6) {
                return pointStr;
            }
            pointArr[1] = pointArr[1].substring(0, 6);
            return pointArr[0] + "." + pointArr[1];
        } else {
            return pointStr;
        }
    }


    /**
     * @note double 位置 转换成  分 秒
     */
    public static String dblToLocation(double data) {
        String ret_s = "";
        int tmp_i_du = (int) data;
        ret_s = String.valueOf(tmp_i_du) + "°";
        //度小数部分
        double tmp_d_du = data - tmp_i_du;
        int tmp_i_fen = (int) (tmp_d_du * 60);
        ret_s = ret_s.concat(String.valueOf(tmp_i_fen) + "′");
        double tmp_d_fen = tmp_d_du * 60 - tmp_i_fen;
        double tmp_i_miao = (double) (tmp_d_fen * 60.000);
        ret_s = ret_s.concat(String.format("%.2f", tmp_i_miao) + "″");
        return ret_s;
    }

    /**
     * @note double 位置 转换成  分 秒
     */
    public static double dblToLocation(String data) {
        if (data == null || data.length() == 0) {
            return 0;
        }
        if (!(data.contains("°") && data.contains("′") && data.contains("″"))) {
            throw new StringIndexOutOfBoundsException("数据格式不正确..." + data);
        }
        String duStr = data.split("°")[0];
        String fenStr = data.split("′")[0].split("°")[1];
        String miaoStr = data.split("′")[1].split("″")[0];
        double lonLat = ((Double.valueOf(miaoStr) / 60) + Double.valueOf(fenStr)) / 60 + Double.valueOf(duStr);
        return lonLat;
    }

    /**
     * 经纬度校验 只校验正数 0-90.000000 0-180.000000 范围内 经度longitude:
     * (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6}) 纬度latitude：
     * (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     *
     * @return
     */
    public static boolean checkLonLat(double longitude, double latitude) {
        return checkLonLat(String.valueOf(longitude), String.valueOf(latitude));
    }

    /**
     * 经纬度校验 只校验正数 0-90.000000 0-180.000000 范围内 经度longitude:
     * (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6}) 纬度latitude：
     * (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     *
     * @return
     */
    public static boolean checkLonLat(String longitude, String latitude) {
        if (longitude == null || longitude.length() == 0 ||
                latitude == null || latitude.length() == 0) {
            return false;
        }
        longitude = longitude.trim();
        latitude = latitude.trim();
        if (TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude)
                || TextUtils.equals("NaN", longitude) || TextUtils.equals("NaN", latitude)
                || TextUtils.equals("0.0", longitude) || TextUtils.equals("0.0", latitude)
                || TextUtils.equals("0", longitude) || TextUtils.equals("0", latitude)) {
            return false;
        }
        longitude = pointIntercept(longitude);
        latitude = pointIntercept(latitude);
        String checkLongitude = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
        String checkLatitude = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
        return longitude.matches(checkLongitude) && latitude.matches(checkLatitude);
    }

    /**
     * 获取经纬度的中心点
     *
     * @return
     */
    @NonNull
    public static LatLngSource getCenter(LatLngSource northeast, LatLngSource southwest) {
        double lat = (northeast.getLatitude() - southwest.getLatitude()) / 2.0D + southwest.getLatitude();
        double lng = (northeast.getLongitude() - southwest.getLongitude()) / 2.0D + southwest.getLongitude();
        return new LatLngSource(lat, lng);
    }

    /**
     * 获取经纬度的中心点
     *
     * @return
     */
    public static double[] getCenter(double northeastLongitude, double northeastLatitude, double southwestLongitude, double southwestLatitude) {
        double lat = (northeastLatitude - southwestLatitude) / 2.0D + southwestLatitude;
        double lng = (northeastLongitude - southwestLongitude) / 2.0D + southwestLongitude;
        return new double[]{lng, lat};
    }
}
