package com.mydesignerclothing.mobile.android.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Parcel;
import android.telephony.PhoneNumberUtils;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.viewpager.widget.ViewPager;

import static android.view.View.VISIBLE;

/**
 * STATIC Utility class for many common UI features used within this application.
 */

public class MyDSCAndroidUIUtils {

  private static final String TAG = MyDSCAndroidUIUtils.class.getSimpleName();

  private static final int TEXT_SIZE = 17;

  private static final String[] mealNames = {"", "Baby Meal", "Bland Meal",
    "Child Meal", "Toddler Meal", "Diabetic Meal",
    "Gluten Free Meal", "Low Sodium/No Salt Added Meal", "Halal Meal", "Hindu Meal",
    "Vegetarian Meal (Non-Dairy)", "Low Cal, Chol, Fat Meal",
    "Dairy Vegetarian Meal", "Asian Vegetarian Meal", "Kosher Meal",
    "Japanese Meal", "Jain Meal",
    "Low Salt Meal", "Low Fat Meal", "Muslim Meal", "Vegan Vegetarian Meal", "Vegetarian/Lacto Meal"};

  private static final int LENGTH_UNMASKED = 4;
  private static final String MASK_CHAR = "*";
  private static final String WHITESPACE = "\\s";
  private static final int CHAR_AT_INDEX = 0;
  private static final int SUBSTRING_INDEX = 1;

  @SuppressWarnings("EmptyMethod")
  private MyDSCAndroidUIUtils() {
    //NOSONAR
  }

  private static String removeAreaCode(String formattedPhoneNumber) {
    return removeSectionBeforeDash(formattedPhoneNumber);
  }

  private static String getAreaCode(String formattedPhoneNumber) {
    String[] splits = formattedPhoneNumber.split("-");
    if (splits.length == 3) {
      return splits[0];
    }
    return null;
  }

  private static String removeContryCode(String formattedPhoneNumber) {
    return removeSectionBeforeDash(formattedPhoneNumber);
  }

  private static String removeSectionBeforeDash(String formattedPhoneNumber) {
    int dashIndex = formattedPhoneNumber.indexOf('-');
    return formattedPhoneNumber.substring(dashIndex + 1);
  }

  public static boolean isCountryUS(String country) {

    return "1".equals(country);
  }

  public static CharSequence setSpanBetweenTokens(CharSequence text, String token, CharacterStyle... cs) {
    // Start and end refer to the points where the span will apply
    int tokenLen = token.length();
    int start = text.toString().indexOf(token) + tokenLen;
    int end = text.toString().indexOf(token, start);
    if (start > -1 && end > -1) {
      // Copy the spannable string to a mutable spannable string
      SpannableStringBuilder ssb = new SpannableStringBuilder(text);
      for (CharacterStyle c : cs) {
        ssb.setSpan(c, start, end, 0);
      }
      // Delete the tokens before and after the span
      ssb.delete(end, end + tokenLen);
      ssb.delete(start - tokenLen, start);
      text = ssb;
    }
    return text;
  }

  private static void setTextColor(View root, @IdRes int id, @ColorRes int color) {
    View view = root.findViewById(id);
    if (!(view instanceof TextView)) {
      return;
    }

    TextView textView = (TextView) view;
    textView.setTextColor(root.getResources().getColor(color));
  }

  public static void setTextColorFor(View root, @ColorRes int color, @IdRes int... ids) {
    for (int id : ids) {
      setTextColor(root, id, color);
    }
  }

  /**
   * Though we don't have iOS's concept of 2x in 3x in android, as mentioned in MOB-12697,
   * getFlightStatus, getFlightStatusByLeg and getFlightScheduleRequest now requires deviceType
   * as 2x or 3x. This method serves the purpose.
   *
   * @param context
   * @return
   */
  public static String getScreenDensityRange(Context context) {
    int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
    if (densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
      return "3x";
    }

    return "2x";
  }

  @NonNull
  public static String formatToKilos(@Nullable Integer value) {
    if (value == null) {
      return "";
    }
    int thousands = value / 1000;
    NumberFormat format = NumberFormat.getInstance();
    format.setGroupingUsed(true);
    format.setMaximumFractionDigits(0);
    return format.format(thousands) + "K";
  }

  public static Bitmap overlayAsteriskGradientMask(Bitmap original, Bitmap overlay) {
    Bitmap bmOverlay = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
    Canvas canvas = new Canvas(bmOverlay);
    canvas.drawBitmap(original, new Matrix(), null);
    canvas.drawBitmap(overlay, ((float) original.getWidth() - overlay.getWidth()) / 2, ((float) original.getHeight() - overlay.getHeight()) / 2, null);
    return bmOverlay;
  }

  public static Bitmap overlayGradientMask(Bitmap original, Bitmap overlay, int alpha) {
    Bitmap bmOverlay = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
    Canvas canvas = new Canvas(bmOverlay);
    Bitmap scaledbmp2 = scale(overlay, original.getWidth(), overlay.getHeight());
    Paint paint = new Paint();
    paint.setAlpha(alpha);
    canvas.drawBitmap(original, new Matrix(), null);
    canvas.drawBitmap(scaledbmp2, new Matrix(), paint);
    return bmOverlay;
  }

  public static Bitmap addGradientToMask(Bitmap src, int color1, int color2) {
    int w = src.getWidth();
    int h = src.getHeight();
    Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(result);

    canvas.drawBitmap(src, 0, 0, null);

    Paint paint = new Paint();
    LinearGradient shader = new LinearGradient(0, 0, 0, h, color1, color2, Shader.TileMode.CLAMP);
    paint.setShader(shader);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawRect(0, 0, w, h, paint);

    return result;
  }


  public static Bitmap scale(Bitmap bitmap, int maxWidth, int maxHeight) {
    int width;
    int height;
    float widthRatio = (float) bitmap.getWidth() / maxWidth;
    float heightRatio = (float) bitmap.getHeight() / maxHeight;

    if (widthRatio >= heightRatio) {
      width = maxWidth;
      height = (int) (((float) width / bitmap.getWidth()) * bitmap.getHeight());
    } else {
      height = maxHeight;
      width = (int) (((float) height / bitmap.getHeight()) * bitmap.getWidth());
    }

    Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    float ratioX = (float) width / bitmap.getWidth();
    float ratioY = (float) height / bitmap.getHeight();
    float middleX = width / 2.0f;
    float middleY = height / 2.0f;

    Matrix scaleMatrix = new Matrix();
    scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

    Canvas canvas = new Canvas(scaledBitmap);
    canvas.setMatrix(scaleMatrix);
    canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
    return scaledBitmap;
  }

  public static boolean isActivityContextFinishing(Context context) {
    Activity activity = (Activity) context;
    return activity.isFinishing();
  }

  private static boolean isActivityFinishing(Activity activity) {
    return activity.isFinishing();
  }

  public static int dpToPx(Context context, int dp) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return (int) ((dp * displayMetrics.density) + 0.5);
  }

  public static int spToPx(Context context, int sp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
  }

  public static int convertDpToPixels(Context context, int dp) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }
}
