package br.com.fiap.stopcar.util.caching.factory;

import br.com.fiap.stopcar.util.caching.cache.DefaultCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class CacheFactory {
    public CacheFactory() {
    }

    public Cache getCache(final String name, final int expiration) {
        return new DefaultCache(name, expiration);
    }
}
