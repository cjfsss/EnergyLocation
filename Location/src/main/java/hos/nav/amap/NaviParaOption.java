package hos.nav.amap;


import hos.location.LatLngSource;

/**
 * <p>Title: NaviParaOption </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 14:57
 */
public class NaviParaOption {
    LatLngSource startPoint;
    String startName;
    LatLngSource endPoint;
    String endName;

    public NaviParaOption() {
    }

    public NaviParaOption startPoint(LatLngSource startPoint) {
        this.startPoint = startPoint;
        return this;
    }

    public NaviParaOption startName(String startName) {
        this.startName = startName;
        return this;
    }

    public NaviParaOption endPoint(LatLngSource endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public NaviParaOption endName(String endName) {
        this.endName = endName;
        return this;
    }

    public LatLngSource getStartPoint() {
        return this.startPoint;
    }

    public LatLngSource getEndPoint() {
        return this.endPoint;
    }

    public String getStartName() {
        return this.startName;
    }

    public String getEndName() {
        return this.endName;
    }
}
