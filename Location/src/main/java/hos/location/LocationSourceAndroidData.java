package hos.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Title: LocationSourceAndroid </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 9:09
 */
@SuppressLint("MissingPermission")
public abstract class LocationSourceAndroidData extends LocationSource {

    private final Context mContext;
    private float mMinimumUpdateDistance;
    private long mMinimumUpdateTime;
    private LocationManager mLocationManager;
    private final List<String> mSelectedLocationProviders;
    private InternalLocationListener mInternalLocationListener;
    private SensorManager mSensorManager;
    private InternalHeadingListener mInternalHeadingListener;
    private Criteria mCriteria;
    private String mProvider;
    private Location mLastLocation;

    public LocationSourceAndroidData(Context context) {
        this.mMinimumUpdateDistance = 0.0F;
        this.mMinimumUpdateTime = 100L;
        this.mSelectedLocationProviders = new ArrayList<>();
        this.mContext = context.getApplicationContext();
    }

    public LocationSourceAndroidData(Context context, Criteria criteria, long minTime, float minDistance) {
        this(context);
        this.mCriteria = criteria;
        this.setMinTimeDistance(minTime, minDistance);
    }

    public LocationSourceAndroidData(Context context, String provider, long minTime, float minDistance) {
        this(context);
        this.mProvider = provider;
        this.setMinTimeDistance(minTime, minDistance);
    }

    private void request() {
        android.location.Location var1;
        if ((var1 = this.mLocationManager.getLastKnownLocation((String)this.mSelectedLocationProviders.get(0))) != null) {
            var1.setSpeed(0.0F);
            var1.setBearing(0.0F);
            this.setLastKnownLocation(convertLocation(var1, true));
        }
        this.requestLocationUpdates();
        this.registerSensorManager();
    }

    @SuppressLint("WrongConstant")
    private void loadLocationManager() {
        if (this.mContext != null) {
            this.mLocationManager = (LocationManager)mContext.getSystemService("location");
        } else {
            throw new IllegalArgumentException(String.format("Parameter %s must not be null", "context"));
        }
    }

    private void setMinTimeDistance(long minTime, float minDistance) {
        if (minTime >= 0L) {
            this.mMinimumUpdateTime = minTime;
            if (minDistance >= 0.0F) {
                this.mMinimumUpdateDistance = minDistance;
            } else {
                throw new IllegalArgumentException(String.format("Parameter %s is out of bounds", "minDistance"));
            }
        } else {
            throw new IllegalArgumentException(String.format("Parameter %s is out of bounds", "minTime"));
        }
    }

    private void changeProvider() {
        if (this.mLocationManager.isProviderEnabled("network")) {
            this.mSelectedLocationProviders.add("network");
        }

        if (this.mLocationManager.isProviderEnabled("gps")) {
            this.mSelectedLocationProviders.add("gps");
        }
    }

    private void requestLocationUpdates() {
        if (this.mInternalLocationListener == null) {
            this.mInternalLocationListener = new InternalLocationListener();
        }
        Iterator var6 = this.mSelectedLocationProviders.iterator();
        while(var6.hasNext()) {
            this.mLocationManager.requestLocationUpdates((String)var6.next(), mMinimumUpdateTime, mMinimumUpdateDistance, mInternalLocationListener);
        }
    }

    private void b(android.location.Location currentLocation, boolean var2) {
        if (currentLocation != null) {
            Location location;
            if ((location = this.mLastLocation) != null) {
                double horizontalAccuracy = location.getHorizontalAccuracy() * 2.0D;
                if ((double)currentLocation.getAccuracy() > horizontalAccuracy) {
                    return;
                }
            }
            Location newLocation;
            this.updateLocation(newLocation = convertLocation(currentLocation, var2));
            mLastLocation = newLocation;
        }
    }

    private void setCriteria(Criteria criteria) {
        if (criteria != null) {
            String baseProvider;
            if ((baseProvider = this.mLocationManager.getBestProvider(criteria, true)) != null) {
                this.mSelectedLocationProviders.add(baseProvider);
            }
        } else {
            throw new IllegalArgumentException(String.format("Parameter %s must not be null", "criteria"));
        }
    }

    private void addProvider(String var1) {
        if (this.mLocationManager.getAllProviders().contains(var1)) {
            this.mSelectedLocationProviders.add(var1);
        }
    }

    @SuppressLint("WrongConstant")
    private void registerSensorManager() {
        if (this.mSensorManager == null) {
            this.mSensorManager = (SensorManager)this.mContext.getSystemService("sensor");
        }
        if (this.mInternalHeadingListener == null) {
            this.mInternalHeadingListener = new InternalHeadingListener();
        }
        mSensorManager.registerListener(mInternalHeadingListener,
                mSensorManager.getDefaultSensor(1), 2);
        mSensorManager.registerListener(mInternalHeadingListener,
                mSensorManager.getDefaultSensor(2), 2);
    }

    private void unregisterSensorManager() {
        if (this.mSensorManager != null && this.mInternalHeadingListener != null) {
            mSensorManager.unregisterListener(mInternalHeadingListener);
            this.mInternalHeadingListener = null;
        }
//        this.updateHeading(0.0D / 0.0);
    }

    private static final Location convertLocation(android.location.Location androidLocation, boolean var1) {
        if (androidLocation != null) {
            return new LocationData(androidLocation);
        } else {
            throw new IllegalArgumentException(String.format("Parameter %s must not be null",  "location"));
        }
    }

    public void requestLocationUpdates(Criteria criteria, long minTime, float minDistance) {
        if (this.isStarted()) {
            this.mSelectedLocationProviders.clear();
            this.setMinTimeDistance(minTime, minDistance);
            this.setCriteria(criteria);
            if (!this.mSelectedLocationProviders.isEmpty()) {
                this.requestLocationUpdates();
            } else {
                throw new IllegalStateException("No location provider found on the device");
            }
        } else {
            throw new IllegalStateException("The location data source is not started yet");
        }
    }

    public void requestLocationUpdates(String provider, long minTime, float minDistance) {
        if (this.isStarted()) {
            this.setMinTimeDistance(minTime, minDistance);
            this.addProvider(provider);
            if (!this.mSelectedLocationProviders.isEmpty()) {
                this.requestLocationUpdates();
            } else {
                throw new IllegalArgumentException(String.format("No provider found for the given name : %s", provider));
            }
        } else {
            throw new IllegalStateException("The location data source is not started yet");
        }
    }

    protected void onStart() {
        new Handler(this.mContext.getMainLooper()).post(new Runnable() {
            public void run() {
                try {
                    loadLocationManager();
                    if (LocationSourceAndroidData.this.mCriteria != null) {
                        LocationSourceAndroidData.this.setCriteria(LocationSourceAndroidData.this.mCriteria);
                    } else if (LocationSourceAndroidData.this.mProvider != null) {
                        LocationSourceAndroidData.this.addProvider(LocationSourceAndroidData.this.mProvider);
                    } else {
                        LocationSourceAndroidData.this.changeProvider();
                    }
                    if (LocationSourceAndroidData.this.mSelectedLocationProviders.isEmpty()) {
                        throw new IllegalStateException(String.format("No provider found for the given name : %s", "selectedLocationProviders"));
                    }
                    LocationSourceAndroidData.this.request();
                } catch (Exception e) {
                    e.printStackTrace();
                    setThrowable(e);
                    notifyLocation();
                }
            }
        });
    }

    protected void onStop() {
        this.mLocationManager.removeUpdates(this.mInternalLocationListener);
        this.mInternalLocationListener = null;
        this.unregisterSensorManager();
    }

    @Override
    public LocationSourceAndroidData.LocationData getLocation() {
        return (LocationSourceAndroidData.LocationData) super.getLocation();
    }

    @Override
    protected void onStartForegroundLocation(Activity activity) {

    }

    @Override
    protected void onStopForegroundLocation() {

    }

    @Override
    public String getCoordType() {
        return CoordType.WGS84.getName();
    }

    private static final class InternalHeadingListener implements SensorEventListener {
        private final float[] mGravity;
        private final float[] mGeomagnetic;
        private final float[] mR;
        private final float[] mI;
        private final float[] mOrientation;
        private float mHeading;
        private float mLastHeading;
        private final float RAD_2_DEG;
        private final float UPDATE_TOLERANCE;

        private InternalHeadingListener() {
            this.mGravity = new float[3];
            this.mGeomagnetic = new float[3];
            this.mR = new float[9];
            this.mI = new float[9];
            this.mOrientation = new float[3];
            this.RAD_2_DEG = 57.29578F;
            this.UPDATE_TOLERANCE = 7.5F;
        }

        public void onSensorChanged(SensorEvent var1) {
            int var2;
            if ((var2 = var1.sensor.getType()) == 1) {
                for(var2 = 0; var2 < 3; ++var2) {
                    this.mGravity[var2] = var1.values[var2];
                }
            } else if (var2 == 2) {
                for(var2 = 0; var2 < 3; ++var2) {
                    this.mGeomagnetic[var2] = var1.values[var2];
                }
            }

            float[] var5 = this.mI;
            float[] var7 = this.mGravity;
            float[] var3 = this.mGeomagnetic;
            if (SensorManager.getRotationMatrix(this.mR, var5, var7, var3)) {
                SensorManager.getOrientation(this.mR, this.mOrientation);
                float var6;
                float var10000 = var6 = this.mOrientation[0] * 57.29578F;
                this.mHeading = var6;
                if (var10000 < 0.0F) {
                    this.mHeading = var6 + 360.0F;
                }

                if ((var6 = Math.abs(this.mHeading - this.mLastHeading)) > 7.5F && var6 < 352.5F) {
                    this.mLastHeading  = this.mHeading;
//                    LocationSourceAndroidData.this.updateHeading((double)mLastHeading);
                }
            }

        }

        public void onAccuracyChanged(Sensor var1, int var2) {
        }
    }

    private final class InternalLocationListener implements LocationListener {
        private android.location.Location mInnerAndroidLocation;

        private InternalLocationListener() {
        }

        public void onLocationChanged(android.location.Location var1) {
            LocationSourceAndroidData.this.b(var1, false);
            this.mInnerAndroidLocation = var1;
        }

        public void onProviderEnabled(String var1) {
            if (LocationSourceAndroidData.this.mSelectedLocationProviders.contains(var1)) {
                LocationSourceAndroidData.this.requestLocationUpdates();
            }

        }

        public void onProviderDisabled(String var1) {
            if (LocationSourceAndroidData.this.mSelectedLocationProviders.contains(var1) && LocationSourceAndroidData.this.mSelectedLocationProviders.size() == 1) {
                LocationSourceAndroidData.this.b(this.mInnerAndroidLocation, true);
            }

        }

        public void onStatusChanged(String var1, int var2, Bundle var3) {
            if (LocationSourceAndroidData.this.mSelectedLocationProviders.contains(var1)) {
                if (var2 == 2) {
                    LocationSourceAndroidData.this.requestLocationUpdates();
                } else {
                    LocationSourceAndroidData.this.b(this.mInnerAndroidLocation, true);
                }
            }

        }
    }

    public static class LocationData extends Location {

        private android.location.Location location;

        public LocationData() {
            super();
        }

        public LocationData(Location location) {
            super(location);
        }

        public LocationData(Location location, android.location.Location location1) {
            super(location);
            this.location = location1;
        }

        public LocationData(android.location.Location location) {
            super(location);
            this.location = location;
        }

        @Override
        protected void initCoordType() {
            setCoordType(CoordType.WGS84.getName());
        }

        @Override
        public <T> T getLocationFor(Class<T> clazz) {
            return clazz.cast(location);
        }

        public android.location.Location getLocation() {
            return location;
        }

        @Override
        @NonNull
        public String toString() {
            return "LocationData{" +
                    "location=" + location +
                    "} " + super.toString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.location, flags);
        }

        protected LocationData(Parcel in) {
            super(in);
            this.location = in.readParcelable(android.location.Location.class.getClassLoader());
        }

        public static final Creator<LocationData> CREATOR = new Creator<LocationData>() {
            @Override
            public LocationData createFromParcel(Parcel source) {
                return new LocationData(source);
            }

            @Override
            public LocationData[] newArray(int size) {
                return new LocationData[size];
            }
        };
    }
}
