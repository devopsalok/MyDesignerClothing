package com.mydesignerclothing.mobile.android.util.upgrade;


import com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;

public enum CreditCardValidations {


  AMERICAN_EXPRESS("3[47][0-9]{13}", 4, MyDesignerClothingValidator.AMERICAN_EXPRESS, MyDesignerClothingValidator.AX),
  VISA("^4[0-9]{12}(?:[0-9]{3})?", 3, MyDesignerClothingValidator.VISA, MyDesignerClothingValidator.VI),
  MASTERCARD("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$",
    3, MyDesignerClothingValidator.MASTERCARD, MyDesignerClothingValidator.CA),
  DISCOVER("^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})$",
    3, MyDesignerClothingValidator.DISCOVER, MyDesignerClothingValidator.DS),
  JCB("^(?:2131|1800|35\\d{3})\\d{11}", 3, MyDesignerClothingValidator.JCB, MyDesignerClothingValidator.JC),
  DINERS_CLUB("3(?:0[0-5]|[68][0-9])[0-9]{11}", 3, MyDesignerClothingValidator.DINERS_CLUB, MyDesignerClothingValidator.DC),
  CARTE_BLANCHE("3(?:0[0-5]|[68][0-9])[0-9]{11}", 3, MyDesignerClothingValidator.CARTE_BLANCHE,
          MyDesignerClothingValidator.CB),
  UATP("^[0-9]{15}$", 0, MyDesignerClothingValidator.UATP, MyDesignerClothingValidator.TP),
  DINERS_CARD("^3(?:0[0-5]|09|[689][0-9])[0-9]{11,16}$", 3, MyDesignerClothingValidator.DINERS_CARD,
          MyDesignerClothingValidator.DC);

  private static final Map<String, CreditCardValidations> BY_ABBRV = new HashMap<>();

  private final String cardPattern;
  private final int cvvLength;
  private final int applicationCardTypeReference;
  private final String cardTypeAbbreviation;

  CreditCardValidations(@NonNull String cardPattern,
                        int cvvLength,
                        int applicationCardTypeReference,
                        @NonNull String cardTypeAbbreviation) {
    this.cardPattern = cardPattern;
    this.cvvLength = cvvLength;
    this.applicationCardTypeReference = applicationCardTypeReference;
    this.cardTypeAbbreviation = cardTypeAbbreviation;
  }

  @NonNull
  public Pattern getCardPattern() {
    return Pattern.compile(this.cardPattern);
  }

  public int getCvvLength() {
    return this.cvvLength;
  }

  public int getApplicationCardTypeReference() {
    return applicationCardTypeReference;
  }

  @NonNull
  private String getCardTypeAbbreviation() {
    return cardTypeAbbreviation;
  }


  public static CreditCardValidations findByAbbreviation(String cardTypeAbbreviation) {
    if (BY_ABBRV.isEmpty()) {
      synchronized (BY_ABBRV) {
        for (CreditCardValidations validations : values()) {
          BY_ABBRV.put(validations.getCardTypeAbbreviation().toLowerCase(), validations);
        }
      }
    }
    return cardTypeAbbreviation == null ? null : BY_ABBRV.get(cardTypeAbbreviation.toLowerCase());
  }
}
