package com.mydesignerclothing.mobile.android.login.core;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.util.BroadcastIntentFactory;
import com.mydesignerclothing.mobile.android.login.apiclient.LoginAPIClient;
import com.mydesignerclothing.mobile.android.login.di.LoginComponent;
import com.mydesignerclothing.mobile.android.login.di.LoginComponentInjector;
import com.mydesignerclothing.mobile.android.login.exceptions.LoginServiceCrashedUnexpectedly;
import com.mydesignerclothing.mobile.android.login.models.LoginRequest;
import com.mydesignerclothing.mobile.android.login.models.LoginRequestDTO;
import com.mydesignerclothing.mobile.android.login.models.LoginResponse;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.apiclient.IAPIClient;
import com.mydesignerclothing.mobile.android.network.apiclient.RequestType;
import com.mydesignerclothing.mobile.android.network.errors.ErrorConverter;
import com.mydesignerclothing.mobile.android.network.exceptions.DuplicateRequestException;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.uikit.util.SimpleObserver;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mydesignerclothing.mobile.android.login.constants.Constant.LOGIN_FAILURE_EVENT;
import static com.mydesignerclothing.mobile.android.login.constants.Constant.LOGIN_SUCCESS_EVENT;

public class LoginService {
    private Context context;
    private static final String TAG = LoginService.class.getSimpleName();

    public LoginService(Context context) {
        this.context = context;
        getLoginComponent(context.getApplicationContext()).inject(this);
    }

    private LoginComponent getLoginComponent(Context context) {
        if (context instanceof LoginComponentInjector) {
            LoginComponentInjector loginComponentInjector = (LoginComponentInjector) context;
            return loginComponentInjector.getLoginComponent();
        }
        return null;
    }

    public Observable<LoginResponse> executeLogin(LoginRequestDTO loginRequestDTO, boolean keepLoggedIn) {

        LoginRequest loginRequest;

        try {
            loginRequest = createLoginRequest(loginRequestDTO);
        } catch (LoginServiceCrashedUnexpectedly e) {
            return Observable.error(e);
        }

        Observable<LoginResponse> userObservable;
        userObservable = getAPIClient().get(LoginAPIClient.class).doLogin(loginRequest)
                .observeOn(AndroidSchedulers.mainThread());

        Observable<LoginResponse> sharedUserObservable =
                userObservable.doOnNext(this::handleLoginSuccess)
                        .observeOn(Schedulers.io())
                        .doOnError(this::handleLoginError)
                        .observeOn(AndroidSchedulers.mainThread())
                        .share();
        sharedUserObservable.subscribe(getDatabaseObserver());
        return sharedUserObservable;
    }

    @NonNull
    private Observer<LoginResponse> getDatabaseObserver() {
        return new SimpleObserver<LoginResponse>() {
            @Override
            public void onNext(@NonNull LoginResponse userSession) {
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
                Intent intent = BroadcastIntentFactory.intent(LOGIN_SUCCESS_EVENT, context);
                localBroadcastManager.sendBroadcast(intent);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                handleFailure(e);
            }

            private void handleFailure(@NonNull Throwable e) {
                if (e instanceof DuplicateRequestException || e instanceof LoginServiceCrashedUnexpectedly) {
                    return;
                }
                Optional<NetworkError> loginError = ErrorConverter.getNetworkError(e);
                if (loginError.isPresent() &&
                        (loginError.get().isOfflineError() || loginError.get().isNetworkFailureError())) {
                    return;
                }

                if (loginError.isPresent()) {
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
                    localBroadcastManager.sendBroadcast(BroadcastIntentFactory.intent(LOGIN_FAILURE_EVENT, context));
                }
            }
        };
    }

    private IAPIClient getAPIClient() {
//        RequestType requestType = environmentsManager.toggle(LOGIN_BFF) ? RequestType.V3 : RequestType.V2;
        return APIClientFactory.get(context, RequestType.V3);
    }

    private void handleLoginSuccess(LoginResponse userSession) {
//        loginSharedPrefManager.setKeyLoginId(userSession.getId());
//        loginSharedPrefManager.setLastLoginTimeRefresh(true);
//        SessionManager.getInstance().createUserSession(userSession);
    }

    private void handleLoginError(Throwable t) {
        if (t instanceof DuplicateRequestException) {
            return;
        }
        Optional<NetworkError> loginError = ErrorConverter.getNetworkError(t);
        if (loginError.isPresent() &&
                (loginError.get().isOfflineError() || loginError.get().isNetworkFailureError())) {
            return;
        }
//        new SessionCleaner(context).clearLoginSessionOnLoginFailure();
    }

    private LoginRequest createLoginRequest(LoginRequestDTO loginRequestDTO)
            throws LoginServiceCrashedUnexpectedly {

        LoginRequest loginRequest = new LoginRequest(context);
        loginRequest.setUserName(loginRequestDTO.getUserName());
        loginRequest.setLastName(loginRequestDTO.getLastName());
        try {
            loginRequest.setPassword(loginRequestDTO.getPassword());
        } catch (Exception e) {
            DMLog.log(TAG, e, Log.ERROR);
            throw new LoginServiceCrashedUnexpectedly(e);
        }
        return loginRequest;
    }
}
