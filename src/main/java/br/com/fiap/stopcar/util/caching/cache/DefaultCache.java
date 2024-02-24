package br.com.fiap.stopcar.util.caching.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class DefaultCache implements Cache {
    private final CaffeineCache cache;

    public DefaultCache(final String name, final int expiration) {
        this.cache = new CaffeineCache(name, Caffeine.newBuilder().expireAfterWrite((long)expiration, TimeUnit.SECONDS).build(), false);
    }

    public ValueWrapper get(final Object key) {
        return this.cache.get(key);
    }

    public void put(final Object key, final Object value) {
        if (value != null) {
            this.cache.put(key, value);
        }

    }

    public void evict(final Object key) {
        this.cache.evict(key);
    }

    public void clear() {
        this.cache.clear();
    }

    public String getName() {
        return this.cache.getName();
    }

    public Object getNativeCache() {
        return this.cache;
    }

    public <T> T get(Object key, Class<T> type) {
        return this.cache.get(key, type);
    }

    public <T> T get(Object key, Callable<T> valueLoader) {
        return this.cache.get(key, valueLoader);
    }

    public ValueWrapper putIfAbsent(Object key, Object value) {
        return this.cache.putIfAbsent(key, value);
    }
}

