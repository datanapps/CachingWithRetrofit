package datanapps.caching.retrofit.network.users;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import datanapps.caching.retrofit.network.NetworkClient;
import datanapps.caching.retrofit.network.NetworkClientWithCaching;
import datanapps.caching.retrofit.network.RetrofitEventListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ApiUserServiceRepoImpl {

   private static final ApiUserServiceRepoImpl instance = new ApiUserServiceRepoImpl();

    private APIUserService mApiUser;

    public static ApiUserServiceRepoImpl getInstance() {
        return instance;
    }

    /**
     * Invoke getWeather via {@link Call} request.
     * @param retrofitEventListener of RetrofitEventListener.
     */
    public void getWeather(final RetrofitEventListener retrofitEventListener) {
        Retrofit retrofit = NetworkClientWithCaching.getRetrofitClient();
        mApiUser = retrofit.create(APIUserService.class);
        Map<String, String> data = new HashMap<>();
        data.put("page", "1");


        Call apiUserCall = mApiUser.getUserList(data);
       /*
        This is the line which actually sends a network request. Calling enqueue() executes a call asynchronously. It has two callback listeners which will invoked on the main thread
        */
        apiUserCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                 */
                if (response.body() != null) {
                        retrofitEventListener.onSuccess(call, response.body());
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                /*
                Error callback
                */
                retrofitEventListener.onError(call, t);
            }
        });
    }

}
