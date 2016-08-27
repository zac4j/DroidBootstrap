package com.zac4j.droidbootstrap.ui.main;

import android.os.Bundle;
import com.zac4j.droidbootstrap.R;
import com.zac4j.droidbootstrap.ui.base.BaseActivity;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView {

  @Inject MainPresenter mMainPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override public void showError(String message) {
    showToast(message);
  }
}
