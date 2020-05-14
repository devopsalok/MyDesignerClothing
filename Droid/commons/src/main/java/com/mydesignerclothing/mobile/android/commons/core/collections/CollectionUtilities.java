package com.mydesignerclothing.mobile.android.commons.core.collections;


import com.mydesignerclothing.mobile.android.commons.core.collections.sequence.SeqLike;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CollectionUtilities {

  @SuppressWarnings("EmptyMethod")
  private CollectionUtilities() {
    //NO SONAR
  }

  public static <E> void each(Function<E> function, List<E> list) {
    each(function, Seqs.of(list));
  }

  public static <E> boolean every(Predicate<E> predicate, List<E> list) {
    for (E element : list) {
      if(!predicate.match(element))
        return false;
    }
    return true;
  }

  public static <E> int count(Predicate<E> predicate, List<E> list) {
    return filter(predicate, list).size();
  }

  public static <E, R> List<R> map(final MapFunction<E, R> function, List<E> list) {
    return map(function, Seqs.of(list));
  }

  public static <E, R> R reduce(final ReduceFunction<R, E> function, R initialValue, List<E> list) {
    return reduce(function, initialValue, Seqs.of(list));
  }

  public static <E> List<E> filter(final Predicate<E> predicate, final List<E> list) {
    return filter(predicate, Seqs.of(list));
  }

  public static <E> List<E> filter(final Predicate<E> predicate, final Iterable<E> iterable) {
    return filter(predicate, Seqs.of(iterable));
  }

  public static <E> List<E> add(E element, List<E> list) {
    return add(element, Seqs.of(list));
  }

  public static <K, V> void each(final Function<Map.Entry<K, V>> function, final Map<K, V> map) {
    each(function, Seqs.of(map));
  }

  public static <K, V, K2, V2> Map<K2, V2> map(final MapFunction<Map.Entry<K, V>, Map.Entry<K2, V2>> function, final Map<K, V> map) {
    return map(function, Seqs.of(map));
  }

  public static <K, V, R> R reduce(final ReduceFunction<R, Map.Entry<K, V>> function, final R initialValue, final Map<K, V> map) {
    return reduce(function, initialValue, Seqs.of(map));
  }

  public static <K, V> Map<K, V> filter(final Predicate<Map.Entry<K, V>> predicate, final Map<K, V> map) {
    return filter(predicate, Seqs.of(map));
  }

  public static <K, V> Map<K, V> add(Map.Entry<K, V> element, Map<K, V> map) {
    return add(element, Seqs.of(map));
  }

  public static <T, E> void each(final Function<E> function, final SeqLike<T, E> seq) {
    seq.each(function);
  }

  public static <T, E, R, TR> TR map(final MapFunction<E, R> function, final SeqLike<T, E> seq) {
    return (TR) seq.map(function);
  }

  public static <T, E, R> R reduce(final ReduceFunction<R, E> function, final R initialValue, final SeqLike<T, E> seq) {
    return seq.reduce(initialValue, function);
  }

  public static <T, E> T reduce(final ReduceFunction<T, E> function, final SeqLike<T, E> seq) {
    return seq.reduce(seq.empty().unwrap(), function);
  }

  public static <T, E> T filter(final Predicate<E> predicate, final SeqLike<T, E> seq) {
    return seq.reduce(seq.empty(), (element, currentResult) -> {
      if (predicate.match(element)) {
        currentResult.add(element);
      }
      return currentResult;
    }).unwrap();
  }

  public static <T, E> T add(E element, SeqLike<T, E> seq) {
    return seq.add(element).unwrap();
  }

  public static <E> List<List<E>> split(SplitPredicate<E> predicate, List<E> collection) {
    List<List<E>> list = new ArrayList<>();
    List<E> currList;
    Iterator<E> iterator = collection.iterator();
    if (iterator.hasNext()) {
      currList = new ArrayList<>();
      list.add(currList);
      E curr = iterator.next();
      currList.add(curr);
      while (iterator.hasNext()) {
        E previous = curr;
        if (iterator.hasNext()) {
          curr = iterator.next();
          if (predicate.match(previous, curr)) {
            currList = new ArrayList<>();
            list.add(currList);
          }
          currList.add(curr);
        }
      }
    }
    return list;
  }

  public static <E> void each(Function<E> function, Iterable<E> iterable) {
    each(function, Seqs.of(iterable));
  }

  /**
   * @deprecated  use  {@link #firstElement(List)} instead
   */
  @Deprecated //use firstElement instead
  public static <E> E first(List<E> collection) {
    return collection == null || collection.isEmpty() ? null : collection.iterator().next();
  }

  public static <E> Optional<E> firstElement(List<E> list) {
    return list == null || list.isEmpty() ? Optional.<E>absent() : Optional.of(list.iterator().next());
  }

  /**
   * @deprecated  use  {@link #lastElement(List)} instead
   */
  @Deprecated //use lastElement instead
  public static <E> E last(List<E> collection) {
    return collection.isEmpty() ? null : collection.get(collection.size() - 1);
  }

  public static <E> Optional<E> lastElement(List<E> list) {
    return list == null || list.isEmpty() ? Optional.<E>absent() : Optional.of(list.get(list.size() - 1));
  }

  public static <E> String join(final String separator, List<E> list) {
    return join(separator, Seqs.of(list));
  }

  public static <T, E> String join(final String separator, SeqLike<T, E> list) {
    return reduce((element, currentResult) -> {
      if (currentResult.isEmpty()) return element.toString();
      return currentResult + separator + element.toString();
    }, "", list);
  }

  public static <E> E search(Predicate<E> predicate, List<E> collection) {
    if(collection==null) return null;

    for (E object : collection) {
      if (predicate.match(object)) {
        return object;
      }
    }
    return null;
  }

  public static <E> int indexOf(Predicate<E> predicate, List<E> collection) {
    for (int index = 0; index < collection.size(); index++) {
      if (predicate.match(collection.get(index))) {
        return index;
      }
    }
    return -1;
  }

  public static <E> Optional<E> find(final Predicate<E> predicate, final List<E> list) {
    final List<E> filterItems = filter(predicate, Seqs.of(list));
    return (filterItems.isEmpty()) ? Optional.absent() : Optional.of(filterItems.iterator().next());
  }

  public static <E> boolean has(Predicate<E> predicate, List<E> collection) {
    return search(predicate, collection) != null;
  }

  public static <K, V> Map.Entry<K, V> entry(K key, V value) {
    return new Entry<>(key, value);
  }

  public static <K, V> Map.Entry<K, V> pair(K key, V value) {
    return new Entry<>(key, value);
  }

  public static <K, V, E extends Map.Entry<K, V>> Map<K, V> merge(Map<K, V> map, E... entries) {
    return merge(map, hash(entries));
  }

  public static <K, V> Map<K, V> merge(Map<K, V> map, Map<K, V> other) {
    map.putAll(other);
    return filter(entry -> !(entry.getValue() instanceof ToBeRemoved), map);
  }

  public static <E> List<E> flatten(List<List<E>> listOfList) {
    ArrayList<E> flattenedList = new ArrayList<>();
    for (List<E> list : listOfList) {
      flattenedList.addAll(list);
    }

    return flattenedList;
  }

  public static <K> List<K> minus(List<K> a, List<K> b) {
    List<K> diff = new ArrayList<>();
    for (K k : a) {
      if(!b.contains(k)) {
        diff.add(k);
      }
    }
    return diff;
  }

  public static <K, V, E extends Map.Entry<K, V>> Map<K, V> hash(E... entries) {
    Map<K, V> map = new HashMap<>(entries.length);
    for (Map.Entry<K, V> entry : entries) {
      map.put(entry.getKey(), entry.getValue());
    }
    return map;
  }

  public static ToBeRemoved toBeRemoved() {
    return new ToBeRemoved();
  }

  public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
    for (Map.Entry<T, E> entry : map.entrySet()) {
      if (value.equals(entry.getValue())) {
        return entry.getKey();
      }
    }
    return null;
  }

  public static <T> HashSet<T> filterNullValuesFromSet(Set<T> set) {
    @SuppressWarnings("EmptyClass") HashSet<T> filteredHashSet = new HashSet<T>() {
    };
    for(T object : set){
      if(object != null)
        filteredHashSet.add(object);
    }
    return filteredHashSet;
  }

  /**
   * A guard method to protect against null values in for-each statements.
   * @param check An iterable of some kind
   * @param <T> The type of the iterable
   * @return The check value or an empty iterable.
   */
  @SuppressWarnings("unchecked")
  public static <T> Iterable<T> neverNull(Iterable<T> check){
    return check == null ? (Iterable<T>) Collections.emptyList() : check;
  }

  @SuppressWarnings("EmptyClass")
  public static class ToBeRemoved {
  }

  public static class Entry<K, V> implements Map.Entry<K, V> {

    private K key;
    private V value;

    public Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public K getKey() {
      return key;
    }

    @Override
    public V getValue() {
      return value;
    }

    @Override
    public V setValue(V v) {
      value = v;
      return value;
    }

    @Override
    public String toString() {
      return "Entry{" +
        "key=" + key +
        ", value=" + value +
        '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Entry<?, ?> entry = (Entry<?, ?>) o;

      if (key != null ? !key.equals(entry.key) : entry.key != null) return false;
      return value != null ? value.equals(entry.value) : entry.value == null;

    }

    @Override
    public int hashCode() {
      int result = key != null ? key.hashCode() : 0;
      result = 31 * result + (value != null ? value.hashCode() : 0);
      return result;
    }
  }

  public static <K extends GenericComparableSorter<T>, T, V extends Comparable<V>> void genericAscDescSorter( List<K> acComparables, final boolean isDescending) {

    Collections.sort(acComparables, (sortObject1, sortObject2) -> {
      V sortField1 = (V) sortObject1.getValue();
      V sortField2 = (V) sortObject2.getValue();
      if (sortField1 == null && sortField2 == null){
        return 0;
      }
      if (sortField1 == null) {
        return -1;
      }
      if (sortField2 == null) {
        return 1;
      }
      if (isDescending){
        return sortField2.compareTo(sortField1);
      }
      return sortField1.compareTo(sortField2);
    });
  }

  public static <T> boolean isEmpty(Iterable<T> input){
    return neverNull(input) == Collections.emptyList();
  }
}
