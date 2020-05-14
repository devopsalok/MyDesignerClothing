package com.mydesignerclothing.mobile.android.network;


import com.mydesignerclothing.mobile.android.commons.core.BaseCallback;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;

public interface AsyncCallback<T> extends BaseCallback<T, NetworkError> {

    @Override
    void onSuccess(T response);

    @Override
    void onFailure(NetworkError error);
}
