package com.mydesignerclothing.mobile.android.registration.viewmodel;

import android.content.res.Resources;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;

import java.util.regex.Pattern;

import androidx.annotation.StringRes;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.isNullOrEmpty;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.isNullOrWhitespace;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.neverNull;
import static com.mydesignerclothing.mobile.android.uikit.view.EditTextControl.STATE_DEFAULT;
import static com.mydesignerclothing.mobile.android.uikit.view.EditTextControl.STATE_ERROR;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.isValidPhoneNumber;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.validateEmail;
import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.validatePasswordLength;

public class RegistrationViewModel extends BaseObservable {
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int phoneNumberState;
    private boolean phoneNumberFocusable;
    private int contactEmailState;
    private boolean emailFocusable;
    private String emailErrorText;
    private String firstNameErrorText = EMPTY_STRING;
    private String lastNameErrorText = EMPTY_STRING;
    private String passwordErrorText = EMPTY_STRING;
    private boolean firstNameFocusable;
    private boolean lastNameFocusable;
    private boolean passwordFocusable;
    private boolean confirmPasswordFocusable;
    private int firstNameState;
    private int lastNameState;
    private int passwordState;
    private int confirmPasswordState;

    private String confirmPasswordStringText;
    private Resources resources;
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^([a-zA-Z\\&\\-'\\s]{2,35})$");

    public RegistrationViewModel(Resources resources) {
        this.resources = resources;
        this.phoneNumberState = STATE_DEFAULT;
        this.emailErrorText = EMPTY_STRING;
        this.contactEmailState = STATE_DEFAULT;
        this.firstNameState = STATE_DEFAULT;
        this.lastNameState = STATE_DEFAULT;
        this.passwordState = STATE_DEFAULT;
        this.confirmPasswordState = STATE_DEFAULT;
        this.confirmPasswordStringText = EMPTY_STRING;
        resetPersonalAndContactInfoFocusable();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Bindable
    public boolean isPasswordFocusable() {
        return passwordFocusable;
    }

    private void setPasswordFocusable(boolean passwordFocusable) {
        this.passwordFocusable = passwordFocusable;
        notifyPropertyChanged(BR.passwordFocusable);
    }

    @Bindable
    public String getPasswordErrorText() {
        return passwordErrorText;
    }

    @Bindable
    public boolean isConfirmPasswordFocusable() {
        return confirmPasswordFocusable;
    }

    public void setConfirmPasswordFocusable(boolean confirmPasswordFocusable) {
        this.confirmPasswordFocusable = confirmPasswordFocusable;
        notifyPropertyChanged(BR.confirmPasswordFocusable);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Bindable
    public int getFirstNameState() {
        return firstNameState;
    }

    @Bindable
    public int getLastNameState() {
        return lastNameState;
    }

    @Bindable
    public String getFirstNameErrorText() {
        return firstNameErrorText;
    }

    @Bindable
    public String getLastNameErrorText() {
        return lastNameErrorText;
    }

    @Bindable
    public boolean isFirstNameFocusable() {
        return firstNameFocusable;
    }

    public void setFirstNameFocusable(boolean firstNameFocusable) {
        this.firstNameFocusable = firstNameFocusable;
        notifyPropertyChanged(BR.firstNameFocusable);
    }

    @Bindable
    public boolean isLastNameFocusable() {
        return lastNameFocusable;
    }

    private void setLastNameFocusable(boolean lastNameFocusable) {
        this.lastNameFocusable = lastNameFocusable;
        notifyPropertyChanged(BR.lastNameFocusable);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Bindable
    public int getPhoneNumberState() {
        return phoneNumberState;
    }

    private void setPhoneNumberState(int phoneNumberState) {
        this.phoneNumberState = phoneNumberState;
        notifyPropertyChanged(BR.phoneNumberState);
    }

    @Bindable
    public boolean isPhoneNumberFocusable() {
        return phoneNumberFocusable;
    }

    private void setPhoneNumberFocusable(boolean phoneNumberFocusable) {
        this.phoneNumberFocusable = phoneNumberFocusable;
        notifyPropertyChanged(BR.phoneNumberFocusable);
    }

    @Bindable
    public int getContactEmailState() {
        return contactEmailState;
    }

    private void setContactEmailState(int contactEmailState) {
        this.contactEmailState = contactEmailState;
        notifyPropertyChanged(BR.contactEmailState);
    }

    @Bindable
    public boolean isEmailFocusable() {
        return emailFocusable;
    }

    private void setEmailFocusable(boolean emailFocusable) {
        this.emailFocusable = emailFocusable;
        notifyPropertyChanged(BR.emailFocusable);
    }

    @Bindable
    public String getEmailErrorText() {
        return emailErrorText;
    }

    private void updateEmailErrorText(String errorText) {
        this.emailErrorText = errorText;
        notifyPropertyChanged(BR.emailErrorText);
    }

    private void showContactEmailError(@StringRes int errorStringRes) {
        updateEmailErrorText(resources.getString(errorStringRes));
        setContactEmailState(STATE_ERROR);
    }

    private void resetEmailErrorStates() {
        setContactEmailState(STATE_DEFAULT);
    }

    private void resetPasswordErrorStates() {
        setPasswordState(STATE_DEFAULT);
        setConfirmPasswordState(STATE_DEFAULT);
    }

    private void resetPersonalAndContactInfoFocusable() {
        setEmailFocusable(false);
        setPasswordFocusable(false);
        setConfirmPasswordFocusable(false);
        setFirstNameFocusable(false);
        setLastNameFocusable(false);
        setPhoneNumberFocusable(false);
    }

    public boolean validatePersonalAndContactInfo() {
        validateContactEmail();
        validatePasswords();
        boolean firstNameValid = validateFirstName();
        boolean lastNameValid = validateLastName();
        validateContactPhoneNumber();
        return contactEmailState == STATE_DEFAULT
                && passwordState == STATE_DEFAULT
                && confirmPasswordState == STATE_DEFAULT
                && firstNameValid
                && lastNameValid
                && phoneNumberState == STATE_DEFAULT;
    }

    private boolean validateFirstName() {
        boolean valid = false;
        if (isNullOrWhitespace(getFirstName())) {
            onFirstNameValidation(STATE_ERROR, R.string.add_passenger_empty_first_name_error);
        } else if (!VALID_NAME_PATTERN.matcher(getFirstName()).matches()) {
            onFirstNameValidation(STATE_ERROR, R.string.invalid_first_name_error);
        } else {
            onFirstNameValidation(STATE_DEFAULT, R.string.empty_string);
            valid = true;
        }
        return valid;
    }

    private boolean validateLastName() {
        boolean valid = false;
        if (isNullOrWhitespace(getLastName())) {
            onLastNameValidation(STATE_ERROR, R.string.add_passenger_empty_last_name_error);
        } else if (!VALID_NAME_PATTERN.matcher(getLastName()).matches()) {
            onLastNameValidation(STATE_ERROR, R.string.invalid_last_name_error);
        } else {
            onLastNameValidation(STATE_DEFAULT, R.string.empty_string);
            valid = true;
        }
        return valid;
    }

    private void onFirstNameValidation(int firstNameState, @StringRes int firstNameErrorText) {
        this.firstNameState = firstNameState;
        notifyPropertyChanged(BR.firstNameState);
        this.firstNameErrorText = resources.getString(firstNameErrorText);
        notifyPropertyChanged(BR.firstNameErrorText);
    }

    private void onLastNameValidation(int lastNameState, @StringRes int lastNameErrorText) {
        this.lastNameState = lastNameState;
        notifyPropertyChanged(BR.lastNameState);
        this.lastNameErrorText = resources.getString(lastNameErrorText);
        notifyPropertyChanged(BR.lastNameErrorText);
    }

    private void validateContactPhoneNumber() {
        if (isValidPhoneNumber(getPhoneNumber())) {
            setPhoneNumberState(STATE_DEFAULT);
        } else {
            setPhoneNumberState(STATE_ERROR);
        }
    }

    private void validateContactEmail() {
        resetEmailErrorStates();
        String contactEmailInput = neverNull(email);

        boolean shouldValidateContactEmail = true;
        boolean isContactEmailValid = validateEmail(contactEmailInput);

        if (isNullOrEmpty(contactEmailInput)) {
            showContactEmailError(R.string.contact_email_empty_error);
            shouldValidateContactEmail = false;
        }

        if (shouldValidateContactEmail && !isContactEmailValid) {
            showContactEmailError(R.string.contact_valid_email_error);
        }

    }

    private void showConfirmPasswordError(@StringRes int errorStringRes) {
        updateConfirmPasswordErrorText(resources.getString(errorStringRes));
        setConfirmPasswordState(STATE_ERROR);
    }

    private void setConfirmPasswordState(int confirmPasswordState) {
        this.confirmPasswordState = confirmPasswordState;
        notifyPropertyChanged(BR.confirmPasswordState);
    }

    private void updateConfirmPasswordErrorText(String errorText) {
        this.confirmPasswordStringText = errorText;
        notifyPropertyChanged(BR.confirmPasswordStringText);
    }

    private void showPasswordError(@StringRes int errorStringRes) {
        updatePasswordErrorText(resources.getString(errorStringRes));
        setPasswordState(STATE_ERROR);
    }

    private void setPasswordState(int passwordState) {
        this.passwordState = passwordState;
        notifyPropertyChanged(BR.passwordState);
    }

    private void updatePasswordErrorText(String errorText) {
        this.passwordErrorText = errorText;
        notifyPropertyChanged(BR.passwordErrorText);
    }

    private void validatePasswords() {
        resetPasswordErrorStates();
        String passwordInput = neverNull(getPassword());
        String confirmPasswordInput = neverNull(getConfirmPassword());

        boolean shouldValidatePassword = true;
        boolean shouldValidateConfirmPassword = true;
        boolean isPasswordValid = validatePasswordLength(passwordInput);
        boolean isConfirmPasswordValid = validatePasswordLength(confirmPasswordInput);

        if (isNullOrEmpty(passwordInput)) {
            showPasswordError(R.string.password_empty_error);
            setPassword(EMPTY_STRING);
            notifyPropertyChanged(BR.confirmPasswordStringText);
            shouldValidatePassword = false;
            setPasswordFocusable(true);
        }

        if (isNullOrEmpty(confirmPasswordInput)) {
            showConfirmPasswordError(R.string.confirm_password_empty_error);
            shouldValidateConfirmPassword = false;
            setConfirmPasswordFocusable(true);
        }

        if (shouldValidatePassword && !isPasswordValid) {
            setConfirmPassword(EMPTY_STRING);
            notifyPropertyChanged(BR.confirmPasswordStringText);
            showPasswordError(R.string.password_valid_error);
            setPasswordFocusable(true);
        }

        if (shouldValidateConfirmPassword && !isConfirmPasswordValid) {
            setConfirmPassword(EMPTY_STRING);
            notifyPropertyChanged(BR.confirmPasswordStringText);
            showConfirmPasswordError(R.string.re_enter_valid_confirm_password_error);
            setConfirmPasswordFocusable(true);
        }

        if (isPasswordValid && isConfirmPasswordValid) {
            if (!passwordInput.equalsIgnoreCase(confirmPasswordInput)) {
                showConfirmPasswordError(R.string.password_do_not_match);
                setConfirmPassword(EMPTY_STRING);
                notifyPropertyChanged(BR.confirmPasswordStringText);
                setConfirmPasswordFocusable(true);
            } else {
                resetPasswordErrorStates();
            }
        }
    }

    public void setContactAndPersonalDetails() {
        setEmail(getEmail());
        setFirstName(getFirstName());
        setLastName(getLastName());
        setPassword(getPassword());
        setConfirmPassword(getConfirmPassword());
        setPhoneNumber(getPhoneNumber());
    }

    @Bindable
    public String getConfirmPasswordStringText() {
        return confirmPasswordStringText;
    }

    @Bindable
    public int getPasswordState() {
        return passwordState;
    }

    @Bindable
    public int getConfirmPasswordState() {
        return confirmPasswordState;
    }
}
