package datanapps.androidofflinecaching;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import datanapps.androidofflinecaching.network.RetrofitEventListener;
import datanapps.androidofflinecaching.network.users.ApiUserServiceRepoImpl;
import datanapps.androidofflinecaching.network.users.model.BaseUser;
import retrofit2.Call;

public class WeatherActivity extends AppCompatActivity {


    @BindView(R.id.weather_recycler_view)
    RecyclerView recyclerView;


    private WeatherAdapter weatherAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        setRecycleView();


        uploadWeatherData();
    }

    private void setRecycleView() {
        weatherAdapter = new WeatherAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(weatherAdapter);
    }



     void uploadWeatherData() {
        //showProgressDialog("Wait...");
        ApiUserServiceRepoImpl.getInstance().getWeather(new RetrofitEventListener() {
            @Override
            public void onSuccess(Call call, Object response) {
                if (response instanceof BaseUser) {
                    Log.d("asd", "-----" + ((BaseUser) response).getData().size());
                    weatherAdapter.setAlbumList(((BaseUser) response).getData());
                }
            }

            @Override
            public void onError(Call call, Throwable t) {

                // snack bar that city can not find
            }
        });
    }

}
