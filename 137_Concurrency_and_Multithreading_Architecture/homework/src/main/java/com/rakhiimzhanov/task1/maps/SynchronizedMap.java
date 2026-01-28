package com.rakhiimzhanov.task1.maps;

import java.util.HashMap;
import java.util.function.BiFunction;

public class SynchronizedMap<K, V> extends HashMap<K, V> {
    @Override
    public synchronized V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public synchronized V get(Object key) {
        return super.get(key);
    }

    @Override
    public synchronized V remove(Object key) {
        return super.remove(key);
    }

    @Override
    public synchronized int size() {
        return super.size();
    }

    @Override
    public synchronized V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        // Synchronized - entire compute operation is atomic
        return super.compute(key, remappingFunction);
    }
}