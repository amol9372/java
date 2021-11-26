package com.expensetracker.trackerapp.builder;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class BaseBuilder {

  public static <T> Stream<T> stream(Collection<T> collection) {
    return Optional.ofNullable(collection)
        .stream()
        .flatMap(Collection::stream);
  }

}
