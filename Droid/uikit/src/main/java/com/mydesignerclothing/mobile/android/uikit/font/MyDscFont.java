package com.mydesignerclothing.mobile.android.uikit.font;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.widget.TextView;


import com.mydesignerclothing.mobile.android.uikit.R;

import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

public class MyDscFont {

  @SuppressWarnings("unused")
  private MyDscFont() {
    //NOSONAR
  }

  public static void applyBookFont(TextView item) {
   // setCustomFont(item, R.font.book_font);
  }

  public static void applyDefaultFont(TextView item) {
    setCustomFont(item, R.font.default_font);
  }

  public static void applyBoldFont(TextView item) {
    setCustomFont(item, R.font.bold_font);
  }

  public static void applyMediumItalicsFont(TextView item) {
    setCustomFont(item, R.font.medium_italic_font);
  }

  public static void setCustomFont(TextView item, @FontRes int fontResource) {
    if(item == null){
      return;
    }
    Typeface typeface = ResourcesCompat.getFont(item.getContext(), fontResource);
    item.setTypeface(typeface);
  }

  public static synchronized Typeface get(Context context, @FontRes int fontResource){
    return ResourcesCompat.getFont(context, fontResource);
  }

  public static CharSequence applyFontToSpan(Context context, @NonNull CharSequence text, @FontRes int fontResource) {
    return applyFontToSpan(context, text, 0, text.length(), fontResource);
  }

  public static CharSequence applyFontToSpan(Context context, CharSequence text, int start, int end, @FontRes int fontResource) {
    SpannableString spannableString;
    if (!(text instanceof Spannable)) {
      spannableString = new SpannableString(text);
    } else {
      spannableString = (SpannableString) text;
    }
    if (end == start || start < 0 || end < 0) {
      return spannableString;
    }
    Typeface typeface = ResourcesCompat.getFont(context, fontResource);
    spannableString.setSpan(new TypefaceSpan(typeface), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableString;
  }
}
