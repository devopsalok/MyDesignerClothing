package com.mydesignerclothing.mobile.android.commons.core.collections;


import com.mydesignerclothing.mobile.android.commons.core.collections.sequence.EmptyListSeqLike;
import com.mydesignerclothing.mobile.android.commons.core.collections.sequence.EmptyMapSeqLike;
import com.mydesignerclothing.mobile.android.commons.core.collections.sequence.ListSeqLike;
import com.mydesignerclothing.mobile.android.commons.core.collections.sequence.MapSeqLike;
import com.mydesignerclothing.mobile.android.commons.core.collections.sequence.SeqLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public abstract class Seqs {


  public static <E> SeqLike<List, E> of(final List<E> list) {
    return list == null || list.isEmpty() ? new EmptyListSeqLike<E>() : new ListSeqLike<>(list);
  }

  public static <E> SeqLike<List, E> of(final E... list) {
    return list == null || list.length == 0 ? new EmptyListSeqLike<E>() : new ListSeqLike<>(asList(list));
  }

  public static <K, V> SeqLike<Map, Map.Entry<K, V>> of(final Map<K, V> map) {
    return map == null || map.isEmpty() ? new EmptyMapSeqLike<K, V>() : new MapSeqLike<>(map);
  }

  public static <E> SeqLike<List, E> of(Iterable<E> collection) {
    return collection == null ? new EmptyListSeqLike<E>() : ofIterable(collection);
  }

  private static <E> SeqLike<List, E> ofIterable(Iterable<E> collection) {
    List<E> list = new ArrayList<>();
    for (E aCollection : collection) {
      list.add(aCollection);
    }
    return new ListSeqLike<>(list);
  }

  public static class ReduceWrapper<E> {

    public E element;

    public ReduceWrapper(E element) {
      this.element = element;
    }
  }
}
