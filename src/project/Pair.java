package project;

import java.util.Map.Entry;

class Pair<K, V> implements Entry<K, V> {
  private final K innerKey;
  private V innerValue;

  Pair(K key, V value) {
    this.innerKey = key;
    this.innerValue = value;
  }

  public K getKey() {
    return this.innerKey;
  }

  public V getValue() {
    return this.innerValue;
  }

  public V setValue(V value) {
    V previousVal = this.innerValue;
    this.innerValue = value;
    return previousVal;
  }
}