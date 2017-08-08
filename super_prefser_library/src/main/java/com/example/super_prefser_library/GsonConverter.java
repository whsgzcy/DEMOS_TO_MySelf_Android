package com.example.super_prefser_library;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public final class GsonConverter implements JsonConverter {

  private final Gson gson = new Gson();

  @Override public <T> T fromJson(String json, Type typeOfT) {
    return gson.fromJson(json, typeOfT);
  }

  @Override public <T> String toJson(T object, Type typeOfT) {
    return gson.toJson(object, typeOfT);
  }
}
