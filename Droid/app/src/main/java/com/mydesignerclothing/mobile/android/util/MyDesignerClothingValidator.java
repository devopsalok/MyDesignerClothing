package com.mydesignerclothing.mobile.android.util;

import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.payment.AppPaymentCardTypeMapper;
import com.mydesignerclothing.mobile.android.util.upgrade.CreditCardValidations;

import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;

import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.neverNull;

public class MyDesignerClothingValidator {

    private static final String TAG = MyDesignerClothingValidator.class.getSimpleName();

    public static final int UNKNOWN_CARD = 999;
    public static final int AMERICAN_EXPRESS = 0;
    public static final int VISA = 1;
    public static final int MASTERCARD = 2;
    public static final int DISCOVER = 3;
    public static final int JCB = 4;
    public static final int DINERS_CLUB = 5;
    public static final int CARTE_BLANCHE = 6;
    public static final int UATP = 7;
    public static final int DINERS_CARD = 8;

    // These values are used in the check in request for the ccType
    public static final String AX = "AX"; // American Express
    public static final String CA = "CA"; // Mastercard
    public static final String CB = "CB"; // Carte Blanche
    public static final String DC = "DC"; // Diners Club
    public static final String DS = "DS"; // Discover
    public static final String JC = "JC"; // JCB
    public static final String TP = "TP"; // UATP
    public static final String VI = "VI"; // Visa
    public static final String UN = "UN"; // UNKNOWN

    private static final String SEQUENTIAL_TEST_BASIS = "0123456789abcdefghijklmnopqrstuvwxyz";

    private MyDesignerClothingValidator() {
    }

    /**
     * Method to validate an email address using a regular expression.
     *
     * @param email - A string to test for valid email.
     * @return - true or false if valid email.
     */
    public static boolean validateEmail(@Nullable String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(neverNull(email));

        return matcher.matches();

    }

    public static boolean isValidPhoneNumber(@Nullable String phoneNumber) {
        return Pattern.compile("^[0-9]{10,14}$").matcher(neverNull(phoneNumber)).matches();
    }

    public static boolean validatePasswordLength(@Nullable String password) {
        return neverNull(password).length() > 8;
    }

    /**
     * Method to obtain the long name from the credit card code
     *
     * @param code - The string to test.
     * @return - A string representing the long name of the credit card type.
     */
    public static String getCreditCardName(String code) {
        /**
         *21463 As per jonathan we need to get rid of AMEX Skymile from card options.
         */
        return new AppPaymentCardTypeMapper().getName(code);
    }

    /**
     * * Method to obtain the long name from the credit card code and support Name Abbreviated
     *
     * @param code - The string to test.
     * @return - A string representing the long name of the credit card type.
     */
    public static String getCreditCardNameAbbreviated(String code) {
        /**
         *21463 As per jonathan we need to get rid of American Express Skymile from card options.
         */
        return new AppPaymentCardTypeMapper().getAbbreviatedCardDisplayName(code);
    }

    /**
     * Method to test validation of user input of credit card information. (Authorization is handled at service level).
     *
     * @param cardID   The id of the type of credit card to validate. Use the constants of this class to pass in.
     * @param ccNumber The credit card number to validate.
     * @return boolean if credit card is a valid input.
     */
    public static boolean validateCreditCard(int cardID, String ccNumber) {

        String cleanCCNumber = removeSpacesAndDashes(ccNumber);
        boolean isValid = checkCreditCardRegex(cardID, ccNumber);

        // In addition, run the Luhn check
        if (isValid) {
            isValid = doLuhnCheck(cleanCCNumber);
        }

        return isValid;
    }

    /**
     * Returns the credit card ID based upon the credit card number
     *
     * @param ccNumber The credit card number to determine the type
     * @return The ID of the credit card
     */
    public static int getCreditCardIDFromNumber(String ccNumber) {
        for (CreditCardValidations validation : CreditCardValidations.values()) {
            if (validation.getCardPattern().matcher(ccNumber).matches()) {
                return validation.getApplicationCardTypeReference();
            }
        }
        return UNKNOWN_CARD;
    }


    /**
     * Tests whether the contents of a string are null or contain no chars.
     *
     * @param s The string to test.
     * @return true or false.
     */
    public static boolean isNullOrEmpty(String s) {

        return s == null || EMPTY_STRING.equalsIgnoreCase(s) || s.trim().length() == 0;
    }

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Trims leading zeros in source string.
     *
     * @param source - The string to trim.
     * @return - A string without leading zeros
     */
    public static String trimLeadingZeros(String source) {
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c != '0' && !Character.isSpaceChar(c)) {
                return source.substring(i);
            }
        }
        return null;
    }

    /**
     * Another validation check for a credit card number.
     * The algorithm runs on the string array, calculating the checksum(final digit in cc number).
     * If the sum modulus 10 is zero, then the credit card is valid.
     * The Luhn algorithm cannot be implemented with a regex because arithmetic is involved.
     *
     * @param ccNumber The credit card number to test.
     * @return true or false if credit card is valid.
     */
    private static boolean doLuhnCheck(String ccNumber) {
        int sum = 0;
        int digit;
        int addend;
        boolean timesTwo = false;

        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            digit = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (timesTwo) {
                addend = digit * 2;
                if (addend > 9) {
                    addend -= 9;
                }
            } else {
                addend = digit;
            }
            sum += addend;
            timesTwo = !timesTwo;
        }
        int modulus = sum % 10;
        return modulus == 0;
    }

    /**
     * Removes all spaces and dashes from user input credit card.
     *
     * @param ccNumber The user input credit card number.
     * @return A credit card number cleaned of spaces and dashes that can be sent to server in request.
     */
    private static String removeSpacesAndDashes(String ccNumber) {

        String dashes = ccNumber.replaceAll("-", "");
        String leadingTrailing = leftTrim(rightTrim(dashes));

        return removeSpaces(leadingTrailing);
    }

    /**
     * Test if a string has 5 or more repeating characters (tests 0-9, a-z).
     * EG: 11111asdccc
     *
     * @param testString - String to test for five or more repeating characters
     * @return Returns true if five or more consecutive repeating characters are found, false otherwise
     */
    public static boolean doesFailFiveRepeatingNumbersOrLettersTest(String testString) {
        if (testString != null) {
            testString = testString.trim().toLowerCase(Locale.US);
            Pattern pattern = Pattern.compile("([0-9a-z])\\1{4,}");
            Matcher matcher = pattern.matcher(testString);
            return matcher.find();
        }
        return false;
    }

    /**
     * Test if a string has a certain number of sequential characters
     * EG: 0123456dfsfs (fail if maxSequence 7 due to 0123456), abcdefg88454 (fail if maxSequence 7 due to abcdefg)
     *
     * @param testString  - String to test for sequential characters
     * @param maxSequence - Maximum number of sequential characters that are allowed
     * @return Return true if a sequential match is found, false otherwise
     */
    public static boolean doesStringFailSequentialTest(String testString, int maxSequence) {
        if (testString != null) {
            testString = testString.trim().toLowerCase(Locale.US);
            int stringLength = testString.length();
            if (stringLength - maxSequence >= 0) {
                int n = stringLength;
                // going backwards for optimization reasons
                do {
                    if (n - maxSequence >= 0) {
                        String subString = testString.substring(n - maxSequence, n);
                        int testIndex = SEQUENTIAL_TEST_BASIS.indexOf(subString);
                        if (testIndex >= 0) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } while (n-- > 0);
            }
        }
        return false;
    }

    /**
     * Test if a string is at 6-9 characters and contains only numbers, alphas and spaces.
     *
     * @param testString - String to test
     * @return Returns true if string is 6-9 characters and contains only numbers, alphas and spaces, false otherwise.
     */
    public static boolean isMeetsValidPassportLengthAndInputValues(String testString) {
        if (testString != null) {
            testString = testString.toLowerCase(Locale.US);
            Pattern pattern = Pattern.compile("^[\\d\\sa-z]{6,}$");
            Matcher matcher = pattern.matcher(testString);
            return matcher.find();
        }
        return false;
    }

    // remove all spaces
    private static String removeSpaces(String str) {
        StringTokenizer st = new StringTokenizer(str, " ", false);
        StringBuilder stringBuilder = new StringBuilder();
        while (st.hasMoreElements())
            stringBuilder.append(st.nextElement());

        return stringBuilder.toString();
    }

    // remove leading whitespace
    private static String leftTrim(String str) {
        return str.replaceAll("^\\s+", "");
    }

    // remove trailing whitespace
    private static String rightTrim(String str) {
        return str.replaceAll("\\s+$", "");
    }

    // make sure there are digits in supplied string and not chars
    private static boolean isNumber(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            CrashTracker.trackStackTrace(TAG, e);
            return false;
        }
    }


    /**
     * Method to test validation of user input of credit card information. (Authorization is handled at service level).
     *
     * @param cardType The id of the type of credit card to validate. Use the constants of this class to pass in.
     * @param ccNumber The credit card number to validate.
     * @return boolean if credit card is a valid input.
     */
    public static boolean validateCreditCard(String cardType, String ccNumber) {

        String cleanCCNumber = removeSpacesAndDashes(ccNumber);
        int cardID = new AppPaymentCardTypeMapper().map(cardType);
        boolean isValid = checkCreditCardRegex(cardID, cleanCCNumber);

        // In addition, run the Luhn check
        if (cleanCCNumber.matches("^[0-9]{1,45}$")) {
            isValid = doLuhnCheck(cleanCCNumber);
        }

        return isValid;
    }

    public static boolean checkCreditCardRegex(int cardID, String cleanCCNumber) {
        boolean isValid = false;
        for (CreditCardValidations validation : CreditCardValidations.values()) {
            if (validation.getApplicationCardTypeReference() == (cardID)) {
                Matcher matcher = validation.getCardPattern().matcher(cleanCCNumber);
                return matcher.matches();
            }
        }
        return isValid;
    }


    /**
     * This method validate the security code length on the basis of the card type.
     *
     * @param code
     * @param securityCode
     * @return
     */
    public static boolean isValidateCardSecurityCode(String code, String securityCode) {
        //This method was implemented in a way that it checks if the security code is 0
        //Hence returns true for invalid scenarios.
        CreditCardValidations validations = CreditCardValidations.findByAbbreviation(code);
        securityCode = securityCode == null ? "" : securityCode.trim();
        return validations == null || securityCode.length() != validations.getCvvLength();
    }
}
