package datanapps.caching.retrofit.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import datanapps.caching.retrofit.R;
import datanapps.caching.retrofit.network.RetrofitEventListener;
import datanapps.caching.retrofit.network.users.ApiUserServiceRepoImpl;
import datanapps.caching.retrofit.network.users.model.BaseUser;
import datanapps.caching.retrofit.utils.Utils;
import retrofit2.Call;

public class UserListActivity extends AppCompatActivity {


    @BindView(R.id.weather_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout swipeRefreshLayout;


    private UserListAdapter weatherAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        setRecycleView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                uploadWeatherData();
            }
        });

        uploadWeatherData();
    }

    private void setRecycleView() {
        weatherAdapter = new UserListAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(weatherAdapter);
    }



     void uploadWeatherData() {

         if (!Utils.isNetworkConnected(this)) {
             Snackbar.make(recyclerView, "No internet", Snackbar.LENGTH_LONG).show();
         }


        ApiUserServiceRepoImpl.getInstance().getWeather(this, new RetrofitEventListener() {
            @Override
            public void onSuccess(Call call, Object response) {
                if (response instanceof BaseUser) {
                    Log.d("asd", "-----" + ((BaseUser) response).getData().size());
                    Snackbar.make(recyclerView, "get data", Snackbar.LENGTH_LONG).show();
                    weatherAdapter.setAlbumList(((BaseUser) response).getData());
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Call call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                // snack bar that city can not find
            }
        });
    }

}
