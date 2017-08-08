package com.example.super_prefser_library;

import java.util.Map;

interface AccessorsProvider {
  Map<Class<?>, Accessor<?>> getAccessors();
}
