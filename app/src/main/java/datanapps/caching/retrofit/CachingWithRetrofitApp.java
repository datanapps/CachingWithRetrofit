package datanapps.caching.retrofit;

import android.app.Application;
import android.content.Context;

public class CachingWithRetrofitApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        CachingWithRetrofitApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return CachingWithRetrofitApp.context;
    }
}
