package com.mydesignerclothing.mobile.android.login.loginmain.viewmodel;

import android.content.res.Resources;

import com.mydesignerclothing.mobile.android.login.BR;
import com.mydesignerclothing.mobile.android.login.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.isNullOrEmpty;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.neverNull;
import static com.mydesignerclothing.mobile.android.uikit.view.EditTextControl.STATE_DEFAULT;
import static com.mydesignerclothing.mobile.android.uikit.view.EditTextControl.STATE_ERROR;

public class LoginMainViewModel extends BaseObservable {
    private String email;
    private String password;
    private int contactEmailState;
    private boolean emailFocusable;
    private String emailErrorText;
    private String passwordErrorText = EMPTY_STRING;
    private boolean passwordFocusable;
    private int passwordState;
    private Resources resources;

    public LoginMainViewModel(Resources resources) {
        this.resources = resources;
        this.emailErrorText = EMPTY_STRING;
        this.contactEmailState = STATE_DEFAULT;
        this.passwordState = STATE_DEFAULT;
        resetUserDetailsFocusable();
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
    }

    private void resetUserDetailsFocusable() {
        setEmailFocusable(false);
        setPasswordFocusable(false);
    }

    public boolean validateUserDetails() {
        validateEmailOrName();
        validatePassword();
        return contactEmailState == STATE_DEFAULT
                && passwordState == STATE_DEFAULT;
    }

    private void validateEmailOrName() {
        resetEmailErrorStates();
        String contactEmailInput = neverNull(email);

        boolean shouldValidateContactEmail = true;
        boolean isContactEmailValid = validateEmail(contactEmailInput);

        if (isNullOrEmpty(contactEmailInput)) {
            showContactEmailError(R.string.email_empty_error);
            shouldValidateContactEmail = false;
        }

        if (shouldValidateContactEmail && !isContactEmailValid) {
            showContactEmailError(R.string.valid_email_error);
        }

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

    private void validatePassword() {
        resetPasswordErrorStates();
        String passwordInput = neverNull(getPassword());

        boolean shouldValidatePassword = true;
        boolean isPasswordValid = validatePasswordLength(passwordInput);

        if (isNullOrEmpty(passwordInput)) {
            showPasswordError(R.string.password_empty_error);
            setPassword(EMPTY_STRING);
            shouldValidatePassword = false;
            setPasswordFocusable(true);
        }

        if (shouldValidatePassword && !isPasswordValid) {
            showPasswordError(R.string.password_valid_error);
            setPassword(EMPTY_STRING);
            setPasswordFocusable(true);
        }
    }

    public void setUserLoginDetails() {
        setEmail(getEmail());
        setPassword(getPassword());
    }

    @Bindable
    public int getPasswordState() {
        return passwordState;
    }

    private boolean validateEmail(@Nullable String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(neverNull(email));

        return matcher.matches();

    }

    public static boolean validatePasswordLength(@Nullable String password) {
        return neverNull(password).length() > 8;
    }
}
