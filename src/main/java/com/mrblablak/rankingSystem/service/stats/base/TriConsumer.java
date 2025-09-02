package com.mrblablak.rankingSystem.service.stats.base;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);
}
