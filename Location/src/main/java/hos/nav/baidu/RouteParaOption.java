package hos.nav.baidu;


import hos.location.LatLngSource;

/**
 * <p>Title: RouteParaOption </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 15:05
 */
public class RouteParaOption {
    LatLngSource startPoint;
    LatLngSource endPoint;
    String startName;
    String endName;
    String cityName;
    EBusStrategyType busStrategyType;

    public RouteParaOption() {
        this.busStrategyType = EBusStrategyType.bus_recommend_way;
    }

    public RouteParaOption startPoint(LatLngSource startPoint) {
        this.startPoint = startPoint;
        return this;
    }

    public LatLngSource getStartPoint() {
        return this.startPoint;
    }

    public RouteParaOption endPoint(LatLngSource endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public LatLngSource getEndPoint() {
        return this.endPoint;
    }

    public RouteParaOption startName(String startName) {
        this.startName = startName;
        return this;
    }

    public String getStartName() {
        return this.startName;
    }

    public RouteParaOption endName(String endName) {
        this.endName = endName;
        return this;
    }

    public String getEndName() {
        return this.endName;
    }

    public RouteParaOption busStrategyType(EBusStrategyType var1) {
        this.busStrategyType = var1;
        return this;
    }

    public EBusStrategyType getBusStrategyType() {
        return this.busStrategyType;
    }

    public RouteParaOption cityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getCityName() {
        return this.cityName;
    }

    public enum EBusStrategyType {
        bus_time_first,
        bus_transfer_little,
        bus_walk_little,
        bus_no_subway,
        bus_recommend_way;

    }
}
