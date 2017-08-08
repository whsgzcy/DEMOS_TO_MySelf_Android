package com.example.super_prefser_library;

class Preconditions {

  private Preconditions() {
  }

  static void checkNotNull(final Object object, final String message) {
    if (object == null) {
      throw new NullPointerException(message);
    }
  }
}
