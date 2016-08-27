package com.zac4j.droidbootstrap.data;

import com.zac4j.droidbootstrap.data.local.PreferencesHelper;
import com.zac4j.droidbootstrap.data.remote.ApiServer;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Data access manager
 * Created by zac on 16-7-21.
 */

@Singleton
public class DataManager {

  private ApiServer mApiServer;
  private PreferencesHelper mPrefsHelper;

  @Inject
  public DataManager(ApiServer apiServer, PreferencesHelper prefsHelper) {
    mApiServer = apiServer;
    mPrefsHelper = prefsHelper;
  }

  public PreferencesHelper getPrefsHelper() {
    return mPrefsHelper;
  }

  public ApiServer getApiServer() {
    return mApiServer;
  }

}
