# CachingWithRetrofit

Simple way to cache  your request with help of retrofit http (Store data offline )


For more detail (max-age, max-stale and only-if-cached):

https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control


![alt text](https://github.com/datanapps/CachingWithRetrofit/blob/master/screens/demo_0.gif)



![alt text](https://github.com/datanapps/CachingWithRetrofit/blob/master/screens/demo_1.gif)

### Download APK : 

https://github.com/datanapps/CachingWithRetrofit/blob/master/screens/app-debug.apk



### Retrofit Network Client :



    public class NetworkClient {


    private static final String BASE_URL = "https://reqres.in";

    private static final int TIMEOUT = 10;
    private static final long cacheSize = 5 * 1024 *1024 ; // 5 x 1024 x 1024

    public static Retrofit retrofit;
    public static Retrofit getRetrofitClient(final Context context) {
        if (retrofit == null) {
            Cache myCache = new Cache(context.getCacheDir(), cacheSize);
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
            okHttpClientBuilder.cache(myCache);
            okHttpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();

                    if (Utils.isNetworkConnected(context)) {
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

