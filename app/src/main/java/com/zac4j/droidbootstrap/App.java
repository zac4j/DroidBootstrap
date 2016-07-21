package com.zac4j.droidbootstrap;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.zac4j.droidbootstrap.di.ActivityContext;
import com.zac4j.droidbootstrap.di.component.ApplicationComponent;
import com.zac4j.droidbootstrap.di.component.DaggerApplicationComponent;
import com.zac4j.droidbootstrap.di.module.ApplicationModule;

/**
 * App
 * Created by zac on 16-7-21.
 */

public class App extends Application {

  private ApplicationComponent mApplicationComponent;

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this);
    }
  }

  public static App get(@ActivityContext Context context) {
    return (App) context.getApplicationContext();
  }

  public ApplicationComponent getApplicationComponent() {
    if (mApplicationComponent == null) {
      mApplicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
    return mApplicationComponent;
  }

}
