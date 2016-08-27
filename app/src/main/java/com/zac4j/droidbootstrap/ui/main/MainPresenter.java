package com.zac4j.droidbootstrap.ui.main;

import com.zac4j.droidbootstrap.data.DataManager;
import com.zac4j.droidbootstrap.di.PerConfig;
import com.zac4j.droidbootstrap.ui.base.BasePresenter;
import com.zac4j.droidbootstrap.util.RxUtils;
import javax.inject.Inject;
import rx.Subscription;

/**
 * Main presenter
 * Created by zac on 16-8-27.
 */
@PerConfig
public class MainPresenter extends BasePresenter<MainView> {

  private DataManager mDataManager;
  private Subscription mSubscription;

  @Inject
  public MainPresenter(DataManager dataManager) {
    mDataManager = dataManager;
  }

  @Override public void attach(MainView mvpView) {
    super.attach(mvpView);
  }

  @Override public void detach() {
    super.detach();
    RxUtils.unsubscribe(mSubscription);
  }
}
