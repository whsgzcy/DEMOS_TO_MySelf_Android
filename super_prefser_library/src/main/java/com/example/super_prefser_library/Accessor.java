package com.example.super_prefser_library;

interface Accessor<T> {
  T get(String key, T defaultValue);

  void put(String key, T value);
}
