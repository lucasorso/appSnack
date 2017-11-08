package com.burguer.zap.burguer.rest.generic;

import com.burguer.zap.burguer.properties.WS_PROPERTY;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LucasOrso on 10/5/17.
 */

public class BaseRetrofit {

    public Retrofit buildRetrofit() {
        Gson lGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ").create();
        HttpLoggingInterceptor lInterceptor = new HttpLoggingInterceptor();
        lInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient lBuild = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(lInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(WS_PROPERTY.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(lGson))
                .client(lBuild)
                .build();
    }

    public static Map<String, String> getParams(GenericRequest aRequest) {
        Gson gson = new GsonBuilder().create();
        String lS = gson.toJson(aRequest);
        return gson.<HashMap<String, String>>fromJson(lS, new TypeToken<HashMap<String, String>>() {}.getType());
    }
}
