package com.jloved.example;

import java.util.LinkedHashMap;
import java.util.Map;

interface LRUCache<E, T> {
    T get(E key);

    void put(E key, T value);
}

public class LRUCacheImpl<E, T> implements LRUCache<E, T> {
    private LinkedHashMap<E, T> cache = null;

    public LRUCacheImpl(int count) {
        this.cache = new LinkedHashMap<E, T>(count + 1, 1, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > count;
            }
        };
    }

    @Override
    public T get(E key) {
        return cache.get(key);
    }

    @Override
    public void put(E key, T value) {
        cache.put(key, value);
    }

    /**
     * 测试代码
     *
     * @param args
     */
    public static void main(String[] args) {
        LRUCache<Integer, Integer> lruCache = new LRUCacheImpl<>(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        System.out.println(lruCache.get(1));
        lruCache.put(3, 3);
        System.out.println(lruCache.get(2));
        lruCache.put(4, 4);
        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4));
    }
}
