package com.zac4j.droidbootstrap.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Image process utility
 * Created by zac on 16-8-25.
 */
public class ImageUtils {

  public static final String CROP_IMAGE_NAME = "cropped.jpg";

  /**
   * Verify if can capture image
   * @param context ui context
   * @param imageFile image file
   * @return true if can capture image
   */
  public static boolean isCanTakePhoto(Context context, File imageFile) {
    return imageFile != null
        && new Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(context.getPackageManager())
        != null;
  }

  public static Bitmap getBitmapByUri(Context context, Uri imageUri, ImageView imageHolder) {
    String[] projection = { MediaStore.MediaColumns.DATA };
    CursorLoader loader = new CursorLoader(context, imageUri, projection, null, null, null);
    Cursor cursor = loader.loadInBackground();
    int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
    cursor.moveToFirst();

    String imagePath = cursor.getString(index);

    int reqWidth = imageHolder.getWidth();
    int reqHeight = imageHolder.getHeight();

    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, options);

    // Determine scale size
    options.inSampleSize = Math.min(options.outWidth / reqWidth, options.outHeight / reqHeight);

    options.inJustDecodeBounds = false;

    return BitmapFactory.decodeFile(imagePath, options);
  }

  /**
   * Create temporary image file
   * @return a temporary image file
   */
  public static File createImageFile() {
    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    try {
      return File.createTempFile(createImageFilename(),
          ".jpg",
          storageDir);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * @return a temporary image filename
   */
  private static String createImageFilename() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    String time = formatter.format(new Date());
    return "IMG_" + time;
  }

  public static String getImagePath(File imageFile) {
    return "file:" + imageFile.getAbsolutePath();
  }

  /**
   * Pixel transfer to Dip
   *
   * @param px pixel
   * @return dip
   */
  public static float pxToDp(float px) {
    float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
    return px / (densityDpi / 160f);
  }
  /**
   * Dip transfer to Pixel
   *
   * @param dp dip
   * @return pixel
   */
  public static int dpToPx(int dp) {
    float density = Resources.getSystem().getDisplayMetrics().density;
    return Math.round(dp * density);
  }

}
