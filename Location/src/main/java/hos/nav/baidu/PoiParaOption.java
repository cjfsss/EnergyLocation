package hos.nav.baidu;


import hos.location.LatLngSource;

/**
 * <p>Title: PoiParaOption </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 15:03
 */
public class PoiParaOption {

    String key;
    LatLngSource center;
    int radius;

    public PoiParaOption() {
    }

    public PoiParaOption key(String key) {
        this.key = key;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public PoiParaOption center(LatLngSource center) {
        this.center = center;
        return this;
    }

    public LatLngSource getCenter() {
        return this.center;
    }

    public PoiParaOption radius(int radius) {
        this.radius = radius;
        return this;
    }

    public int getRadius() {
        return this.radius;
    }
}
