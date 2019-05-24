package datanapps.androidofflinecaching.network.users;

import java.util.Map;

import datanapps.androidofflinecaching.network.users.model.BaseUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * API for getting weather from https://darksky.net/
 */
public interface APIUserService {

    @GET("api/users?")
    Call<BaseUser> getUserList(@QueryMap Map<String, String> map);



}
