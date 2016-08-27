package com.zac4j.droidbootstrap;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.facebook.stetho.Stetho;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.zac4j.droidbootstrap.di.ActivityContext;
import com.zac4j.droidbootstrap.di.component.ApplicationComponent;
import com.zac4j.droidbootstrap.di.component.DaggerApplicationComponent;
import com.zac4j.droidbootstrap.di.module.ApplicationModule;
import timber.log.Timber;

/**
 * App
 * Created by zac on 16-7-21.
 */

public class App extends Application {

  private ApplicationComponent mApplicationComponent;

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
      Stetho.initializeWithDefaults(this);
    } else {
      CrashReport.initCrashReport(getApplicationContext(), "Tencent Bugly Id", false);
      Timber.plant(new CrashReportingTree());
    }
  }

  /**
   * There is no TAG parameter. It’s automatically assigned as the caller class’ name by Timber.
   */
  private static class CrashReportingTree extends Timber.Tree {
    @Override protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }

      BuglyLog.i(tag, message);

      if (t != null) {
        if (priority == Log.ERROR) {
          BuglyLog.e(tag, message, t);
        } else if (priority == Log.WARN) {
          BuglyLog.w(tag, message);
        }
      }
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
