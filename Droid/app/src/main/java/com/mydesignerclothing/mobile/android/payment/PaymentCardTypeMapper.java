package com.mydesignerclothing.mobile.android.payment;

import static com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities.hash;
import static com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities.pair;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.isNullOrEmpty;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.AMERICAN_EXPRESS;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.AX;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.CA;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.CARTE_BLANCHE;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.CB;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.DISCOVER;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.DS;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.JC;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.JCB;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.MASTERCARD;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.TP;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.UATP;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.VI;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.VISA;

public abstract class PaymentCardTypeMapper {

    public int map(String code) {
        if (code.equalsIgnoreCase(AX)) {
            return AMERICAN_EXPRESS;
        } else if (code.equalsIgnoreCase(VI)) {
            return VISA;
        } else if (code.equalsIgnoreCase(CA)) {
            return MASTERCARD;
        } else if (code.equalsIgnoreCase(DS)) {
            return DISCOVER;
        } else if (code.equalsIgnoreCase(JC)) {
            return JCB;
        } else if (code.equalsIgnoreCase(CB)) {
            return CARTE_BLANCHE;
        } else if (code.equalsIgnoreCase(TP)) {
            return UATP;
        }
        return flavorSpecificCardType(code);
    }

    public String getName(String creditCardCode) {
        String creditCardName = getCreditCardName(creditCardCode, false);
        return (creditCardName != null) ? creditCardName : flavorSpecificName(creditCardCode);
    }

    public String getAbbreviatedCardDisplayName(String cardType) {
        return !isNullOrEmpty(getCreditCardName(cardType, true)) ?
                getCreditCardName(cardType, true) : flavorSpecificName(cardType);
    }

    private static String getCreditCardName(String creditCardCode, boolean abbreviated) {
        return hash(
                pair(AX, abbreviated ? "AMEX" : "American Express"),
                pair(VI, "VISA"),
                pair(CA, "Mastercard"),
                pair(DS, "Discover"),
                pair(JC, "JCB"),
                pair(CB, "Carte Blanche"),
                pair(TP, abbreviated ? "UATP" : "UATP/Delta Equity Card")
        ).get(creditCardCode);
    }

    public abstract int flavorSpecificCardType(String code);

    public abstract String flavorSpecificName(String code);
}
