package com.example.super_prefser_library;

import java.lang.reflect.Type;

public interface JsonConverter {
  <T> T fromJson(String json, Type typeOfT);

  <T> String toJson(T object, Type typeOfT);
}
