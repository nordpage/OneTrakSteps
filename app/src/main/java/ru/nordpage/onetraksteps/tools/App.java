package ru.nordpage.onetraksteps.tools;

import android.app.Application;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ru.nordpage.onetraksteps.tools.Constant.BASE_URL;

public class App extends Application {

    private static StepsApi stepsApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        stepsApi = retrofit.create(StepsApi.class);
    }

    public static StepsApi getApi() {
        return stepsApi;
    }
}

