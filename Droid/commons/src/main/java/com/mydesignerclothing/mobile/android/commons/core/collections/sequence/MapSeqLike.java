package com.mydesignerclothing.mobile.android.commons.core.collections.sequence;

import com.mydesignerclothing.mobile.android.commons.core.collections.Function;
import com.mydesignerclothing.mobile.android.commons.core.collections.MapFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.ReduceFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.Seqs;

import java.util.HashMap;
import java.util.Map;


public class MapSeqLike<K, V> implements SeqLike<Map, Map.Entry<K, V>> {

  private final Map<K, V> map;

  public MapSeqLike(Map<K, V> map) {
    this.map = map;
  }

  @Override
  public <R> Map map(final MapFunction<Map.Entry<K, V>, R> function) {
    final Map mapped = new HashMap();
    this.each(new Function<Map.Entry<K, V>>() {
      @Override
      public void apply(Map.Entry<K, V> elem) {
        final Map.Entry entry = (Map.Entry) function.map(elem);
        mapped.put(entry.getKey(), entry.getValue());
      }
    });
    return mapped;
  }

  @Override
  public <R> R reduce(final R initialValue, final ReduceFunction<R, Map.Entry<K, V>> function) {
    final Seqs.ReduceWrapper<R> result = new Seqs.ReduceWrapper<>(initialValue);
    this.each(new Function<Map.Entry<K, V>>() {
      @Override
      public void apply(Map.Entry<K, V> elem) {
        result.element = function.apply(elem, result.element);
      }
    });
    return result.element;
  }

  @Override
  public void each(Function<Map.Entry<K, V>> function) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      function.apply(entry);
    }
  }

  @Override
  public SeqLike<Map, Map.Entry<K, V>> empty() {
    return new MapSeqLike<>(new HashMap<K, V>());
  }

  @Override
  public SeqLike<Map, Map.Entry<K, V>> add(Map.Entry<K, V> element) {
    map.put(element.getKey(), element.getValue());
    return this;
  }

  @Override
  public Map unwrap() {
    return map;
  }
}
