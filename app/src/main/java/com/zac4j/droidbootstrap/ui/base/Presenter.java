package com.zac4j.droidbootstrap.ui.base;

/**
 * Presenter interface
 * Created by zac on 16-7-21.
 */

public interface Presenter<T extends MvpView> {

  void attach(T mvpView);

  void detach();
}
