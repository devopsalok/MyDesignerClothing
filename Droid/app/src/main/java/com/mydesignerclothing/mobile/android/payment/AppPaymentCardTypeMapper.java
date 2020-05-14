package com.mydesignerclothing.mobile.android.payment;

import static com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities.hash;
import static com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities.pair;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.DC;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.DINERS_CARD;

public class AppPaymentCardTypeMapper extends PaymentCardTypeMapper {

  @Override
  public int flavorSpecificCardType(String code) {
     if (code.equalsIgnoreCase(DC)) {
      return DINERS_CARD;
    }
    return -1;
  }

  @Override
  public String flavorSpecificName(String code) {
    return hash(
        pair(DC, "Diners")
    ).get(code);
  }
}
