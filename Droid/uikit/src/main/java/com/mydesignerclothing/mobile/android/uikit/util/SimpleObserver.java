package com.mydesignerclothing.mobile.android.uikit.util;


import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SimpleObserver<T> implements Observer<T> {
  @Override
  public void onSubscribe(@NonNull Disposable d) {
    //NO OP
  }

  @Override
  public void onNext(@NonNull T t) {
    //NO OP
  }

  @Override
  public void onError(@NonNull Throwable e) {
    //NO OP
  }

  @Override
  public void onComplete() {
    //NO OP
  }
}
