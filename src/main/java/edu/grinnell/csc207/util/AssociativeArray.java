package edu.grinnell.csc207.util;

import java.util.Arrays;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 * @param <K> the key type
 * @param <V> the value type
 * @author Sal Karki
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V>[] pairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating an array of KVPair instances
    this.pairs = (KVPair<K, V>[]) new KVPair[DEFAULT_CAPACITY];
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * clones the associative array.
   * @return a cloned version of the associatve array.
   */
  @SuppressWarnings("unchecked")
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> copy = new AssociativeArray<>();
    // Allocate the pairs array with the same size as the original
    copy.pairs = (KVPair<K, V>[]) new KVPair[this.size]; // Allocate new array for pairs
    for (int i = 0; i < this.size; i++) {
      copy.pairs[i] = this.pairs[i].clone(); // Deep copy of KVPair
    } // for
    copy.size = this.size; // Set the size of the new array
    return copy;
  } // clone()

  /**
   * Convert the array to a string.
   * @return a string of the form "{Key:Value, Key:Value, etc}
   */
  public String toString() {
    String result = "{";
    for (int i = 0; i < this.size; i++) {
      result += this.pairs[i].toString();
      if (i < this.size - 1) {
        result += ", ";
      } // if
    } // for
    result += "}";
    return result;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   * @param key The key whose value we are setting.
   * @param value The value of that key.
   * @throws NullKeyException if key is null.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null) {
      throw new NullKeyException();
    } // if
    try {
      // Try to find the key and update the value.
      int index = find(key);
      pairs[index].val = value; // Update existing value.
    } catch (KeyNotFoundException e) {
      // If the key doesn't exist, add it to the array.
      if (this.size >= this.pairs.length) {
        expand();
      } // if
      pairs[this.size++] = new KVPair<>(key, value); // Add new key-value pair.
    } // try/catch
  } // set()

  /**
   * Get the value associated with key.
   * @param key Given key.
   * @return The corresponding value.
   * @throws KeyNotFoundException when the key is null/doesnt exist.
   */
  public V get(K key) throws KeyNotFoundException {
    int index = find(key);
    return pairs[index].val;
  } // get(K)

  /**
   * Determine if key appears in the associative array.
   * @param key The key we are trying to find
   * @return true if the key appears and false otherwise.
   */
  public boolean hasKey(K key) {
    try {
      find(key);
      return true;
    } catch (KeyNotFoundException e) {
      return false;
    } // try/catch
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key.
   * @param key The key (and value) we want to remove.
   */
  public void remove(K key) {
    try {
      int index = find(key);
      pairs[index] = pairs[--this.size];
      pairs[this.size] = null;
    } catch (KeyNotFoundException e) {
      // Do nothing if the key is not found.
    } // try/catch
  } // remove(K)

  /**
   * Find number of pairs in the associative array.
   * @return The number of pairs.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the array (double size).
   */
  void expand() {
    this.pairs = Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the pair associated with the given key.
   * @param key The key we are trying to find.
   * @return The index of the key.
   * @throws KeyNotFoundException if the key does not exist.
   */
  int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < this.size; i++) {
      if (pairs[i].key.equals(key)) {
        return i;
      } // if
    } // for
    throw new KeyNotFoundException();
  } // find(K)
} // class AssociativeArray
