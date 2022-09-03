package hos.location;

import android.content.Context;
import android.location.Criteria;
import android.os.Parcelable;

import hos.cache.StorageFile;


/**
 * <p>Title: LocationSourceAndroidCache </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/5/8 11:05
 */
public class LocationSourceAndroidDataCache extends LocationSourceAndroidData {

    public LocationSourceAndroidDataCache(Context context) {
        super(context);
    }

    public LocationSourceAndroidDataCache(Context context, Criteria criteria, long minTime, float minDistance) {
        super(context, criteria, minTime, minDistance);
    }

    public LocationSourceAndroidDataCache(Context context, String provider, long minTime, float minDistance) {
        super(context, provider, minTime, minDistance);
    }

    @Override
    protected void saveCacheLocation( String key, Location location) {
        try {
            StorageFile.saveCache(key, (Parcelable) location);
            if (location instanceof LocationData) {
                StorageFile.saveCache(key + "androidLocation", (Parcelable) ((LocationData) location).getLocation());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Override
    protected Location getCacheLocation( String key) {
        try {
            Location location = StorageFile.getData(key, Location.CREATOR);
            if (location == null) {
                return null;
            }
            android.location.Location androidLocation = StorageFile.getData(key + "androidLocation", android.location.Location.CREATOR);
            if (androidLocation == null) {
                return new LocationData(location);
            }
            return new LocationData(location, androidLocation);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
