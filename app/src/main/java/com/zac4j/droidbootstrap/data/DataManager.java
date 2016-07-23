package com.zac4j.droidbootstrap.data;

import com.zac4j.droidbootstrap.data.local.PreferencesHelper;
import com.zac4j.droidbootstrap.data.remote.WebService;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zac on 16-7-21.
 */

@Singleton
public class DataManager {

  private WebService mWebService;
  private PreferencesHelper mPrefsHelper;

  @Inject
  public DataManager(WebService webService, PreferencesHelper prefsHelper) {
    mWebService = webService;
    mPrefsHelper = prefsHelper;
  }

  public PreferencesHelper getPrefsHelper() {
    return mPrefsHelper;
  }
}
