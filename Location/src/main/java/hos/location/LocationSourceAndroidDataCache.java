package hos.location;

import android.content.Context;
import android.location.Criteria;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hos.util.cache.StorageFile;


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
    protected void saveCacheLocation(@NonNull String key, Location location) {
        StorageFile.saveCache(key, (Parcelable) location);
        if (location instanceof LocationData) {
            StorageFile.saveCache(key + "androidLocation", (Parcelable) ((LocationData) location).getLocation());
        }
    }

    @Nullable
    @Override
    protected Location getCacheLocation(@NonNull String key) {
        Location location = StorageFile.getCache(key, Location.CREATOR);
        if (location == null) {
            return null;
        }
        android.location.Location androidLocation = StorageFile.getCache(key + "androidLocation", android.location.Location.CREATOR);
        if (androidLocation == null) {
            return new LocationData(location);
        }
        return new LocationData(location, androidLocation);
    }
}
