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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Time format toolkit
 * Created by zac on 16-7-21.
 */

public class TimeUtils {

  private static final long SECOND = 1000L;
  private static final long MINUTE = 60 * SECOND;
  private static final long HOUR = 60 * MINUTE;
  private static final long DAY = 24 * HOUR;
  private static final long SECOND_MILLIS = 1000000000000L;

  public static String getTimeAgo(long time) {
    // If timestamp given in seconds, convert to millis
    long now = System.currentTimeMillis();
    time = getTimeMillis(time);

    long diff = now - time;
    if (diff < MINUTE) {
      return "just now";
    } else if (diff < 2 * MINUTE) {
      return "a minute ago";
    } else if (diff < 50 * MINUTE) {
      return diff / MINUTE + " minutes ago";
    } else if (diff < 90 * MINUTE) {
      return "an hour ago";
    } else if (diff < 24 * HOUR) {
      return diff / HOUR + " hours ago";
    } else if (diff < 48 * HOUR) {
      return "yesterday";
    } else {
      return diff / DAY + " days ago";
    }
  }

  /**
   * Generate a fancy timestamp based on unix epoch time that is more user friendly than just
   * a raw output by collapsing the time into manageable formats based on how much time has
   * elapsed since epoch
   *
   * @param epoch the time in unix epoch
   * @return the fancy timestamp
   */
  public static String getFancyTimeStamp(long epoch) {
    // First, check to see if it's within 1 minute of the current date
    epoch = getTimeMillis(epoch);
    if (System.currentTimeMillis() - epoch < 60000) {
      return "Just now";
    }

    // Get calendar for just now
    Calendar now = Calendar.getInstance();

    // Generate Calendar for this time
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(epoch);

    // Based on the date, determine what to print out
    // 1) Determine if time is the same day
    if (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
      // 2) Determine if time is the same month
      if (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH)) {
        // 3) Determine if time is the same day
        if (cal.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)) {
          // true, return just the time
          SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
          return formatter.format(cal.getTime());
        } else {
          // false, return the week and time
          SimpleDateFormat formatter = new SimpleDateFormat("EEE, h:mm a", Locale.getDefault());
          return formatter.format(cal.getTime());
        }
      } else {
        SimpleDateFormat formatter =
            new SimpleDateFormat("EEE, MMM d, h:mm a", Locale.getDefault());
        return formatter.format(cal.getTime());
      }
    } else {
      SimpleDateFormat format = new SimpleDateFormat("M/d/yy", Locale.getDefault());
      return format.format(cal.getTime());
    }
  }

  private static long getTimeMillis(long time) {
    return time < SECOND_MILLIS ? time * 1000 : time;
  }
}
