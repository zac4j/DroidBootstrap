/*
 * Copyright (c) 2015 Zac
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zac4j.droidbootstrap.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.inputmethod.InputMethodManager;
import java.io.Closeable;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Utilities
 * Created by zac on 16-7-21.
 */

public class Utils {

  public static String md5(final String originalText) {
    try {
      final MessageDigest digest = MessageDigest.getInstance("md5");
      digest.update(originalText.getBytes());
      final byte[] bytes = digest.digest();
      final StringBuilder sb = new StringBuilder();
      for (byte aByte : bytes) {
        sb.append(String.format("%02X", aByte));
      }
      return sb.toString().toLowerCase();
    } catch (Exception exc) {
      return "";
    }
  }

  public static void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static float pxToDp(float px) {
    float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
    return px / (densityDpi / 160f);
  }

  public static int dpToPx(int dp) {
    float density = Resources.getSystem().getDisplayMetrics().density;
    return Math.round(dp * density);
  }

  public static void hideKeyboard(Activity activity) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
  }

}
