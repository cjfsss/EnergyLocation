package hos.location;

import android.os.Parcel;
import android.util.Log;




import java.util.Iterator;
import java.util.List;

/**
 * <p>Title: LatLngSourceBounds </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/11/24 15:00
 */
public class LatLngSourceBounds {

    public final LatLngSource northeast;
    public final LatLngSource southwest;

    LatLngSourceBounds( LatLngSource var1,  LatLngSource var2) {
        this.northeast = var1;
        this.southwest = var2;
    }

    protected LatLngSourceBounds(Parcel var1) {
        this.northeast = (LatLngSource) var1.readParcelable(LatLngSource.class.getClassLoader());
        this.southwest = (LatLngSource) var1.readParcelable(LatLngSource.class.getClassLoader());
    }

    public boolean contains( LatLngSource var1) {
        if (var1 == null) {
            return false;
        } else {
            double var2 = this.southwest.getLatitude();
            double var4 = this.northeast.getLatitude();
            double var6 = this.southwest.getLongitude();
            double var8 = this.northeast.getLongitude();
            double var10 = var1.getLatitude();
            double var12 = var1.getLongitude();
            return var10 >= var2 && var10 <= var4 && var12 >= var6 && var12 <= var8;
        }
    }

    public boolean contains( LatLngSourceBounds var1) {
        boolean var2 = false;
        if (var1 == null) {
            return var2;
        } else {
            if (this.contains(var1.southwest) && this.contains(var1.northeast)) {
                var2 = true;
            }
            return var2;
        }
    }

    public boolean intersects( LatLngSourceBounds var1) {
        if (var1 == null) {
            return false;
        } else if (this.northeast != null && this.southwest != null) {
            return this.a(var1) || var1.a(this);
        } else {
            Log.e("LatLngBounds", "current LatLngBounds is invalid, please check the structure parameters are legal");
            return false;
        }
    }

    
    public LatLngSource getCenter() {
        return LatLngUtils.getCenter(northeast, southwest);
    }

    private boolean a( LatLngSourceBounds var1) {
        if (var1 != null && var1.northeast != null && var1.southwest != null) {
            double var2 = var1.northeast.getLongitude() + var1.southwest.getLongitude() - this.northeast.getLongitude() - this.southwest.getLongitude();
            double var4 = this.northeast.getLongitude() - this.southwest.getLongitude() + var1.northeast.getLongitude() - this.southwest.getLongitude();
            double var6 = var1.northeast.getLatitude() + var1.southwest.getLatitude() - this.northeast.getLatitude() - this.southwest.getLatitude();
            double var8 = this.northeast.getLatitude() - this.southwest.getLatitude() + var1.northeast.getLatitude() - var1.southwest.getLatitude();
            return Math.abs(var2) < var4 && Math.abs(var6) < var8;
        } else {
            return false;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel var1, int var2) {
        var1.writeParcelable(this.northeast, var2);
        var1.writeParcelable(this.southwest, var2);
    }

    
    public String toString() {
        StringBuilder var1 = new StringBuilder();
        var1.append("southwest: ");
        var1.append(this.southwest.getLatitude());
        var1.append(", ");
        var1.append(this.southwest.getLongitude());
        var1.append("\n");
        var1.append("northeast: ");
        var1.append(this.northeast.getLatitude());
        var1.append(", ");
        var1.append(this.northeast.getLongitude());
        return var1.toString();
    }

    public static final class Builder {
        private double southLat;
        private double northLat;
        private double southLng;
        private double northLng;
        private boolean isInclude = true;

        public Builder() {
        }

        
        public LatLngSourceBounds build() {
            LatLngSource north = new LatLngSource(this.northLat, this.northLng);
            LatLngSource south = new LatLngSource(this.southLat, this.southLng);
            return new LatLngSourceBounds(north, south);
        }

        
        public Builder include( LatLngSource var1) {
            if (var1 == null) {
                return this;
            } else {
                if (this.isInclude) {
                    this.isInclude = false;
                    this.northLat = this.southLat = var1.getLatitude();
                    this.northLng = this.southLng = var1.getLongitude();
                }

                this.handle(var1);
                return this;
            }
        }

        
        public Builder include( List<LatLngSource> var1) {
            if (var1 != null && var1.size() != 0) {
                if (var1.get(0) != null && this.isInclude) {
                    this.isInclude = false;
                    this.northLat = this.southLat = ((LatLngSource) var1.get(0)).getLatitude();
                    this.northLng = this.southLng = ((LatLngSource) var1.get(0)).getLongitude();
                }
                Iterator<LatLngSource> var2 = var1.iterator();
                while (var2.hasNext()) {
                    LatLngSource var3 = var2.next();
                    this.handle(var3);
                }
                return this;
            } else {
                return this;
            }
        }

        private void handle( LatLngSource var1) {
            if (var1 != null) {
                double lat = var1.getLatitude();
                double lng = var1.getLongitude();
                if (lat < this.southLat) {
                    this.southLat = lat;
                }
                if (lat > this.northLat) {
                    this.northLat = lat;
                }
                if (lng < this.southLng) {
                    this.southLng = lng;
                }
                if (lng > this.northLng) {
                    this.northLng = lng;
                }
            }
        }
    }
}
