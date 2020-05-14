package com.mydesignerclothing.mobile.android.commons.core.collections.sequence;

import com.mydesignerclothing.mobile.android.commons.core.collections.Function;
import com.mydesignerclothing.mobile.android.commons.core.collections.MapFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.ReduceFunction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class EmptyMapSeqLike<K, V> implements SeqLike<Map, Map.Entry<K, V>> {

  @Override
  public <R> Map map(MapFunction<Map.Entry<K, V>, R> function) {
    return Collections.emptyMap();
  }

  @Override
  public <R> R reduce(R initialValue, ReduceFunction<R, Map.Entry<K, V>> function) {
    return initialValue;
  }

  @Override
  public void each(Function<Map.Entry<K, V>> function) {
  }

  @Override
  public SeqLike<Map, Map.Entry<K, V>> empty() {
    return new MapSeqLike<>(new HashMap<K, V>());
  }

  @Override
  public SeqLike<Map, Map.Entry<K, V>> add(Map.Entry<K, V> element) {
    final HashMap<K, V> kvHashMap = new HashMap<K, V>();
    kvHashMap.put(element.getKey(), element.getValue());
    return new MapSeqLike<>(kvHashMap);
  }

  @Override
  public Map unwrap() {
    return new HashMap();
  }
}
