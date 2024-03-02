package br.com.fiap.stopcar.application.config.cache;

import br.com.fiap.stopcar.domain.constants.CacheConstants;
import br.com.fiap.stopcar.util.caching.annotation.EnableMemcached;
import br.com.fiap.stopcar.util.caching.factory.CacheFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableMemcached
public class CacheConfiguration {
    private static final int THIRTY_MINUTES = 1800;
    @Bean
    public CacheManager cacheManager(final List<Cache> caches) {
        final SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }

    @Bean(CacheConstants.FIND_RESERVATION_BY_ID)
    public Cache getDefaultCacheFindAllReservations(final CacheFactory cacheFactory) {
        return cacheFactory.getCache(CacheConstants.FIND_RESERVATION_BY_ID, THIRTY_MINUTES);
    }

}
