package br.com.personalportifolio.brewer.configuration;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CidadeCacheConfig {

    @Bean
    public CacheManager caffeineCacheManager() {
        var cacheManager = new CaffeineCacheManager("cidades");
        cacheManager.setCaffeine(caffeineCacheBuilder());

        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(3)
                .expireAfterAccess(5, TimeUnit.MINUTES);
    }

}
