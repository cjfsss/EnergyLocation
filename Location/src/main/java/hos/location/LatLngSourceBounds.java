package hos.location;

import android.os.Parcel;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    LatLngSourceBounds(@NonNull LatLngSource var1, @NonNull LatLngSource var2) {
        this.northeast = var1;
        this.southwest = var2;
    }

    protected LatLngSourceBounds(Parcel var1) {
        this.northeast = (LatLngSource) var1.readParcelable(LatLngSource.class.getClassLoader());
        this.southwest = (LatLngSource) var1.readParcelable(LatLngSource.class.getClassLoader());
    }

    public boolean contains(@Nullable LatLngSource var1) {
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

    public boolean contains(@Nullable LatLngSourceBounds var1) {
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

    public boolean intersects(@Nullable LatLngSourceBounds var1) {
        if (var1 == null) {
            return false;
        } else if (this.northeast != null && this.southwest != null) {
            return this.a(var1) || var1.a(this);
        } else {
            Log.e("LatLngBounds", "current LatLngBounds is invalid, please check the structure parameters are legal");
            return false;
        }
    }

    @NonNull
    public LatLngSource getCenter() {
        return LatLngUtils.getCenter(northeast, southwest);
    }

    private boolean a(@Nullable LatLngSourceBounds var1) {
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

    @NonNull
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
        private double a;
        private double b;
        private double c;
        private double d;
        private boolean e = true;

        public Builder() {
        }

        @NonNull
        public LatLngSourceBounds build() {
            LatLngSource var1 = new LatLngSource(this.b, this.d);
            LatLngSource var2 = new LatLngSource(this.a, this.c);
            return new LatLngSourceBounds(var1, var2);
        }

        @NonNull
        public Builder include(@Nullable LatLngSource var1) {
            if (var1 == null) {
                return this;
            } else {
                if (this.e) {
                    this.e = false;
                    this.b = this.a = var1.getLatitude();
                    this.d = this.c = var1.getLongitude();
                }

                this.a(var1);
                return this;
            }
        }

        @NonNull
        public Builder include(@Nullable List<LatLngSource> var1) {
            if (var1 != null && var1.size() != 0) {
                if (var1.get(0) != null && this.e) {
                    this.e = false;
                    this.b = this.a = ((LatLngSource) var1.get(0)).getLatitude();
                    this.d = this.c = ((LatLngSource) var1.get(0)).getLongitude();
                }
                Iterator<LatLngSource> var2 = var1.iterator();
                while (var2.hasNext()) {
                    LatLngSource var3 = var2.next();
                    this.a(var3);
                }
                return this;
            } else {
                return this;
            }
        }

        private void a(@Nullable LatLngSource var1) {
            if (var1 != null) {
                double var2 = var1.getLatitude();
                double var4 = var1.getLongitude();
                if (var2 < this.a) {
                    this.a = var2;
                }
                if (var2 > this.b) {
                    this.b = var2;
                }
                if (var4 < this.c) {
                    this.c = var4;
                }
                if (var4 > this.d) {
                    this.d = var4;
                }
            }
        }
    }
}
