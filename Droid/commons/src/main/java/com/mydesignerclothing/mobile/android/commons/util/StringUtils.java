package com.mydesignerclothing.mobile.android.commons.util;

public class StringUtils {
  private static final String REPLACE_LEADING_ZEROS_EXPRESSION = "^0{1,}";
  public static final String EMPTY_STRING = "";
  public static final String STRING_IN_ESCAPED_QUOTES = "\"%s\"";

  private StringUtils() {
    //Sonar needs a private constructor in all util files
  }

  public static boolean isNullOrEmpty(String s) {
    return s == null || "".equals(s);
  }

  public static String stripLeadingZeros(String value) {
    return value != null ? value.replaceAll(REPLACE_LEADING_ZEROS_EXPRESSION, EMPTY_STRING) : null;
  }

  public static boolean isNullOrWhitespace(String s) {
    return s == null || "".equals(s.trim());
  }

  public static String neverNull(String value) {
    if (isNullOrEmpty(value)) {
      return EMPTY_STRING;
    } else {
      return value;
    }
  }

  public static String formatForJavascript(String input) {
    return String.format(STRING_IN_ESCAPED_QUOTES, input);
  }

  public static String removeSpaces(String str) {
    return str.trim().replace(MyDesignerClothingUtilConstants.SPACE, EMPTY_STRING);
  }
}
