package datanapps.caching.retrofit.network;

import android.content.Context;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import datanapps.caching.retrofit.CachingWithRetrofitApp;
import datanapps.caching.retrofit.utils.Utils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    //"https://datanapps.com/DNARestAPIs/getUserLists"

    private static final String BASE_URL = "https://datanapps.com";

    private static final int TIMEOUT = 10;
    private static final long cacheSize = 5 * 1024 *1024 ; // 5 x 1024 x 1024
    public static Retrofit retrofit;


    public static Retrofit getRetrofitClient() {

        if (retrofit == null) {
            Cache myCache = new Cache(CachingWithRetrofitApp.getAppContext().getCacheDir(), cacheSize);
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
            okHttpClientBuilder.cache(myCache);
            okHttpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();

                    if (Utils.isNetworkConnected(CachingWithRetrofitApp.getAppContext())) {
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                    } else {
                        request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }


                    return chain.proceed(request);
                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }


}
