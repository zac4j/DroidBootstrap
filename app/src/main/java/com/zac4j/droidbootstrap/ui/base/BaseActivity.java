package com.zac4j.droidbootstrap.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;
import com.zac4j.droidbootstrap.App;
import com.zac4j.droidbootstrap.di.component.ActivityComponent;
import com.zac4j.droidbootstrap.di.component.DaggerPerConfigComponent;
import com.zac4j.droidbootstrap.di.component.PerConfigComponent;
import com.zac4j.droidbootstrap.di.module.ActivityModule;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base Activity
 * Created by zac on 16-7-21.
 */

public class BaseActivity extends AppCompatActivity {

  private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
  private static final AtomicLong NEXT_ID = new AtomicLong(0);
  private static final LongSparseArray<PerConfigComponent> sComponentsArray =
      new LongSparseArray<>();

  private ActivityComponent mActivityComponent;
  private long mActivityId;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
    // being called after a configuration change.
    mActivityId = savedInstanceState != null ? savedInstanceState.getLong(KEY_ACTIVITY_ID) :
        NEXT_ID.getAndIncrement();
    PerConfigComponent perConfigComponent;
    if (sComponentsArray.indexOfKey(mActivityId) < 0) {
      perConfigComponent = DaggerPerConfigComponent.builder()
          .applicationComponent(App.get(this).getApplicationComponent())
          .build();
      sComponentsArray.put(mActivityId, perConfigComponent);
    } else {
      perConfigComponent = sComponentsArray.get(mActivityId);
    }
    mActivityComponent = perConfigComponent.activityComponent(new ActivityModule(this));
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putLong(KEY_ACTIVITY_ID, mActivityId);
  }

  @Override protected void onDestroy() {
    if (!isChangingConfigurations()) {
      sComponentsArray.remove(mActivityId);
    }
    super.onDestroy();
  }

  ActivityComponent getActivityComponent() {
    return mActivityComponent;
  }

  /**
   * Show toast with given message
   * @param message message to show in the toast
   */
  public void showToast(String message) {
    if (TextUtils.isEmpty(message)) {
      return;
    }
    Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
  }

}
