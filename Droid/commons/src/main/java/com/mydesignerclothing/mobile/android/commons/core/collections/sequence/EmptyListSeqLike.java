package com.mydesignerclothing.mobile.android.commons.core.collections.sequence;

import com.mydesignerclothing.mobile.android.commons.core.collections.Function;
import com.mydesignerclothing.mobile.android.commons.core.collections.MapFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.ReduceFunction;

import java.util.ArrayList;
import java.util.List;


public class EmptyListSeqLike<E> implements SeqLike<List, E> {

  @Override
  public <R> List<E> map(MapFunction<E, R> function) {
    return new ArrayList<>();
  }

  @Override
  public <R> R reduce(R initialValue, ReduceFunction<R, E> function) {
    return initialValue;
  }

  @Override
  public void each(Function<E> function) {
  }

  @Override
  public SeqLike<List, E> empty() {
    return new ListSeqLike<E>(new ArrayList<E>());
  }

  @Override
  public SeqLike<List, E> add(E element) {
    final List arrayList = new ArrayList();
    arrayList.add(element);
    return new ListSeqLike<>(arrayList);
  }

  @Override
  public List unwrap() {
    return new ArrayList();
  }
}
