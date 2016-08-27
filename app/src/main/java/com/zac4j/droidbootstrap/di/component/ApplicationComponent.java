package com.zac4j.droidbootstrap.di.component;

import android.content.Context;

import com.google.gson.Gson;
import com.zac4j.droidbootstrap.data.local.PreferencesHelper;
import com.zac4j.droidbootstrap.data.remote.ApiServer;
import com.zac4j.droidbootstrap.data.remote.HttpClient;
import com.zac4j.droidbootstrap.di.ApplicationContext;
import com.zac4j.droidbootstrap.di.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Application Component
 * Created by zac on 16-7-3.
 */

@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent {
  @ApplicationContext Context context();

  PreferencesHelper prefsHelper();

  Gson gson();

  HttpClient httpClient();

  ApiServer webService();
}
