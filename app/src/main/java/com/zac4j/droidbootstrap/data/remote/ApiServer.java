package com.zac4j.droidbootstrap.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Web Service
 * Created by zac on 16-7-3.
 */

public interface ApiServer {

  String BASE_URL = "";

  class Factory {
    public static ApiServer create() {
      Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
          .client(new HttpClient().create())
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();

      return retrofit.create(ApiServer.class);
    }
  }
}
